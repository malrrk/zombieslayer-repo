// Light1a.java
// LightSensor, event driven

import ch.aplu.raspisim.*;

public class Light1a implements LightListener
{
  public Light1a()
  {
    Robot robot = new Robot();
    LightSensor ls = new LightSensor(LightSensor.LS_FRONT_LEFT);
    ls.addLightListener(this);
    while (!robot.isEscapeHit())
    {  
      Tools.delay(500);
    }
    robot.exit();
  }
  
  public void bright(int id, int v)
  {
    System.out.println("bright at " + v);
  }

  public void dark(int id, int v)
  {
    System.out.println("dark at " + v);
  }

  public static void main(String[] args)
  {
    new Light1a();
  }
  
  // ------------------ Environment --------------------------
  static
  {
    RobotContext.useTorch(1, 150, 250, 100);
  }
}