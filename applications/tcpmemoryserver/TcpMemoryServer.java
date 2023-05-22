// TcpMemoryServer.java
// Used with TcpMemory.java

import ch.aplu.tcp.*;
import ch.aplu.util.*;
import java.util.*;
import java.text.*;
import javax.swing.JOptionPane;

public class TcpMemoryServer extends TcpBridge implements TcpBridgeListener
{
  private final static String version = "1.00";
  private final static String sessionID = "select_a_very_long_ID";
  private final static String myName = "MemoryServer";
  private int nbCells = 16;  // Must be even
  private int[] state = new int[nbCells];
  private final int restartDelay = 10000;  // 10 s
  private static Console c;
  // Hashtable to manage game. Key: roomName, Value: Player's nickname list
  private Hashtable<String, ArrayList<String>> rooms =
    new Hashtable<String, ArrayList<String>>();
  private final Object monitor = new Object();


  public TcpMemoryServer()
  {
    super(sessionID, myName);
    addTcpBridgeListener(this);
    c = Console.init(new Position(0, 0), new Size(500, 600));
    c.setTitle("Memory Server V" + version);
    c.println("Memory server started on " + now());
    c.println("Connecting to relay " + getRelay() + "...");
    ArrayList<String> connectList = connectToRelay(6000);
    if (connectList.isEmpty())
      errorQuit("Connection failed");
    if (!connectList.get(0).equals(myName))
      errorQuit("Only one instance of server allowed");
    c.println("Connection established. Service enabled.");
  }

  private void errorQuit(String msg)
  {
    c.println(msg);
    System.exit(1);
  }

  private static String requestEntry(String prompt)
  {
    String entry = "";
    while (entry.length() < 3)
    {
      entry = JOptionPane.showInputDialog(null, prompt, "");
      if (entry == null)
        System.exit(0);
    }
    return entry.trim();
  }

  private void dealingOut(ArrayList<String> players)
  {
    synchronized (monitor)
    {
      // Distribute numbers 0..nbCells arbitrarily into state
      // First put 16 numbers into a list
      ArrayList<Integer> numbers = new ArrayList<Integer>();
      for (int i = 0; i < nbCells; i++)
        numbers.add(i);
      // Now take out one by one at arbitrary location and put it into state
      int k = 0;
      while (!numbers.isEmpty())
      {
        int index = (int)(Math.random() * numbers.size());
        state[k] = numbers.get(index);
        numbers.remove(index);
        k++;
      }

      int[] data = new int[17];
      data[0] = Command.GAME_INIT;
      for (int i = 0; i < 16; i++)
        data[i + 1] = state[i];
      send(myName, players.get(0), data);
      send(myName, players.get(1), data);
    }
  }

  public void notifyRelayConnection(boolean connected)
  {
  }

  public void notifyAgentConnection(String agentName, boolean connected)
  {
    if (!connected)
      removePlayer(agentName);
  }

  private void removePlayer(String agentName)
  {
    synchronized (monitor)
    {
      Enumeration e = rooms.keys();
      while (e.hasMoreElements())
      {
        String roomName = (String)e.nextElement();
        ArrayList<String> players = rooms.get(roomName);
        int nb = players.size();

        if (nb == 1)  // One waiting player
        {
          rooms.remove(roomName);
          c.println("Player " + agentName + " and room " + roomName + " removed.");
          return;
        }

        if (nb == 2)  // Game in progress
        {
          for (int i = 0; i < nb; i++)
          {
            if (players.get(i).equals(agentName))
            {
              send(myName, players.get((i + 1) % 2), Command.DISCONNECTED);
              players.remove(agentName);
              c.println("Player " + agentName + " in room " + roomName + " removed.");
              return;
            }
          }
        }
      }
    }
  }

  public void pipeRequest(final String source, String destination, int[] indata)
  {
    // ------------------- REQUEST_GAME_ENTRY ------------------------
    if (indata[0] == Command.REQUEST_GAME_ENTRY)
    {
      String roomName = TcpTools.intAryToString(indata, 1);
      c.println("Player " + source + " requests entry in room: " + roomName);
      ArrayList<String> players = rooms.get(roomName);
      if (players == null)  // No player in room
      {
        c.println("First player in room");
        players = new ArrayList<String>();
        players.add(source);
        rooms.put(roomName, players);
        send(myName, source, Command.WAIT_REMOTE_CONNECT);
        return;
      }
      if (players.size() == 2)
      {
        c.println("Too many players. Game in process.");
        send(myName, source, Command.TOO_MANY_PLAYERS);
        return;
      }
      if (players.size() == 1)
      {
        c.println("Player " + players.get(0) + " waiting in room");
        c.println("Game starting");
        players.add(source);
        rooms.put(roomName, players);

        // We transmit names of players
        String player0 = players.get(0);
        String player1 = players.get(1);
        c.println("Following players in game:");
        c.println("First player: " + player0);
        c.println("Second player: " + player1);

        String text =
          "'" + TcpTools.stripNickname(player0)
          + "' (you) against '"
          + TcpTools.stripNickname(player1) + "'";
        sendCommand(myName, player0, Command.PLAYER_NAMES,
          TcpTools.stringToIntAry(text));

        text = "'" + TcpTools.stripNickname(player1)
          + "' (you) against '"
          + TcpTools.stripNickname(player0) + "'";
        sendCommand(myName, player1, Command.PLAYER_NAMES,
          TcpTools.stringToIntAry(text));

        // We transmit who begins
        send(myName, player0, Command.LOCAL_MOVE); // First player begins
        send(myName, player1, Command.REMOTE_MOVE);

        // We transmit new game state
        c.println("Dealing out now.");
        dealingOut(players);
        return;
      }
    }

    final String dest = getPartner(source);
    final String roomName = getRoomName(source);

    // ------------------- GAME_OVER ---------------------------------
    if (indata[0] == Command.GAME_OVER)  // Reported from player with last hit
    {
      final int sourceScore = indata[1];
      final int destScore = indata[2];
      c.println("Game over in room '" + roomName + "'");
      c.println("Score:-- " + source + ": " + sourceScore
        + "; " + dest + ": " + destScore);
      c.println("Restarting in " + restartDelay + " s");
      // Must run in an own thread because pipeRequest should not be blocked
      new Thread()
      {
        public void run()
        {
          TcpTools.delay(restartDelay);
          synchronized (monitor)  // Must avoid disconnection
          {
            if (getRoomName(source).equals(roomName)
              && getRoomName(dest).equals(roomName))
            // both still in room
            {
              ArrayList<String> players = new ArrayList<String>();
              players.add(source);
              players.add(dest);
              dealingOut(players);
              if (sourceScore <= destScore)  // Loser or last hitter restarts
              {
                send(myName, source, Command.LOCAL_MOVE);
                send(myName, dest, Command.REMOTE_MOVE);
              }
              else
              {
                send(myName, dest, Command.LOCAL_MOVE);
                send(myName, source, Command.REMOTE_MOVE);
              }
            }
          }
        }
      }.start();
      return;
    }

    // -------- All other cases: just pass the data to destination ---
    send(source, dest, indata);
    reportPipeAction(roomName, source, dest, indata);
  }

  private String getPartner(String player)
  // Returns empty string if player not found or if he has no partner
  {
    Enumeration e = rooms.keys();
    while (e.hasMoreElements())
    {
      String roomName = (String)e.nextElement();
      ArrayList<String> players = rooms.get(roomName);
      if (players.size() < 2)  // Only one player in room
        continue;
      for (int i = 0; i < 2; i++)
      {
        if (players.get(i).equals(player))
          return players.get((i + 1) % 2);
      }
    }
    return "";
  }

  private String getRoomName(String player)
  // Returns empty string if player not found in any room
  {
    Enumeration e = rooms.keys();
    while (e.hasMoreElements())
    {
      String roomName = (String)e.nextElement();
      ArrayList<String> players = rooms.get(roomName);
      for (int i = 0; i < players.size(); i++)
      {
        if (players.get(i).equals(player))
          return roomName;
      }
    }
    return "";

  }

  private void reportPipeAction(String roomName, String source, String dest, int[] data)
  {
    c.println("Pipe action in room '" + roomName + "' :"
      + source + "-->" + dest);
    c.print("data: [");
    for (int i = 0; i < data.length; i++)
    {
      c.print(data[i]);
      if (i < data.length - 1)
        c.print(", ");
    }
    c.println("]");
  }

  static String now()
  {
    String DATE_FORMAT_NOW;
    DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
    Calendar cal = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
    synchronized (sdf)
    {
      return sdf.format(cal.getTime());
    }
  }

  public static void main(String[] args)
  {
    new TcpMemoryServer();
  }
}
