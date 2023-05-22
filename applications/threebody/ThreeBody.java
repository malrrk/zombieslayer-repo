// ThreeBody.java
// Simulation of three-body movement in space

import ch.aplu.jgamegrid.*;

public class ThreeBody extends GameGrid
{
  public ThreeBody()
  {
    super(800, 600, 1, null, true);
    setSimulationPeriod(50);
    CelestialBody body1 = new CelestialBody(1, 1E26, new GGVector(0, 1E4));
    addActor(body1, new Location(200, 150));
    CelestialBody body2 = new CelestialBody(2, 1E27, new GGVector(0, 0));
    addActor(body2, new Location(600, 150));
    CelestialBody body3 = new CelestialBody(3, 1E25, new GGVector(-1E3, 1E4));
    addActor(body3, new Location(400, 50));
    show();
  }

  public static void main(String[] args)
  {
    new ThreeBody();
  }
}
