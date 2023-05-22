// Ex11.java
// Search sequences with same suit

import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.*;
import ch.aplu.util.Monitor;

public class Ex11 extends CardGame implements GGMouseListener
{
  public enum Suit
  {
    SPADES, HEARTS, DIAMONDS, CLUBS
  }

  public enum Rank
  {
    ACE, KING, QUEEN, JACK, TEN, NINE, EIGHT, SEVEN, SIX, FIVE, FOUR, THREE, TWO
  }
  private Deck deck = new Deck(Suit.values(), Rank.values(), "cover");

  public Ex11()
  {
    super(900, 615, 30);
    addMouseListener(this, GGMouse.lPress);
    while (true)
    {
      Hand hand = deck.dealingOut(1, 25)[0];

      hand.setView(this, new RowLayout(new Location(450, 80), 890));
      hand.sort(Hand.SortType.RANKPRIORITY, true);

      Hand[] sequence3 = hand.extractSequences(Suit.HEARTS, 3);
      for (int i = 0; i < sequence3.length; i++)
      {
        sequence3[i].
          setView(this, new RowLayout(new Location(70 + 150 * i, 230), 120));
        sequence3[i].draw();
      }

      Hand[] sequence4 = hand.extractSequences(Suit.HEARTS, 4);
      for (int i = 0; i < sequence4.length; i++)
      {
        sequence4[i].
          setView(this, new RowLayout(new Location(70 + 150 * i, 380), 120));
        sequence4[i].draw();
      }

      Hand[] sequence5 = hand.extractSequences(Suit.HEARTS, 5);
      for (int i = 0; i < sequence5.length; i++)
      {
        sequence5[i].
          setView(this, new RowLayout(new Location(100 + 200 * i, 530), 150));
        sequence5[i].draw();
      }

      setStatusText("Click to generate the next card set.");
      Monitor.putSleep();
      // Cleanup all hands
      hand.removeAll(false);
      for (Hand h : sequence3)
        h.removeAll(false);
      for (Hand h : sequence4)
        h.removeAll(false);
      for (Hand h : sequence5)
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
    new Ex11();
  }
}
