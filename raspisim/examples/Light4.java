// Light4.java
// LightSensor

import ch.aplu.jgamegrid.*;
import ch.aplu.raspisim.*;

public class Light4
{
  public Light4()
  {
    Robot robot = new Robot();
    Gear gear = new Gear();
    LightSensor lsFL = new LightSensor(LightSensor.LS_FRONT_LEFT);
    LightSensor lsFR = new LightSensor(LightSensor.LS_FRONT_RIGHT);
    LightSensor lsRL = new LightSensor(LightSensor.LS_REAR_LEFT);
    LightSensor lsRR = new LightSensor(LightSensor.LS_REAR_RIGHT);
    gear.setSpeed(25);
    gear.forward();
    double s = 0.02;
    while (!robot.isEscapeHit())
    {
      double vFL = lsFL.getValue();
      double vFR = lsFR.getValue();
      double vRL = lsRL.getValue();
      double vRR = lsRR.getValue();
      double d = (vFL - vFR) / (vFL + vFR);
      if (vRL + vRR > vFL + vFR)  // torch behind robot
        gear.right();
      else if (d > -s && d < s)
        gear.forward();
      else
      {
        if (d >= s)
          gear.rightArc(0.05);
        else
          gear.leftArc(0.05);
      }
      Tools.delay(100);
    }
    robot.exit();
  }

  public static void main(String[] args)
  {
    new Light4();
  }

  // ------------------ Environment --------------------------
  static
  {
    RobotContext.useTorch(1, 150, 250, 100);
    RobotContext.useTorch(1, 250, 250, 100);
    RobotContext.useTorch(1, 350, 250, 100);
  }
}
