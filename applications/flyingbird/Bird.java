// FlyingBird.java
// Used in FlyingBird.java

import ch.aplu.jgamegrid.*;

public class Bird extends Actor
{
  public Bird()
  {
    super("sprites/bird.gif", 10);
  }

  public void act()
  {
    move();
    showNextSprite();
  }
}
