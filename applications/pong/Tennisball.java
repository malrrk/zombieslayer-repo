// Tennisball.java
// Used for Pong

import ch.aplu.jgamegrid.*;

public class Tennisball extends Actor
{

  private int width;
  private int height;

  public Tennisball(int width, int height)
  {
    super("sprites/tennisball.gif");
    this.width = width;
    this.height = height;
  }

  public void reset()
  {
    setLocation(new Location(width / 2, height / 2));
    double direction;
    double r = Math.random();
    if (r < 0.25)
      direction = -60 + 40 * Math.random();
    if (r >= 0.25 && r < 0.5)
      direction = 20 + 40 * Math.random();
    if (r >= 0.5 && r < 0.75)
      direction = 120 + 40 * Math.random();
    else
      direction = 200 + 40 * Math.random();
    direction = 80;
    setDirection(direction);
  }

  public void act()
  {
    Location loc = getLocation();
    double dir = getDirection();
    if (loc.x < 20 || loc.x > width - 20)
    {
      setDirection(180 - dir);
      if (loc.x < 20)
        setLocation(new Location(25, loc.y));
      else
        setLocation(new Location(width - 25, loc.y));
    }

    double r = 20 * Math.random();  // Add some inaccuracy to make game smarter
    if (loc.y < 15 || loc.y > height - 15)
    {
      if (dir > 45 && dir <= 90 || dir > 225 && dir <= 270 ||
        dir >= 135 && dir <= 180 || dir > 315 && dir < 360)
        dir = 360 - dir + r;
      else if (dir >= 90 && dir < 135 || dir >= 270 && dir < 315 ||
        dir >= 0 && dir <= 45 || dir >= 180 && dir <= 225)
        dir = 360 - dir - r;
      else
        dir = 360 - dir;
      setDirection(dir);
      if (loc.y < 15)
        setLocation(new Location(loc.x, 20));
      else
        setLocation(new Location(loc.x, height - 20));
    }
    move();
  }
}
