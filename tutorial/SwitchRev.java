// SwitchRev.java
// Used in Ex06Rev.java

import ch.aplu.jgamegrid.*;

public class SwitchRev extends Actor implements GGMouseTouchListener
{
  private Bulb bulb;

  public SwitchRev(Bulb bulb)
  {
    super("sprites/switch.gif", 2);
    this.bulb = bulb;
  }

public void mouseTouched(Actor actor, GGMouse mouse, java.awt.Point pix)
{
  showNextSprite();
  bulb.show(getIdVisible());
  gameGrid.refresh();
}
}
