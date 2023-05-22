// Ex06.java

import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.*;

public class Ex06 extends CardGame
{
  public enum Suit
  {
    SPADES, HEARTS, DIAMONDS, CLUBS
  }

  public enum Rank
  {
    ACE, KING, QUEEN, JACK, TEN
  }
  //
  private final Deck deck =
    new Deck(Suit.values(), Rank.values(), "cover");
  private final int nbPlayers = 4;
  private final Location[] handLocations =
  {
    new Location(300, 525),
    new Location(75, 300),
    new Location(300, 75),
    new Location(525, 300),
    new Location(300, 300)
  };
  private final Location[] bidLocations =
  {
    new Location(300, 350),
    new Location(250, 300),
    new Location(300, 250),
    new Location(350, 300),
  };
  private final Location[] stockLocations =
  {
    new Location(400, 500),
    new Location(100, 400),
    new Location(200, 100),
    new Location(500, 200),
  };
  private Hand[] hands;
  private Hand[] bids = new Hand[nbPlayers];
  private Hand[] stocks = new Hand[nbPlayers];
  private Hand talon;

  public Ex06()
  {
    super(600, 600, 30);
    dealingOut();
    setStatusText("Dealing out...done. Double click to play.");
    initBids();
    initStocks();
  }

  private void dealingOut()
  {
    setStatusText("Dealing out...");

    hands = deck.dealingOut(nbPlayers, 0);  // All cards in talon
    talon = hands[nbPlayers];
    talon.setVerso(true);

    for (int i = 0; i < nbPlayers; i++)
    {
      hands[i].setView(this, new StackLayout(handLocations[i], 90 * i));
      hands[i].draw();
      hands[i].setTouchEnabled(true);
    }

    talon.setView(this, new StackLayout(handLocations[nbPlayers]));
    talon.draw();

    while (!talon.isEmpty())
    {
      for (int i = 0; i < nbPlayers; i++)
      {
        Card top = talon.getLast();
        top.setVerso(i == 0 ? false : true);
        talon.transfer(top, hands[i], true);
      }
    }
  }

  private void initBids()
  {
    for (int i = 0; i < nbPlayers; i++)
    {
      bids[i] = new Hand(deck);
      bids[i].setView(this, new StackLayout(bidLocations[i]));
      bids[i].addCardListener(new CardAdapter()
      {
        public void atTarget(Card card, Location loc)
        {
          card.getHand().draw();
        }
      });
      hands[i].setTargetArea(new TargetArea(bidLocations[i]));
      hands[i].setTouchEnabled(true);
      final int k = i;
      hands[i].addCardListener(new CardAdapter()
      {
        public void leftDoubleClicked(Card card)
        {
          card.setVerso(false);
          card.transfer(bids[k], true);
        }

        public void rightClicked(Card card)
        {
          transferToStock(k);
        }
      });
    }
  }

  private void initStocks()
  {
    for (int i = 0; i < nbPlayers; i++)
    {
      stocks[i] = new Hand(deck);
      double rotationAngle;
      if (i == 0 || i == 2)
        rotationAngle = 0;
      else
        rotationAngle = 90;
      stocks[i].
        setView(this, new StackLayout(stockLocations[i], rotationAngle));
    }
  }

  private void transferToStock(int player)
  {
    for (int i = 0; i < nbPlayers; i++)
    {
      bids[i].setTargetArea(new TargetArea(stockLocations[player]));
      Card c = bids[i].getLast();
      if (c == null)
        continue;
      c.setVerso(true);
      bids[i].transferNonBlocking(c, stocks[player], true);
    }
  }

  public static void main(String[] args)
  {
    new Ex06();
  }
}
