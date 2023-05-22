// Angelfish.java
// Every instance has a GGKeyListener
// Used in Ex07b.java

import java.awt.event.KeyEvent;
import ch.aplu.jgamegrid.*;

public class Angelfish extends Actor implements GGKeyListener
{
  public Angelfish()
  {
    super("sprites/angelfish.gif");
  }

  public void act()
  {
    move();
    if (getX() < 0 || getX() > 599)
    {
      turn(180);
      setHorzMirror(!isHorzMirror());
    }
  }

  public boolean keyPressed(KeyEvent evt)
  {
    switch (evt.getKeyCode())
    {
      case KeyEvent.VK_UP:
        if (getY() > 0)
          setY(getY() - 1);
        break;
      case KeyEvent.VK_DOWN:
        if (getY() < 599)
          setY(getY() + 1);
        break;
    }
    return false;  // Don't consume the key
  }

  public boolean keyReleased(KeyEvent evt)
  {
    return false;
  }
}
