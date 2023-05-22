// Ex10a.java
// Set paint order

import ch.aplu.jgamegrid.*;

public class Ex10a extends GameGrid
{
  public Ex10a()
  {
    super(600, 200, 1, null, false);
    setSimulationPeriod(50);

    Earth earth = new Earth();
    addActor(earth, new Location(300, 100));

    // Rocket added last->default paint order: in front of earth
    Rocket rocket = new Rocket();
    addActor(rocket, new Location(200, 100));
    
    show();
    doRun();
  }

  public static void main(String[] args)
  {
    new Ex10a();
  }
}
