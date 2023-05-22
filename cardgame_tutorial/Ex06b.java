// Ex06b.java

import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.*;
import ch.aplu.util.Monitor;

public class Ex06b extends CardGame
{
  public enum Suit
  {
    SPADES, HEARTS, DIAMONDS, CLUBS
  }

  public enum Rank
  {
    ACE, KING, QUEEN, JACK, TEN
  }

  class MyCardValues implements Deck.CardValues
  {
    public int[] values(Enum suit)
    {
      int[] defaultValues = new int[]
      {
        11, 4, 3, 2, 10
      };
      return defaultValues;
    }
  }
  //
  private final Deck deck =
    new Deck(Suit.values(), Rank.values(), "cover", new MyCardValues());
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
  private int currentPlayer = 0;
  private int nbMovesInRound = 0;
  private int targetCount = 0;

  public Ex06b()
  {
    super(600, 600, 30);
    dealingOut();
    setStatusText("Dealing out...done. Starting player: " + currentPlayer);
    initBids();
    initStocks();
    hands[0].setTouchEnabled(true);
  }

  private void dealingOut()
  {
    setStatusText("Dealing out...");

    hands = deck.dealingOut(nbPlayers, 0, false);  // All cards in talon
    talon = hands[nbPlayers];
    talon.setVerso(true);

    for (int i = 0; i < nbPlayers; i++)
    {
      hands[i].setView(this, new StackLayout(handLocations[i], 90 * i));
      hands[i].draw();
    }

    talon.setView(this, new StackLayout(handLocations[nbPlayers]));
    talon.draw();

    while (!talon.isEmpty())
    {
      for (int i = 0; i < nbPlayers; i++)
      {
        Card top = talon.getLast();
        top.setVerso(i == 0 ? false : true);
        talon.setTargetArea(new TargetArea(handLocations[i]));
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
          targetCount++;
          if (targetCount == nbPlayers)
            Monitor.wakeUp();  // All cards in stock->continue
        }
      });

      hands[i].setTargetArea(new TargetArea(bidLocations[i]));
      final int k = i;
      hands[i].addCardListener(new CardAdapter()
      {
        public void leftDoubleClicked(Card card)
        {
          card.setVerso(false);
          card.transfer(bids[k], true);
          hands[currentPlayer].setTouchEnabled(false);
          currentPlayer = (currentPlayer + 1) % nbPlayers;
          if (nbMovesInRound == 3)
          {
            setStatusText("Evaluating round...");
            nbMovesInRound = 0;
            currentPlayer = transferToWinner();
            stocks[currentPlayer].draw();
          }
          else
            nbMovesInRound++;
          if (hands[currentPlayer].isEmpty())
            showResult();
          else
          {
            setStatusText("Current player: " + currentPlayer);
            hands[currentPlayer].setTouchEnabled(true);
          }
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
      stocks[i].setView(this, new StackLayout(stockLocations[i], rotationAngle));
    }
  }

  private int transferToWinner()
  {
    delay(3000);
    Hand eval = new Hand(deck);
    for (int i = 0; i < nbPlayers; i++)
      eval.insert(bids[i].getFirst(), false);
    int nbWinner = eval.getMaxPosition(Hand.SortType.RANKPRIORITY);
    setStatusText("Round winner: " + nbWinner);
    transferToStock(nbWinner);
    return nbWinner;
  }

  private void transferToStock(int player)
  {
    targetCount = 0;
    for (int i = 0; i < nbPlayers; i++)
    {
      bids[i].setTargetArea(new TargetArea(stockLocations[player]));
      Card c = bids[i].getLast();
      if (c == null)
        continue;
      c.setVerso(true);
      bids[i].transferNonBlocking(c, stocks[player], true);
    }
    Monitor.putSleep();  // Wait until all cards are transferred to stock
    stocks[player].draw();
  }

  private void showResult()
  {
    String text = "Game over. Summary: ";
    for (int i = 0; i < nbPlayers; i++)
    {
      text += "Player # " + i + ": " + stocks[i].getScore();
      if (i < nbPlayers - 1)
        text += "; ";
    }
    setStatusText(text);
  }

  public static void main(String[] args)
  {
    new Ex06b();
  }
}
