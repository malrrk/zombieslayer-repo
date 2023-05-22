// Ex06a.java
// Several GGMouseListeners
// Click to create a new actor, drag an existing actor

import ch.aplu.jgamegrid.*;
import java.awt.*;

public class Ex06a extends GameGrid implements GGMouseListener
{
  public Ex06a()
  {
    super(10, 10, 50, Color.green, false);
    setTitle("Click to create a honey pot, press and drag to move it");
    addActor(new Bear(Honey.class), new Location(0, 0));
    addMouseListener(this, GGMouse.lPress);
    show();
    doRun();
  }

  public boolean mouseEvent(GGMouse mouse)
  {
    Location location = toLocationInGrid(mouse.getX(), mouse.getY());
    if (isEmpty(location))  // Do not create an actor if cell is occupied
    {
      Honey honey = new Honey();
      addActor(honey, location);
      addMouseListener(honey, GGMouse.lPress | GGMouse.lDrag | GGMouse.lRelease);
    }
    return false;  // Don't consume the event, other listeners must be notified
  }

  public static void main(String[] args)
  {
    new Ex06a();
  }
}
