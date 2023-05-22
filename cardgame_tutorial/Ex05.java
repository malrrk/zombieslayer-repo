// Ex05.java

import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.*;

public class Ex05 extends CardGame
{
  public enum Suit
  {
    SPADES, HEARTS, DIAMONDS, CLUBS
  }

  public enum Rank
  {
    ACE, KING, QUEEN, JACK, TEN, NINE, EIGHT, SEVEN, SIX
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
  };
  private final Location talonLocation = new Location(300, 300);
  private final int packetSize = 3;
  private Hand[] hands;
  private Hand talon;

  public Ex05()
  {
    super(600, 600, 30);
    dealingOut();
    setStatusText("Dealing out...done.");
  }

  private void dealingOut()
  {
    setStatusText("Dealing out in batches...");

    hands = deck.dealingOut(nbPlayers, 0);  // All cards in shuffled talon
    talon = hands[nbPlayers];

    for (int i = 0; i < nbPlayers; i++)
    {
      hands[i].setView(this, new RowLayout(handLocations[i], 300, 90 * i));
      hands[i].draw();
    }

    CardCover talonCover = new CardCover(this, talonLocation, deck, 1, 0);

    while (!talon.isEmpty())
    {
      for (int i = 0; i < nbPlayers; i++)
      {
        for (int k = 0; k < packetSize; k++)
        {
          hands[i].insert(talon.getLast(), false);
          talon.removeLast(false);
        }
        CardCover cardCover = new CardCover(this, talonLocation, deck, 1, 0);
        cardCover.slideToTarget(handLocations[i], 10, true, true);
        cardCover.removeSelf();
        if (i == 0)
        {
          hands[i].setVerso(false);
          hands[i].sort(Hand.SortType.RANKPRIORITY, false);
        }
        else
          hands[i].setVerso(true);
        hands[i].draw();
      }
    }
    talonCover.removeSelf();
    hands[0].setTouchEnabled(true);
  }

  public static void main(String[] args)
  {
    new Ex05();
  }

}
