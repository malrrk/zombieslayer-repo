// Fire.java


import ch.aplu.jgamegrid.*;

public class Fire extends Actor
{
  public Fire()
  {
    super("sprites/fire.gif", 4);
    setSlowDown(10);
  }
  
  public void act()
  {
    if (getIdVisible() == 2)
      setActEnabled(false);
    showNextSprite();
  }
}
