// Ex06.java
// Virtual switches

import ch.aplu.jgamegrid.*;
import java.awt.Color;

public class Ex06 extends GameGrid
{
  public Ex06()
  {
    super(7, 4, 40, false);
    setBgColor(new Color(210, 210, 210));
    for (int i = 0; i < 3; i++)
    {
      Bulb bulb = new Bulb(i);
      addActor(bulb, new Location(2*i + 1, 1));
      Switch aSwitch = new Switch(bulb);
      addActor(aSwitch, new Location(2*i + 1, 3));
      addMouseListener(aSwitch, GGMouse.lPress);
    }
    show();
  }

  public static void main(String[] args)
  {
    new Ex06();
  }
}
