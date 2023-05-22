// NavigationPane.java

import ch.aplu.jgamegrid.*;
import java.awt.*;
import ch.aplu.util.*;

class NavigationPane extends GameGrid
{
  private class SimulatedPlayer extends Thread
  {
    public void run()
    {
      while (true)
      {
        int nb = (int)(6 * Math.random() + 1);
        startMoving(nb);
        Monitor.putSleep();
      }
    }

  }

  private GamePane gp;
  private GGPanel p;
  private int nbRolls = 0;
  private final int histoWidth = 100;
  private final int histoHeight = 50;
  private final int maxHisto = 40;  // maximum of histo bar, stop simulation
  private int[] histo = new int[histoWidth + 1];
  private int nbGames = 0;
  private final Location statusLocation = new Location(20, 55);
  private final Location statusDisplayLocation = new Location(100, 55);
  private final Location scoreLocation = new Location(220, 55);
  private final Location scoreDisplayLocation = new Location(300, 55);
  private GGTextField statusField;
  private GGTextField scoreField;

  NavigationPane()
  {
    setCellSize(1);
    setNbHorzCells(400);
    setNbVertCells(600);
    p = getPanel();
  }

  void setGamePane(GamePane gp)
  {
    this.gp = gp;
  }

  void createGui()
  {
    createHisto();
    addActor(new Actor("sprites/linedisplay.gif"), statusDisplayLocation);
    statusField = new GGTextField(this, "# Roll: 0", statusLocation, false);
    statusField.setFont(new Font("Arial", Font.PLAIN, 16));
    statusField.setTextColor(YELLOW);
    statusField.show();

    addActor(new Actor("sprites/linedisplay.gif"), scoreDisplayLocation);
    scoreField = new GGTextField(this, "# Games: 0", scoreLocation, false);
    scoreField.setFont(new Font("Arial", Font.PLAIN, 16));
    scoreField.setTextColor(YELLOW);
    scoreField.show();
    new SimulatedPlayer().start();
  }

  private void showStatus(String text)
  {
    statusField.setText(text);
  }

  private void showScore(String text)
  {
    scoreField.setText(text);
  }

  private void updateHistogram(int nbRolls)
  {
    if (nbRolls > histoWidth)
      return;
    nbGames++;
    histo[nbRolls]++;
    p.line(nbRolls, 0, nbRolls, histo[nbRolls]);
    showStatus("# Rolls: " + nbRolls);
    showScore("# Games: " + nbGames);
    drawGrid();
  }

  private void drawGrid()
  {
    p.color(Color.YELLOW);
    for (int i = 0; i <= 100; i++)
      p.point(i, 0);
    p.setLineWidth(1);
    p.drawText("0", 0.5, 0.5);
    p.line(0, 0, 0, 50);
    for (int i = 10; i <= 90; i += 10)
    {
      p.drawText("" + i, i - 4.5, 0.5);
      p.line(i, 0, i, 50);
    }
    p.drawText("100", 95, 0.5);
    p.line(100, 0, 100, 50);

    p.line(0, 0, 100, 0);
    for (int y = 10; y <= 40; y += 10)
    {
      p.line(0, y, 100, y);
      p.drawText("" + y, 95, y + 0.1);
    }
    p.line(0, 50, 100, 50);
    p.drawText("50", 95, 49);
    p.color(Color.BLACK);
    p.setLineWidth(2);
    refresh();
  }

  void prepareNextRoll(int currentIndex)
  {
    if (currentIndex == 100)  // Game over
    {
      playSound(GGSound.CLICK);
      updateHistogram(nbRolls);
      nbRolls = 0;
      int max = 0;
      for (int i = 0; i <= histoWidth; i++)
      {
        if (histo[i] > max)
          max = histo[i];
      }
      if (max < maxHisto)
        Monitor.wakeUp();
      else
      {
        showStatus("Stopped");
        refresh();
      }
    }
    else
      Monitor.wakeUp();
  }

  void startMoving(int nb)
  {
    nbRolls++;
    gp.getPuppet().go(nb);
  }

  private void createHisto()
  {
    p.setRefreshEnabled(false);
    p.window(0, histoWidth, 0, histoHeight);
    p.clear(new Color(150, 190, 255));
    p.setFont(new Font("SansSerif", Font.PLAIN, 12));
    p.setLineWidth(2);
    for (int nb = 0; nb <= histoWidth; nb++)
    {
      histo[nb] = 0;
      p.point(nb, 0);
    }
    drawGrid();
  }

}
