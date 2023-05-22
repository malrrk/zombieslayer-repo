// Line2.java
// Two side sensors, channel robot

import ch.aplu.raspisim.*;
//import ch.aplu.raspi.*;

public class Line2
{
  public Line2()
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
      if (vL == 1 && vR == 0)
        gear.forward();
      else if (vL == 1 && vR == 1)
        gear.rightArc(0.1);
      else if (vL == 0 && vR == 0)
        gear.leftArc(0.1);
    }
    robot.exit();
  }

  public static void main(String[] args)
  {
    new Line2();
  }

  // ------------------ Environment --------------------------
  static
  {
    RobotContext.useBackground("sprites/border.gif");
    RobotContext.setStartPosition(250, 490);
  }
}
