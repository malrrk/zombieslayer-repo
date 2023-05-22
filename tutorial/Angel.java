// Angel.java
// Used in Ex11.java

import ch.aplu.jgamegrid.*;

public class Angel extends Actor
{
  public Angel()
  {
    super("sprites/angel.gif");
  }

  public void act()
  {
    Location loc = getLocation();
    double dir = getDirection();
    if (loc.x < 50 || loc.x > 550)
    {
      setDirection(180 - dir);
      if (loc.x < 50)
      {
        setX(55);
        setHorzMirror(false);
      }
      else
      {
        setX(545);
        setHorzMirror(true);
      }
    }
    if (loc.y < 50 || loc.y > 550)
    {
      setDirection(360 - dir);
      if (loc.y < 50)
        setY(55);
      else
        setY(545);
    }
    move();
  }
}


