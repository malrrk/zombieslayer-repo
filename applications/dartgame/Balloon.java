// Balloon.java
// Used for DartGame

import ch.aplu.jgamegrid.*;

public class Balloon extends Actor
{
  private boolean inHeaven = false;
  private int step = 1;

  public Balloon()
  {
    super("sprites/balloon.gif");
  }

  public void reset()
  {
    inHeaven = false;
  }
  
  public void act()
  {
    if (!inHeaven)
    {
      setY(getY() - 1);
      if (getY() < 30)
      {
        inHeaven = true;
        setActorCollisionEnabled(false);
      }
    }
    else
    {
      if (getX() <= 0 || getX() >= 600)
        step = -step;
      setX(getX() + step);
    }
  }
}
