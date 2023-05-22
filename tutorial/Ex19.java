// Ex19.java
// Multithreading demonstration
// Callback mouseEvent() runs in EDT and actor's act() runs in animation thread
// No need of synchronization, because GameGrid is thread-safe

import ch.aplu.jgamegrid.*;
import java.awt.*;

public class Ex19 extends GameGrid implements GGMouseListener
{
  public Ex19()
  {
    super(600, 600, 1, null, false);
    setBgColor(Color.blue);
    setSimulationPeriod(30);
    addMouseListener(this, GGMouse.lPress);
    show();
    doRun();
  }

  // This callback runs in Event Dispatch Thread (EDT)
  // May interfere with animation thread
  public boolean mouseEvent(GGMouse mouse)
  {
    for (int i = 0; i < 50; i++)
    {
      addActor(new Diamond(), toLocationInGrid(mouse.getX(), mouse.getY()),
        360 * Math.random());
      Thread.currentThread().yield(); // Will invite animation thread to run
                                      // Improves time behaviour on slow CPU
    }
    return true;
  }

  public static void main(String[] args)
  {
    new Ex19();
  }
}
