// Infrared1.java
// Two side sensors, channel robot

import ch.aplu.raspisim.*;
//import ch.aplu.raspi.*;

public class Infrared1
{
  public Infrared1()
  {
    Robot robot = new Robot();
    Gear gear = new Gear();
    InfraredSensor isL = 
      new InfraredSensor(InfraredSensor.IR_LEFT);
    InfraredSensor isR = 
      new InfraredSensor(InfraredSensor.IR_RIGHT);
    gear.forward();

    while (!robot.isEscapeHit())
    {
      int vL = isL.getValue();
      int vR = isR.getValue();
      if (vL == 1)
      {
        gear.backward(250);
        gear.right(180);
        gear.forward();
      }
      if (vR == 1)
      {
        gear.backward(250);
        gear.left(180);
        gear.forward();
      }
    }
    robot.exit();
  }

  public static void main(String[] args)
  {
    new Infrared1();
  }

  // ------------------ Environment --------------
  static
  {
    RobotContext.useObstacle("sprites/racetrack.gif", 
      250, 250);
    RobotContext.setStartPosition(420, 480);
  }
}
