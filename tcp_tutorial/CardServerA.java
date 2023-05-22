// CardServerA.java

import ch.aplu.tcp.*;
import java.util.*;
import ch.aplu.jcardgame.*;
import ch.aplu.util.*;
import java.util.*;
import java.awt.*;

public class CardServerA extends TcpBridge implements TcpBridgeListener
{
  private int nbPlayers = CardPlayerA.nbPlayers;
  private ArrayList<String> playerList = new ArrayList<String>();
  private ArrayList<Integer> selectedCardNumbers = new ArrayList<Integer>();
  private String serverName;
  private int clientCount = 0;
  private int turnPlayerId = 0;
  private int nbEngaged = nbPlayers;

  public CardServerA(String sessionID, String gameRoom, String serverName)
  {
    super(sessionID, serverName);
    this.serverName = serverName;
    addTcpBridgeListener(this);
    ArrayList<String> connectList = connectToRelay(6000);
    if (connectList.isEmpty())
    {
      ch.aplu.util.Console console = new ch.aplu.util.Console();
      System.out.println("Connection to relay failed");
      return;
    }
    if (!connectList.get(0).equals(serverName))
    {
      System.out.println("A server instance is already running.");
      disconnect();
    }
    else
    {
      final ch.aplu.util.Console console =
        new ch.aplu.util.Console(new Position(0, 0),
        new Size(300, 400), new Font("Courier.PLAIN", 10, 10));
      console.addExitListener(new ExitListener()
      {
        public void notifyExit()
        {
          console.end();
        }
      });
      System.out.println("Server in game room '" + gameRoom + "' running.");
    }
  }

  public void notifyRelayConnection(boolean connected)
  {
  }

  public void notifyAgentConnection(String agentName, boolean connected)
  {
    if (!connected && playerList.contains(agentName))
    {
      System.out.println("Player: " + agentName + " disconnected");
      if (playerList.contains(agentName))
        sendCommandToGroup(serverName, playerList, 10,
          CommandA.DISCONNECT, TcpTools.stringToIntAry(agentName));
      playerList.remove(agentName);
    }
    else
    {
      if (playerList.size() >= nbPlayers)
        return;

      System.out.println("Player: " + agentName + " connected");
      playerList.add(agentName);
      if (playerList.size() == nbPlayers)
      {
        String names = "";
        for (int i = 0; i < playerList.size(); i++)
        {
          names += playerList.get(i);
          if (i < playerList.size() - 1)
            names += ',';
        }
        int[] data = TcpTools.stringToIntAry(names);
        sendCommandToGroup(serverName, playerList, 10,
          CommandA.REPORT_NAMES, data);
      }
    }
  }

  public void pipeRequest(String source, String destination, int[] indata)
  {
    switch (indata[0])
    {
      case CommandA.READY_FOR_FAN:
        System.out.println("Got READY_FOR_FAN from " + source);
        clientCount++;
        if (clientCount == nbEngaged)
        {
          clientCount = 0;
          sendFan();
        }
        break;

      case CommandA.FAN_SELECTED_CARD:
        int playerId = indata[1];
        int cardNumber = indata[2];
        System.out.println("Got FAN_SELECTED_CARD from " + source
          + ". Card: " + new Card(CardTableA.deck, cardNumber));

        // Check if already selected, this may happen, when two
        // players clicks the same card at almost the same time
        // before the FAN_SELECTED_CARD info reaches the clients
        if (selectedCardNumbers.contains(cardNumber))  // Yes -> no duplication
          return;

        selectedCardNumbers.add(cardNumber);
        sendCommandToGroup(serverName, playerList, 10,
          CommandA.FAN_SELECTED_CARD, playerId, cardNumber);
        System.out.println("Sent FAN_SELECTED_CARD to all ");
        break;
    }
  }

  private void giveTurn()
  {
    for (int i = 0; i < playerList.size(); i++)
    {
      if (i != turnPlayerId)
      {
        sendCommand(serverName, playerList.get(i),
          CommandA.OTHER_TURN);
        System.out.println("Sent OTHER_TURN to " + playerList.get(i));
      }
    }
    sendCommand(serverName, playerList.get(turnPlayerId),
      CommandA.MY_TURN);
    System.out.println("Sent MY_TURN to " + playerList.get(turnPlayerId));
    turnPlayerId += 1;
    turnPlayerId %= nbPlayers;
  }

  private void sendFan()
  {
    selectedCardNumbers.clear();
    int fanSize = 8;
    Hand fan = CardTableA.deck.dealingOut(1, fanSize)[0];
    int[] cardNumbers = new int[fanSize];
    for (int i = 0; i < fanSize; i++)
      cardNumbers[i] = fan.get(i).getCardNumber();

    sendCommandToGroup(serverName, playerList, 100,
      CommandA.FAN_DATA, cardNumbers);
    System.out.println("Sent FAN_DATA to all");
  }

  protected void checkStart(int[] selectedCardNumbers)
  {
    int playerId = 0;
    nbEngaged = 0;
    for (int i = 0; i < nbPlayers; i++)
    {
      if (selectedCardNumbers[i] >= 0)
      {
        nbEngaged++;
        playerId = i;
      }
    }
    if (nbEngaged == 1)
    {
      System.out.println("Starting player: " + 
        CardPlayerA.playerNames[playerId]);
      turnPlayerId = playerId;
      sendCommandToGroup(serverName, playerList, 10,
         CommandA.GAME_STARTING, turnPlayerId);
      giveTurn();
    }
    else
      sendFan();
  }
}
