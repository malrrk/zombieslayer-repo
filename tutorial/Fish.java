// Fish.java
// Extends Actor and overrides act()
// Used in Ex01.java

import ch.aplu.jgamegrid.*;

public class Fish extends Actor
{
  public Fish()
  {
    super("sprites/nemo.gif");
  }

  // Called in every simulation cycle
  public void act()
  {
    move();
    if (!isMoveValid())
      turn(180);
  }
}
