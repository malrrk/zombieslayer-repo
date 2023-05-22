// Sokoban.java
// Simple Sokoban implementation

import ch.aplu.jgamegrid.*;
import java.awt.event.KeyEvent;
import java.awt.*;

public class Sokoban extends GameGrid implements GGKeyListener
{
  // ------------- Inner classes -------------
  private class SokobanActor extends Actor
  {
    public SokobanActor()
    {
      super(true, "sprites/sokoban.gif");  // Rotatable
    }
  }

  private class SokobanTarget extends Actor
  {
    public SokobanTarget()
    {
      super("sprites/target.gif");
    }
  }

  private class SokobanStone extends Actor
  {
    public SokobanStone()
    {
      super("sprites/stone.gif", 2); // Two sprites
    }
  }
  // ------------- End of inner classes ------
  //
  private final static SokobanGrid grid = new SokobanGrid(0);
  private final static int nbHorzCells = grid.getNbHorzCells();
  private final static int nbVertCells = grid.getNbVertCells();
  private final Color borderColor = new Color(100, 100, 100);
  private SokobanStone[] stones = new SokobanStone[grid.getNbStones()];
  private SokobanTarget[] targets = new SokobanTarget[grid.getNbStones()];
  private SokobanActor sok;
  private boolean isFinished = false;

  public Sokoban()
  {
    super(nbHorzCells, nbVertCells, 30, false);
    setTitle("Sokoban -- www.aplu.ch/jgamegrid");
    GGBackground bg = getBg();
    drawBoard(bg);
    drawActors();
    addKeyListener(this);
    show();
    // Check if finished
    int nbTarget = 0;
    while (nbTarget < grid.getNbStones())
    {
      nbTarget = 0;
      for (int i = 0; i < grid.getNbStones(); i++)
      {
        if (stones[i].getIdVisible() == 1)
          nbTarget++;
      }
      setTitle("# Stones at Target: " + nbTarget);
      delay(500);
    }
    setTitle("Game over. Well done!");
    isFinished = true;
  }

  private void drawActors()
  {
    int stoneIndex = 0;
    int targetIndex = 0;

    for (int y = 0; y < nbVertCells; y++)
    {
      for (int x = 0; x < nbHorzCells; x++)
      {
        Location location = new Location(x, y);
        int a = grid.getCell(location);
        if (a == 5) // Sokoban actor
        {
          sok = new SokobanActor();
          addActor(sok, location);
        }
        if (a == 3) // Stones
        {
          stones[stoneIndex] = new SokobanStone();
          addActor(stones[stoneIndex], location);
          stoneIndex++;
        }
        if (a == 4) // Targets
        {
          targets[targetIndex] = new SokobanTarget();
          addActor(targets[targetIndex], location);
          targetIndex++;
        }
      }
    }
    setPaintOrder(SokobanTarget.class);
  }

  private void drawBoard(GGBackground bg)
  {
    bg.clear(new Color(230, 230, 230));
    bg.setPaintColor(Color.darkGray);
    for (int y = 0; y < nbVertCells; y++)
    {
      for (int x = 0; x < nbHorzCells; x++)
      {
        Location location = new Location(x, y);
        int a = grid.getCell(location);
        if (a == 1 || a == 3 || a == 4 || a == 5)
        {
          bg.fillCell(location, Color.lightGray);
          Point center = toPoint(location);
          bg.drawLine(new Point(center.x - 15, center.y - 15),
            new Point(center.x + 15, center.y + 15));
          bg.drawLine(new Point(center.x - 15, center.y + 15),
            new Point(center.x + 15, center.y - 15));
        }
        if (a == 2)  // Border
          bg.fillCell(location, borderColor);
      }
    }
  }

  public boolean keyPressed(KeyEvent evt)
  {
    if (isFinished)
      return true;
    Location next = null;
    switch (evt.getKeyCode())
    {
      case KeyEvent.VK_LEFT:
        next = sok.getLocation().getNeighbourLocation(Location.WEST);
        sok.setDirection(Location.WEST);
        break;
      case KeyEvent.VK_UP:
        next = sok.getLocation().getNeighbourLocation(Location.NORTH);
        sok.setDirection(Location.NORTH);
        break;
      case KeyEvent.VK_RIGHT:
        next = sok.getLocation().getNeighbourLocation(Location.EAST);
        sok.setDirection(Location.EAST);
        break;
      case KeyEvent.VK_DOWN:
        next = sok.getLocation().getNeighbourLocation(Location.SOUTH);
        sok.setDirection(Location.SOUTH);
        break;
    }
    if (next != null && canMove(next))
    {
      sok.setLocation(next);
    }
    refresh();
    return true;
  }

  public boolean keyReleased(KeyEvent evt)
  {
    return true;
  }

  private boolean canMove(Location location)
  {
    // Test if try to move into border
    Color c = getBg().getColor(location);
    if (c.equals(borderColor))
      return false;
    else // Test if there is a stone
    {
      SokobanStone stone = (SokobanStone)getOneActorAt(location, SokobanStone.class);
      if (stone != null)
      {
        // Try to move the stone
        stone.setDirection(sok.getDirection());
        if (moveStone(stone))
          return true;
        else
          return false;
      }
    }
    return true;
  }

  private boolean moveStone(SokobanStone stone)
  {
    Location next = stone.getNextMoveLocation();
    // Test if try to move into border
    Color c = getBg().getColor(next);
    if (c.equals(borderColor))
      return false;

    // Test if there is another stone
    SokobanStone neighbourStone =
      (SokobanStone)getOneActorAt(next, SokobanStone.class);
    if (neighbourStone != null)
      return false;

    // Move the stone
    stone.setLocation(next);

    // Check if we are at a target
    if (getOneActorAt(next, SokobanTarget.class) != null)
      stone.show(1);
    else
      stone.show(0);
    return true;
  }

  public static void main(String[] args)
  {
    new Sokoban();
  }
}
