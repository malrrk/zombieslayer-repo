// PacMan.java
// Simple PacMan implementation

import ch.aplu.jgamegrid.*;
import java.awt.*;

public class PacMan extends GameGrid
{
  private final static int nbHorzCells = 30;
  private final static int nbVertCells = 33;
  protected PacActor pacActor = new PacActor();
  private Ghost pinky = new Ghost(this, 0);
  private Ghost blinky = new Ghost(this, 1);
  protected PacGrid grid = new PacGrid();

  public PacMan()
  {
    super(nbHorzCells, nbVertCells, 20, false);
    setSimulationPeriod(60);
    setTitle("PacMan -- www.aplu.ch/jgamegrid");
    GGBackground bg = getBg();
    drawGrid(bg);
    addActor(pacActor, new Location(14, 24));
    addKeyRepeatListener(pacActor);
 //   setKeyRepeatPeriod(100);
    pinky.setSlowDown(3);
    addActor(pinky, new Location(13, 15), Location.NORTH);
    blinky.setSlowDown(3);
    addActor(blinky, new Location(14, 15), Location.NORTH);
    doRun();
    show();
    // Loop to look for collision in the application thread
    // This makes it improbable that we miss a hit
    while (!(pinky.getLocation().equals(pacActor.getLocation()) ||
      blinky.getLocation().equals(pacActor.getLocation())))
      delay(10);

    Location loc = pacActor.getLocation();
    pacActor.removeSelf();
    bg.setPaintColor(Color.red);
    bg.setFont(new Font("Arial", Font.BOLD, 96));
    bg.drawText("Game Over",
      new Point(toPoint(new Location(2, 15))));
    addActor(new Actor("sprites/explosion3.gif"), loc);
    doPause();
  }

  private void drawGrid(GGBackground bg)
  {
    bg.clear(Color.gray);
    bg.setPaintColor(Color.white);
    for (int y = 0; y < nbVertCells; y++)
    {
      for (int x = 0; x < nbHorzCells; x++)
      {
        Location location = new Location(x, y);
        int a = grid.getCell(location);
        if (a == 1 || a == 2)
          bg.fillCell(location, Color.lightGray);
        if (a == 1)  // Pill
          bg.fillCircle(toPoint(location), 3);
      }
    }
  }

  public static void main(String[] args)
  {
    new PacMan();
  }
}
