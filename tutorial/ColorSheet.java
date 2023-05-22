// ColorSheet.java

import ch.aplu.jgamegrid.*;
import java.awt.*;

public class ColorSheet extends GameGrid
{
  public ColorSheet()
  {
    super(600, 600);
    show();
    GGPanel p = getPanel(0, 255, 0, 255);
    for (int i = 1; i <= 255; i++)
    {
      int b = i * 255 / 255;
      int r = 255 - b;
      int g = (500 * Math.abs(i - 128)) / 255;
      Color c = new Color(r, g, b);
      p.color(c);
      p.move(i, 128);
      p.rectangle(1, 256, true);
    }
  }

  public static void main(String[] args)
  {
    new ColorSheet();
  }
}
