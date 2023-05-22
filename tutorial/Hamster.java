// Hamster.java
// Used in Ex04a.java

import ch.aplu.jgamegrid.*;

public class Hamster extends Actor
{
  public Hamster()
  {
    super(true, "sprites/hamster.gif");
  }

  public void act()
  {
    Actor hazelnut = gameGrid.getOneActorAt(getLocation(), Hazelnut.class);
    if (hazelnut != null)
      hazelnut.removeSelf();

    // Try to turn +-90 degrees each 5 periods
    if (nbCycles % 5 == 0)
      turn(Math.random() < 0.5 ? 90 : -90);

    // If new location is valid, move to it
    if (canMove())
      move();
    // if not, turn 90, 180 or 270 degrees until a valid location is found
    else
    {
      for (int i = 1; i < 4; i++)
      {
        turn(i * 90);
        if (canMove())
          break;
      }
    }
  }

  private boolean canMove()
  {
    if (isMoveValid() &&
      gameGrid.getOneActorAt(getNextMoveLocation(), Rock.class) == null)
      return true;  // Inside grid and no rock
    return false;
  }
}
