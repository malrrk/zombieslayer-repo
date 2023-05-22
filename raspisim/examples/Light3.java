// Light3.java
// LightSensor

import ch.aplu.raspisim.*;

public class Light3
{
  public Light3()
  {
    Robot robot = new Robot();
    Gear gear = new Gear();
    LightSensor lsL = new LightSensor(LightSensor.LS_FRONT_LEFT);
    LightSensor lsR = new LightSensor(LightSensor.LS_FRONT_RIGHT);
    gear.setSpeed(25);
    gear.forward();
    double s = 0.02;
    while (!robot.isEscapeHit())
    {
      double vL = lsL.getValue();
      double vR = lsR.getValue();
      double d = (vL - vR) / (vL + vR);
      if (d > -s && d < s)
        gear.forward();
      else if (d >= s) // on left side
        gear.leftArc(0.05);
      else
        gear.rightArc(0.05); // on right side
      Tools.delay(100);
    }
    robot.exit();
  }

  public static void main(String[] args)
  {
    new Light3();
  }

  // ------------------ Environment --------------------------
  static
  {
    RobotContext.useTorch(1, 150, 250, 100);
    RobotContext.showStatusBar(20);
    RobotContext.setStatusText("Drag Torch");
  }
}
