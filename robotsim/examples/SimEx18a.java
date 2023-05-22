// SimEx18.java
// 4 torch sensors, polling

import ch.aplu.robotsim.*;

public class SimEx18a
{
  private Gear gear = new Gear();

  public SimEx18a()
  {
    LegoRobot robot = new LegoRobot();
    LightSensor frontRight = new LightSensor(SensorPort.S1, true);
    LightSensor frontLeft = new LightSensor(SensorPort.S2, true);
    LightSensor rearRight = new LightSensor(SensorPort.S3, true);
    LightSensor rearLeft = new LightSensor(SensorPort.S4, true);
    
    robot.addPart(gear);
    robot.addPart(frontRight);
    robot.addPart(frontLeft);
    robot.addPart(rearRight);
    robot.addPart(rearLeft);
    while (!robot.isEscapeHit())
    {
      int v1 = frontRight.getValue();
      int v2 = frontLeft.getValue();
      int v3 = rearRight.getValue();
      int v4 = rearLeft.getValue();
      System.out.println("v = " + v1 + ", "+ v2 + ", "+ v3 + ", "+ v4);
      Tools.delay(500);
    }
  }

  public static void main(String[] args)
  {
    new SimEx18a();
  }

  // ------------------ Environment --------------------------
  static
  {
    RobotContext.useTorch(1, 150, 250, 100);
  }
}
