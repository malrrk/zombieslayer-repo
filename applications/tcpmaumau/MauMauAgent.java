// MauMauAgent.java

import ch.aplu.tcp.*;
import ch.aplu.util.*;
import javax.swing.JOptionPane;
import java.util.*;
import ch.aplu.jcardgame.*;

public class MauMauAgent
{
  private String serverName = "MauMauServer";
  private MauMauServer server;
  private String gameRoom;
  private TcpAgent agent;
  private String sessionID = "MauMau: &4**&/**()";
  private GameTable gameTable;
  private String currentPlayerName;
  private String[] playerNames = null;
  private int nbPlayers = 4;
  private ModelessOptionPane mop =
    new ModelessOptionPane("Trying to connect to relay...");

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
          String client = TcpTools.intAryToString(data, 1);
          if (gameTable != null)
            gameTable.stopGame(client);
          server.disconnect();
          break;

        case Command.TALON_DATA:
          for (int i = 1; i < data.length; i++)
          {
            Deck deck = gameTable.deck;
            Card card =
              new Card(deck,
              deck.getSuit(deck.getSuitId(data[i - 1])),
              deck.getRank(deck.getRankId(data[i - 1])));
            gameTable.startTalon.insert(card, false);
          }
          // Long lasting code executed in separate thread
          new Thread()
          {
            public void run()
            {
              gameTable.initHands();
              agent.sendCommand("", Command.READY_TO_PLAY);
            }
          }.start();
          break;
        case Command.DO_PLAY:
          gameTable.setMyTurn();
          break;
        case Command.DO_WAIT:
          String turnPlayer = TcpTools.intAryToString(data, 1);
          gameTable.setOtherTurn(turnPlayer);
          break;
        case Command.CARD_FROM_TALON:
          gameTable.moveCardFromTalon();
          break;
        case Command.CARD_TO_PILE:
          gameTable.moveCardToPile(data[1]);
          break;
        case Command.DO_RESHUFFLE:
          gameTable.reshuffle();
          break;
        case Command.CONTINUE_AFTER_RESHUFFLE:
          gameTable.showOkBtn();
          break;
        case Command.FATAL_ERROR:
          gameTable.setStatusText("No cards available for reshuffling. Game stopped.");
          gameTable.doPause();
          break;
        case Command.TRUMP_SUITID:
          int trumpSuitId = data[1];
          gameTable.setTrumpActor(trumpSuitId);
          break;
      }
    }

    public void notifyBridgeConnection(boolean connected)
    {
      if (!connected && gameTable != null)
        gameTable.setStatusText("Client with game server disconnected. Game stopped.");
    }
  }

  public MauMauAgent()
  {
    if (TcpMauMau.debug)
      gameRoom = "123";
    else
      gameRoom = requestEntry("Enter a unique room name (ASCII 3..15 chars):");
    sessionID = sessionID + gameRoom;
    agent = new TcpAgent(sessionID, serverName);
    String requestedName;
    if (TcpMauMau.debug)
      requestedName = "Alpha";
    else
      requestedName = requestEntry("Enter your name (ASCII 3..15 chars):");
    agent.addTcpAgentListener(new MyTcpAgentAdapter());
    connect(requestedName);
  }

  private void connect(String requestedName)
  {
    mop.setTitle("TcpMauMau V" + TcpMauMau.version);
    server = new MauMauServer(sessionID, gameRoom, serverName);
    TcpTools.delay(2000); // Let server come-up
    ArrayList<String> connectList = agent.connectToRelay(requestedName, 6000);
    if (connectList.isEmpty())
      errorQuit(mop, "Connection to relay failed. Terminating now...");

    int nb = connectList.size();
    currentPlayerName = connectList.get(0);
    if (nb > nbPlayers)
      errorQuit(mop, "Game room full. Terminating now...");

    if (nb < nbPlayers)
      mop.setText("Connection established. Name: " + currentPlayerName
        + "\nCurrently " + nb + " player(s) in game room."
        + "\nWaiting for " + (nbPlayers - nb) + " more player(s)...");

    while (playerNames == null)  // Wait until names are reported
      TcpTools.delay(10);

    mop.dispose();
    gameTable =
      new GameTable(agent, playerNames, getCurrentPlayerIndex());
    agent.sendCommand("", Command.READY_FOR_TALON);
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

  private int getCurrentPlayerIndex()
  {
    for (int index = 0; index < nbPlayers; index++)
    {
      if (playerNames[index].equals(currentPlayerName))
        return index;
    }
    return 0;
  }

  private void errorQuit(ModelessOptionPane mop, String msg)
  {
    mop.setText(msg);
    TcpTools.delay(3000);
    System.exit(0);
  }
}
