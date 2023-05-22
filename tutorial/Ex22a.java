// Ex22a.java

import ch.aplu.jgamegrid.*;
import java.awt.*;

public class Ex22a extends GameGrid 
 implements GGMouseTouchListener, GGMouseListener
{
  private Point hotSpot;
  private Actor nemo = new Actor("sprites/nemo.gif"); 

  public Ex22a()
  {
    super(300, 300, 1, false);
    setTitle("Drag Nemo!");
    addMouseListener(this, GGMouse.lDrag);
    nemo.addMouseTouchListener(this,
      GGMouse.lPress | GGMouse.lRelease);
    addActor(nemo, new Location(150, 150));
    show();
  }
  
  public boolean mouseEvent(GGMouse mouse)
  {
    if (hotSpot == null)
      return true;
    Location loc =
      new Location(mouse.getX() - hotSpot.x, mouse.getY() - hotSpot.y);
    nemo.setLocation(loc);
    refresh();
    return true;
  }

  public void mouseTouched(Actor actor, GGMouse mouse, Point spot)
  {
    switch (mouse.getEvent())
    {
      case GGMouse.lPress:
        hotSpot = spot;
        break;
    
      case GGMouse.lRelease:
        hotSpot = null;
        break;
    }
  }

  public static void main(String[] args)
  {
    new Ex22a();
  }
}
