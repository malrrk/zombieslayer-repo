// Line1.java
// Two side sensors, channel robot

import ch.aplu.raspisim.*;
//import ch.aplu.raspi.*;

public class Line1
{
  public Line1()
  {
    Robot robot = new Robot();
    Gear gear = new Gear();
    gear.setSpeed(30);
    InfraredSensor isL = 
      new InfraredSensor(InfraredSensor.IR_LINE_LEFT);
    InfraredSensor isR = 
      new InfraredSensor(InfraredSensor.IR_LINE_RIGHT);
    gear.forward();

    while (!robot.isEscapeHit())
    {
      int vL = isL.getValue();
      int vR = isR.getValue();
      if (vL == 0 || vR == 0)
      {
        gear.backward(1200);
        gear.left(550);
        gear.forward();
      }
    }
    robot.exit();
  }

  public static void main(String[] args)
  {
    new Line1();
  }

  // ------------------ Environment --------------------------
  static
  {
    RobotContext.useBackground("sprites/circle.gif");
  }
}
