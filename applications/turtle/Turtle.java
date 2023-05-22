// Turtle.java
// Used for TurtleApp's
// Simple turtle implementation

import ch.aplu.jgamegrid.*;
import java.awt.Color;

public class Turtle extends Actor
{
  private Location loc = new Location();
  private boolean isPenDown = true;

  public Turtle()
  {
    super(true, "sprites/turtle.gif");
  }

  public Turtle(String imagePath)
  {
    super(true, imagePath);
  }

  public void penDown()
  {
    isPenDown = true;
  }

  public void penUp()
  {
    isPenDown = false;
  }

  public void home()
  {
    loc.x = getLocationStart().x;
    loc.y = getLocationStart().y;
    setDirection(getDirectionStart());
  }

  public void clear()
  {
    getBackground().clear(Color.white);
  }

  public void reset()
  {
    home();
  }

  public void forward(int distance)
  {
    setOnTop();
    setLocation(getLocation().getAdjacentLocation(getDirection(), distance));
    if (isPenDown)
    {
      Location newLoc = getLocation();
      getBackground().drawLine(loc.x, loc.y, newLoc.x, newLoc.y);
      loc.x = newLoc.x;
      loc.y = newLoc.y;
    }
  }

  public void back(int distance)
  {
    setOnTop();
    setLocation(getLocation().getAdjacentLocation(180 + getDirection(), distance));
    if (isPenDown)
    {
      Location newLoc = getLocation();
      getBackground().drawLine(loc.x, loc.y, newLoc.x, newLoc.y);
      loc.x = newLoc.x;
      loc.y = newLoc.y;
    }
  }

  public void left(double angle)
  {
    setOnTop();
    turn(360 - angle);
  }

  public void right(double angle)
  {
    setOnTop();
    turn(angle);
  }
}
