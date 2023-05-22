// RightPaddle.java
// Used for Pong

import ch.aplu.jgamegrid.*;
import java.awt.event.KeyEvent;

public class RightPaddle extends Actor implements GGKeyRepeatListener
{
  private int nbHits = 0;
  private int height;

  public RightPaddle(int height)
  {
    super("sprites/rightpaddle.gif");
    this.height = height;
  }

  public void keyRepeated(int keyCode)
  {
    Location next = null;
    switch (keyCode)
    {
      case KeyEvent.VK_P:
        next = getLocation().getAdjacentLocation(Location.NORTH);
        moveTo(next);
        break;

      case KeyEvent.VK_L:
        next = getLocation().getAdjacentLocation(Location.SOUTH);
        moveTo(next);
        break;
    }
  }

  public boolean keyReleased(KeyEvent evt)
  {
    return true;
  }

  private void moveTo(Location location)
  {
    if (location.y > 30 && location.y < height - 30)
      setLocation(location);
  }

  public int collide(Actor actor1, Actor actor2)
  {
    double direction = actor2.getDirection();
    if (direction > 270)
      direction = direction - 360;
    if (direction > -90 && direction < 90)
    {
      double dir = 180 - direction;
      actor2.setDirection(dir);
      nbHits++;
  //    gameGrid.playSound(this, GGSound.PING);
    }
    return 10;
  }

  protected int getNbHits()
  {
    return nbHits;
  }
}
