// Ex11.java
// Use background drawing methods to animate the playground

import ch.aplu.jgamegrid.*;
import java.awt.Color;

public class Ex11 extends GameGrid
{
  private final int nbStars = 20;
  private Star[] stars = new Star[nbStars];
  private final int mx[] =
  {
    10, 40, 50, 80, 100, 130, 150, 220, 250, 300,
    320, 350, 380, 390, 410, 450, 500, 420, 550, 570
  };
  private final int my[] =
  {
    -10, 50, 250, -60, 10, 350, -60, 200, 500, 50,
    -10, 50, 250, -60, 10, 350, -60, 200, 500, 50
  };
  private final double rotInc[] =
  {
    0.01, -0.02, 0.03, -0.02, 0.05, -0.1, 0.03, -0.02, -0.2, -0.1,
    0.01, 0.02, -0.03, 0.02, -0.05, 0.1, -0.03, 0.02, 0.2, 0.1
  };
  private final Color colors[] =
  {
    Color.yellow, Color.white, Color.green,  Color.magenta, Color.pink,
    Color.red, Color.orange, Color.cyan, Color.blue, Color.red,
    Color.yellow, Color.white, Color.green,  Color.magenta, Color.pink,
    Color.red, Color.orange, Color.cyan, Color.blue, Color.red
  };
  private GGBackground bg = getBg();
  private int dy = 0;

  public Ex11()
  {
    super(600, 600, 1, false);
    setSimulationPeriod(50);
    initStars();
    addActor(new Angel(), new Location(300, 300), -22);
    show();
    doRun();
  }

  private void initStars()
  {
    int size;
    for (int i = 0; i < nbStars; i++)
    {
      size = (int)(10 + 40 * Math.random());
      stars[i] = new Star(bg, size, colors[i]);
    }
  }

  public void act()
  {
    drawParadies();
  }

  private void drawParadies()
  {
    bg.clear(new Color(175, 175, 255));
    for (int i = 0; i < nbStars; i++)
    {
      stars[i].turn(rotInc[i]);
      stars[i].showAt(mx[i], my[i] + dy);
    }
    dy += 2;
  }


  public static void main(String[] args)
  {
    new Ex11();
  }
}
