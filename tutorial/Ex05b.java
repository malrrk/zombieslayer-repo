// Ex5b.java
// Several rotatable multi-sprite actors like PacMan ghosts

import ch.aplu.jgamegrid.*;

public class Ex05b extends GameGrid
{
  public Ex05b()
  {
    super(800, 600, 1, null, true);
    setSimulationPeriod(20);
    setBgColor(new java.awt.Color(100, 100, 100));
    Ghost[] ghost = new Ghost[7];
    for (int i = 0; i < 7; i++)
    {
      ghost[i] = new Ghost();
      Location startLocation = new Location(310 + 30 * i, 100);
      int startDirection = 66 + 66 * i;
      addActor(ghost[i], startLocation, startDirection);
    }
    show();
  }

  public static void main(String[] args)
  {
    new Ex05b();
  }
}
