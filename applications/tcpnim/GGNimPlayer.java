// GGNimPlayer.java
// See GGNimServer.java for the design principles

import ch.aplu.jgamegrid.*;
import java.util.*;
import ch.aplu.tcp.*;
import javax.swing.JOptionPane;
import java.awt.*;

public class GGNimPlayer extends GameGrid
  implements GGMouseTouchListener, GGButtonListener
{
  // ------------------- Inner class MyTcpAgentAdapter -------------
  private class MyTcpAgentAdapter implements TcpAgentListener
  {
    private boolean isReady = true;

    public void notifyRelayConnection(boolean connected)
    {
    }

    public void notifyAgentConnection(String agentName, boolean connected)
    {
    }

    public void notifyBridgeConnection(boolean connect)
    {
      if (!connect)
      {
        setStatusText("Game server disconnected. Game terminated.");
        okBtn.setEnabled(false);
        isMouseEnabled = false;
      }
    }

    public void dataReceived(String source, int[] data)
    {
      if (!isReady)
        return;

      String str = "";
      switch (data[0])
      {
        case Command.GAME_RUNNING:
          setStatusText("Game in process.\nPlease wait for next game.\n"
            + "Terminating now...");
          TcpTools.delay(5000);
          System.exit(0);
          break;

        case Command.PLAYER_DISCONNECT:
          disconnectedPlayer = TcpTools.intAryToString(data, 1);
          break;

        case Command.NUMBER_OF_PLAYERS:
          initPlayground();
          if (!disconnectedPlayer.equals(""))
            disconnectedPlayer = "Disconnected: " + disconnectedPlayer + ". ";
          setStatusText(disconnectedPlayer
            + "Current number in group: " + data[1]
            + ". Waiting for more players to join...");
          disconnectedPlayer = "";
          break;

        case Command.GAME_STARTING:
          str = "Group complete. In group: ";
          String[] names =
            TcpTools.split(TcpTools.intAryToString(data, 1), "&&");
          for (int i = 0; i < names.length; i++)
          {
            if (i < names.length - 1)
              str += names[i] + "; ";
            else
              str += names[i];
          }
          setStatusText(str + ". Wait start selection...");
          break;

        case Command.INIT_PLAYGROUND:
          nbMatches = data[1];
          initPlayground();
          break;

        case Command.REPORT_POSITION:
          Location loc = new Location(data[1], 4);
          getOneActorAt(loc).removeSelf();
          nbMatches--;
          setStatusText(nbMatches + " left. " + remotePlayer + " is playing.");
          refresh();
          break;

        case Command.LOCAL_MOVE:
          activate();
          setStatusText(nbMatches + " left. It's you to play.");
          nbTaken = 0;
          okBtn.setEnabled(true);
          isMouseEnabled = true;
          break;

        case Command.REMOTE_MOVE:
          remotePlayer = TcpTools.intAryToString(data, 1);
          setStatusText(nbMatches + " left. " + remotePlayer + " will play.");
          break;

        case Command.WINNER:
          String loser = TcpTools.intAryToString(data, 1);
          setStatusText("Game over. Player " + loser + " lost the game.");
          break;

        case Command.LOSER:
          setStatusText("Game over. You lost the game.");
          break;
      }
    }
  }

  // --------------- Inner class Match --------------
  private class Match extends Actor
  {
    public Match()
    {
      super("sprites/match.gif");
    }
  }
  // ------------------- End of inner class ------------------------
  //
  private final static String serverName = "NimServer";
  private String agentName;
  private String remotePlayer = "";
  private static String sessionID = "14dfhqh/6asd&%";
  private String disconnectedPlayer = "";
  private int nbMatches;
  private GGBackground bg;
  private GGButton okBtn = new GGButton("sprites/ok.gif", true);
  private int nbTaken;
  private volatile boolean isMouseEnabled;
  private TcpAgent agent;

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

  public GGNimPlayer()
  {
    super(56, 9, 12, false);
    sessionID = sessionID
      + requestEntry("Enter a unique session ID (ASCII >3 chars):");
    agent = new TcpAgent(sessionID, serverName);
    agentName = requestEntry("Enter your name (ASCII >3 chars):");
    initGameWindow();
    addStatusBar(30);
    setStatusText("Connecting to relay " + agent.getRelay() + "...");
    agent.addTcpAgentListener(new MyTcpAgentAdapter());
    ArrayList<String> connectList =
      agent.connectToRelay(agentName, 6000);
    if (connectList.isEmpty())
    {
      setStatusText("Connection to relay failed. Terminating now...");
      TcpTools.delay(3000);
      System.exit(1);
    }
    setStatusText("Connection established. Personal name: " + connectList.get(0));
    setTitle("Player's name: " + connectList.get(0));
    // Game server must be up and running
    if (!agent.isBridgeConnected())
    {
      setStatusText("Game server not found. Terminating now...");
      TcpTools.delay(3000);
      System.exit(1);
    }
  }

  private void initGameWindow()
  {
    bg = getBg();
    bg.clear(Color.blue);
    addActor(okBtn, new Location(50, 4));
    okBtn.addButtonListener(this);
    show();
  }

  private void initPlayground()
  {
    okBtn.setEnabled(false);
    isMouseEnabled = false;
    removeActors(Match.class);
    for (int i = 0; i < nbMatches; i++)
    {
      Match match = new Match(); 
      addActor(match, new Location(2 + 3 * i, 4));
      match.addMouseTouchListener(this, GGMouse.lClick);
    }
  }

  public void mouseTouched(Actor actor, GGMouse mouse, Point pix)
  {
    if (!isMouseEnabled)
      return;
    if (nbTaken == 3)
      setStatusText("Take a maximum of 3. Click 'OK' to continue");
    else
    {
      agent.sendCommand(
        agentName, Command.REPORT_POSITION, actor.getLocation().x);
      actor.removeSelf();
      nbMatches--;
      setStatusText(nbMatches + " matches remaining. Click 'OK' to continue");
      nbTaken++;
      refresh();
    }
  }

  public void buttonClicked(GGButton button)
  {
    if (nbTaken == 0)
      setStatusText("You have to remove at least 1 match");
    else
    {
      agent.sendCommand(agentName, Command.REPORT_OK);
      okBtn.setEnabled(false);
      isMouseEnabled = false;
    }
  }

  public void buttonPressed(GGButton button)
  {
  }

  public void buttonReleased(GGButton button)
  {
  }

  private String requestEntry(String prompt)
  {
    String entry = "";
    while (entry.length() < 1)
    {
      entry = JOptionPane.showInputDialog(null, prompt, "");
      if (entry == null)
        System.exit(0);
    }
    return entry.trim();
  }

  public static void main(String[] args)
  {
    new GGNimPlayer();
  }
}
