// SimEx4a.java
// One touch sensor, polling
// Stop when colliding with obstacle

import ch.aplu.robotsim.*;

public class SimEx4a implements CollisionListener
{
  private Gear gear = new Gear();
  
  public SimEx4a()
  {
    LegoRobot robot = new LegoRobot();
    robot.addCollisionListener(this);
    TouchSensor ts = new TouchSensor(SensorPort.S3);
    robot.addPart(gear);
    robot.addPart(ts);
    gear.setSpeed(30);
    gear.forward();
    while (true)
    {
      if (ts.isPressed())
      {
        gear.backward(1200);
        gear.left(750);
        gear.forward();
      }
    }
  }
  
  public void collide()
  {
    gear.stop();
  }

  public static void main(String[] args)
  {
    new SimEx4a();
  }

  // ------------------ Environment --------------------------
  static
  {
    RobotContext.showNavigationBar();
    RobotContext.useObstacle("sprites/bar0.gif", 250, 100);
    RobotContext.useObstacle("sprites/bar1.gif", 400, 250);
    RobotContext.useObstacle("sprites/bar2.gif", 250, 400);
    RobotContext.useObstacle("sprites/bar3.gif", 100, 250);
  }

}
