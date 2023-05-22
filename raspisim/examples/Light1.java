// Light1.java
// LightSensor, polling

import ch.aplu.raspisim.*;
//import ch.aplu.raspi.*;

public class Light1
{
  public Light1()
  {
    Robot robot = new Robot();
    LightSensor lsL = 
      new LightSensor(LightSensor.LS_FRONT_LEFT);
    LightSensor lsR = 
      new LightSensor(LightSensor.LS_FRONT_RIGHT);

    while (!robot.isEscapeHit())
    {
      int vL = lsL.getValue();
      int vR = lsR.getValue();
      showValues(vL, vR);
      Tools.delay(100);
    }
    robot.exit();
  }

  private void showValues(int v1, int v2)
  {
    RobotContext.
      setStatusText("Drag torch! Light intensity" +
      "front left: " + v1 + ", front right: " + v2);
  }

  public static void main(String[] args)
  {
    new Light1();
  }

  // ------------------ Environment --------------------------
  static
  {
    RobotContext.useTorch(1, 150, 250, 100);
    RobotContext.showStatusBar(20);
  }
}
