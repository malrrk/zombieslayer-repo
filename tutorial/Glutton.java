// Glutton.java

import ch.aplu.jgamegrid.*;

public class Glutton extends Actor
{
  public Glutton()
  {
    super(true, "sprites/ghost.gif", 4);
  }

  public void act()
  {
    showNextSprite();
    move();
    if (!isMoveValid())
      removeSelf();
  }
}
