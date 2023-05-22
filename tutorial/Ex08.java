// Ex08.java
// Test for class Dart

import ch.aplu.jgamegrid.*;
import java.awt.Color;

public class Ex08 extends GameGrid
{
  public Ex08()
  {
    super(400, 300, 1, false);
    setTitle("Move dart using mouse left button drag");
    setSimulationPeriod(50);
    setBgColor(new Color(182, 220, 234));
    Dart dart = new Dart();
    addActor(dart, new Location(50, 50));
    addMouseListener(dart, GGMouse.lDrag);
    show();
    doRun();
  }

  public static void main(String[] args)
  {
    new Ex08();
  }
}
