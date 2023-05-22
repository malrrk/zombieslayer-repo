// Maze.java
// Randomly generated maze using class GGMaze
// No need to run the simulation loop
// Application not derived from GameGrid (to show a variant)

import ch.aplu.jgamegrid.*;
import java.awt.Color;

public class Maze
{
  private final int nbHorzCells = 31;  // must be odd
  private final int nbVertCells = 31;  // ditto
  private final int cellSize = 18;

  public Maze()
  {
    GameGrid gg = new GameGrid(nbHorzCells, nbVertCells, cellSize, false);
    GGMaze maze = drawMaze(gg.getBg());
    Bug bug = new Bug(maze);
    gg.addActor(bug, maze.getStartLocation());
    gg.addKeyListener(bug);
    gg.show();
  }

  private GGMaze drawMaze(GGBackground bg)
  {
    GGMaze maze = new GGMaze(nbHorzCells, nbVertCells);
    for (int x = 0; x < nbHorzCells; x++)
      for (int y = 0; y < nbVertCells; y++)
      {
        Location location = new Location(x, y);
        if (maze.isWall(location))
          bg.fillCell(location, Color.black);
        else
          bg.fillCell(location, Color.white);
      }
    return maze;
  }

  public static void main(String[] args)
  {
    new Maze();
  }
}
