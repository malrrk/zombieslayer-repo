// CardServer.java

import ch.aplu.tcp.*;
import java.util.*;
import ch.aplu.jcardgame.*;
import ch.aplu.util.*;
import java.awt.*;

public class CardServer extends TcpBridge implements TcpBridgeListener
{
  private ArrayList<String> playerList = new ArrayList<String>();
  private String serverName;
  private int clientCount = 0;
  private int nbPlayers = 2;
  private int turnPlayerIndex = 0;
  private int nbReady = 0;

  public CardServer(String sessionID, String gameRoom, String serverName)
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
        sendCommandToGroup(serverName, playerList, 100, 
          CardPlayer.Command.DISCONNECT, TcpTools.stringToIntAry(agentName));
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
        sendCommandToGroup(serverName, playerList, 100,
          CardPlayer.Command.REPORT_NAMES, data);
      }
    }
  }

  public void pipeRequest(String source, String destination, int[] indata)
  {
    switch (indata[0])
    {
      case CardPlayer.Command.READY_FOR_TALON:
        System.out.println("Got READY_FOR_TALON from " + source);
        clientCount++;
        if (clientCount == nbPlayers)
        {
          clientCount = 0;
          sendStartTalon();
          System.out.println("Sent TALON_DATA to all");
        }
        break;

      case CardPlayer.Command.READY_TO_PLAY:
        nbReady++;
        System.out.println("Got READY_TO_PLAY from " + source);
        if (nbReady == nbPlayers)
        {
          nbReady = 0;
          giveTurn();
        }

        break;

      case CardPlayer.Command.CARD_TO_LINE:
        for (int i = 0; i < playerList.size(); i++)
        {
          if (!playerList.get(i).equals(source))
          {
            sendCommand(serverName, playerList.get(i),
              CardPlayer.Command.CARD_TO_LINE, indata[1], indata[2]);
            System.out.println("Sent CARD_TO_LINE to " + playerList.get(i));
          }
        }
        break;
    }
  }

  private void giveTurn()
  {
    for (int i = 0; i < playerList.size(); i++)
    {
      if (i != turnPlayerIndex)
      {
        sendCommand(serverName, playerList.get(i),
          CardPlayer.Command.OTHER_TURN);
        System.out.println("Sent OTHER_TURN to " + playerList.get(i));
      }
    }
    sendCommand(serverName, playerList.get(turnPlayerIndex),
      CardPlayer.Command.MY_TURN);
    System.out.println("Sent MY_TURN to " + playerList.get(turnPlayerIndex));
    turnPlayerIndex += 1;
    turnPlayerIndex %= nbPlayers;
  }

  private void sendStartTalon()
  {
    Hand talon = CardTable.deck.dealingOut(0, 0)[0]; // All cards, shuffled
    int[] cardNumbers = new int[talon.getNumberOfCards()];
    for (int i = 0; i < talon.getNumberOfCards(); i++)
      cardNumbers[i] = talon.get(i).getCardNumber();
    sendCommandToGroup(serverName, playerList, 100,
      CardPlayer.Command.TALON_DATA, cardNumbers);
  }
}
