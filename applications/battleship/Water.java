// Water.java


import ch.aplu.jgamegrid.*;

public class Water extends Actor
{
  public Water()
  {
    super("sprites/water.gif", 4);
    setSlowDown(5);
  }

  public void act()
  {
    showNextSprite();
    if (getIdVisible() == 0)
      removeSelf();
  }
}
