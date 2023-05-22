// Ghost.java
// Used in Ex05b.java

public class Ghost extends CapturedActor
{

  public Ghost()
  {
    super(true, "sprites/ghost.gif", 4);
  }
  
  public void act()
  {
    if (getNbCycles() % 5 == 0)
      showNextSprite();
    super.move(10);
  }
}
