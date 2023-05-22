// Ex03.java

import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.*;
import java.awt.*;

public class Ex03 extends CardGame implements GGMouseTouchListener
{
  public enum Suit
  {
    SPADES, HEARTS, DIAMONDS, CLUBS
  }

  public enum Rank
  {
    ACE, KING, QUEEN, JACK, TEN
  }
  //
  private Deck deck = new Deck(Suit.values(), Rank.values(), "cover");
  private Point hotspot = new Point(0, 0);
  private Hand talon;
  private Hand hand;
  private final Location talonLocation = new Location(250, 300);
  private final Location handLocation = new Location(250, 100);

  public Ex03()
  {
    super(500, 400, 30);

    Hand[] hands = deck.dealingOut(1, 2);  // only two cards in hand
    hand = hands[0];
    hand.setView(this, new RowLayout(handLocation, 400));
    talon = hands[1];
    talon.setView(this, new StackLayout(talonLocation));
    hand.draw();
    talon.draw();
    takeFromTalon();
    setStatusText("Drag cards from card talon to the hand");
  }

  private void insertInHand(Actor actor)
  {
    actor.removeSelf();
    Card card = ((CardActor)actor).getCard();
    hand.insert(card, false);
    hand.sort(Hand.SortType.RANKPRIORITY, true);
    takeFromTalon();
  }

  private void takeFromTalon()
  {
    Card top = talon.getLast();
    if (top == null)  // talon empty
      return;
    System.out.println("top = " + top);
    talon.remove(top, false);  // Remove from talon and game grid
    addActor(top.getCardActor()); // Reinsert into game grid
  }

  private void addActor(CardActor cardActor)
  {
    addActor(cardActor, new Location(talonLocation));
    cardActor.addMouseTouchListener(this,
      GGMouse.lPress | GGMouse.lDrag | GGMouse.lRelease, true);
  }

  public void mouseTouched(Actor actor, GGMouse mouse, Point spot)
  {
    switch (mouse.getEvent())
    {
      case GGMouse.lPress:
        hotspot = spot;
        break;

      case GGMouse.lDrag:
        actor.setLocation(
          new Location(mouse.getX() - hotspot.x, mouse.getY() - hotspot.y));
        if (actor.getLocation().getDistanceTo(handLocation) < 50)
          insertInHand(actor);
        break;

      case GGMouse.lRelease:
        insertInHand(actor);
        break;
    }
  }

  public static void main(String[] args)
  {
    new Ex03();
  }
}