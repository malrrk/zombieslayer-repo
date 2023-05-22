// SimEx16a.java
// Search fixed target using the ultrasonic sensor

import ch.aplu.robotsim.*;
import java.awt.*;

public class SimEx16a
{
  private TurtleRobot robot;
  private Gear gear;
  private UltrasonicSensor us;

  public SimEx16a()
  {
    robot = new TurtleRobot();
    gear = new Gear();
    robot.addPart(gear);
    gear.setSpeed(10);
    us = new UltrasonicSensor(SensorPort.S1);
    robot.addPart(us);
    us.setBeamAreaColor(Color.green);  // May be commented out
    us.setProximityCircleColor(Color.lightGray); // May be commented out
    runRobotProgram();
  }

  private void runRobotProgram()
  {
    searchTarget();
    while (true)
    {
      if (us.getDistance() < 50)
        gear.stop();
    }
  }

  private void searchTarget()
  {
    while (true)
    {
      gear.right(50);
      int distance = us.getDistance();
      if (distance != -1)
      {
        gear.right(1500);
        gear.forward();
        return;
      }
    }
  }

  public static void main(String[] args)
  {
    new SimEx16a();
  }

  // ------------------ Environment --------------------------
  static
  {
    Point[] mesh =
    {
      new Point(50, 0), new Point(25, 42), new Point(-25, 42),
      new Point(-50, 0), new Point(-25, -42), new Point(25, -42)
    };

    RobotContext.useTarget("sprites/target_red.gif", mesh, 350, 350);
  }
}