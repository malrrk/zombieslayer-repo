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
        setLocation(new Location(getX(), getY() - 1));
      else
        setLocation(new Location(getX() + 1, getY()));
    }
    else     // Cells starting left 20, 40, .. 100
    {
      if (ones == 0)
        setLocation(new Location(getX(), getY() - 1));
      else
        setLocation(new Location(getX() - 1, getY()));
    }
    cellIndex++;
  }

  public void act()
  {
    if ((cellIndex / 10) % 2 == 0)
    {
      if (isHorzMirror())
        setHorzMirror(false);
    }
    else
    {
      if (!isHorzMirror())
        setHorzMirror(true);
    }

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
        gp.setSimulationPeriod(100);
        setActEnabled(false);
        setLocation(currentCon.locEnd);
        cellIndex = currentCon.cellEnd;
        setLocationOffset(new Point(0, 0));
        currentCon = null;
        np.prepareRoll(cellIndex);
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
        np.prepareRoll(cellIndex);
        return;
      }

      nbSteps--;
      if (nbSteps == 0)
      {
        // Check if on connection start
        if ((currentCon = gp.getConnectionAt(getLocation())) != null)
        {
          gp.setSimulationPeriod(50);
          y = gp.toPoint(currentCon.locStart).y;
          if (currentCon.locEnd.y > currentCon.locStart.y)
            dy = gp.animationStep;
          else
            dy = -gp.animationStep;
          if (currentCon instanceof Snake)
          {
            np.showStatus("Digesting...");
            np.playSound(GGSound.MMM);
          }
          else
          {
            np.showStatus("Climbing...");
            np.playSound(GGSound.BOING);
          }
        }
        else
        {
          setActEnabled(false);
          np.prepareRoll(cellIndex);
        }
      }
    }
  }

}
