// Rocket.java
// Used in Ex10.java

import ch.aplu.jgamegrid.*;

public class Earth extends Actor
{
  private static final int nbSprites = 18;

  public Earth()
  {
    super("sprites/earth.gif", nbSprites);
    setSlowDown(6);
  }

  public void act()
  {
    showNextSprite();
  }
}
