// Ex07b.java
// Poll keys

import ch.aplu.jgamegrid.*;
import java.awt.event.KeyEvent;
import java.awt.Color;

public class Ex07b extends GameGrid
{
  private Clownfish fish;

  public Ex07b()
  {
    super(10, 10, 60, Color.red, false);
    setTitle("Space bar for new object, cursor to move up/down");
    doRun();
    show();
  }

  public void act()
  {
    if (kbhit())
    {
      switch (getKeyCode())
      {
        case KeyEvent.VK_UP:
          if (fish.getY() > 0)
            fish.setY(fish.getY() - 1);
          break;
        case KeyEvent.VK_DOWN:
          if (fish.getY() < 9)
            fish.setY(fish.getY() + 1);
          break;
        case KeyEvent.VK_SPACE:
        {
          fish = new Clownfish();
          addActor(fish, new Location(0, 0));
        }
      }
    }
  }

  public static void main(String[] args)
  {
    new Ex07b();
  }
}
