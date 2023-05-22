// Frog.java
// Used for Frogger game

import ch.aplu.jgamegrid.*;
import java.awt.event.KeyEvent;

public class Frog extends Actor implements GGKeyRepeatListener
{
  private boolean isFinished = false;

  public Frog()
  {
    super("sprites/frog.gif");
  }

  public void act()
  {
    if (getLocation().y < 25)
    {
      if (!isFinished)
      {
        isFinished = true;
        gameGrid.playSound(this, GGSound.FROG);
      }
    }
    else
      isFinished = false;
  }

  public void keyRepeated(int keyCode)
  {
    switch (keyCode)
    {
      case KeyEvent.VK_UP:
        if (!isFinished)
          setY(getLocation().y - 5);
        break;
      case KeyEvent.VK_DOWN:
        setY(getLocation().y + 5);
        break;
      case KeyEvent.VK_LEFT:
        setX(getLocation().x - 5);
        break;
      case KeyEvent.VK_RIGHT:
        setX(getLocation().x + 5);
        break;
    }
  }
  
  public int collide(Actor actor1, Actor actor2)
  {
    gameGrid.playSound(this, GGSound.BOING);
    setLocation(new Location(400, 560));
    setDirection(Location.NORTH);
    return 0;
  }

}
