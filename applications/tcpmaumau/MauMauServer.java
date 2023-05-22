// MauMauServer.java

import ch.aplu.util.*;
import ch.aplu.tcp.*;
import java.util.*;
import ch.aplu.jcardgame.*;
import java.awt.*;

public class MauMauServer extends TcpBridge implements TcpBridgeListener
{
  private ArrayList<String> playerNames = new ArrayList<String>();
  private String serverName;
  private int clientCount = 0;
  private int clientCount1 = 0;
  private int clientCount2 = 0;
  private int nbPlayers = 4;
  private int turnPlayerIndex;
  private String reshuffleSource;

  public MauMauServer(String sessionID, String gameRoom, final String serverName)
  {
    super(sessionID, serverName);
    this.serverName = serverName;
    addTcpBridgeListener(this);
    ArrayList<String> connectList = connectToRelay(6000);
    if (connectList.isEmpty())
    {
      ch.aplu.util.Console console = new ch.aplu.util.Console();
      console.setTitle("TcpMauMau Server V" + TcpMauMau.version);
      System.out.println("Connection to relay failed");
      return;
    }
    if (!connectList.get(0).equals(serverName))
    {
      System.out.println("A server instance is already running.");
      disconnect();
      return;
    }

    final ch.aplu.util.Console console =
      new ch.aplu.util.Console(new Position(0, 0),
      new Size(300, 400), new Font("Courier.PLAIN", 10, 10));
    console.setTitle("TcpMauMau Server V" + TcpMauMau.version);
    console.addExitListener(new ExitListener()
    {
      public void notifyExit()
      {
        console.end();
      }
    });
    System.out.println("Server in game room '" + gameRoom + "' running.");
  }

  public void notifyRelayConnection(boolean connected)
  {
  }

  public void notifyAgentConnection(String agentName, boolean connected)
  {
    if (!connected && playerNames.contains(agentName))
    {
      System.out.println("Player: " + agentName + " disconnected");
      if (playerNames.contains(agentName))
        sendCommandToGroup(serverName, playerNames, 100, Command.DISCONNECT,
          TcpTools.stringToIntAry(agentName));
      playerNames.remove(agentName);
    }
    else
    {
      if (playerNames.size() >= 4)
        return;
      System.out.println("Player: " + agentName + " connected");
      playerNames.add(agentName);
      if (playerNames.size() == nbPlayers)
      {
        String names = "";
        for (int i = 0; i < playerNames.size(); i++)
        {
          names += playerNames.get(i);
          if (i < playerNames.size() - 1)
            names += ',';
        }
        int[] data = TcpTools.stringToIntAry(names);
        sendCommandToGroup(serverName, playerNames, 100, Command.REPORT_NAMES, data);
      }
    }
  }

  public void pipeRequest(String source, String destination, int[] indata)
  {
    System.out.println("Got Command  " + Command.toString(indata[0])
      + " from " + source);
    switch (indata[0])
    {
      case Command.READY_FOR_TALON:
        clientCount++;
        if (clientCount == nbPlayers)
        {
          sendStartTalon();
          System.out.println("Sent TALON_DATA to all");
        }
        break;

      case Command.READY_TO_PLAY:
        clientCount1++;
        if (clientCount1 == nbPlayers)
        {
          // Player connected first will start game
          turnPlayerIndex = 0;
          String startingPlayer = playerNames.get(0);
          for (int i = 1; i < playerNames.size(); i++)
          {
            sendCommand(serverName, playerNames.get(i),
              Command.DO_WAIT,
              TcpTools.stringToIntAry(startingPlayer));
            System.out.println("Sent DO_WAIT to " + playerNames.get(i));
          }
          sendCommand(serverName, startingPlayer, Command.DO_PLAY);
          System.out.println("Sent DO_PLAY to " + startingPlayer);
        }
        break;

      case Command.CARD_FROM_TALON:
        for (int i = 0; i < playerNames.size(); i++)
        {
          if (!playerNames.get(i).equals(source))
          {
            sendCommand(serverName, playerNames.get(i),
              Command.CARD_FROM_TALON);
            System.out.println("Sent CARD_FROM_TALON to " + playerNames.get(i));
          }
        }
        break;

      case Command.CARD_TO_PILE:
        for (int i = 0; i < playerNames.size(); i++)
        {
          if (!playerNames.get(i).equals(source))
          {
            sendCommand(serverName, playerNames.get(i),
              Command.CARD_TO_PILE, indata[1]);
            System.out.println("Sent CARD_TO_PILE to " + playerNames.get(i));
          }
        }
        break;

      case Command.NEXT_TURN:
        int trumpSuitId = indata[1];
        turnPlayerIndex = (turnPlayerIndex + 1) % nbPlayers;
        String turnPlayer = playerNames.get(turnPlayerIndex);
        for (int i = 0; i < playerNames.size(); i++)
        {
          if (!playerNames.get(i).equals(turnPlayer))
          {
            if (trumpSuitId != -1)
            {
              sendCommand(serverName, playerNames.get(i),
                Command.TRUMP_SUITID, trumpSuitId);
              System.out.println("Sent TRUMP_SUITID # " + trumpSuitId + " to " + playerNames.get(i));
            }
            sendCommand(serverName, playerNames.get(i),
              Command.DO_WAIT, TcpTools.stringToIntAry(turnPlayer));
            System.out.println("Sent DO_WAIT to " + playerNames.get(i));
          }
        }
        if (trumpSuitId != -1)
        {
          sendCommand(serverName, turnPlayer, Command.TRUMP_SUITID, trumpSuitId);
          System.out.println("Sent TRUMP_SUITID # " + trumpSuitId + " to " + turnPlayer);
        }
        sendCommand(serverName, turnPlayer, Command.DO_PLAY);
        System.out.println("Sent DO_PLAY to " + turnPlayer);
        break;

      case Command.REQUEST_RESHUFFLE:
        reshuffleSource = new String(source);
        sendCommandToGroup(serverName, playerNames, 100, Command.DO_RESHUFFLE);
        System.out.println("Sent DO_RESHUFFLE to all");
        break;

      case Command.RESHUFFLE_DONE:
        clientCount2++;
        if (clientCount2 == nbPlayers)
        {
          sendCommand(serverName, reshuffleSource,
            Command.CONTINUE_AFTER_RESHUFFLE);
          System.out.println("Sent CONTINUE_AFTER_RESHUFFLE to "
            + reshuffleSource);
        }
        break;

      case Command.FATAL_ERROR:
        sendCommandToGroup(serverName, playerNames, 100, Command.FATAL_ERROR);
        System.out.println("Sent FATAL_ERROR to all");
        break;
    }
  }

  private void sendStartTalon()
  {
    Hand talon = GameTable.deck.dealingOut(0, 0, !TcpMauMau.debug)[0];
    int[] cardNumbers = new int[talon.getNumberOfCards()];
    for (int i = 0; i < talon.getNumberOfCards(); i++)
      cardNumbers[i] = talon.get(i).getCardNumber();
    sendCommandToGroup(serverName, playerNames, 100, Command.TALON_DATA, cardNumbers);
  }
}
