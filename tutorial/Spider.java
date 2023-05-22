// Spider.java
// Used in Ex12.java

import ch.aplu.jgamegrid.*;

public class Spider extends Actor
{
  private int xOld, yOld;

  public Spider()
  {
    super(true, "sprites/spider.gif");
  }
  
  public void act()
  {
    move();
    int x = getX();
    int y = getY();
    gameGrid.getBg().drawLine(xOld, yOld, x, y);
    xOld = x;
    yOld = y;
    setDirection(70 + 40 * Math.random());
    if (getY() > 220)
      removeSelf();
  }
}
