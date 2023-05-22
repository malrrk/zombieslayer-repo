// Light6a.java
// Shadow

import ch.aplu.raspisim.*;
import java.awt.Color;

public class Light6a
{
  public Light6a()
  {
    Robot robot = new Robot();
    Gear gear = new Gear();
    LightSensor ls = new LightSensor(LightSensor.LS_FRONT_LEFT);
    gear.leftArc(0.5);
    boolean isLightOn = false;
    while (!robot.isEscapeHit())
    {
      int v = ls.getValue();
      if (!isLightOn && v == 0)
      {  
        isLightOn = true;
        Led.setColorAll(Color.white);
      }
      if (isLightOn && v > 0)
      {  
        isLightOn = false;
        Led.clearAll();
      }
      Tools.delay(100);
    }  
    robot.exit();
  }

  public static void main(String[] args)
  {
    new Light6a();
  }
  
  // ------------------ Environment --------------------------
  static
  {
    RobotContext.useTorch(1, 250, 250, 100);
    RobotContext.useShadow(50, 150, 450, 200);
    RobotContext.useShadow(100, 300, 350, 450);
  }
}