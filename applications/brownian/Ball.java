// Ball.java
// Used in Brownian.java

import java.awt.Point;

public class Ball extends CapturedActor
{
  private Point oldPt;;

  public Ball()
  {
    super(false, "sprites/ball.gif", 2);
  }

  public void reset()
  {
    oldPt = gameGrid.toPoint(getLocationStart());
  }
  
  public void act()
  {
    super.move(3);
    if (getIdVisible() == 1)
    {
      Point pt = gameGrid.toPoint(getLocation());
      getBackground().drawLine(oldPt.x, oldPt.y, pt.x, pt.y);
      oldPt.x = pt.x;
      oldPt.y = pt.y;
    }
  }
}
