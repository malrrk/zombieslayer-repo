// Fish.java
// Extends Actor and overrides act()

import ch.aplu.jgamegrid.*;

public class Fish extends Actor
{

  public Fish()
  {
    super("sprites/nemo.gif");
  }

  public void act()
  {
    if (isRemoved())
      return;
    setX(getX() + 10);
    if (getX() > 600)
      removeSelf();
  }
}
