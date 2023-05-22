// Ex01.java

import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.*;

public class Ex01 extends CardGame
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

  public Ex01()
  {
    super(600, 600);

    Hand[] hands = deck.dealingOut(4, 5);

    RowLayout rowLayout0 = new RowLayout(new Location(150, 520), 300);
    hands[0].setView(this, rowLayout0);
    hands[0].draw();

    RowLayout rowLayout1 = new RowLayout(new Location(150, 370), 300);
    rowLayout1.setStepDelay(10);
    hands[1].setView(this, rowLayout1);
    hands[1].draw();

    ColumnLayout columnLayout0 = new ColumnLayout(new Location(370, 390), 400);
    hands[2].setView(this, columnLayout0);
    hands[2].draw();

    ColumnLayout columnLayout1 = new ColumnLayout(new Location(470, 390), 400);
    columnLayout1.setScaleFactor(0.7);
    columnLayout1.setStepDelay(10);
    hands[3].setView(this, columnLayout1);
    hands[3].draw();

    FanLayout fanLayout = new FanLayout(new Location(300, 700), 600, 250, 290);
    hands[4].setView(this, fanLayout);
    hands[4].draw();
  }

  public static void main(String[] args)
  {
    new Ex01();
  }
}
