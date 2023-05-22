// Fly.java
// Used for CatGame

import ch.aplu.jgamegrid.*;

public class Fly extends Actor
{
  private CatGame cg;

  public Fly(CatGame cg)
  {
    super(true, "sprites/fly1.gif");
    this.cg = cg;
  }

  public void act()
  {
    setDirection(225 + 90 * Math.random());
    move();
    if (getY() < 10)
      removeSelf();
  }

  public int collide(Actor a1, final Actor a2)
  {
    cg.increaseEatenFlies();
    a2.show(2);
    cg.refresh();
    new Thread()
    {
      public void run()
      {
        delay(400);
        a2.show(0);
      }
    }.start();
    removeSelf();
    return 0;
  }
}
