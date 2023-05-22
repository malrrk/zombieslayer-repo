// MauMau1.java
// For debugging see comments // Debug

import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.*;
import java.util.*;

public class MauMau1 extends CardGame implements GGButtonListener
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
  private final String version = "2.0";
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
  private final int thinkingTime = 2000;
  private Hand[] hands;
  private Hand pile = new Hand(deck);  // Playing stack
  private Hand talon; // Stock
  private boolean isPartnerMoves = false;
  private GGButton okBtn = new GGButton("sprites/done30.gif", true);
  private Location btnLocation = new Location(480, 580);
  private Location hideLocation = new Location(-500, - 500);

  public MauMau1()
  {
    super(600, 600, 30);
//    Card.noVerso = true;    // Debug
    setTitle("Mau-Mau (V" + version + ") Constructed with JGameGrid (www.aplu.ch)");
    setStatusText("Initializing...");
    initHands();
    addActor(okBtn, hideLocation);
    okBtn.addButtonListener(this);

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
//          Deck.showHeapSize();  // Debug
        }
        setMyMove();
      }
//      Deck.showHeapSize(); // Debug
      delay(100);
    }
  }

  private void initHands()
  {
//    hands = deck.dealingOut(nbPlayers, nbStartCards, false);  // Debug
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

    hands[0].addCardListener(new CardAdapter()
    {
      public void leftDoubleClicked(Card card)
      {
        Card revealed = pile.getLast();
        if (card.getRank() == revealed.getRank()
          || card.getSuit() == revealed.getSuit())
        {
          setMouseTouchEnabled(false);
          okBtn.setLocation(hideLocation);
          card.transfer(pile, true);
        }
        else
          setStatusText("Selected " + card + " forbidden.");
      }

      public void atTarget(Card card, Location targetLocation)
      {
        hands[0].draw();
        if (!checkOver(0))
          setPartnerMoves();
      }
    });

    talon.addCardListener(new CardAdapter()
    {
      public void leftDoubleClicked(Card card)
      {
        setMouseTouchEnabled(false);
        card.setVerso(false);
        talon.setTargetArea(new TargetArea(handLocations[0]));
        card.transfer(hands[0], false);
        talon.draw();
      }

      public void atTarget(Card card, Location targetLocation)
      {
        if (targetLocation.equals(handLocations[0]))
        {
          card.setVerso(false);
          setStatusText("Card drawn: " + card);
          hands[0].sort(Hand.SortType.SUITPRIORITY, true);
          if (checkTalon())
            waitOk();
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
    setStatusText("Press 'Done' to continue or double click on one of your cards to move it to the pile");
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
    Card revealed = pile.getLast();
    // Get list of cards that are allowed
    ArrayList<Card> allowed = new ArrayList<Card>();
    for (Card card : hands[nbPlayer].getCardList())
      if (card.getRank() == revealed.getRank()
        || card.getSuit() == revealed.getSuit())
        allowed.add(card);

    talon.setTargetArea(new TargetArea(handLocations[nbPlayer]));
    if (allowed.isEmpty())
    {
      Card top = talon.getLast();
      top.transfer(hands[nbPlayer], true);
      talon.draw();
      top.setVerso(true);
      return checkTalon();
    }

    Card selectedCard = allowed.get(0);   // Other strategy here
    selectedCard.setVerso(false);
    selectedCard.transfer(pile, true);
    hands[nbPlayer].sort(Hand.SortType.SUITPRIORITY, true);
    return true;
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

  public static void main(String[] args)
  {
    new MauMau1();
  }
}
