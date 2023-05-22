// WaveActor.java
// Dynamically created actor, used in Ex17.java

import java.awt.*;
import java.awt.image.BufferedImage;
import ch.aplu.jgamegrid.*;

public class WaveActor extends Actor
{
  private static int nb = 40;  // Number of sprites

  public WaveActor(int width, int height)
  {
    super(createImages(width, height));
  }

  public void act()
  {
    showNextSprite();
  }

  private static BufferedImage[] createImages(int width, int height)
  {
    BufferedImage[] bis = new BufferedImage[nb];
    for (int i = 0; i < nb; i++)
      bis[i] = waveImage(width, height, width / nb * i);
    return bis;
  }

  private static BufferedImage waveImage(int width, int height, int d)
  {
    BufferedImage bi = new BufferedImage(width, height, Transparency.BITMASK);
    Graphics2D g2D = bi.createGraphics();

    // Set background color
    g2D.setColor(Color.blue);
    g2D.fillRect(0, 0, width - 1, height - 1);

    // Set line color
    g2D.setColor(Color.white);

    // Draw wave
    double k = 2 * Math.PI / width;
    double xOld = 0;
    double yOld = 0;
    double y;
    double a = 0.4 * height;
    for (int x = 0; x <= width; x += 1)
    {
      y = a * Math.sin(k * (x - d));
      if (x == 0)
      {
        xOld = 0;
        yOld = y;
      }
      else
      {
        
        
        Point ptOld = toPix(xOld, yOld, height);
        Point pt = toPix(x, y, height);
                 g2D.drawLine(ptOld.x, ptOld.y, pt.x, pt.y);
 g2D.drawLine(pt.x, 0, pt.x, pt.y); 

//        g2D.drawLine(ptOld.x, ptOld.y, pt.x, pt.y);

        xOld = x;
        yOld = y;
      }
    }
    return bi;
  }

  private static Point toPix(double x, double y, int height)
  {
    return new Point((int)x, height / 2 - (int)y);
  }


}
