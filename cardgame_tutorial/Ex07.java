// Ex07.java
// Image files for e.g. for spades:
// spades_0..spades_<nbRanks-1> for standard
// spades_<nbRanks>..spades_<2*nbRanks-1> for selected

import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.*;
import java.awt.Color;

public class Ex07 extends CardGame
{
  public enum Suit
  {
    SPADES, HEARTS, DIAMONDS, CLUBS
  }

  public enum Rank
  {
    ACE, KING, QUEEN, JACK, TEN, NINE, EIGHT, SEVEN, SIX, FIVE, FOUR, THREE, TWO
  }
  //
  private int nbRanks = Rank.values().length;
  private Deck deck = new Deck(Suit.values(), Rank.values(), "cover");
  private Hand hand = new Hand(deck);
  private ToolBarText textItem =
    new ToolBarText("Select Card:", 30);
  private ToolBarSeparator separator0 =
    new ToolBarSeparator(2, 30, Color.black);
  private ToolBarStack spades =
    new ToolBarStack("sprites/spades.gif", nbRanks);
  private ToolBarStack hearts =
    new ToolBarStack("sprites/hearts.gif", nbRanks);
  private ToolBarStack diamonds =
    new ToolBarStack("sprites/diamonds.gif", nbRanks);
  private ToolBarStack clubs =
    new ToolBarStack("sprites/clubs.gif", nbRanks);
  private ToolBarSeparator separator1 =
    new ToolBarSeparator(2, 30, Color.black);
  private ToolBarItem okBtn =
    new ToolBarItem("sprites/ok30.gif", 2);

  public Ex07()
  {
    super(300, 250, 30);
    setStatusText("Select a card by clicking on the card stacks and press OK.");
    hand.setView(this, new RowLayout(new Location(150, 125), 290));
    hand.draw();
    initToolBar();
  }

  private void initToolBar()
  {
    ToolBar toolBar = new ToolBar(this);
    toolBar.addItem(textItem, separator0, spades, hearts, diamonds,
      clubs, separator1, okBtn);
    toolBar.show(new Location(10, 10));

    toolBar.addToolBarListener(new ToolBarAdapter()
    {
      public void leftPressed(ToolBarItem item)
      {
        if (item == okBtn)
        {
          okBtn.show(1);
          Card card = getSelectedCard(deck);
          if (card != null)
          {
            if (hand.insert(card, true))
            {
              setStatusText("Card " + card + " successfully inserted.");
              if (hand.getNumberOfCards() == 2)
                initSortBtn();

            }
            else
              setStatusText("Failed to insert card " + card
                + " (no duplication allowed)");
          }
        }
        else
        {
          ToolBarStack stackItem = (ToolBarStack)item;
          if (stackItem.isSelected())
            stackItem.showNext();
          else
          {
            deselectAll();
            stackItem.setSelected(true);
          }
        }
      }

      public void leftReleased(ToolBarItem item)
      {
        if (item == okBtn)
          item.show(0);
      }
    });
  }

  private void initSortBtn()
  {
    ToolBar toolBar = new ToolBar(this);
    final ToolBarItem sortBtn = new ToolBarItem("sprites/sortBtn.gif", 2);
    toolBar.addItem(sortBtn);
    toolBar.show(new Location(233, 212));
    toolBar.addToolBarListener(new ToolBarAdapter()
    {
      public void leftPressed(ToolBarItem item)
      {
        sortBtn.show(1);
        hand.sort(Hand.SortType.RANKPRIORITY, true);
      }

      public void leftReleased(ToolBarItem item)
      {
        sortBtn.show(0);
      }
    });
  }

  private void deselectAll()
  {
    spades.setSelected(false);
    hearts.setSelected(false);
    diamonds.setSelected(false);
    clubs.setSelected(false);
  }

  private Card getSelectedCard(Deck deck)
  // Assumes that only one card stack is selected
  // Returns null, if no card is selected
  {
    Card card = null;
    int[] ids = ToolBarStack.getSelectedItemIds();
    for (int i = 0; i < ids.length; i++)
    {
      int id = ids[i];
      System.out.println(id);
      if (id != -1)  // Selected item found
      {
        deck.getSuit(i);
        deck.getRank(id);
        card = new Card(deck, deck.getSuit(i), deck.getRank(id));
        break;
      }
    }
    return card;
  }
 
  public static void main(String[] args)
  {
    new Ex07();
  }
}
