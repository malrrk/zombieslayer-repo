// Langton.java
// Simulation of Langton's ant  

import ch.aplu.jgamegrid.*;
import java.awt.Color;

public class Langton extends GameGrid
{
  private final Actor ant = new Actor(true, "sprites/ant.gif");
  private final String title = "JGameGrid - Langton's Ant. # Steps: ";

  public Langton()
  {
    super(100, 100, 6, new Color(240, 240, 240), true);
    setSimulationPeriod(10);
    setBgColor(Color.white);
    addActor(ant, new Location(50, 50), -90);
    reset();
    show();
  }
  
  public void reset()
  {
    getBg().clear();
    setTitle(title + getNbCycles());
  }

  public void act()
  {
    setTitle(title + (getNbCycles() + 1));
    Location loc = ant.getLocation();
    GGBackground bg = getBg();
    Color c = bg.getColor(loc);
    if (c.equals(Color.white))
    {
      bg.fillCell(loc, Color.black, false);
      ant.setDirection(ant.getDirection() + 90);
    }
    else
    {
      bg.fillCell(loc, Color.white, false);
      ant.setDirection(ant.getDirection() - 90);
    }
    ant.move(1);
    if (ant.isNearBorder())
      doPause();
  }

  public static void main(String[] args)
  {
    new Langton();
  }
}
