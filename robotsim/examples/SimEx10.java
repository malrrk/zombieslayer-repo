// SimEx10.java
// One light sensor, one touchsensor, road follower

import ch.aplu.robotsim.*;

public class SimEx10
{
  public SimEx10()
  {
    LegoRobot robot = new LegoRobot();
    Gear gear = new Gear();
    LightSensor ls = new LightSensor(SensorPort.S3);
    TouchSensor ts = new TouchSensor(SensorPort.S1);
    robot.addPart(gear);
    robot.addPart(ls);
    robot.addPart(ts);
    ls.activate(true);

    while (true)
    {
      int v = ls.getValue();
      if (v < 100)  // black
        gear.forward();
      if (v > 600 && v < 700)  // green
        gear.leftArc(0.1);
      if (v > 800)  // yellow
        gear.rightArc(0.1);
      if (ts.isPressed())
      {  
        gear.stop();
        break;
      }
    }
  }

  public static void main(String[] args)
  {
    new SimEx10();
  }

  // ------------------ Environment --------------------------
  static
  {
    RobotContext.setStartPosition(40, 460);
    RobotContext.setStartDirection(-90);
    RobotContext.useBackground("sprites/road.gif");
    RobotContext.useObstacle("sprites/chocolate.gif", 400, 50);
  }
}
