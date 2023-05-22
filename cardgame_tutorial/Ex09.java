// Ex09.java
// random batch transfer

import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.*;

public class Ex09 extends CardGame
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
  private Hand upperHand;
  private Hand lowerHand;

  public Ex09()
  {
    super(460, 400);

    Hand[] hands = deck.dealingOut(2, 5, true);
    upperHand = hands[0];
    upperHand.setView(this, new RowLayout(new Location(230, 100), 400));
    lowerHand = hands[1];
    lowerHand.setView(this, new RowLayout(new Location(230, 300), 400));
    sortAndDrawHands();
    initToolBar();
  }

  private void sortAndDrawHands()
  {
    upperHand.sort(Hand.SortType.RANKPRIORITY, true);
    lowerHand.sort(Hand.SortType.RANKPRIORITY, true);
  }

  private void initToolBar()
  {
    final ToolBarItem upBtn = new ToolBarItem("sprites/up30.gif", 2);
    final ToolBarStack numbers = new ToolBarStack("sprites/number30.gif", 10);
    final ToolBarItem downBtn = new ToolBarItem("sprites/down30.gif", 2);
    ToolBar toolBar = new ToolBar(this);
    toolBar.addItem(upBtn, numbers, downBtn);
    toolBar.show(new Location(160, 185));
    numbers.show(3);  // Default start number

    toolBar.addToolBarListener(new ToolBarAdapter()
    {
      public void leftPressed(ToolBarItem item)
      {
        if (item == upBtn)
        {
          upBtn.show(1);
          Hand.randomBatchTransfer(numbers.getItemId(),
            lowerHand, upperHand, false);
          sortAndDrawHands();
        }
        if (item == downBtn)
        {
          downBtn.show(1);
          Hand.randomBatchTransfer(numbers.getItemId(),
            upperHand, lowerHand, false);
          sortAndDrawHands();
        }
        if (item == numbers)
          ((ToolBarStack)item).showNext();
      }

      public void leftReleased(ToolBarItem item)
      {
        if (item == upBtn)
          upBtn.show(0);
        if (item == downBtn)
          downBtn.show(0);
      }
    });
  }

  public static void main(String[] args)
  {
    new Ex09();
  }
}
