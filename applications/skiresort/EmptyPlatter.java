// EmptyPlatter.java

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;

public class EmptyPlatter extends Actor
{
  private static double[] liftStart;
  private static double liftSpeed;
  private static double[] liftDirection;
  private double posX, posY;

  public EmptyPlatter()
  {
    super("sprites/lift_platter.png");
    liftStart = SkiLift.liftStart;
    liftSpeed = SkiLift.liftSpeed;
    liftDirection = SkiLift.liftDirection;
    posX = liftStart[0];
    posY = liftStart[1];
  }

  public void act()
  {
    setPosition(posX + liftDirection[0] * liftSpeed, posY
      + liftDirection[1] * liftSpeed);
    setLocation(posToLoc(posX, posY));

    if (posY < 50)
      removeSelf();
  }

  public void setPosition(double x, double y)
  {
    this.posX = x;
    this.posY = y;
  }

  public Location posToLoc(double n, double m)
  {
    Location loc;
    loc = new Location((int)n, (int)m);
    return loc;
  }
}
