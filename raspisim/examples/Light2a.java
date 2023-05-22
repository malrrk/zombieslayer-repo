// Light2a.java
// LightSensor

import ch.aplu.raspisim.*;

public class Light2a
{
  public Light2a()
  {
    Robot robot = new Robot();
    LightSensor lsFL = new LightSensor(LightSensor.LS_FRONT_LEFT);
    LightSensor lsFR = new LightSensor(LightSensor.LS_FRONT_RIGHT);
    LightSensor lsRL = new LightSensor(LightSensor.LS_REAR_LEFT);
    LightSensor lsRR = new LightSensor(LightSensor.LS_REAR_RIGHT);
    while (!robot.isEscapeHit())
    {
      double v0 = lsFL.getValue();
      double v1 = lsFR.getValue();
      double v2 = lsRL.getValue();
      double v3 = lsRR.getValue();
      System.out.println("FrontLeft, FrontRight, RearLeft, RearRight; " 
        + v0 + ", " + v1 + ", " + v2 + ", " + v3);
      Tools.delay(100);
    }  
    robot.exit();
  }

  public static void main(String[] args)
  {
    new Light2a();
  }
  
   static
  {
    RobotContext.useTorch(1, 150, 250, 100);
    RobotContext.showStatusBar(20);
  }
}