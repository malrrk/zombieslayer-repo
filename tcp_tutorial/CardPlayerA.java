// CardPlayerA.java
// 2 to 4 players select a card from the offering fan. Best rank
// will start the game.
// If debug = true, the cards are shown face up.

import ch.aplu.tcp.*;
import ch.aplu.util.*;
import javax.swing.JOptionPane;
import ch.aplu.jcardgame.*;
import java.util.*;

public class CardPlayerA
{
  protected final static boolean debug = false;
  private final String serverName = "CardServer";
  private String sessionID = "CardPlayerFan /adf7&&a%+";
  private TcpAgent agent;
  private CardServerA cardServer;
  private CardTableA cardTable;
  private String currentPlayerName;
  private int myPlayerId;
  protected final static int nbPlayers = 4;
  protected static String[] playerNames = null;
  private int nbEngaged;
  private int nbSelected;
  
  private int[] selectedCardNumbers = new int[nbPlayers]; 
  // >=0: valid, -1: not selected, -2: player out

  private class MyTcpAgentAdapter extends TcpAgentAdapter
  {
    public void dataReceived(String source, int[] data)
    {
      switch (data[0])
      {
        case CommandA.REPORT_NAMES:
          playerNames = TcpTools.split(TcpTools.intAryToString(data, 1), ",");
          break;

        case CommandA.DISCONNECT:
          String client = TcpTools.intAryToString(data, 1);
          agent.disconnect();
          cardServer.disconnect();
          if (cardTable != null)
            cardTable.stopGame(client);
          break;
          
        case CommandA.FAN_DATA:
          int size = data.length - 1;
          int[] cardNumbers = new int[size];
          System.arraycopy(data, 1, cardNumbers, 0, size);
          cardTable.initFan(cardNumbers);
          nbEngaged = 0;
          nbSelected = 0;
          String in = "In: ";
          String out = "Out: ";
          for (int i = 0; i < nbPlayers; i++)
          {
            if (selectedCardNumbers[i] != -2)  // not out
            {
              selectedCardNumbers[i] = -1;
              nbEngaged++;
              in += playerNames[i] + ", ";
            }  
            else
              out += playerNames[i] + ", ";
           if (selectedCardNumbers[myPlayerId] != -2)
              cardTable.enableFan();
          }
          cardTable.setStatusText(in + "  -   " + out);
          break;

        case CommandA.FAN_SELECTED_CARD:
          int playerId = data[1];
          int cardNumber = data[2];
          cardTable.moveSelectedCard(playerId, cardNumber);
          if (selectedCardNumbers[playerId] == -1)  // Not yet selected
          {
            nbSelected++;
            selectedCardNumbers[playerId] = cardNumber;
          }
          if (nbSelected == nbEngaged)
          {  
            bestRank();
            if (cardServer.isConnected())  // Client with running card server
              cardServer.checkStart(selectedCardNumbers);
          }
          break;

        case CommandA.GAME_STARTING:
          cardTable.removeFan();
          cardTable.addStartInfo(playerNames[data[1]]);
          break;

        case CommandA.MY_TURN:
          cardTable.setMyTurn();
          break;

        case CommandA.OTHER_TURN:
          cardTable.setOtherTurn();
          break;
      }
    }

    public void notifyBridgeConnection(boolean connected)
    {
      if (!connected && cardTable != null)
      {
        cardTable.
          setStatusText("Client with game server disconnected. Game stopped.");
        agent.disconnect();
      }
    }
  }

  public CardPlayerA()
  {
    for (int i = 0; i < nbPlayers; i++)
      selectedCardNumbers[i] = -1;  // not selected
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
    ModelessOptionPane mop = new ModelessOptionPane("Trying to connect to relay...");
    cardServer = new CardServerA(sessionID, gameRoom, serverName);
    TcpTools.delay(2000); // Let server come-up

    ArrayList<String> connectList = agent.connectToRelay(requestedName, 6000);
    if (connectList.isEmpty())
    {
      mop.setText("Connection to relay failed. Terminating now...");
      CardTableA.delay(3000);
      System.exit(0);
    }

    int nb = connectList.size();
    currentPlayerName = connectList.get(0);
    if (nb > nbPlayers)
    {
      mop.setText("Game room full. Terminating now...");
      CardTableA.delay(3000);
      System.exit(0);
    }

    if (nb < nbPlayers)
      mop.setText("Connection established. Name: " + currentPlayerName
        + "\nCurrently " + nb + " player(s) in game room."
        + "\nWaiting for " + (nbPlayers - nb) + " more player(s)...");

    while (playerNames == null)  // Wait until player names are reported
      TcpTools.delay(10);

    for (int i = 0; i < nbPlayers; i++)
    {
      if (playerNames[i].equals(currentPlayerName))
      {
        myPlayerId = i;
        break;
      }
    }
    mop.setVisible(false);
    cardTable = new CardTableA(agent, myPlayerId);
    agent.sendCommand("", CommandA.READY_FOR_FAN, nbPlayers);
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
  
  private void bestRank()
  {
    // Put cards into array
    ArrayList<Integer> selectedRankIds = new ArrayList<Integer>();
    for (int i = 0; i < nbPlayers; i++)
    {
      int nb = selectedCardNumbers[i];
      if (nb >= 0)
        selectedRankIds.add(CardTableA.deck.getRankId(nb));
    }  

    // Determine best rank id (lowest value)
    int highestRankId = Integer.MAX_VALUE;
    for (Integer id : selectedRankIds)
    {
      if (id < highestRankId)
        highestRankId = id;
    }
    
    // Takes the players out, if less than best
    for (int i = 0; i < nbPlayers; i++)
    {
      int nb = selectedCardNumbers[i];
      if (nb >= 0 && CardTableA.deck.getRankId(nb) > highestRankId)
         selectedCardNumbers[i] = -2;
    }  
  }

  public static void main(String[] args)
  {
    new CardPlayerA();
  }
}
