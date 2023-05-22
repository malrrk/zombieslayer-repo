// Ex08.java
// Cut a card deck (hand)

import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.*;
import java.awt.Color;

public class Ex08 extends CardGame
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
  private Hand hand = deck.dealingOut(1, 5, false)[0];

  public Ex08()
  {
    super(300, 200);
    hand.setView(this, new RowLayout(new Location(150, 80), 290));
    hand.draw();
    initToolBar();
  }

  private void initToolBar()
  {
    final ToolBarStack numbers =
      new ToolBarStack("sprites/number30.gif", 10);
    ToolBarSeparator separator =
      new ToolBarSeparator(2, 30, Color.black);
    final ToolBarItem cutBtn =
      new ToolBarItem("sprites/cutBtn.gif", 3);
    ToolBar toolBar = new ToolBar(this);
    toolBar.addItem(numbers, separator, cutBtn);
    toolBar.show(new Location(7, 160));

    toolBar.addToolBarListener(new ToolBarAdapter()
    {
      public void leftPressed(ToolBarItem item)
      {
        if (item == cutBtn)
        {
          cutBtn.show(1);
          hand.cut(numbers.getItemId(), true);
        }
        else
          ((ToolBarStack)item).showNext();
      }

      public void leftReleased(ToolBarItem item)
      {
        if (item == cutBtn)
          item.show(0);
      }
    });
  }

  public static void main(String[] args)
  {
    new Ex08();
  }
}
