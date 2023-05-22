// SimEx20.java
// Shadows

import ch.aplu.robotsim.*;
import java.awt.Color;

public class SimEx20
{
  public SimEx20()
  {
    LegoRobot robot = new LegoRobot();
    LightSensor ls = new LightSensor(SensorPort.S3, true);
    robot.addPart(ls);
    Gear gear = new Gear();
    robot.addPart(gear);

    gear.leftArc(0.5);
    boolean isLightOn = false;
    while (!robot.isEscapeHit())
    {
      int v = ls.getValue();
      if (!isLightOn && v == 0)
      {
        isLightOn = true;
        robot.playTone(2000, 100);
      }
      if (isLightOn && v > 0)
      {
        isLightOn = false;
        robot.playTone(1000, 100);
      }
      Tools.delay(100);
    }
    robot.exit();
  }

  public static void main(String[] args)
  {
    new SimEx20();
  }

  // ------------------ Environment --------------------------
  static
  {
    RobotContext.useTorch(1, 250, 250, 100);
    RobotContext.useShadow(50, 150, 450, 200);
    RobotContext.useShadow(100, 300, 350, 450);
  }
}
