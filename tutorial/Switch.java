// Switch.java
// Used in Ex06.java

import ch.aplu.jgamegrid.*;

public class Switch extends Actor implements GGMouseListener
{
  private Bulb bulb;

  public Switch(Bulb bulb)
  {
    super("sprites/switch.gif", 2);
    this.bulb = bulb;
  }

public boolean mouseEvent(GGMouse mouse)
{
  Location location =
    gameGrid.toLocationInGrid(mouse.getX(), mouse.getY());
  if (location.equals(getLocation()))
    showNextSprite();
  bulb.show(getIdVisible());
  gameGrid.refresh();
  return false;
}
}
