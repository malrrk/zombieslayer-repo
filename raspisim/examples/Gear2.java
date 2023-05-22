// Gear2.java
// Gear, speed

import ch.aplu.raspisim.*;

public class Gear2
{
  public Gear2()
  {
    Robot robot = new Robot();
    Gear gear = new Gear();
    gear.setSpeed(60);
    gear.rightArc(0.15, 2000);
    gear.leftArc(0.15, 3600);
    robot.exit();
  }

  public static void main(String[] args)
  {
    new Gear2();
  }
}
