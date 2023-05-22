// SimEx14two.java
// Poll two ultrasonic sensors

import ch.aplu.robotsim.*;
import java.awt.Color;
import java.awt.Point;

public class SimEx14two
{
  public SimEx14two()
  {
    LegoRobot robot = new LegoRobot();
    Gear gear = new Gear();
    robot.addPart(gear);
    UltrasonicSensor us1 = new UltrasonicSensor(SensorPort.S1);
    robot.addPart(us1);
    us1.setBeamAreaColor(Color.green);
    us1.setProximityCircleColor(Color.lightGray);
    UltrasonicSensor us2 = new UltrasonicSensor(SensorPort.S2);
    robot.addPart(us2);
  
    gear.setSpeed(15);
    gear.forward();
    
    while (true)
    {
      int distance1 = us1.getDistance();
      int distance2 = us2.getDistance();
      System.out.println("d2 = " + distance2);
      if (distance1 > 0 && distance1 < 50)
      {
        gear.backward(2000);
        gear.left(1000);
        gear.forward();
      }
    }
  }

  public static void main(String[] args)
  {
    new SimEx14two();
  }

   // ------------------ Environment --------------------------
  static
  {
    Point[] mesh_hbar =
    {
      new Point(200, 10), new Point(-200, 10),
      new Point(-200, -10), new Point(200, -10)
    };
    Point[] mesh_vbar =
    {
      new Point(10, 200), new Point(-10, 200),
      new Point(-10, -200), new Point(10, -200)
    };
    RobotContext.useTarget("sprites/bar0.gif", mesh_hbar, 250, 100);
    RobotContext.useTarget("sprites/bar0.gif", mesh_hbar, 250, 400);
    RobotContext.useTarget("sprites/bar1.gif", mesh_vbar, 100, 250);
    RobotContext.useTarget("sprites/bar1.gif", mesh_vbar, 400, 250);
  }

}
