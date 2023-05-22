// PacGrid.java

import ch.aplu.jgamegrid.*;

public class PacGrid
{
  private final int nbHorzCells = 30;
  private final int nbVertCells = 33;
  private int[][] a = new int[33][30];

  public PacGrid()
  {
    String maze =
      "xxxxxxxxxxxxxxx" + // 0
      "xxxxxxxxxxxxxxx" + // 1
      "xx............x" + // 2
      "xx.xxxx.xxxxx.x" + // 3
      "xx.xxxx.xxxxx.x" + // 4
      "xx.xxxx.xxxxx.x" + // 5
      "xx............." + // 6
      "xx.xxxx.xx.xxxx" + // 7
      "xx.xxxx.xx.xxxx" + // 8
      "xx......xx....x" + // 9
      "xxxxxxx.xxxxx x" + // 10
      "xxxxxxx.xxxxx x" + // 11
      "xxxxxxx.xx     " + // 12
      "xxxxxxx.xx xxx " + // 13
      "xxxxxxx.xx xxx " + // 14
      "xx     .   xx  " + // 15
      "xxxxxxx.xx xxxx" + // 16
      "xxxxxxx.xx xxxx" + // 17
      "xxxxxxx.xx     " + // 18
      "xxxxxxx.xx xxxx" + // 19
      "xxxxxxx.xx xxxx" + // 20
      "xx............x" + // 21
      "xx.xxxx.xxxxx.x" + // 22
      "xx.xxxx.xxxxx.x" + // 23
      "xx...xx........" + // 24
      "xxxx.xx.xx.xxxx" + // 25
      "xxxx.xx.xx.xxxx" + // 26
      "xx......xx....x" + // 27
      "xx.xxxxxxxxxx.x" + // 28
      "xx.xxxxxxxxxx.x" + // 29
      "xx............." + // 30
      "xxxxxxxxxxxxxxx" + // 31
      "xxxxxxxxxxxxxxx";   // 32

    // Copy structure into integer array
    for (int i = 0; i < nbVertCells; i++)
    {
      for (int k = 0; k < nbHorzCells / 2; k++)
        a[i][k] = toInt(maze.charAt(15 * i + k));
      // Mirror at vertical axis at nbHorzCells / 2
      for (int k = nbHorzCells / 2; k < nbHorzCells; k++)
        a[i][k] = toInt(maze.charAt(15 * i + (29 - k)));
    }
  }

  public int getCell(Location location)
  {
    return a[location.y][location.x];
  }

  public boolean isInnerRegion(Location location)
  {
    if (location.equals(new Location(14, 13)) ||
      location.equals(new Location(15, 13)) ||
      location.equals(new Location(14, 14)) ||
      location.equals(new Location(15, 14)) ||
      location.equals(new Location(13, 15)) ||
      location.equals(new Location(15, 15)) ||
      location.equals(new Location(15, 15)) ||
      location.equals(new Location(16, 16)))
    {
      return true;
    }
    return false;
  }

  private int toInt(char c)
  {
    if (c == 'x')
    return 0;
    if (c == '.')
    return 1;
    if (c == ' ')
    return 2;
    return -1;
  }
}
