// Player.java
// Cycle 1: Enter into game room, initialize game table, deal out cards
// See TODO where to put your code

import ch.aplu.tcp.*;
import ch.aplu.util.*;
import java.util.*;

public class Player extends TcpAgent
{
  private int nbPlayers = SchwarzPeter.nbPlayers;
  private Server cardServer;
  private CardTable cardTable;
  private String[] playerNames = null;
  private String currentPlayerName;
  private int myPlayerId;

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
          disconnect();
          cardServer.disconnect();
          if (cardTable != null)
            cardTable.stopGame(client);
          break;

        case Command.DECK_DATA:
          System.out.println("TODO: Got series " + Arrays.toString(data));
          break;
      }
    }

    public void notifyBridgeConnection(boolean connected)
    {
      if (!connected && cardTable != null)
      {
        cardTable.
          setStatusText("Client with game server disconnected. Game stopped.");
        disconnect();
      }
    }
  }

  public Player()
  {
    super(SchwarzPeter.sessionID, SchwarzPeter.serverName);
    this.nbPlayers = nbPlayers;
    addTcpAgentListener(new MyTcpAgentAdapter());
    ModelessOptionPane mop = new ModelessOptionPane("Trying to connect to relay...");
    cardServer = new Server();
    TcpTools.delay(2000); // Let server come-up

    ArrayList<String> connectList = connectToRelay(SchwarzPeter.playerName, 6000);
    if (connectList.isEmpty())
    {
      mop.setText("Connection to relay failed. Terminating now...");
      CardTable.delay(3000);
      System.exit(0);
    }

    int nb = connectList.size();
    currentPlayerName = connectList.get(0);
    if (nb > nbPlayers)
    {
      mop.setText("Game room full. Terminating now...");
      CardTable.delay(3000);
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
    cardTable = new CardTable(this, playerNames, myPlayerId);
    sendCommand("", Command.READY_FOR_TALON);
  }
}
