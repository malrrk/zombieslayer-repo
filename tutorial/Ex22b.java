// Ex22b.java

import ch.aplu.jgamegrid.*;
import java.awt.*;

public class Ex22b extends GameGrid
  implements GGMouseTouchListener, GGMouseListener
{
  private Actor touchedActor = null;
  private Point hotSpot;

  public Ex22b()
  {
    super(5, 5, 100, Color.red, false);
    setTitle("Drag Nemo!");
    addMouseListener(this, GGMouse.lDrag);
    for (int i = 0; i < 4; i++)
    {
      Actor nemo = new Actor("sprites/nemo.gif");
      addActor(nemo, getRandomEmptyLocation());
      nemo.addMouseTouchListener(this, GGMouse.lPress | GGMouse.lRelease, true);
    }
    show();
  }

  public boolean mouseEvent(GGMouse mouse)
  {
    if (touchedActor != null)
    {
      Point pt =
        new Point(mouse.getX() - hotSpot.x, mouse.getY() - hotSpot.y);
      touchedActor.setPixelLocation(pt);
      refresh();
    }
    return false;
  }

  public void mouseTouched(Actor actor, GGMouse mouse, Point spot)
  {
    switch (mouse.getEvent())
    {
      case GGMouse.lPress:
        touchedActor = actor;
        touchedActor.setOnTop();
        hotSpot = spot;
        break;
      case GGMouse.lRelease:
        touchedActor.setLocationOffset(new Point(0, 0));
        touchedActor = null;
        break;
    }
    refresh();
  }

  public static void main(String[] args)
  {
    new Ex22b();
  }

}
