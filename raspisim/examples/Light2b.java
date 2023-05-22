// Light2b.java
// LightSensor

import ch.aplu.raspisim.*;

public class Light2b
{
  public Light2b()
  {
    Robot robot = new Robot();
    Gear gear = new Gear();
    gear.leftArc(0.2);
    LightSensor ls = new LightSensor(LightSensor.LS_FRONT_LEFT);
    while (!robot.isEscapeHit())
    {
      double v = ls.getValue();
      System.out.println("v: " + v);
      Tools.delay(1000);
    }  
    robot.exit();
  }

  public static void main(String[] args)
  {
    new Light2b();
  }
  
   static
  {
    RobotContext.useTorch(1, 150, 250, 100);
    RobotContext.showStatusBar(20);
  }
}