// Ex10.java
// Search pairs, trips, quads

import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.*;
import ch.aplu.util.Monitor;

public class Ex10 extends CardGame implements GGMouseListener
{
  public enum Suit
  {
    SPADES, HEARTS, DIAMONDS, CLUBS
  }

  public enum Rank
  {
    ACE, KING, QUEEN, JACK, TEN, NINE, EIGHT, SEVEN, SIX
  }
  private Deck deck = new Deck(Suit.values(), Rank.values(), "cover");

  public Ex10()
  {
    super(900, 615, 30);
    addMouseListener(this, GGMouse.lPress);
    while (true)
    {
      Hand hand = deck.dealingOut(1, 25)[0];
      RowLayout rowLayout = new RowLayout(new Location(450, 80), 890);
      hand.setView(this, rowLayout);
      hand.sort(Hand.SortType.RANKPRIORITY, true);
      hand.draw();
      setStatusText("Click to get pairs, trips and quads");
      Monitor.putSleep();

      Hand[] pairs = hand.extractPairs();
      for (int i = 0; i < pairs.length; i++)
      {
        pairs[i].
          setView(this, new RowLayout(new Location(70 + 150 * i, 230), 120));
        pairs[i].draw();
      }
      Hand[] trips = hand.extractTrips();
      for (int i = 0; i < trips.length; i++)
      {
        trips[i].
          setView(this, new RowLayout(new Location(70 + 150 * i, 380), 120));
        trips[i].draw();
      }

      Hand[] quads = hand.extractQuads();
      for (int i = 0; i < quads.length; i++)
      {
        quads[i].
          setView(this, new RowLayout(new Location(100 + 200 * i, 530), 150));
        quads[i].draw();
      }
      setStatusText("Click to generate the next card set.");
      Monitor.putSleep();
      // Cleanup all hands
      hand.removeAll(false);
      for (Hand h : pairs)
        h.removeAll(false);
      for (Hand h : trips)
        h.removeAll(false);
      for (Hand h : quads)
        h.removeAll(false);
    }
  }

  public boolean mouseEvent(GGMouse mouse)
  {
    Monitor.wakeUp();
    return true;
  }

  public static void main(String[] args)
  {
    new Ex10();
  }
}
