// Ghost.java
// Used for PacMan

import ch.aplu.jgamegrid.*;
import java.awt.Color;
import java.util.*;

public class Ghost extends Actor
{
  private PacMan pacMan;
  private int type;
  private ArrayList<Location> visitedList = new ArrayList<Location>();
  private final int listLength = 10;

  public Ghost(PacMan pacMan, int type)
  {
    super("sprites/ghost_" + type + ".gif");
    this.pacMan = pacMan;
    this.type = type;
  }

  public void act()
  {
    moveStrategy();
    if (getDirection() > 150 && getDirection() < 210)
      setHorzMirror(false);
    else
      setHorzMirror(true);
  }

  private void moveStrategy()
  {
    Location pacLocation = pacMan.pacActor.getLocation();
    double oldDirection = getDirection();

    // Use an approach strategy:
    // Determine direction to pacActor and try to move in
    // this direction. To avoid looping do not apply the
    // strategy if location has been recently visited
    Location.CompassDirection compassDir =
      getLocation().get4CompassDirectionTo(pacLocation);
    Location next = getLocation().getNeighbourLocation(compassDir);
    setDirection(compassDir);
    if (type == 0 && !pacMan.grid.isInnerRegion(getLocation()) &&
      !isVisited(next) && canMove(next))
    {
      move();
    }
    else
    {
      // normal movement
      int sign = Math.random() < 0.5 ? 1 : -1;
      setDirection(oldDirection);
      turn(sign * 90);  // Try to turn left/right
      next = getNextMoveLocation();
      if (canMove(next))
      {
        setLocation(next);
      }
      else
      {
        setDirection(oldDirection);
        next = getNextMoveLocation();
        if (canMove(next)) // Try to move forward
        {
          setLocation(next);
        }
        else
        {
          setDirection(oldDirection);
          turn(-sign * 90);  // Try to turn right/left
          next = getNextMoveLocation();
          if (canMove(next))
          {
            setLocation(next);
          }
          else
          {

            setDirection(oldDirection);
            turn(180);  // Turn backward
            next = getNextMoveLocation();
            setLocation(next);
          }
        }
      }
    }
    addVisitedList(next);
  }

  private void addVisitedList(Location location)
  {
    visitedList.add(location);
    if (visitedList.size() == listLength)
      visitedList.remove(0);
  }

  private boolean isVisited(Location location)
  {
    for (Location loc : visitedList)
      if (loc.equals(location))
        return true;
    return false;
  }

  private boolean canMove(Location location)
  {
    Color c = getBackground().getColor(location);
    if (c.equals(Color.gray))
      return false;
    else
      return true;
  }
}
