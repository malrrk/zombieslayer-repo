// Ship.java


import ch.aplu.jgamegrid.*;
import java.awt.event.KeyEvent;

public abstract class Ship extends Actor
  implements GGMouseListener, GGKeyListener
{
  private int size;
  private Location[] cells;   // Current occupied locations
  private boolean[] isHit;
  private Location lastLocation;
  private boolean isDragging = false;
  private boolean isMouseEnabled = true;

  public Ship(String img, int size)
  {
    super(true, img, 2); // Rotatable, 2 images
    this.size = size;
    cells = new Location[size];
    isHit = new boolean[size];
  }

  public void reset()
  {
    show(1);
    Location loc = getLocationStart();
    for (int i = 0; i < size; i++)
    {
      cells[i] = new Location(loc.x + i, loc.y);
      isHit[i] = false;
    }
  }

  public boolean mouseEvent(GGMouse mouse)
  {
    if (!isMouseEnabled)
      return false;
    Location location = gameGrid.toLocationInGrid(mouse.getX(), mouse.getY());
    switch (mouse.getEvent())
    {
      case GGMouse.lPress:
        lastLocation = location.clone();
        if (gameGrid.getOneActorAt(location) == this)
          isDragging = true;
        break;
      case GGMouse.lDrag:
        if (isDragging && advance(location, 0))
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
    updateCells(getLocation());
    return false;
  }

  private void updateCells(Location loc)
  {
    int dir = getIntDirection();
    for (int i = 0; i < size; i++)
    {
      if (dir == 0)
        cells[i] = new Location(loc.x + i, loc.y);
      if (dir == 90)
        cells[i] = new Location(loc.x, loc.y + i);
      if (dir == 180)
        cells[i] = new Location(loc.x - i, loc.y);
      if (dir == 270)
        cells[i] = new Location(loc.x, loc.y - i);
    }
  }

  public boolean keyPressed(KeyEvent evt)
  {
    if (!isDragging)
      return false;
    switch (evt.getKeyCode())
    {
      case KeyEvent.VK_UP:
        advance(getLocation(), 90);
        break;
      case KeyEvent.VK_DOWN:
        advance(getLocation(), -90);
        break;
    }
    return false;  // Don't consume the key
  }

  public boolean keyReleased(KeyEvent evt)
  {
    return false;
  }

  private boolean advance(Location loc, int angle)
  // Try to advance to new location/direction
  // Return false, if outside grid or overlap with another ship
  {
    turn(angle);

    boolean ok = true;
    // Save current cell locations
    Location[] currentCells = new Location[size];
    for (int i = 0; i < size; i++)
      currentCells[i] = cells[i].clone();

    // Update cell locations
    updateCells(loc);

    // Check if all cells are in grid
    for (int i = 0; i < size; i++)
    {
      if (!gameGrid.isInGrid(cells[i]))
        ok = false;
    }

    // If ok, check for overlap
    if (ok)
    {
      for (Actor a : gameGrid.getActors(Ship.class))
      {
        if (a != this && overlap((Ship)a))
          ok = false;
      }
    }

    // If failed, restore old cells and old direction
    if (!ok)
    {
      for (int i = 0; i < size; i++)
        cells[i] = currentCells[i].clone();
      turn(-angle);
    }
    return ok;
  }

  private boolean overlap(Ship a)
  // Check if current ship overlaps with given ship a
  {
    for (int i = 0; i < size; i++)
      for (int k = 0; k < a.size; k++)
        if (cells[i].equals(a.cells[k]))
          return true;
    return false;
  }

  public String hit(Location loc)
  {
    String reply = null;
    for (int i = 0; i < size; i++)
    {
      if (cells[i].equals(loc))
      {
        isHit[i] = true;
        gameGrid.addActor(new Fire(), loc);
        int status = getStatus();
        if (status == 0)
          reply = "hit";
        if (status == 1)
          reply = "sunk";
        if (status == 2)
          reply = "allSunk";
      }
    }
    if (reply == null)
      reply = "miss";
    return reply;
  }

  private int getStatus()
  // 0: hit
  // 1: ship sunk
  // 2: all ships sunk
  {
    boolean allHit = true;
    for (int i = 0; i < size; i++)
      allHit = allHit && isHit[i];  // false, if one or more locs are not hit
    if (allHit)
    {
      removeSelf();
      for (int i = 0; i < size; i++)
        gameGrid.removeActorsAt(cells[i], Fire.class);
      if (gameGrid.getNumberOfActors(Ship.class) == 0)
      {
       
        return 2;
      }
      return 1;
    }
    return 0;
  }

  protected void setMouseEnabled(boolean b)
  {
    isMouseEnabled = b;
  }

}
