// Ex12.java
// Applet

import ch.aplu.jgamegrid.*;
import javax.swing.*;
import java.awt.*;

public class Ex12 extends JApplet implements GGActListener
{
  private GameGrid gg;

  public void init()
  {
    gg = new GameGrid();
    gg.setNbHorzCells(400);
    gg.setNbVertCells(200);
    gg.setCellSize(1);
    gg.setSimulationPeriod(100);
    gg.setBgColor(new Color(182, 220, 234));
    gg.addActListener(this);
    getContentPane().add(gg);
  }

  public void start()
  {
    gg.doRun();
  }

  public void stop()
  {
    gg.stopGameThread();
  }

  public void act()
  {
    if (gg.getNbCycles() % 10 == 0)
    {
      int x = 5 + (int)(390 * Math.random());
      gg.addActor(new Spider(),
        new Location(x, -20), 90);
    }
  }

  private void initStandalone()
  {
    init();
    JFrame f = new JFrame();
    f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    f.setContentPane(getContentPane());
    f.pack();  // Must be called before start()
    start();
    f.setVisible(true);
  }

  public static void main(String[] args)
  {
    EventQueue.invokeLater(new Runnable()
    {
      public void run()
      {
        new Ex12().initStandalone();
      }
    });
  }
}
