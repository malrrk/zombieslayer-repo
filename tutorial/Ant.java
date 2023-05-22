// Ant.java

import ch.aplu.jgamegrid.*;

public class Ant extends Actor
{
  public Ant()
  {
    super(true, "sprites/ant.gif", 2);
  }

  public void act()
  {
    showNextSprite();
    for (Actor a : gameGrid.getActors(Glutton.class))
    {
      if (getDistance(a) < 50)
        setDirection(getLocation().getDirectionTo(a.getLocation()) + 180);
    }
    move(10);
    setDirection(getDirection() + -20 + 40 * Math.random());
    if (!isMoveValid())
      removeSelf();
  }

  private double getDistance(Actor a)
  {
    return Math.sqrt((a.getX() - getX()) * (a.getX() - getX()) +
      (a.getY() - getY()) * (a.getY() - getY()));
  }
}
