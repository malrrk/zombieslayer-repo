// Player.java
// Used in Ex05a.java
// Sprite images from http://www.brackeen.com/javagamebook

public class Player extends CapturedActor
{
  public Player()
  {
    super(false, "sprites/head.gif", 3);
  }
  
  public void act()
  {
    if (getNbCycles() % 5 == 0)
      showNextSprite();
    super.move(10);
  }
}
