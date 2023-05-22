// Ex23.java
// Dynamic sprite scaling

import ch.aplu.jgamegrid.*;
import java.awt.*;

public class Ex23 extends GameGrid
{
  public Ex23()
  {
    super(300, 300, 1, false);
    setSimulationPeriod(100);
    Actor explosion = new Actor("sprites/explosion.gif");
    addActor(new ScaledActor(explosion), new Location(150, 150));
    show();
    doRun();
  }

  public static void main(String[] args)
  {
    new Ex23();
  }
}
