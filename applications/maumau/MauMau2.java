// MauMau2.java

import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.*;
import java.util.*;

public class MauMau2 extends CardGame
  implements GGButtonListener
{
  public enum Suit
  {
    SPADES, HEARTS, DIAMONDS, CLUBS
  }

  public enum Rank
  {
    ACE, KING, QUEEN, JACK, TEN, NINE, EIGHT, SEVEN
  }
  //

  private final String version = "3.1";
  private final String info = "It's your move. "
    + "Hint: Double click on one of your cards or on the talon.";
  private final int nbPlayers = 4;
  private final int nbStartCards = 5;
  private final int handWidth = 300;
  private final Deck deck = new Deck(Suit.values(), Rank.values(), "cover");
  private final Location[] handLocations =
  {
    new Location(300, 525),
    new Location(75, 300),
    new Location(300, 75),
    new Location(525, 300)
  };
  private final Location talonLocation = new Location(250, 300);
  private final Location pileLocation = new Location(350, 300);
  private final Location textLocation = new Location(300, 400);
  private final int thinkingTime = 3000;
  private Hand[] hands;
  private Hand pile = new Hand(deck);  // Playing stack
  private Hand talon; // Stock
  private boolean isPartnerMoves = false;
  private GGButton okBtn = new GGButton("sprites/done30.gif", true);
  private Location btnLocation = new Location(100, 580);
  private Location hideLocation = new Location(-500, - 500);
  private Actor toolBarTextActor = new Actor("sprites/text_item.png");
  private Location toolBarTextLocation = new Location(502, 550);
  private ToolBarItem spadeItem = new ToolBarItem("sprites/spades_item.png", 2);
  private ToolBarItem heartItem = new ToolBarItem("sprites/hearts_item.png", 2);
  private ToolBarItem diamondItem = new ToolBarItem("sprites/diamonds_item.png", 2);
  private ToolBarItem clubItem = new ToolBarItem("sprites/clubs_item.png", 2);
  private ToolBar toolBar;
  private Location toolBarLocation = new Location(470, 567);
  private Actor trumpActor = null;
  private Suit trump = null;
  private Location trumpActorLocation = new Location(350, 200);

  public MauMau2()
  {
    super(600, 600, 30);
    setTitle("Mau-Mau (V" + version + ") Constructed with JGameGrid (www.aplu.ch)");
    setStatusText("Initializing...");
    initHands();
    addActor(okBtn, hideLocation);
    okBtn.addButtonListener(this);
    toolBarTextActor.hide();
    addActor(toolBarTextActor, toolBarTextLocation);
    toolBar = new ToolBar(this);
    toolBar.addItem(spadeItem, heartItem, diamondItem, clubItem);
    toolBar.show(hideLocation);
    toolBar.addToolBarListener(new ToolBarAdapter()
    {
      public void leftPressed(ToolBarItem item)
      {
        if (item == spadeItem)
          trump = Suit.SPADES;
        if (item == heartItem)
          trump = Suit.HEARTS;
        if (item == diamondItem)
          trump = Suit.DIAMONDS;
        if (item == clubItem)
          trump = Suit.CLUBS;

        trumpActor = getTrumpActor(trump);
        addActor(trumpActor, trumpActorLocation);
        showTrumpSelection(false);
        setPartnerMoves();
      }

      public void leftReleased(ToolBarItem item)
      {
      }

    });

    while (true)
    {
      if (isPartnerMoves)
      {
        isPartnerMoves = false;
        for (int i = 1; i < nbPlayers; i++)
        {
          setStatusText("Player " + i + " thinking...");
          delay(thinkingTime);

          if (!simulateMove(i))  // Error: no cards available talon
            return;
          if (checkOver(i))
            return;
        }
        setMyMove();
      }
      delay(100);
    }
  }

  private void initHands()
  {
    hands = deck.dealingOut(nbPlayers, nbStartCards, true);
    talon = hands[nbPlayers];

    Card top = talon.getLast();
    talon.remove(top, true);
    pile.insert(top, true);

    hands[0].sort(Hand.SortType.SUITPRIORITY, true);

    RowLayout[] layouts = new RowLayout[nbPlayers];
    for (int i = 0; i < nbPlayers; i++)
    {
      layouts[i] = new RowLayout(handLocations[i], handWidth);
      layouts[i].setRotationAngle(90 * i);
      hands[i].setView(this, layouts[i]);
      hands[i].setTargetArea(new TargetArea(pileLocation));
      if (i == 0)
        layouts[i].setStepDelay(10);
      hands[i].draw();
    }
    layouts[0].setStepDelay(0);

    for (int i = 1; i <= nbPlayers; i++)
      hands[i].setVerso(true);

    talon.setView(this, new StackLayout(talonLocation));
    talon.draw();
    pile.setView(this, new StackLayout(pileLocation));
    pile.draw();

    hands[0].addCardListener(new CardAdapter()  // Player 0 plays card
    {
      public void leftDoubleClicked(Card card)
      {
        if (trumpActor == null)  // No trump
        {
          Card revealed = pile.getLast();

          if (card.getRank() == Rank.JACK
            || card.getRank() == revealed.getRank()
            || card.getSuit() == revealed.getSuit())
          {
            setMouseTouchEnabled(false);
            okBtn.setLocation(hideLocation);
            card.transfer(pile, true);
          }
          else
            setStatusText("Selected " + card + " forbidden.");
        }
        else  // Got trump
        {
          if (card.getRank() == Rank.JACK
            || card.getSuit() == trump)
          {
            setMouseTouchEnabled(false);
            okBtn.setLocation(hideLocation);
            card.transfer(pile, true);
            removeTrumpActor();
          }
          else
            setStatusText("Selected " + card + " forbidden.");

        }
      }

      public void atTarget(Card card, Location targetLocation)
      {
        hands[0].draw();
        if (!checkOver(0))
        {
          if (pile.getLast().getRank() == Rank.JACK)
            showTrumpSelection(true);
          else
            setPartnerMoves();
        }
      }

    });

    talon.addCardListener(new CardAdapter()  // Player 0 reclaims card from talon
    {
      public void leftDoubleClicked(Card card)
      {
        setMouseTouchEnabled(false);
        card.setVerso(false);
        talon.setTargetArea(new TargetArea(handLocations[0]));
        card.transfer(hands[0], true);
        talon.draw();
      }

      public void atTarget(Card card, Location targetLocation)
      {
        if (targetLocation.equals(handLocations[0]))
        {
          card.setVerso(false);
          hands[0].sort(Hand.SortType.SUITPRIORITY, true);
          if (checkTalon())
          {
            // Check if you can play it
            Card top = pile.getLast();
            if (card.getRank() == Rank.JACK
              || card.getRank() == top.getRank()
              || card.getSuit() == top.getSuit())
              waitOk();
            else
              setPartnerMoves();
          }
        }
        for (int i = 1; i < nbPlayers; i++)
        {
          if (targetLocation.equals(handLocations[i]))
          {
            card.setVerso(true);
            hands[i].sort(Hand.SortType.SUITPRIORITY, true);
          }
        }
      }

    });
    setMyMove();
  }

  private void showTrumpSelection(boolean show)
  {
    if (show)
    {
      setStatusText("Select a trump suit mandatory for the next player");
      toolBar.setLocation(toolBarLocation);
      toolBarTextActor.show();
    }
    else
    {
      toolBar.setLocation(hideLocation);
      toolBarTextActor.hide();
    }
  }

  private void setMouseTouchEnabled(boolean enable)
  {
    talon.setTouchEnabled(enable);
    hands[0].setTouchEnabled(enable);
  }

  private void setMyMove()
  {
    setMouseTouchEnabled(true);
    setStatusText(info);
  }

  private void waitOk()
  {
    setMouseTouchEnabled(true);
    setStatusText("Press 'Done' to continue or double click on one of"
      + " your cards to move it to the pile");
    okBtn.setLocation(btnLocation);
  }

  private void setPartnerMoves()
  {
    isPartnerMoves = true;
    setStatusText("Wait other player's draw.");
  }

  private boolean simulateMove(int nbPlayer)
  // Returns false, if checkDrawingStack() fails
  {
    // Check if a Jack available and play it
    for (Card card : hands[nbPlayer].getCardList())
    {
      if (card.getRank() == Rank.JACK)  // Yes, play it
      {
        removeTrumpActor();
        card.setVerso(false);
        card.transfer(pile, true);
        hands[nbPlayer].sort(Hand.SortType.SUITPRIORITY, true);
        selectTrumpActor(nbPlayer);
        delay(2000);
        return true;
      }
    }

    // Get list of cards that are allowed
    ArrayList<Card> allowed = new ArrayList<Card>();
    if (trumpActor == null)  // No trump imposed
    {
      Card revealed = pile.getLast();
      for (Card card : hands[nbPlayer].getCardList())
        if (card.getRank() == revealed.getRank()
          || card.getSuit() == revealed.getSuit())
          allowed.add(card);
    }
    else  // Trump imposed
    {
      for (Card card : hands[nbPlayer].getCardList())
      {
        if (card.getSuit() == trump)
          allowed.add(card);
      }
    }
    talon.setTargetArea(new TargetArea(handLocations[nbPlayer]));
    if (allowed.isEmpty())
    {
      Card card = talon.getLast();
      card.transfer(hands[nbPlayer], true);
      removeTrumpActor();
      talon.draw();
      card.setVerso(true);
      delay(500);
      // Check if we can play it
      Card top = pile.getLast();
      if (card.getRank() == Rank.JACK || card.getRank() == top.getRank()
        || card.getSuit() == top.getSuit())
      {
        card.transfer(pile, true);
        card.setVerso(false);
      }
      if (card.getRank() == Rank.JACK)
        selectTrumpActor(nbPlayer);

      return checkTalon();
    }

    Card selectedCard = allowed.get(0);   // Other strategy here
    selectedCard.setVerso(false);
    selectedCard.transfer(pile, true);
    removeTrumpActor();
    hands[nbPlayer].sort(Hand.SortType.SUITPRIORITY, true);
    return true;
  }

  private void selectTrumpActor(int nbPlayer)
  {
    int max = 0;
    Suit maxSuit = Suit.SPADES;
    for (Suit suit : Suit.values())
    {
      int nb = hands[nbPlayer].getNumberOfCardsWithSuit(suit);
      if (nb > max)
      {
        max = nb;
        maxSuit = suit;
      }
    }
    trump = maxSuit;
    trumpActor = getTrumpActor(trump);
    addActor(trumpActor, trumpActorLocation);
  }

  private boolean checkTalon()
  // Returns false, if check fails because there are no cards available
  {
    if (talon.isEmpty())  // Talon empty, need to shuffle
    {
      if (pile.getNumberOfCards() < 2)
      {
        setStatusText("Fatal error: No cards available for talon");
        doPause();
        setMouseEnabled(false);
        return false;
      }

      // Show info text
      Actor actor = new Actor("sprites/reshuffle.gif");
      addActor(actor, textLocation);

      // Move animated card cover from playing pile to talon
      CardCover cardCover =
        new CardCover(this, pileLocation, deck, 1, 0, false);
      cardCover.slideToTarget(talonLocation, 2, false, true); // On bottom

      // Save card on pile top and remove it
      Card topCard = pile.getLast();
      pile.remove(topCard, false);
      // Shuffle cards
      pile.shuffle(false);
      // Hide all pictures
      pile.setVerso(true);
      // Insert into talon
      for (Card card : pile.getCardList())
        talon.insert(card, false);
      // Cleanup playing pile
      pile.removeAll(false);
      // Insert saved card
      pile.insert(topCard, false);
      // Redraw piles
      pile.draw();
      talon.draw();

      // Remove info text
      delay(2000);
      actor.removeSelf();
    }
    return true;
  }

  private boolean checkOver(int nbPlayer)
  {
    if (hands[nbPlayer].isEmpty())
    {
      addActor(new Actor("sprites/gameover.gif"), textLocation);
      setStatusText("Game over. Winner is player: " + nbPlayer);
      for (int i = 0; i < nbPlayers; i++)
        hands[i].setVerso(false);   // Show player hands
      refresh();
      setMouseEnabled(false);
      doPause();
      return true;
    }
    return false;
  }

  public void buttonPressed(GGButton button)
  {
  }

  public void buttonReleased(GGButton button)
  {
  }

  public void buttonClicked(GGButton button)
  {
    okBtn.setLocation(hideLocation);
    setPartnerMoves();
  }

  private Actor getTrumpActor(Suit suit)
  {
    switch (suit)
    {
      case SPADES:
        return new Actor("sprites/bigspade.gif");
      case HEARTS:
        return new Actor("sprites/bigheart.gif");
      case DIAMONDS:
        return new Actor("sprites/bigdiamond.gif");
      case CLUBS:
        return new Actor("sprites/bigclub.gif");
    }
    return null;
  }

  private void removeTrumpActor()
  {
    if (trumpActor != null)
    {
      trumpActor.removeSelf();
      trumpActor = null;
      trump = null;
    }
  }

  public static void main(String[] args)
  {
    new MauMau2();
  }

}
