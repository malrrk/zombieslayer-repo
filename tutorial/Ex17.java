// Ex17.java
// Actor with several sprites from BufferedImage

import ch.aplu.jgamegrid.*;
import java.awt.*;

public class Ex17 extends GameGrid
{
  public Ex17()
  {
    super(600, 200, 1, false);
    setSimulationPeriod(20);
    setBgColor(Color.white);
    addActor(new WaveActor(400, 100), new Location(300, 100));
    show();
    doRun();
  }

  public static void main(String[] args)
  {
    new Ex17();
  }
}
