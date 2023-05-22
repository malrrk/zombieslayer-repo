// GameOfLife1.java

import ch.aplu.jgamegrid.*;
import java.awt.Color;

public class GameOfLife3 extends GameGrid
{
  private static final int s = 80;  // Number of cells in each direction
  private static final int z = 2000; // Size of population at start
  private boolean[][] a = new boolean[s][s];

  public GameOfLife3()
  {
    super(s, s, 10, Color.red);
    setTitle("Conway's Game Of Life");
    reset();
    show();
  }

  public void reset()
  {
    for (int x = 0; x < s; x++)
      for (int y = 0; y < s; y++)
        a[x][y] = false;  // All cells dead

    Location loc;
    for (int n = 0; n < z; n++)
    {
      loc = getRandomEmptyLocation();
      a[loc.x][loc.y] = true;
    }
    showPopulation();
  }

  private void showPopulation()
  {
    Location loc;
    for (int x = 0; x < s; x++)
    {
      for (int y = 0; y < s; y++)
      {
        loc = new Location(x, y);
        if (a[x][y])
          getBg().fillCell(loc, Color.green, false);
        else
          getBg().fillCell(loc, Color.black, false);
      }
    }
  }

  private int getNumberOfNeighbours(int x, int y)
  {
    int nb = 0;
    for (int i = Math.max(0, x - 1); i < Math.min(s, x + 2); i++)
    {
      for (int k = Math.max(0, y - 1); k < Math.min(s, y + 2); k++)
      {
        if (!(i == x && k == y))
        {
          if (a[i][k])
            nb++;
        }
      }
    }
    return nb;
  }

  public void act()
  {
    boolean[][] b = new boolean[s][s];
    for (int x = 0; x < s; x++)
    {
      for (int y = 0; y < s; y++)
      {
        int nb = getNumberOfNeighbours(x, y);
        if (a[x][y])    // living cell
        {
          if (nb < 2)
            b[x][y] = false;
          else if (nb > 3)
            b[x][y] = false;
          else
            b[x][y] = true;
        }
        else   // dead cell
        {
          if (nb == 3)
            b[x][y] = true;
          else
            b[x][y] = false;
        }
      }
    }
    a = b;
    showPopulation();
  }

  public static void main(String[] args)
  {
    new GameOfLife3();
  }
}
