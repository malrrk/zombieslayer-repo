// Ex07.java
// Poll keys, bad implementation

import ch.aplu.jgamegrid.*;
import java.awt.event.KeyEvent;
import java.awt.Color;

public class Ex07 extends GameGrid
{
  private Clownfish fish = null;

  public Ex07()
  {
    super(10, 10, 60, Color.red, false);
    setTitle("Space bar for new object, cursor to move up/down");
    doRun();
    show();
  }

  public void act()
  {
    if (isKeyPressed(KeyEvent.VK_SPACE))
    {
      fish = new Clownfish();
      addActor(fish, new Location(0, 0));
    }
    if (fish == null)
      return;
    if (isKeyPressed(KeyEvent.VK_UP) && fish.getY() > 0)
      fish.setY(fish.getY() - 1);
    if (isKeyPressed(KeyEvent.VK_DOWN) && fish.getY() < 9)
      fish.setY(fish.getY() + 1);
  }

  public static void main(String[] args)
  {
    new Ex07();
  }
}
