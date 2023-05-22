// Moire.java

import ch.aplu.jgamegrid.*;
import java.awt.*;

public class Moire extends GameGrid
{
  private GGPanel p;

  public Moire()
  {
    super(500, 500);
    setBgColor(Color.white);
    show();
    p = getPanel();
    p.window(0, 10, 0, 10);
    p.color(Color.black);
    for (int i = 0; i <= 10; i++)
    {
      for (int k = 0; k <= 10; k++)
      {
        p.line(i, 0, k, 10);
        delay(100);
      }
    }

    for (int i = 0; i <= 10; i++)
    {
      for (int k = 0; k <= 10; k++)
      {
        p.line(0, i, 10, k);
        delay(100);
      }
    }
  }

  public static void main(String[] args)
  {
    new Moire();
  }
}
