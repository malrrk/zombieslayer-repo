// Ex07a.java
// Poll keys, better implementation

import ch.aplu.jgamegrid.*;
import java.awt.event.KeyEvent;
import java.awt.Color;

public class Ex07a extends GameGrid
{
  private Clownfish fish;
  private boolean isSpaceKeyDown = false;

  public Ex07a()
  {
    super(10, 10, 60, Color.red, false);
    setSimulationPeriod(50);
    setTitle("Space bar for new object, cursor to move up/down");
    doRun();
    show();
  }

  public void act()
  {
    if (isKeyPressed(KeyEvent.VK_UP) && fish.getY() > 0)
      fish.setY(fish.getY() - 1);
    if (isKeyPressed(KeyEvent.VK_DOWN) && fish.getY() < 9)
      fish.setY(fish.getY() + 1);
    if (isKeyPressed(KeyEvent.VK_SPACE) && !isSpaceKeyDown)
    {
      fish = new Clownfish();
      addActor(fish, new Location(0, 0));
    }
    isSpaceKeyDown = isKeyPressed(KeyEvent.VK_SPACE);
  }

  public static void main(String[] args)
  {
    new Ex07a();
  }
}
