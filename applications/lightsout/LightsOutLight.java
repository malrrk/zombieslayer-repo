// LightsOutLight.java

import ch.aplu.jgamegrid.*;
import java.util.*;
import java.awt.*;

public class LightsOutLight extends GameGrid implements GGMouseListener
{
  public LightsOutLight()
  {
    super(5, 5, 50, Color.black, false);
    setTitle("LightsOutLight");
    for (int i = 0; i < 5; i++)
    {
      for (int k = 0; k < 5; k++)
      {
        Actor actor = new Actor("sprites/lightout.gif", 2);
        addActor(actor, new Location(i, k));
        actor.show(1);
      }
    }
    addMouseListener(this, GGMouse.lPress);
    show();
  }

  public boolean mouseEvent(GGMouse mouse)
  {
    Location location = toLocationInGrid(mouse.getX(), mouse.getY());
    ArrayList<Location> locations = location.getNeighbourLocations(0.5);
    locations.add(new Location(location.x, location.y));
    for (Location loc : locations)
    {
      if (isInGrid(loc))
      {
        Actor a = getOneActorAt(loc);
        a.showNextSprite();
      }
    }
    refresh();
    return true;
  }

  public static void main(String[] args)
  {
    new LightsOutLight();
  }
}
