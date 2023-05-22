// Bug.java
// Used for maze
// canMove() checks the background color of the cell (black for the wall)
// refresh() is necessary because the simulation loop does not run

import ch.aplu.jgamegrid.*;
import java.awt.event.KeyEvent;
import java.awt.*;

public class Bug extends Actor implements GGKeyListener
{
  private Location startLocation;
  private Location exitLocation;
  private boolean isGameOver = false;

  public Bug(GGMaze maze)
  {
    super(true, "sprites/smallbug.gif");  // Rotatable
    startLocation = maze.getStartLocation();
    exitLocation = maze.getExitLocation();
  }

  public boolean keyPressed(KeyEvent evt)
  {
    if (isGameOver)
      System.exit(0);

    Location next = null;
    switch (evt.getKeyCode())
    {
      case KeyEvent.VK_LEFT:
        if (getLocation().equals(startLocation))
          return true;
        next = getLocation().getNeighbourLocation(Location.WEST);
        setDirection(Location.WEST);
        break;
      case KeyEvent.VK_UP:
        next = getLocation().getNeighbourLocation(Location.NORTH);
        setDirection(Location.NORTH);
        break;
      case KeyEvent.VK_RIGHT:
        if (getLocation().equals(exitLocation))
          return true;
        next = getLocation().getNeighbourLocation(Location.EAST);
        setDirection(Location.EAST);
        break;
      case KeyEvent.VK_DOWN:
        next = getLocation().getNeighbourLocation(Location.SOUTH);
        setDirection(Location.SOUTH);
        break;
    }
    if (next != null && canMove(next))
    {
      setLocation(next);
      gameGrid.refresh();  // Necessary because the simulation loop does  not run
    }
    if (next != null && next.equals(exitLocation))
    {
      GGBackground bg = getBackground();
      bg.clear(Color.red);
      bg.drawText("Game over. Press any key...", new Point(100, 100));
      isGameOver = true;
      gameGrid.refresh();
    }
    return true;
  }

  public boolean keyReleased(KeyEvent evt)
  {
    return true;
  }

  private boolean canMove(Location location)
  {
    Color c = getBackground().getColor(location);
    if (c.equals(Color.black))
      return false;
    else
      return true;
  }
}
