// NumberBlock.java
// Used for FifteenPuzzle.java

import ch.aplu.jgamegrid.*;

public class NumberBlock extends Actor implements GGMouseListener
{
  private Location location;
  private Location lastLocation;
  private boolean isDragging = false;
  private int num;

  public NumberBlock(int id)
  {
    super("sprites/stone" + id + ".gif");
    num = id + 1;
  }

  public void act()
  {
    if (isDragging)
    {
      setLocation(lastLocation);
      // Blinking
      if (isVisible())
        hide();
      else
        show();
    }
  }
  
  public int getNum()
  {
    return num;
  }

  private boolean isFreeNeighbourCell(Location location)
  {
    GameGrid gg = gameGrid;
    Location[] neighbour = new Location[4];
    neighbour[0] = new Location(location.x, location.y - 1);  // NORTH
    neighbour[1] = new Location(location.x - 1, location.y);  // WEST
    neighbour[2] = new Location(location.x, location.y + 1);  // SOUTH
    neighbour[3] = new Location(location.x + 1, location.y);  // EAST
    for (int i = 0; i < 4; i++)
    {
      if (gg.isInGrid(neighbour[i]) && gg.getNumberOfActorsAt(neighbour[i]) == 0)
        return true;
    }
     return false;
  }

  public boolean mouseEvent(GGMouse mouse)
  {
    GameGrid gg = gameGrid;
    location = gg.toLocationInGrid(mouse.getX(), mouse.getY());
    Actor actor = gg.getOneActorAt(location);
    if (actor != null && // Cell not empty
      isFreeNeighbourCell(location) &&  // One free cell to move to
      actor == this && // Restrict to current instance
      mouse.getEvent() == GGMouse.lPress)
    {
      isDragging = true;
      lastLocation = location.clone();
    }
    if (isDragging && mouse.getEvent() == GGMouse.lRelease)
    {
      setLocation(lastLocation);
      isDragging = false;
      show();
    }
    if (isDragging && mouse.getEvent() == GGMouse.lDrag)
    {
      if (gg.getNumberOfActorsAt(location) < 1) // Cell not occupied
        lastLocation = location.clone();
    }
    return false;  // Don't consume
  }
}
