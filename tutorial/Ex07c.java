// Ex07c.java
// Use GGKeyListener

import ch.aplu.jgamegrid.*;
import java.awt.event.KeyEvent;

public class Ex07c extends GameGrid implements GGKeyListener
{
  public Ex07c()
  {
    super(600, 600, 1, null, "sprites/reef.gif", false);
    setTitle("Space bar for new fish, cursor to move up/down");
    setSimulationPeriod(50);
    addKeyListener(this);
    doRun();
    show();
  }

  public boolean keyPressed(KeyEvent evt)
  {
    if (evt.getKeyCode() == KeyEvent.VK_SPACE)
    {
      Angelfish fish = new Angelfish();
      addActor(fish, new Location(300, 300));
      addKeyListener(fish);
    }
    return false;  // Don't consume
  }

  public boolean keyReleased(KeyEvent evt)
  {
    return false;
  }

  public static void main(String[] args)
  {
    new Ex07c();
  }
}
