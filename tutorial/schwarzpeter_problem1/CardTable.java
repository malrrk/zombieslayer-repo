// CardTable.java
// Cycle 1: Enter into game room, initialize game table, deal out cards
// See TODO where to put your code

import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.*;
import ch.aplu.tcp.*;
import java.util.*;

public class CardTable extends CardGame
{
  public static enum Suit
  {
    SPADES, HEARTS
  }

  public static enum Rank
  {
    ACE, KING, QUEEN, JACK, TEN, NINE, EIGHT, SEVEN, SIX
  }
  //
  protected static Deck deck = new Deck(Suit.values(), Rank.values(), "cover");
  private Card uglyCard = new Card(deck, Suit.SPADES, Rank.JACK);
  private Card uglyCardTwin = new Card(deck, Suit.HEARTS, Rank.JACK);
  private final int nbPlayers = SchwarzPeter.nbPlayers;
  private final int handWidth = 300; 
  private final Location[] handLocations =
  {
    new Location(300, 520),
    new Location(75, 300),
    new Location(300, 80),
    new Location(525, 300)
  };
  private final Location[] stockLocations =
  {
    new Location(520, 540),
    new Location(60, 520),
    new Location(80, 50),
    new Location(540, 80),
  };

  private Hand[] hands = new Hand[nbPlayers];
  private Hand[] stocks = new Hand[nbPlayers];
  private String[] playerNames;
  private TcpAgent agent;
  private int myPlayerId;

  public CardTable(TcpAgent agent, String[] playerNames, int myPlayerId)
  {
    super(600, 600, 30);
    this.agent = agent;
    this.playerNames = new String[nbPlayers];
    for (int i = 0; i < nbPlayers; i++)
      this.playerNames[i] = playerNames[i];
    this.myPlayerId = myPlayerId;
    setTitle("My player's name: " + playerNames[myPlayerId]);
  }

  protected void initHands(int[] cardNumbers)
  {
    for (int i = 0; i < nbPlayers; i++)
    {
      stocks[i] = new Hand(deck);
      hands[i] = new Hand(deck);
      hands[i].setTouchEnabled(true);
      RowLayout handLayout = new RowLayout(handLocations[i], handWidth);
      handLayout.setRotationAngle(90 * i);
      hands[i].setView(this, handLayout);
      StackLayout stockLayout = new StackLayout(stockLocations[i]);
      stockLayout.setRotationAngle(90 * i);
      stocks[i].setView(this, stockLayout);
    }

    // Insert cards into hands
    int playerId = 0;
    for (int i = 0; i < cardNumbers.length; i++)
    {
      Card card = new Card(deck, cardNumbers[i]);
      System.out.println("TODO: insert " + card);
      delay(200);
      playerId = (playerId + 1) % nbPlayers;
    }
    agent.sendCommand("", Command.READY_TO_PLAY);
  }

  protected void stopGame(String client)
  {
    setStatusText(client + " disconnected. Game stopped.");
    setMouseEnabled(false);
    doPause();
  }

  private int toHandId(int playerId)
  {
    int n = playerId - myPlayerId;
    return n >= 0 ? n : (nbPlayers + n);
  }

  private int toPlayerId(int handId)
  {
    return (myPlayerId + handId) % nbPlayers;
  }
}
