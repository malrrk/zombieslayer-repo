// Explosion.java
// Used for SpaceInvader

import ch.aplu.jgamegrid.*;

public class Explosion extends Actor
{
  public Explosion()
  {
    super("sprites/explosion1.gif");
    setSlowDown(3);
  }

  public void act()
  {
    removeSelf();
  }
}
