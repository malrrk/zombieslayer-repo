// Star.java
// Used in Ex11.java

import java.awt.geom.*;
import java.awt.*;
import ch.aplu.jgamegrid.*;

public class Star
{
  private GGBackground bg;
  private Color color;
  private GeneralPath tri1, tri2; // 2 triangles at (0,0)

  public Star(GGBackground bg, int radius, Color color)
  {
    this.bg = bg;
    this.color = color;
    setSize(radius);
  }

  private void setSize(int size)
  {
    // First triangle, size is radius of circumcircle, center at (0,0)
    tri1 = new GeneralPath();
    tri1.moveTo(size, 0);
    tri1.lineTo((int)(-0.5 * size), (int)(0.866 * size));
    tri1.lineTo((int)(-0.5 * size), (int)(-0.866 * size));
    tri1.closePath();

    // Second triangle like first, but rotated 180 degrees
    tri2 = (GeneralPath)tri1.clone();
    AffineTransform t = new AffineTransform();
    t.rotate(Math.PI);
    tri2.transform(t);
  }

  public void turn(double angle)
  {
    AffineTransform t = new AffineTransform();
    t.rotate(angle);
    tri1.transform(t);
    tri2.transform(t);
  }

  public void showAt(int mx, int my)
  {
    bg.setPaintColor(color);
    AffineTransform t = new AffineTransform();
    t.translate(mx, -50 + my % 650);  // Restrict to playground

    // Cloning to avoid side effects
    GeneralPath gp1 = (GeneralPath)tri1.clone();
    GeneralPath gp2 = (GeneralPath)tri2.clone();
    gp1.transform(t);
    gp2.transform(t);
    bg.fillGeneralPath(gp1);
    bg.fillGeneralPath(gp2);
  }
}
