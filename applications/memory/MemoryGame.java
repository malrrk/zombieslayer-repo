// MemoryGame.java
// Memory puzzle

import ch.aplu.jgamegrid.*;
import ch.aplu.util.*;

public class MemoryGame extends GameGrid implements GGMouseListener
{
  // ------------- Inner class MemoryCard --------------
  private class MemoryCard extends Actor
  {
    private int id;

    public MemoryCard(int id)
    {
      super("sprites/card" + id + ".gif", "sprites/cardback.gif");
      this.id = id;
    }

    public int getId()
    {
      return id;
    }
  }
  // ------------- End of inner class ------------------
  //
  private boolean isReady = true;
  private MemoryCard card1;
  private MemoryCard card2;

  public MemoryGame()
  {
    super(4, 4, 115, null, null, false);
    MemoryCard[] cards = new MemoryCard[16];

    for (int i = 0; i < 16; i++)
    {
      if (i < 8)
        cards[i] = new MemoryCard(i);
      else
        cards[i] = new MemoryCard(i - 8);
      addActor(cards[i], getRandomEmptyLocation());
      cards[i].show(1);
    }
    addMouseListener(this, GGMouse.lPress);
    doRun();
    show();
    // Application thread used to flip back cards
    while (true)
    {
      Monitor.putSleep();  // Wait until there is something to do
      delay(1000);
      card1.show(1);  // Flip cards back
      card2.show(1);
      isReady = true;
      setMouseEnabled(true);  // Rearm mouse events
    }
  }

  public boolean mouseEvent(GGMouse mouse)
  {
    Location location = toLocation(mouse.getX(), mouse.getY());
    MemoryCard card = (MemoryCard)getOneActorAt(location);
    if (card.getIdVisible() == 0)  // Card already flipped->no action
      return true;

    card.show(0); // Show picture
    if (isReady)
    {
      isReady = false;
      card1 = card;
    }
    else
    {
      card2 = card;
      if (card1.getId() == card2.getId())  // Pair found, let them visible
        isReady = true;
      else
      {
        setMouseEnabled(false); // Disable mouse events until application thread flipped back cards
        Monitor.wakeUp();
      }
    }
    return true;
  }

  public static void main(String[] args)
  {
    new MemoryGame();
  }
}
