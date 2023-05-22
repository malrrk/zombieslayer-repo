// Ex22.java

import ch.aplu.jgamegrid.*;
import java.awt.*;

public class Ex22 extends GameGrid implements GGMouseTouchListener
{
  private Point hotSpot;

  public Ex22()
  {
    super(300, 300, 1, false);
    setTitle("Drag Nemo!");
    Actor nemo = new Actor("sprites/nemo.gif");
    nemo.addMouseTouchListener(this,
      GGMouse.lPress | GGMouse.lDrag | GGMouse.lRelease);
    addActor(nemo, new Location(150, 150));
    show();
  }

  public void mouseTouched(Actor actor, GGMouse mouse, Point spot)
  {
    switch (mouse.getEvent())
    {
      case GGMouse.lPress:
        hotSpot = spot;
        break;
      case GGMouse.lDrag:
        if (hotSpot == null)  // Pressed outside sprite
          hotSpot = spot;
        Location loc = 
          new Location(mouse.getX() - hotSpot.x, mouse.getY() - hotSpot.y);
        actor.setLocation(loc);
        refresh();
        break;
      case GGMouse.lRelease:
        hotSpot = null;
        break;
    }
  }

  public static void main(String[] args)
  {
    new Ex22();
  }
}
