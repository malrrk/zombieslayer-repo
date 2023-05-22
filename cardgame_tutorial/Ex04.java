// Ex04.java

import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.*;

public class Ex04 extends CardGame
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
  private Hand[] hands;
  private Hand talon;

  public Ex04()
  {
    super(600, 600, 30);
    dealingOut();
    setStatusText("Dealing Out...done.");
  }

  private void dealingOut()
  {
    setStatusText("Dealing Out...");

    hands = deck.dealingOut(nbPlayers, 0);  // All cards in talon
    talon = hands[nbPlayers];

    for (int i = 0; i < nbPlayers; i++)
    {
      hands[i].setView(this, new RowLayout(handLocations[i], 300, 90 * i));
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
        talon.transfer(top, hands[i], false); // Don't show to avoid flickering
        hands[i].sort(Hand.SortType.RANKPRIORITY, true);
      }
    }
  }

  public static void main(String[] args)
  {
    new Ex04();
  }
}
