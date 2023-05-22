// Brownian.java
// Applet, Brownian motion

import ch.aplu.jgamegrid.*;
import javax.swing.*;
import java.awt.*;

public class Brownian extends JApplet
{
  private GameGrid gg;

  public void init()
  {
    gg = new GameGrid();
    gg.setCellSize(1);
    gg.setNbHorzCells(400);
    gg.setNbVertCells(400);
    gg.setSimulationPeriod(10);
    getContentPane().add(gg);
  }

  public void start()
  {
    gg.setBgColor(new java.awt.Color(100, 100, 100));
    BallWorld.create(gg);
    gg.doRun();
  }

  public void stop()
  {
    gg.stopGameThread();
  }

  private void initStandalone()
  {
    init();
    JFrame f = new JFrame();
    f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    f.setContentPane(getContentPane());
    f.pack();
    start();
    f.setVisible(true);
  }

  public static void main(String[] args)
  {
    EventQueue.invokeLater(new Runnable()
    {
      public void run()
      {
        new Brownian().initStandalone();
      }
    });
  }
}
