// Ex05a.java
// Many non-rotatable actors of same class

import ch.aplu.jgamegrid.*;

public class Ex05a extends GameGrid
{
  public Ex05a()
  {
    super(800, 600, 1, false);
    setSimulationPeriod(40);
    setBgColor(new java.awt.Color(100, 100, 100));
    for (int i = 0; i < 3; i++)
    {
      Player player = new Player();
      Location startLocation = new Location(200 + 100 * i, 125);
      int startDirection = 15 * i;
      addActor(player, startLocation, startDirection);
    }
    show();
    doRun();
  }

  public static void main(String[] args)
  {
    new Ex05a();
  }
}
