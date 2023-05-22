// Alien.java
// Used for SpaceInvader

import ch.aplu.jgamegrid.*;

public class Alien extends Actor
{
  private final int maxNbSteps = 16;
  private int nbSteps;

  public Alien()
  {
    super("sprites/alien.gif");
    setSlowDown(7);
  }

  public void reset()
  {
    nbSteps = 7;
  }

  public void act()
  {
    if (nbSteps < maxNbSteps)
    {
      move();
      nbSteps++;
    }
    else
    {
      nbSteps = 0;
      int angle;
      if (getDirection() == 0)
        angle = 90;
      else
        angle = -90;
      turn(angle);
      move();
      turn(angle);
    }
    if (getLocation().y > 90)
      removeSelf();
  }

 


}
