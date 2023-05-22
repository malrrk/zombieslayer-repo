// SimEx5.java
// One touch sensor, event driven

import ch.aplu.robotsim.*;

public class SimEx5 implements TouchListener
{
  private Gear gear = new Gear();

  public SimEx5()
  {
    LegoRobot robot = new LegoRobot();
    TouchSensor ts = new TouchSensor(SensorPort.S3);
    robot.addPart(gear);
    robot.addPart(ts);
    ts.addTouchListener(this);
    gear.setSpeed(30);
    gear.forward();
  }

  public void pressed(SensorPort port)
  {
    gear.backward(1200);
    gear.left(750);
    gear.forward();
  }

  public void released(SensorPort port)
  {
  }

  public static void main(String[] args)
  {
    new SimEx5();
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
