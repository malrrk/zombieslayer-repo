// SimEx18.java
// One torch sensor, polling

import ch.aplu.robotsim.*;

public class SimEx18
{
  private Gear gear = new Gear();

  public SimEx18()
  {
    LegoRobot robot = new LegoRobot();
    LightSensor ls = new LightSensor(SensorPort.S1, true);
    robot.addPart(ls);
    while (!robot.isEscapeHit())
    {
      int v = ls.getValue();
      System.out.println("v = " + v);
      Tools.delay(500);
    }
  }

  public static void main(String[] args)
  {
    new SimEx18();
  }

  // ------------------ Environment --------------------------
  static
  {
    RobotContext.useTorch(1, 150, 250, 100);
  }
}
