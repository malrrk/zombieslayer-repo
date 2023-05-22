// ScaledActor.java
// Dynamically scaled actor, used in Ex23.java

import java.awt.*;
import java.awt.image.BufferedImage;
import ch.aplu.jgamegrid.*;

public class ScaledActor extends Actor
{
  private static int nb = 10;  // Number of sprites

  public ScaledActor(Actor actor)
  {
    super(createImages(actor));
  }

  public void act()
  {
    showNextSprite();
  }

  private static BufferedImage[] createImages(Actor actor)
  {
    BufferedImage[] bis = new BufferedImage[nb];
    for (int i = 0; i < nb; i++)
    {
      double factor = 0.1 * (i + 1);
      bis[i] = actor.getScaledImage(factor, 10 * i);
    }
    return bis;
  }
}
