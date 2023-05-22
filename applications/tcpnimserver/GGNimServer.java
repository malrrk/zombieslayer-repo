// GGNimServer.java

/* Design principles:
 * - It gives a simpler design if the game server must be running
 *   before any client connects to the relay
 * - The server decides how many matches are present when the game starts
 *   (the graphics is not yet adapted so support different numbers)
 * - The server and every player manage the number of remaining
 *   matches individually
 * - The win/lost situation is transmitted from the server
 * - The server decides who starts the game, in the first round it
 *   is an arbritrary player; afterwards it is the player who lost
 * - If a player disconnects during the game, the game is terminated and
 *   the other players must wait for the completion of the group
 */
import java.util.*;
import ch.aplu.util.Console;
import ch.aplu.tcp.*;
import javax.swing.JOptionPane;

public class GGNimServer extends TcpBridge implements TcpBridgeListener
{
  private final int nbMatchesStart = 15;
  private final static String serverName = "NimServer";
  private static String sessionID = "14dfhqh/6asd&%";
  private static Console c;
  private Vector<String> players = new Vector<String>();
  private int groupSize;
  private int nbMatches;

  // Protocol tags
  private interface Command
  {
    int GAME_RUNNING = 0;
    int NUMBER_OF_PLAYERS = 1;
    int INIT_PLAYGROUND = 2;
    int GAME_STARTING = 3;
    int REMOTE_MOVE = 4;
    int LOCAL_MOVE = 5;
    int REPORT_OK = 6;
    int REPORT_POSITION = 7;
    int WINNER = 8;
    int LOSER = 9;
    int PLAYER_DISCONNECT = 10;
  }

  static
  {
    c.print("Enter a unique session ID: ");
    sessionID = sessionID + c.readLine();
  }

  public GGNimServer()
  {
    super(sessionID, serverName);

    // Request group size
    boolean ok = false;
    while (!ok)
    {
      String groupSizeStr =
        JOptionPane.showInputDialog(null,
        "Number of players in group (1..10)", "");
      if (groupSizeStr == null)
        System.exit(0);
      try
      {
        groupSize = Integer.parseInt(groupSizeStr.trim());
      }
      catch (NumberFormatException ex)
      {
      }
      if (groupSize > 0 && groupSize <= 10)
        ok = true;
    }
    c.println("Server '" + serverName + "' connecting to relay "
      + getRelay() + "...");
    addTcpBridgeListener(this);
    ArrayList<String> connectList = connectToRelay(6000);
    if (connectList.isEmpty())
      errorQuit("Connection failed (timeout reached)");
    if (!connectList.get(0).equals(serverName))
      errorQuit("An instance of '" + serverName + "' already running.");
    c.println("Connection established");
  }

  private void errorQuit(String msg)
  {
    c.println(msg);
    c.println("Shutdown now...");
    TcpTools.delay(3000);
    System.exit(1);
  }

  public void notifyRelayConnection(boolean connected)
  {
    if (!connected)
      c.println("Connection to relay broken.");
  }

  public void notifyAgentConnection(String agentName, boolean connected)
  {
    String str = "";
    if (!connected)
    {
      c.println("Player: " + agentName + " disconnected");
      if (players.contains(agentName))
      {
        players.remove(agentName);
        sendCommandToGroup(Command.PLAYER_DISCONNECT,
          TcpTools.stringToIntAry(agentName));
        sendCommandToGroup(Command.NUMBER_OF_PLAYERS, players.size());
        sendCommandToGroup(Command.INIT_PLAYGROUND, nbMatchesStart);
      }
      return;
    }

    c.println("Player: " + agentName + " connected");
    if (players.size() == groupSize)
    {
      c.println("Game already running");
      sendCommand(serverName, agentName, Command.GAME_RUNNING);
      return;
    }

    // Send starting number of matches
    sendCommand(serverName, agentName, Command.INIT_PLAYGROUND, nbMatchesStart);

    // Add player to group
    players.add(agentName);
    sendCommandToGroup(Command.NUMBER_OF_PLAYERS, players.size());

    if (players.size() == groupSize)
    {
      // Group completed. Game started.
      for (String nickname : players)
        str += nickname + "&&";
      sendCommandToGroup(Command.GAME_STARTING, TcpTools.stringToIntAry(str));
      nbMatches = nbMatchesStart;
      TcpTools.delay(5000);
      // A random player starts to play
      String startPlayer = players.get((int)(groupSize * Math.random()));
      giveMoveTo(startPlayer);
    }
  }

  private void giveMoveTo(String player)
  {
    int indexOfPlayer = players.indexOf(player);
    sendCommand(serverName, player, Command.LOCAL_MOVE);
    for (int i = 0; i < groupSize; i++)
    {
      if (i != indexOfPlayer)
        sendCommand(serverName, players.get(i), Command.REMOTE_MOVE,
          TcpTools.stringToIntAry(player));
    }
  }

  private void sendCommandToGroup(int command, int... data)
  {
    for (String nickname : players)
      sendCommand(serverName, nickname, command, data);
  }

  public void pipeRequest(String source, String destination, int[] indata)
  {
    // Check if group is still complete
    if (groupSize != players.size())
      return;
    switch (indata[0])
    {
      case Command.REPORT_POSITION:  // Pass command to other players
        c.println("Player " + source + " reports position " + indata[1]);
        nbMatches--;
        c.println("Number of remaining matches: " + nbMatches);
        for (String player : players)
        {
          if (!player.equals(source))
            sendCommand(serverName, player, Command.REPORT_POSITION, indata[1]);
        }
        break;

      case Command.REPORT_OK:
        c.println("Player " + source + " reports OK click");
        int nextPlayerIndex = (players.indexOf(source) + 1) % groupSize;
        String nextPlayer = players.get(nextPlayerIndex);
        if (nbMatches <= 0) // Game over
        {
          sendCommand(serverName, source, Command.LOSER);
          for (String player : players)
          {
            if (!player.equals(source))
              sendCommand(serverName, player, Command.WINNER,
                TcpTools.stringToIntAry(source));
          }
          TcpTools.delay(4000);
          sendCommandToGroup(Command.INIT_PLAYGROUND, nbMatchesStart);
          nbMatches = nbMatchesStart;
          giveMoveTo(source);
        }
        else
        {
          c.println("Give move to next player: " + nextPlayer);
          giveMoveTo(nextPlayer);
        }
        break;
    }
  }

  public static void main(String[] args)
  {
    new GGNimServer();
  }
}
