// TargetFinder.java
// Event driven ultrasonic sensor

import ch.aplu.raspisim.*;
import java.awt.Point;

public class TargetFinder
{
  private Robot robot = new Robot();
  private Gear gear = new Gear();
  private UltrasonicSensor us = new UltrasonicSensor();
  private int left, right;

  public TargetFinder()
  {
    us.setBeamAreaColor("green");
    us.setProximityCircleColor("lightgray");

    gear.setSpeed(10);
    searchTarget();
    gear.left((right - left) * 25);
    gear.forward();
    while (!robot.isEscapeHit())
    {
      double dist = us.getDistance();
      if (dist <= 20)
        break;
    }
    robot.exit();
  }

  private void searchTarget()
  {
    boolean found = false;
    int step = 0;
    while (!robot.isEscapeHit())
    {
      gear.right(50);
      step = step + 1;
      double dist = us.getDistance();
      if (dist != -1)
      {
        if (!found)
        {
          found = true;
          left = step;
        }
      }
      else
      {
        if (found)
        {
          right = step;
          break;
        }
      }
    }
  }

  public static void main(String[] args)
  {
    new TargetFinder();
  }

  // ------------------ Environment --------------------------
  static
  {
    Point[] mesh =
    {
      new Point(50, 0), new Point(25, 43), new Point(-25, 43),
      new Point(-50, 0), new Point(-25, -43), new Point(25, -43)
    };
    RobotContext.useTarget("sprites/redtarget.gif",
      mesh, 400, 400);
  }
}
