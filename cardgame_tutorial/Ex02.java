// Ex02.java

import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.*;

public class Ex02 extends CardGame
{
  public enum Suit
  {
    SPADES, HEARTS, DIAMONDS, CLUBS
  }

  public enum Rank
  {
    ACE, KING, QUEEN, JACK, TEN, NINE, EIGHT, SEVEN, SIX
  }

  private Deck deck =
    new Deck(Suit.values(), Rank.values(), "cover");

  public Ex02()
  {
    super(600, 600);
    
    Hand[] hands = deck.dealingOut(1, 9);
    final Hand stock = hands[1];
    stock.setView(this, new StackLayout(new Location(300, 150)));
    stock.draw();

    hands[0].setView(this, new RowLayout(new Location(300, 400), 500));
    hands[0].sort(Hand.SortType.SUITPRIORITY, false);
    hands[0].draw();
    hands[0].addCardListener(new CardAdapter()
    {
      public void leftDoubleClicked(Card card)
      {
        card.transfer(stock, true);
      }
    });
    hands[0].setTouchEnabled(true);
    setDoubleClickDelay(300);
  }

  public static void main(String[] args)
  {
    new Ex02();
  }
}
