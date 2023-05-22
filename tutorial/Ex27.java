// Ex27.java
// FloodFill

import ch.aplu.jgamegrid.*;
import java.awt.image.BufferedImage;
import java.awt.*;

public class Ex27 extends GameGrid implements GGMouseListener
{
  private BufferedImage seed;

  public Ex27()
  {
    super(600, 400, 1, false);
    setSimulationPeriod(30);
    setTitle("Click to generate a colored camel");
    getBg().clear(new Color(250, 245, 202));
    addMouseListener(this, GGMouse.lPress);
    show();
    seed = new Actor("sprites/camel.gif").getImage();
    doRun();
  }

  public boolean mouseEvent(GGMouse mouse)
  {
    int r = (int)(128 * Math.random() + 128);
    int g = (int)(128 * Math.random() + 128);
    int b = (int)(128 * Math.random() + 128);
    Color color = new Color(r, g, b);

    Location location = toLocation(mouse.getX(), mouse.getY());
    Actor a = new Actor(GGBitmap.floodFill(seed, new Point(75, 30),
      Color.white,
      color));
    addActor(a, location);
    return true;
  }

  public void act()
  {
    for (Actor actor : getActors())
    {
      actor.move(1);
      if (actor.getX() > 700)
        actor.removeSelf();
    }
  }

  public static void main(String[] args)
  {
    new Ex27();
  }

}
