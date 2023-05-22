// Tetromino.java

import ch.aplu.jgamegrid.*;
import java.util.*;

// Class is abstract because only specific tetrominos can be created
abstract class Tetromino extends Actor
{
  protected Tetris tetris;
  private boolean isStarting = true;
  private int rotId = 0;
  private int nb;
  protected ArrayList<TetroBlock> blocks = new ArrayList<TetroBlock>();
  private Tetromino nextTetromino = null;

  Tetromino(Tetris tetris)
  {
    super();
    this.tetris = tetris;
  }

  public void act()
  {
    if (isStarting)
    {
      for (TetroBlock a : blocks)
      {
        Location loc =
          new Location(getX() + a.getRelLoc(0).x, getY() + a.getRelLoc(0).y);
        gameGrid.addActor(a, loc);
      }
      isStarting = false;
      nb = 0;
    }
    else
    {
      setDirection(90);
      if (nb == 1)
        nextTetromino = tetris.createRandomTetromino();
      if (!advance())
      {
        if (nb == 0)  // Game is over when tetromino cannot fall down
          tetris.gameOver();
        else
        {
          setActEnabled(false);
          gameGrid.addActor(nextTetromino, new Location(3, 0));
          tetris.setCurrentTetromino(nextTetromino);
        }
      }
      nb++;
    }
  }

  void display(GameGrid gg, Location location)
  {
    for (TetroBlock a : blocks)
    {
      Location loc =
        new Location(location.x + a.getRelLoc(0).x, location.y + a.getRelLoc(0).y);
      gg.addActor(a, loc);
    }
  }

  void left()
  {
    if (isStarting)
      return;
    setDirection(180);
    advance();
  }

  void right()
  {
    if (isStarting)
      return;
    setDirection(0);
    advance();
  }

  void rotate()
  {
    if (isStarting)
      return;

    int oldRotId = rotId; // Save it
    rotId++;
    if (rotId == 4)
      rotId = 0;

    if (canRotate(rotId))
    {
      for (TetroBlock a : blocks)
      {
        Location loc = new Location(getX() + a.getRelLoc(rotId).x, getY() + a.getRelLoc(rotId).y);
        a.setLocation(loc);
      }
    }
    else
      rotId = oldRotId;  // Restore

  }

  private boolean canRotate(int rotId)
  {
    // Check for every rotated tetroBlock within the tetromino
    for (TetroBlock a : blocks)
    {
      Location loc =
        new Location(getX() + a.getRelLoc(rotId).x, getY() + a.getRelLoc(rotId).y);
      if (!gameGrid.isInGrid(loc))  // outside grid->not permitted
        return false;
      TetroBlock block =
        (TetroBlock)(gameGrid.getOneActorAt(loc, TetroBlock.class));
      if (blocks.contains(block))  // in same tetromino->skip
        break;
      if (block != null)  // Another tetroBlock->not permitted
        return false;
    }
    return true;
  }

  void drop()
  {
    if (isStarting)
      return;
    setSlowDown(0);
  }

  private boolean advance()
  {
    boolean canMove = true;
    for (TetroBlock a : blocks)
    {
      if (a.isRemoved())
        break;
      if (!gameGrid.isInGrid(a.getNextMoveLocation()))
      {
        canMove = false;
        break;
      }
    }

    for (TetroBlock a : blocks)
    {
      if (a.isRemoved())
        break;
      TetroBlock block =
        (TetroBlock)(gameGrid.getOneActorAt(a.getNextMoveLocation(),
        TetroBlock.class));
      if (block != null && !blocks.contains(block))
      {
        canMove = false;
        break;
      }
    }

    if (canMove)
    {
      move();
      return true;
    }
    return false;
  }

  // Override Actor.setDirection()
  public void setDirection(double dir)
  {
    super.setDirection(dir);
    for (TetroBlock a : blocks)
      a.setDirection(dir);
  }

  // Override Actor.move()
  public void move()
  {
    if (isRemoved())
      return;
    super.move();
    for (TetroBlock a : blocks)
    {
      if (a.isRemoved())
        break;
      a.move();
    }
  }

  // Override Actor.removeSelf()
  public void removeSelf()
  {
    super.removeSelf();
    for (TetroBlock a : blocks)
      a.removeSelf();
  }
}
