// Infrared3.java
// One ir sensor, polling

import ch.aplu.raspisim.*;

public class Infrared3
{
  public Infrared3()
  {
    Robot robot = new Robot();
    Gear gear = new Gear();
    InfraredSensor is = new InfraredSensor(InfraredSensor.IR_CENTER);
    gear.setSpeed(30);
    gear.forward();
    while (true)
    {
      if (is.getValue() == 1)
      {
        gear.backward(1200);
        gear.left(750);
        gear.forward();
      }
    }
  }

  public static void main(String[] args)
  {
    new Infrared3();
  }

  // ------------------ Environment --------------------------
  static
  {
    RobotContext.showNavigationBar();
    RobotContext.useObstacle("sprites/bar0.gif", 250, 100);
    RobotContext.useObstacle("sprites/bar1.gif", 400, 250);
    RobotContext.useObstacle("sprites/bar2.gif", 250, 400);
    RobotContext.useObstacle("sprites/bar3.gif", 100, 250);
  }

}
