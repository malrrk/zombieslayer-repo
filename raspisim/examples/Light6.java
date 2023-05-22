// Light6.java
// Shadow

import ch.aplu.raspisim.*;

public class Light6
{
  public Light6()
  {
    Robot robot = new Robot();
    Gear gear = new Gear();
    LightSensor ls = new LightSensor(LightSensor.LS_FRONT_LEFT);
    gear.leftArc(0.5);
    while (!robot.isEscapeHit())
    {
      int v = ls.getValue();
      System.out.println("v: " + v);
      Tools.delay(500);
    }  
    robot.exit();
  }

  public static void main(String[] args)
  {
    new Light6();
  }
  
  // ------------------ Environment --------------------------
  static
  {
    RobotContext.useTorch(1, 250, 250, 100);
    RobotContext.useShadow(50, 150, 450, 200);
    RobotContext.useShadow(100, 300, 350, 450);
  }
}