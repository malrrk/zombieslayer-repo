// ChipActor.java
// Dynamically created actor

import java.awt.*;
import java.awt.image.BufferedImage;
import ch.aplu.jgamegrid.*;
import java.awt.geom.*;

public class ChipActor extends Actor
{
  private Color color;
  
  public ChipActor(Color color)
  {
    super(createImage(color));
    this.color = color;
  }
  
  protected Color getColor()
  {
    return color;
  }

  private static BufferedImage createImage(Color color)
  {
    int d = 50;
    BufferedImage bi = new BufferedImage(d, d, Transparency.BITMASK);
    Graphics2D g2D = bi.createGraphics();

    // Set background color
    g2D.setColor(new Color(255, 255, 255, 0));  // Transparent
    g2D.fillRect(0, 0, d, d);

    float[] fractions =
    {
      0.7f, 1f
    };
    Color[] colors =
    {
      color, Color.darkGray
    };
    Point2D center = new Point2D.Float(d / 2, d / 2);
    RadialGradientPaint paint = 
      new RadialGradientPaint(center, d / 2f, fractions, colors);
    g2D.setPaint(paint);
    g2D.fillOval(0, 0, d, d);
    return bi;
  }
}
