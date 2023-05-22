// TcpMemory.java
// Game server handles everything
// Design: See http://www.aplu.ch/tcpjlib, example 3

import javax.swing.JOptionPane;
import ch.aplu.jgamegrid.*;
import ch.aplu.tcp.*;
import java.awt.Color;
import ch.aplu.util.*;

public class TcpMemory extends GameGrid implements GGMouseListener
{
  private final String VERSION = "1.5";
  private final String serverName = "MemoryServer";
  private final static String sessionID = "select_a_very_long_ID";
  private TcpAgent tcpAgent;
  private MemoryCard firstCard;
  private MemoryCard secondCard;
  private boolean isFirstMove;
  private int myScore;
  private int rivalScore;
  private String playerNames;
  private static final int horzCells = 4;
  private static final int vertCells = 4;
  private static final int nbCells = horzCells * vertCells; // Must be even
  private MemoryCard[] cards = new MemoryCard[nbCells];
  private String roomName = "";
  private boolean isMouseEnabled =  false;

  public TcpMemory()
  {
    super(horzCells, vertCells, 115, Color.red, null, false);
    setTitle("Memory Game (V" + VERSION + ")");
    setBgColor(Color.white);
    for (int i = 0; i < nbCells; i++)
    {
      if (i < nbCells / 2)
        cards[i] = new MemoryCard(i);
      else
        cards[i] = new MemoryCard(i - nbCells / 2);
    }
    addMouseListener(this, GGMouse.lPress);
    show();
    connect();

    // Main thread is responsible to turn back cards on a bad move
    while (true)
    {
      Monitor.putSleep();  // Wait until there is something to do
      delay(2000); // Sleep a while
      firstCard.show(1);
      secondCard.show(1);
      refresh();
      tcpAgent.send("", Command.REPORT_HIDE,
        firstCard.getLocation().x, firstCard.getLocation().y);
      tcpAgent.send("", Command.REPORT_HIDE,
        secondCard.getLocation().x, secondCard.getLocation().y);
      tcpAgent.send("", Command.LOCAL_MOVE);
    }
  }

  public boolean mouseEvent(GGMouse mouse)
  {
    if (!isMouseEnabled)
      return false;
    Location location = toLocation(mouse.getX(), mouse.getY());
    MemoryCard card = (MemoryCard)getOneActorAt(location);
    if (card.getIdVisible() == 0) // Card already flipped
      return true;
    card.show(0); // Show picture
    refresh();
    // Report to partner
    tcpAgent.send("", Command.REPORT_SHOW, location.x, location.y);

    if (isFirstMove)
    {
      setStatusText("Please click on a second card!");
      isFirstMove = false;
      firstCard = card;
    }
    else
    {
      isMouseEnabled = false;  // Inhibit more than two clicks
      setStatusText("");
      isFirstMove = true;
      secondCard = card;
      if (firstCard.getId() == secondCard.getId()) // Successful move
      {
        myScore++;
        tcpAgent.send("", Command.REPORT_SCORE, myScore);
        setStatusText("Score: " + myScore + "/" + rivalScore);
        if (isGameOver())
        {
          tcpAgent.send("", Command.GAME_OVER, myScore, rivalScore);
          showFinalScore();
          return true;
        }
      }
      else // Bad move
      {
        setStatusText("Wait for partner's move!");
        Monitor.wakeUp();
      }
    }
    return true;
  }

  private boolean isGameOver()
  {
    for (int i = 0; i < nbCells; i++)
    {
      if (cards[i].getIdVisible() == 1)
        return false;
    }
    return true;
  }

  private String requestEntry(String prompt)
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

  private void connect()
  {
    addStatusBar(30);
    roomName = requestEntry("Enter the game room (ASCII > 2 chars):");
    String localName = requestEntry("Enter your name (ASCII > 2 chars):");
    tcpAgent = new TcpAgent(sessionID, serverName);
    tcpAgent.addTcpAgentListener(new TcpAgentAdapter()
    {
      public void dataReceived(String source, int[] data)
      {
        switch (data[0])
        {
          case Command.TOO_MANY_PLAYERS:
            setTitle("Room '" + roomName + "' occupated");
            setStatusText("Connected. But room occupated.");
            break;

          case Command.WAIT_REMOTE_CONNECT:
            clear();
            setTitle("Waiting for partner in room '" + roomName + "' ...");
            setStatusText("Wait for partner in room '" + roomName + "'.");
            break;

          case Command.PLAYER_NAMES:
            playerNames = TcpTools.intAryToString(data, 1);
            setTitle("In game: " + playerNames + " in room '" + roomName + "'");
            break;

          case Command.DISCONNECTED:
            clear();
            setTitle("Waiting for partner in room '" + roomName + "' ...");
            setStatusText("Partner disconnected. Wait for partner in room '"
              + roomName + "'.");
            break;

          case Command.REMOTE_MOVE:
            setStatusText("Wait for partner's move...");
            break;

          case Command.LOCAL_MOVE:
            isFirstMove = true;
            setStatusText("It's your move. Please click on two cards!");
            isMouseEnabled = true;
            break;

          case Command.GAME_INIT:
            init(data);
            break;

          case Command.REPORT_SHOW:
            showCard(data[1], data[2], true);
            break;

          case Command.REPORT_HIDE:
            showCard(data[1], data[2], false);
            break;

          case Command.REPORT_SCORE:
            rivalScore = data[1];
            if (isGameOver())
              showFinalScore();
            else
            {
              setStatusText("Score: " + myScore + "/" + rivalScore);
              tcpAgent.send("", Command.LOCAL_MOVE);
            }
            break;
        }
      }

      public void notifyBridgeConnection(boolean connected)
      {
        if (!connected)
        {
          setStatusText("Bridge disconnected. Game will terminate.");
          delay(5000);
          System.exit(0);
        }
      }
    });
    String s = "Connecting to relay...";
    setStatusText(s);
    if (tcpAgent.connectToRelay(localName, 6000).isEmpty())
    {
      setStatusText(s + "failed.");
      return;
    }
    setStatusText(s + "OK.");
    if (!tcpAgent.isBridgeConnected())
    {
      setStatusText(s + "OK. But game server not found.");
      return;
    }
    // Login successful
    tcpAgent.sendCommand("", Command.REQUEST_GAME_ENTRY,
      TcpTools.stringToIntAry(roomName));
  }

  private void showFinalScore()
  {
    String s = "";
    if (myScore > rivalScore)
      s = " (Winner)";
    if (myScore < rivalScore)
      s = " (Loser)";
    if (myScore == rivalScore)
      s = " (Tie)";
    setStatusText("Game over. Score: " + myScore + "/" + rivalScore + s
      + ". Wait a moment for next game.");
  }

  private void showCard(int x, int y, boolean show)
  {
    if (show)
      getOneActorAt(new Location(x, y)).show(0);
    else
      getOneActorAt(new Location(x, y)).show(1);
    refresh();
  }

  private void clear()
  {
    removeAllActors();
    refresh();
  }

  private void init(int[] data)
  {
    myScore = 0;
    rivalScore = 0;
    isFirstMove = true;
    removeAllActors();
    int k = 1;
    for (int x = 0; x < nbHorzCells; x++)
      for (int y = 0; y < nbVertCells; y++)
        addActor(cards[data[k++]], new Location(x, y));
    for (int i = 0; i < nbCells; i++)
      cards[i].show(1);  // show back
    refresh();
  }

  public static void main(String[] args)
  {
    new TcpMemory();
  }
}
