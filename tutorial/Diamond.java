// Diamond.java

import ch.aplu.jgamegrid.*;

public class Diamond extends Actor
{
  public Diamond()
  {
    super("sprites/diamond.gif", 4); // 4 sprites
  }

  // This method runs in animation thread
  // May interfere with mouse callback
  public void act()
  {
    if (!isMoveValid())
      removeSelf();
    else
    {
      showNextSprite();
      move();
    }
  }
}
