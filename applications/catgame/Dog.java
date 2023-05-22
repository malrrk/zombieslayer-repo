// Dog.java

import ch.aplu.jgamegrid.*;

public class Dog extends Actor
{
  private CatGame cg;
  private int skipAct;

  public Dog(CatGame cg)
  {
    super("sprites/dog.gif", 3);
    this.cg = cg;
  }

  public void reset()
  {
    skipAct = 0;
    setHorzMirror(false);
  }

  public void act()
  {
    move(3);
    if (getIdVisible() == 1)
      show(0);
    else
      if (getIdVisible() == 0)
        show(1);
    if (skipAct > 0)
    {
      skipAct--;
      return;
    }
    // This will make the game more difficult:
    // If dog sees the cat on the base line, he turns toward the cat
    int dist = getX() - cg.getCat().getX();
    if (cg.getCat().getY() == 235)
    {
      if (dist > 0)
        turnLeft();
      else
        turnRight();
    }
  }

  public int collide(Actor actor, Location location)
  {
    if (location.equals(new Location(3, 3)) ||
      location.equals(new Location(11, 3)))
      turnRight();
    if (location.equals(new Location(10, 3)) ||
      location.equals(new Location(20, 3)))
     turnLeft();
    skipAct = 80;
    return 0;
  }

  private void turnRight()
  {
    setDirection(0);
    setHorzMirror(false);
  }

  private void turnLeft()
  {
    setDirection(180);
    setHorzMirror(true);
  }

}


