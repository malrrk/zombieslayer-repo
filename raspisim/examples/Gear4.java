// Gear4.java
// Gear, leftArc

import ch.aplu.raspisim.*;

public class Gear4
{
  public Gear4()
  {
    Robot robot = new Robot();
    Gear gear = new Gear();
//    gear.setSpeed(100);
    gear.setSpeed(20);

    gear.forward();
    Tools.delay(500);
    gear.leftArc(1);
    Tools.delay(10000);
    robot.exit();
  }

  public static void main(String[] args)
  {
    new Gear4();
  }
}