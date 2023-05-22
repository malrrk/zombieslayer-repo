// CardTableA.java

import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.*;
import ch.aplu.tcp.*;
import java.awt.*;

public class CardTableA extends CardGame
{
  public static enum Suit
  {
    SPADES, HEARTS, DIAMONDS, CLUBS
  }

  public static enum Rank
  {
    ACE, KING, QUEEN
  }
  //
  protected static Deck deck = new Deck(Suit.values(), Rank.values(), "cover");
  private final int nbPlayers = CardPlayerA.nbPlayers;
  private final int handWidth = 300;
  private final Location[] handLocations =
  {
    new Location(300, 520),
    new Location(75, 300),
    new Location(300, 80),
    new Location(525, 300)
  };
  private final Location[] fanCenters =
  {
    new Location(300, 1000),
    new Location(1000, 300),
    new Location(300, -400),
    new Location(-400, 300),
  };
  private final double[] fanStartDirections =
  {
    262, 172, 82, 352
  };
  private final double[] fanEndDirections =
  {
    278, 188, 98, 368
  };
  private Hand[] hands = new Hand[nbPlayers];
  private int myPlayerId;
  private TcpAgent agent;
  private Hand fan = new Hand(deck);

  public CardTableA(TcpAgent agent, int myPlayerId)
  {
    super(600, 600, 30);
    this.agent = agent;
    this.myPlayerId = myPlayerId;
    setTitle("Current player's name: " + CardPlayerA.playerNames[myPlayerId]);
    initHands();
  }

  protected void initHands()
  {
    for (int i = 0; i < nbPlayers; i++)
    {
      hands[i] = new Hand(deck);
      RowLayout handLayout = new RowLayout(handLocations[i], handWidth);
      handLayout.setRotationAngle(90 * i);
      hands[i].setView(this, handLayout);
    }
  }

  protected void initFan(int[] cardNumbers)
  {
    removeFan();
    fan = new Hand(deck);
    for (int i = 0; i < cardNumbers.length; i++)
      fan.insert(cardNumbers[i], false);
    FanLayout fanLayout =
      new FanLayout(
      fanCenters[myPlayerId],
      700,
      fanStartDirections[myPlayerId], fanEndDirections[myPlayerId]);
    fanLayout.setStepDelay(5);
    fan.setVerso(!CardPlayerA.debug);  // Show cards for debugging
    fan.setView(this, fanLayout);
    fan.draw();

    fan.addCardListener(new CardAdapter()
    {
      public void leftDoubleClicked(Card card)
      {
        agent.sendCommand("", CommandA.FAN_SELECTED_CARD,
          myPlayerId, card.getCardNumber());
      }
    });
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

  protected void stopGame(String client)
  {
    setStatusText(client + " disconnected. Game stopped.");
    setMouseEnabled(false);
    doPause();
  }

  protected void setMyTurn()
  {
    setStatusText("It's your turn. " + 
      "Double click on one of your cards to play it.");
    hands[0].setTouchEnabled(true);
  }

  protected void setOtherTurn()
  {
    setStatusText("Wait for you turn.");
  }

  protected void moveSelectedCard(int playerId, int cardNumber)
  {
    if (playerId == myPlayerId)  // Selection confirmed
      fan.setTouchEnabled(false);
    Card card = fan.getCard(cardNumber);
    card.setVerso(false);
    fan.setTargetArea(new TargetArea(handLocations[toHandId(playerId)]));
    ((FanLayout)fan.getLayout()).setStepDelay(0);
    fan.transfer(card, hands[toHandId(playerId)], true);
  }

  protected void enableFan()
  {
    fan.setTouchEnabled(true);
  }

  protected void removeFan()
  {
    fan.removeAll(false);
    for (Hand hand : hands)
      hand.removeAll(false);
  }

  protected void addStartInfo(String startingPlayer)
  {
    TextActor ta = new TextActor(startingPlayer + " starting now...", 
      Color.white, new Color(255, 255, 255, 0), // Transparent background
      new Font("Arial", Font.BOLD, 36));
    addActor(ta, new Location(100, 300));
  }
}
