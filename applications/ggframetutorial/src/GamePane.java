// GamePane.java

import ch.aplu.jgamegrid.*;
import java.util.ArrayList;

class GamePane extends GameGrid
{
  private NavigationPane np;
  private Puppet puppet;
  private ArrayList<Connection> cons = new ArrayList<Connection>();
  final Location startLocation = new Location(-1, 9);  // outside grid
  final int animationStep = 10;
  
  GamePane()
  {
    setSimulationPeriod(100);
    setBgImagePath("sprites/gamepane.png");
    setCellSize(60);
    setNbHorzCells(10);
    setNbHorzCells(10);
    doRun();
    createSnakes();
    createLadders();
  }

  private void createSnakes()
  {
    // Assumption: no horizontal snake
    cons.add(new Snake(98, 79));
    cons.add(new Snake(95, 75));
    cons.add(new Snake(93, 73));
    cons.add(new Snake(87, 24));
    cons.add(new Snake(62, 19));
    cons.add(new Snake(64, 41));
    cons.add(new Snake(54, 34));
    cons.add(new Snake(17, 7));
  }

  private void createLadders()
  {
    // Assumption: no horizonal ladder
    cons.add(new Ladder(80, 100));
    cons.add(new Ladder(71, 91));
    cons.add(new Ladder(28, 84));
    cons.add(new Ladder(51, 67));
    cons.add(new Ladder(21, 42));
    cons.add(new Ladder(1, 38));
    cons.add(new Ladder(9, 31));
    cons.add(new Ladder(4, 14));
  }

  void setNavigationPane(NavigationPane np)
  {
    this.np = np;
  }

  void createGui()
  {
    puppet = new Puppet(this, np);
    addActor(puppet, startLocation);
  }

  Puppet getPuppet()
  {
    return puppet;
  }
  
  Connection getConnectionAt(Location loc)
  {
    for (Connection con : cons)
      if (con.locStart.equals(loc))
        return con;
    return null;
  }

  static Location cellToLocation(int cellIndex)
  {
    int index = cellIndex - 1;  // 0..99

    int tens = index / 10;
    int ones = index - tens * 10;

    int y = 9 - tens;
    int x;

    if (tens % 2 == 0)     // Cells starting left 01, 21, .. 81
      x = ones;
    else     // Cells starting left 20, 40, .. 100
      x = 9 - ones;

    return new Location(x, y);
  }
  
  int x(int y, Connection con)
  {
    int x0 = toPoint(con.locStart).x;
    int y0 = toPoint(con.locStart).y;
    int x1 = toPoint(con.locEnd).x;
    int y1 = toPoint(con.locEnd).y;
    // Assumption y1 != y0
    double a = (double)(x1 - x0) / (y1 - y0);
    double b = (double)(y1 * x0 - y0 * x1) / (y1 - y0);
    return (int)(a * y + b);
  }

}
