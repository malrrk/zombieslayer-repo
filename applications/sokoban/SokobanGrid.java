// SokobanGrid.java

import ch.aplu.jgamegrid.*;

public class SokobanGrid
{
  private final static int nbHorzCells = 19;
  private final static int nbVertCells = 11;
  private static int[][] a = new int[nbHorzCells][nbVertCells];
  private static int nbStones = 0;

  private final static String soko_0 =
    "    xxxxx          " + // 0 (19)
    "    x...x          " + // 1
    "    x*..x          " + // 2
    "  xxx..*xx         " + // 3
    "  x..*.*.x         " + // 4
    "xxx.x.xx.x   xxxxxx" + // 5
    "x...x.xx.xxxxx..oox" + // 6
    "x.*..*..........oox" + // 7
    "xxxxx.xxx.xAxx..oox" + // 8
    "    x.....xxxxxxxxx" + // 9
    "    xxxxxxx        ";  //10
  private final static int nbHorzCells_0 = 19;
  private final static int nbVertCells_0 = 11;

  private final static String soko_1 =
    "xxxxxxxxxxxx  " + // 0  (14)
    "xoo..x.....xxx" + // 1
    "xoo..x.*..*..x" + // 2
    "xoo..x*xxxx..x" + // 3
    "xoo....A.xx..x" + // 4
    "xoo..x.x..*.xx" + // 5
    "xxxxxx.xx*.*.x" + // 6
    "  x.*..*.*.*.x" + // 7
    "  x....x.....x" + // 8
    "  xxxxxxxxxxxx"; // 9
  private final static int nbHorzCells_1 = 14;
  private final static int nbVertCells_1 = 10;

  private final static String soko_2 =
  "        xxxxxxxx " + // 0  (17)
  "        x.....Ax " + // 1
  "        x.*x*.xx " + // 2
  "        x.*..*x  " + // 3
  "        xx*.*.x  " + // 4
  "xxxxxxxxx.*.x.xxx" + // 5
  "xoooo..xx.*..*..x" + // 6
  "xxooo....*..*...x" + // 7
  "xoooo..xxxxxxxxxx" + // 8
  "xxxxxxxx         ";  // 9
  private final static int nbHorzCells_2 = 17;
  private final static int nbVertCells_2 = 10;

  private final static String[] sokoModel =
  {
    soko_0, soko_1, soko_2
  };

  private final static int[] nbHorzCellsModel =
  {
    nbHorzCells_0, nbHorzCells_1, nbHorzCells_2
  };

  private final static int[] nbVertCellsModel =
  {
    nbVertCells_0, nbVertCells_1, nbVertCells_2
  };

  private static int model;

  public SokobanGrid(int model)
  {
    this.model = model;

    // Copy structure into integer array
    for (int k = 0; k < nbVertCellsModel[model]; k++)
    {
      for (int i = 0; i < nbHorzCellsModel[model]; i++)
      {
        switch (sokoModel[model].charAt(nbHorzCellsModel[model] * k + i))
        {
          case ' ':
            a[i][k] = 0;  // Empty outside
            break;
          case '.':
            a[i][k] = 1;  // Empty inside
            break;
          case 'x':
            a[i][k] = 2;  // Border
            break;
          case '*':
            a[i][k] = 3;  // Stones
            nbStones++;
            break;
          case 'o':
            a[i][k] = 4;  // Target positions
            break;
          case 'A':
            a[i][k] = 5;  // Sokoban actor
            break;
        }
      }
    }
  }

  public static int getNbHorzCells()
  {
    return nbHorzCellsModel[model];
  }

  public static int getNbVertCells()
  {
    return nbVertCellsModel[model];
  }

  public static int getNbStones()
  {
    return nbStones;
  }

  public static int getCell(Location location)
  {
    return a[location.x][location.y];
  }
}
