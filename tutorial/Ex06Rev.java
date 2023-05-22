// Ex06Rev.java
// Use of GGMouseTouchListener

import ch.aplu.jgamegrid.*;
import java.awt.*;

public class Ex06Rev extends GameGrid
{
  public Ex06Rev()
  {
    super(7, 4, 40, false);
    setBgColor(new Color(210, 210, 210));
    for (int i = 0; i < 3; i++)
    {
      Bulb bulb = new Bulb(i);
      addActor(bulb, new Location(2*i + 1, 1));
      SwitchRev aSwitch = new SwitchRev(bulb);
      addActor(aSwitch, new Location(2*i + 1, 3));
      aSwitch.addMouseTouchListener(aSwitch, GGMouse.lPress);
      aSwitch.setMouseTouchRectangle(new Point(-10, 10), 30, 20);
      // Only switch knob active. Used for both sprite IDs
    }
    show();
  }

  public static void main(String[] args)
  {
    new Ex06Rev();
  }
}
