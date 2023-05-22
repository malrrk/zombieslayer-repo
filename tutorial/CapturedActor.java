// CapturedActor.java
// Used in Ex05.java, Player.java, Ghost.java, Ex09.java, Ex12.java

import ch.aplu.jgamegrid.*;

public class CapturedActor extends Actor
{
  public CapturedActor(boolean isRotatable, String imagePath, int nbSprites)
  {
    super(isRotatable, imagePath, nbSprites);
  }

  public CapturedActor(String imagePath)
  {
    super(false, imagePath, 1);
  }

  public void move(int dist)
  {
    java.awt.Point pt = gameGrid.toPoint(getNextMoveLocation());
    double dir = getDirection();
    if (pt.x < 0 || pt.x > gameGrid.getPgWidth())
    {
      setDirection(180 - dir);
      setHorzMirror(!isHorzMirror());
    }

    if (pt.y < 0 || pt.y > gameGrid.getPgHeight())
      setDirection(360 - dir);

    super.move(dist);
  }
}
