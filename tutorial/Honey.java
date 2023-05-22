// Honey.java
// Every instance has its own GGMouseListener
// Used in Ex06a.java

import ch.aplu.jgamegrid.*;

public class Honey extends Actor implements GGMouseListener
{
  private Location lastLocation;
  private boolean isDragging = false;

  public Honey()
  {
    super("sprites/honey.gif");
  }

  public boolean mouseEvent(GGMouse mouse)
  {
    Location location = gameGrid.toLocationInGrid(mouse.getX(), mouse.getY());
    switch (mouse.getEvent())
    {
      case GGMouse.lPress:
        if (gameGrid.getOneActorAt(location) == this)  // Must restrict to current instance
          isDragging = true;
        lastLocation = location.clone();
        break;
      case GGMouse.lDrag:
        if (isDragging && gameGrid.isEmpty(location)) // Prevent to drag at occupied location
        {
          setLocation(location);
          lastLocation = location.clone();
        }
        break;
      case GGMouse.lRelease:
        if (isDragging)
        {
          setLocation(lastLocation);
          isDragging = false;
        }
        break;
    }
    return false;  // Don't consume the event, other listener must be notified
  }
}
