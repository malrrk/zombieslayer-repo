// Buddy.java
// Player with embedded server

import ch.aplu.tcp.*;
import ch.aplu.util.*;
import javax.swing.JOptionPane;
import java.util.*;

public class Buddy
{
  protected static final int NB_PLAYERS = 3;
  private final boolean debug = true;
  private BuddyServer buddyServer;
  private final String serverName = "BuddyServer";
  private String sessionID = "7%=iaMM7as%*/&)";
  private ModelessOptionPane mop;
  private TcpAgent agent;
  private String[] playerNames = null;
  private String currentPlayerName;

  // Protocol tags
  public static interface Command
  {
    int REPORT_NAMES = 0;
    int DISCONNECT = 1;
  }

  private class MyTcpAgentAdapter extends TcpAgentAdapter
  {
    public void dataReceived(String source, int[] data)
    {
      switch (data[0])
      {
        case Command.REPORT_NAMES:
          playerNames = TcpTools.split(TcpTools.intAryToString(data, 1), ",");
          break;

        case Command.DISCONNECT:
          String player = TcpTools.intAryToString(data, 1);
          agent.disconnect();
          buddyServer.disconnect();
          mop.setText(player + " disconnected. Game stopped.");
          break;
      }
    }

    public void notifyBridgeConnection(boolean connected)
    {
      if (!connected)
      {
        mop.setText("Buddy with game server disconnected. Game stopped.");
        agent.disconnect();
      }
    }
  }

  public Buddy()
  {
    String gameRoom = debug ? "123"
      : requestEntry("Enter a unique room name (ASCII 3..15 chars):");
    sessionID = sessionID + gameRoom;
    agent = new TcpAgent(sessionID, serverName);
    String requestedName = debug ? "max"
      : requestEntry("Enter your name (ASCII 3..15 chars):");
    agent.addTcpAgentListener(new MyTcpAgentAdapter());
    connect(gameRoom, requestedName);
  }

  private void connect(String gameRoom, String requestedName)
  {
    mop = new ModelessOptionPane("Trying to connect to relay...");
    mop.setTitle("Game Room: " + gameRoom);
    buddyServer = new BuddyServer(sessionID, gameRoom, serverName);
    TcpTools.delay(2000); // Let server come-up
    
    ArrayList<String> connectList = agent.connectToRelay(requestedName, 6000);
    if (connectList.isEmpty())
    {
      mop.setText("Connection to relay failed. Terminating now...");
      TcpTools.delay(3000);
      System.exit(0);
    }

    int nb = connectList.size();
    currentPlayerName = connectList.get(0);
    if (nb > NB_PLAYERS)
    {
      mop.setText("Game room full. Terminating now...");
      CardTable.delay(3000);
      System.exit(0);
    }

    if (nb < NB_PLAYERS)
      mop.setText("Connection established. Name: " + currentPlayerName
        + "\nCurrently " + nb + " player(s) in game room."
        + "\nWaiting for " + (NB_PLAYERS - nb) + " more player(s)...");

    while (playerNames == null)  // Wait until player names are reported
      TcpTools.delay(10);

    mop.setTitle("Current player: " + currentPlayerName);
    String participants = "";
    for (int i = 0; i < playerNames.length; i++)
      participants += playerNames[i] + "; ";
    mop.setText("Game ready to start. Participants:\n" + participants);
  }

  private String requestEntry(String prompt)
  {
    String entry = "";
    while (entry.length() < 3 || entry.length() > 15)
    {
      entry = JOptionPane.showInputDialog(null, prompt, "");
      if (entry == null)
        System.exit(0);
    }
    return entry.trim();
  }

  public static void main(String[] args)
  {
    new Buddy();
  }
}
