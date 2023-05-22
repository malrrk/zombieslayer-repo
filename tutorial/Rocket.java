// Rocket.java
// Used in Ex10a.java

import ch.aplu.jgamegrid.*;

public class Rocket extends Actor
{
  public Rocket()
  {
    super("sprites/rocket.gif");
  }

  public void act()
  {
    move();
    if (getX() < -100 || getX() > 700)
    {
      turn(180);
      if (isHorzMirror())
      {
        setHorzMirror(false);
        gameGrid.setPaintOrder(Rocket.class);  // Rocket in foreground
      }
      else
      {
        setHorzMirror(true);
        gameGrid.setPaintOrder(Earth.class);  // Earth in foreground
      }
    }
  }
}
