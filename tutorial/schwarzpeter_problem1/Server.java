// Server.java
// Cycle 1: Enter into game room, initialize game table, deal out cards

import ch.aplu.tcp.*;
import java.util.*;
import ch.aplu.jcardgame.*;
import ch.aplu.util.*;
import java.awt.*;

public class Server extends TcpBridge implements TcpBridgeListener
{
  private ArrayList<String> playerList = new ArrayList<String>();
  private int clientCount = 0;
  private int nbPlayers = SchwarzPeter.nbPlayers;

  public Server()
  {
    super(SchwarzPeter.sessionID, SchwarzPeter.serverName);
    addTcpBridgeListener(this);
    ArrayList<String> connectList = connectToRelay(6000);
    if (connectList.isEmpty())
    {
      ch.aplu.util.Console console = new ch.aplu.util.Console();
      System.out.println("Connection to relay failed");
      return;
    }
    if (!connectList.get(0).equals(SchwarzPeter.serverName))
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
      System.out.println("Server up and running.");
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
        sendCommandToGroup("", playerList, 100, 
          Command.DISCONNECT, TcpTools.stringToIntAry(agentName));
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
        sendCommandToGroup("", playerList, 100,
          Command.REPORT_NAMES, data);
      }
    }
  }

  public void pipeRequest(String source, String destination, int[] indata)
  {
    switch (indata[0])
    {
      case Command.READY_FOR_TALON:
        System.out.println("Got READY_FOR_TALON from " + source);
        clientCount++;
        if (clientCount == nbPlayers)
        {
          clientCount = 0;
          sendDeck();
          System.out.println("Sent DECK_DATA to all");
        }
        break;

      case Command.READY_TO_PLAY:
        System.out.println("Got READY_TO_PLAY from " + source);
        break;
    }
  }

  private void sendDeck()
  {
    Hand fullHand = CardTable.deck.toHand(!SchwarzPeter.debug);
    int[] cardNumbers = new int[fullHand.getNumberOfCards()];
    for (int i = 0; i < fullHand.getNumberOfCards(); i++)
      cardNumbers[i] = fullHand.get(i).getCardNumber();
    sendCommandToGroup("", playerList, 100,
      Command.DECK_DATA, cardNumbers);
  }
}
