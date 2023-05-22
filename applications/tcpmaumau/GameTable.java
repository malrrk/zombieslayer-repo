// GameTable.java
// For debugging see comments // Debug
// Every instance "knows":
// - personal name of all players and
// - the index of the current player
// - the index of player that has the turn

import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.*;
import java.awt.*;
import ch.aplu.tcp.*;

public class GameTable extends CardGame
  implements GGButtonListener
{
  public static enum Suit
  {
    SPADES, HEARTS, DIAMONDS, CLUBS
  }

  public static enum Rank
  {
    ACE, KING, QUEEN, JACK, TEN, NINE, EIGHT, SEVEN
  }
  //
  protected static Deck deck = new Deck(Suit.values(), Rank.values(), "cover");
  protected static Hand startTalon = new Hand(deck);
  private final String info = "It's your turn. "
    + "Hint: Double click on one of your cards or on the talon.";
  private final String info1 = "Wait for your turn.";
  private final int nbPlayers = 4;
  private final int nbStartCards = 5;
  private final int handWidth = 300;
  private final Location[] handLocations =
  {
    new Location(300, 525),
    new Location(75, 300),
    new Location(300, 75),
    new Location(525, 300)
  };
  private final Location[] textLocations =
  {
    new Location(460, 590),
    new Location(5, 475),
    new Location(460, 10),
    new Location(595, 475)
  };
  private final Location talonLocation = new Location(250, 300);
  private final Location pileLocation = new Location(350, 300);
  private final Location textLocation = new Location(300, 400);
  private Hand[] hands = new Hand[nbPlayers];
  private Hand pile = new Hand(deck);  // Playing stack
  private Hand talon = new Hand(deck); // Stock
  private GGButton okBtn = new GGButton("sprites/done30.gif", true);
  private Location btnLocation = new Location(100, 580);
  private Location hideLocation = new Location(-500, - 500);
  private Actor toolBarTextActor = new Actor("sprites/text_item.png");
  private Location toolBarTextLocation = new Location(525, 550);
  private ToolBarItem spadeItem = new ToolBarItem("sprites/spades_item.png", 2);
  private ToolBarItem heartItem = new ToolBarItem("sprites/hearts_item.png", 2);
  private ToolBarItem diamondItem = new ToolBarItem("sprites/diamonds_item.png", 2);
  private ToolBarItem clubItem = new ToolBarItem("sprites/clubs_item.png", 2);
  private ToolBar toolBar;
  private Location toolBarLocation = new Location(470, 567);
  private Actor trumpActor = null;
  private Location trumpActorLocation = new Location(350, 200);
  private Suit trumpSuit = null;
  private int trumpSuitId = -1;
  private TcpAgent agent;
  private int currentPlayerIndex;
  private int turnPlayerIndex;
  private String[] playerNames;
  private BlinkingName[] blinkingNames = new BlinkingName[nbPlayers];
  private boolean isFatalError = false;

  public GameTable(final TcpAgent agent, String[] playerNames,
    int currentPlayerIndex)
  {
    super(600, 600, 30);
    this.agent = agent;
    this.playerNames = new String[nbPlayers];
    for (int i = 0; i < nbPlayers; i++)
      this.playerNames[i] = playerNames[i];
    this.currentPlayerIndex = currentPlayerIndex;
    if (TcpMauMau.debug)
      Card.noVerso = true;
    setTitle("TcpMauMau V" + TcpMauMau.version + ". Current player's name: "
      + playerNames[currentPlayerIndex]);

    for (int i = 0; i < playerNames.length; i++)
      blinkingNames[i] = new BlinkingName(playerNames[i]);

    if (TcpMauMau.debug)
    {
      switch (currentPlayerIndex)
      {
        case 0:
          setPosition(20, 0);
          break;
        case 1:
          setPosition(625, 0);
          break;
        case 2:
          setPosition(20, 300);
          break;
        case 3:
          setPosition(625, 300);
          break;
      }
    }
    addActor(okBtn, hideLocation);
    okBtn.addButtonListener(this);
    toolBarTextActor.hide();
    addActor(toolBarTextActor, toolBarTextLocation);
    toolBar = new ToolBar(this);
    toolBar.addItem(spadeItem, heartItem, diamondItem, clubItem);
    toolBar.show(hideLocation);
    toolBar.addToolBarListener(new ToolBarAdapter()
    {
      public void leftPressed(ToolBarItem item)  // Trump selection done
      {
        if (item == spadeItem)
          trumpSuit = Suit.SPADES;
        if (item == heartItem)
          trumpSuit = Suit.HEARTS;
        if (item == diamondItem)
          trumpSuit = Suit.DIAMONDS;
        if (item == clubItem)
          trumpSuit = Suit.CLUBS;

        trumpSuitId = deck.getSuitId(trumpSuit);
        showTrumpSelection(false);
        agent.sendCommand("", Command.NEXT_TURN, trumpSuitId);
      }

      public void leftReleased(ToolBarItem item)
      {
      }
    });
  }

  protected void initHands()
  {
    for (int i = 0; i < nbPlayers; i++)
    {
      hands[i] = new Hand(deck);
      for (int k = 0; k < nbStartCards; k++)
      {
        hands[i].insert(startTalon.get(i * nbStartCards + k), false);
      }
    }
    for (int i = nbStartCards * nbPlayers; i < startTalon.getNumberOfCards(); i++)
      talon.insert(startTalon.get(i), true);

    Card top = talon.getLast();
    talon.remove(top, false);
    pile.insert(top, false);
    talon.setVerso(true);

    talon.setView(this, new StackLayout(talonLocation));
    talon.draw();
    pile.setView(this, new StackLayout(pileLocation));
    pile.draw();

    hands[currentPlayerIndex].sort(Hand.SortType.SUITPRIORITY, false);
    for (int i = 0; i < nbPlayers; i++)
    {
      if (i != currentPlayerIndex)
        hands[i].setVerso(true);
    }

    RowLayout[] layouts = new RowLayout[nbPlayers];
    for (int i = 0; i < nbPlayers; i++)
    {
      int k = (currentPlayerIndex + i) % nbPlayers;
      layouts[k] = new RowLayout(handLocations[i], handWidth);
      layouts[k].setRotationAngle(90 * i);
      hands[k].setView(this, layouts[k]);
      hands[k].setTargetArea(new TargetArea(pileLocation));
      if (k == currentPlayerIndex)
        layouts[k].setStepDelay(10);
      else
        layouts[k].setStepDelay(0);
      hands[k].draw();
      addActor(blinkingNames[k], textLocations[i]);
      if (i == nbPlayers - 1)  // Right align text
        blinkingNames[k].setLocationOffset(new Point(-blinkingNames[k].getTextWidth(), 0));
    }
    layouts[currentPlayerIndex].setStepDelay(0);

    hands[currentPlayerIndex].addCardListener(new CardAdapter()
    {
      public void leftDoubleClicked(Card card)  // Click to deliver card to pile
      {
        if (trumpActor == null)  // No trump
        {
          Card revealed = pile.getLast();
          if (card.getRank() == Rank.JACK
            || card.getRank() == revealed.getRank()
            || card.getSuit() == revealed.getSuit())
          {
            agent.sendCommand("", Command.CARD_TO_PILE, card.getCardNumber());
            setMouseTouchEnabled(currentPlayerIndex, false);
            okBtn.setLocation(hideLocation);
            card.transfer(pile, true);
            if (checkOver(currentPlayerIndex))
              return;
            if (card.getRank() == Rank.JACK)
              showTrumpSelection(true);
            else
              agent.sendCommand("", Command.NEXT_TURN, trumpSuitId);
          }
          else
            setStatusText("Selected " + card + " forbidden.");
        }
        else  // Got trump
        {
          if (card.getRank() == Rank.JACK
            || card.getSuit() == trumpSuit)
          {
            agent.sendCommand("", Command.CARD_TO_PILE, card.getCardNumber());
            setMouseTouchEnabled(currentPlayerIndex, false);
            okBtn.setLocation(hideLocation);
            card.transfer(pile, true);
            removeTrumpActor();
            if (card.getRank() == Rank.JACK)
              showTrumpSelection(true);
            else
              agent.sendCommand("", Command.NEXT_TURN, trumpSuitId);
          }
          else
            setStatusText("Selected " + card + " forbidden.");
        }
      }
    });

    talon.addCardListener(new CardAdapter()  // Player reclaims card from talon
    {
      public void leftDoubleClicked(Card card)
      {
        agent.sendCommand("", Command.CARD_FROM_TALON);
        setMouseTouchEnabled(currentPlayerIndex, false);
        talon.setTargetArea(new TargetArea(handLocations[0]));
        card.transfer(hands[currentPlayerIndex], false);
        hands[currentPlayerIndex].sort(Hand.SortType.SUITPRIORITY, true);
        card.setVerso(false);
        removeTrumpActor();
        if (!checkReshuffle())
        {
           if (isFatalError)
            {
              agent.sendCommand("", Command.FATAL_ERROR);
              setMouseTouchEnabled(currentPlayerIndex, false);
              return;
            }

          // Check if you can play it
          Card top = pile.getLast();
          if (card.getRank() == Rank.JACK
            || card.getRank() == top.getRank()
            || card.getSuit() == top.getSuit())
            showOkBtn();
          else
          {
            setMouseTouchEnabled(currentPlayerIndex, false);
            agent.sendCommand("", Command.NEXT_TURN, trumpSuitId);
          }
        }
      }
    });
  }

  private void showTrumpSelection(boolean show)
  {
    if (show)
    {
      setStatusText("Select a trump suit mandatory for the next player");
      toolBar.setLocation(toolBarLocation);
      toolBarTextActor.show();
      toolBar.setOnTop(CardActor.class);
    }
    else
    {
      toolBar.setLocation(hideLocation);
      toolBarTextActor.hide();
    }
  }

  private void setMouseTouchEnabled(int nbPlayer, boolean enable)
  {
    talon.setTouchEnabled(enable);
    hands[nbPlayer].setTouchEnabled(enable);
  }

  protected void setMyTurn()
  {
    setMouseTouchEnabled(currentPlayerIndex, true);
    for (int i = 0; i < nbPlayers; i++)
      blinkingNames[i].setBlinkingEnabled(false);
    blinkingNames[currentPlayerIndex].setBlinkingEnabled(true);
    setStatusText(info);
  }

  protected void setOtherTurn(String turnPlayer)
  {
    setStatusText(turnPlayer + " is playing. " + info1);
    setTurnPlayerIndex(turnPlayer);
    for (int i = 0; i < nbPlayers; i++)
      blinkingNames[i].setBlinkingEnabled(false);
    blinkingNames[turnPlayerIndex].setBlinkingEnabled(true);
  }

  protected void showOkBtn()
  {
    setMouseTouchEnabled(currentPlayerIndex, true);
    setStatusText("Press 'Done' to continue or double click on one of"
      + " your cards to move it to the pile");
    setPaintOrder(GGButton.class);
    okBtn.setLocation(btnLocation);
  }

  private boolean checkReshuffle()
  // Returns true, if reshuffling is necessary
  {
    if (talon.isEmpty())  // Talon empty, need to shuffle
    {
      if (pile.getNumberOfCards() < 2)
      {
        isFatalError = true;
        return false;
      }
      setStatusText("Must reshuffle talon. Please wait...");
      agent.sendCommand("", Command.REQUEST_RESHUFFLE);
      return true;
    }
    return false;
  }

  protected void reshuffle()
  {
    // Talon reconstructed using cards from pile (no shuffling!)
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

    // Remove card cover
    cardCover.removeSelf();

    agent.sendCommand("", Command.RESHUFFLE_DONE);
  }

  private boolean checkOver(int nbPlayer)
  {
    if (hands[nbPlayer].isEmpty())
    {
      addActor(new Actor("sprites/gameover.gif"), textLocation);
      setStatusText("Game over. Winner is player: " + playerNames[nbPlayer]);
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
    setMouseTouchEnabled(currentPlayerIndex, false);
    okBtn.setLocation(hideLocation);
    agent.sendCommand("", Command.NEXT_TURN, trumpSuitId);
  }

  private Actor getTrumpActor(int suitId)
  {
    Suit suit = (Suit)deck.getSuit(suitId);
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

  protected void drawStartTalon()
  {
    startTalon.draw();
  }

  private void removeTrumpActor()
  {
    if (trumpActor != null)
    {
      trumpActor.removeSelf();
      trumpActor = null;
      trumpSuit = null;
      trumpSuitId = -1;
    }
  }

  protected void moveCardFromTalon()
  {
    removeTrumpActor();
    Card card = talon.getLast();
    int k = turnPlayerIndex - currentPlayerIndex;
    if (k < 0)
      k = nbPlayers + k;
    talon.setTargetArea(new TargetArea(handLocations[k]));
    talon.transfer(card, hands[turnPlayerIndex], true);
  }

  protected void moveCardToPile(int cardNumber)
  {
    Card card = hands[turnPlayerIndex].getCard(cardNumber);
    card.setVerso(false);
    hands[turnPlayerIndex].transfer(card, pile, true);
    removeTrumpActor();
    checkOver(turnPlayerIndex);
  }

  private void setTurnPlayerIndex(String turnPlayer)
  {
    int index = 0;
    for (index = 0; index < nbPlayers; index++)
    {
      if (playerNames[index].equals(turnPlayer))
      {
        turnPlayerIndex = index;
        return;
      }
    }
  }

  protected void setTrumpActor(int trumpSuitId)
  {
    trumpActor = getTrumpActor(trumpSuitId);
    trumpSuit = (Suit)deck.getSuit(trumpSuitId);
    addActor(trumpActor, trumpActorLocation);
  }

  protected void stopGame(String client)
  {
     setStatusText(client + " disconnected. Game stopped.");
      setMouseEnabled(false);
      doPause();
  }
}
