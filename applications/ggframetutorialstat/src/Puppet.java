// Puppet.java

import ch.aplu.jgamegrid.*;
import java.awt.Point;

class Puppet extends Actor
{
  private GamePane gp;
  private NavigationPane np;
  private int cellIndex = 0;
  private int nbSteps;
  private Connection currentCon = null;
  private int y;
  private int dy;

  Puppet(GamePane gp, NavigationPane np)
  {
    super("sprites/cat.gif");
    this.gp = gp;
    this.np = np;
  }

  void go(int nbSteps)
  {
    if (cellIndex == 100)  // after game over
    {
      cellIndex = 0;
      setLocation(gp.startLocation);
    }
    this.nbSteps = nbSteps;
    setActEnabled(true);
  }

  private void moveToNextCell()
  {
    int tens = cellIndex / 10;
    int ones = cellIndex - tens * 10;
    if (tens % 2 == 0)     // Cells starting left 01, 21, .. 81
    {
      if (ones == 0 && cellIndex > 0)
      {
        setLocation(new Location(getX(), getY() - 1));
        setHorzMirror(false);
      }
      else
        setLocation(new Location(getX() + 1, getY()));
    }
    else     // Cells starting left 20, 40, .. 100
    {
      if (ones == 0)
      {
        setLocation(new Location(getX(), getY() - 1));
        setHorzMirror(true);
      }
      else
        setLocation(new Location(getX() - 1, getY()));
    }
    cellIndex++;
  }

  public void act()
  {
    // Animation: Move on connection
    if (currentCon != null)
    {
      int x = gp.x(y, currentCon);
      setPixelLocation(new Point(x, y));
      y += dy;

      // Check end of connection
      if ((dy > 0 && (y - gp.toPoint(currentCon.locEnd).y) > 0)
        || (dy < 0 && (y - gp.toPoint(currentCon.locEnd).y) < 0))
      {
        setActEnabled(false);
        setLocation(currentCon.locEnd);
        cellIndex = currentCon.cellEnd;
        setLocationOffset(new Point(0, 0));
        currentCon = null;
        np.prepareNextRoll(cellIndex);
      }
      return;
    }

    // Normal movement
    if (nbSteps > 0)
    {
      moveToNextCell();

      if (cellIndex == 100)  // Game over
      {
        setActEnabled(false);
        np.prepareNextRoll(cellIndex);
        return;
      }

      nbSteps--;
      if (nbSteps == 0)
      {
        // Check if on connection start
        if ((currentCon = gp.getConnectionAt(getLocation())) != null)
        {
            setActEnabled(false);
            setLocation(currentCon.locEnd);
            cellIndex = currentCon.cellEnd;
            currentCon = null;
            np.prepareNextRoll(cellIndex);
        }
        else
        {
          setActEnabled(false);
          np.prepareNextRoll(cellIndex);
        }
      }
    }

  }

}
