// Gear1.java
// Gear

import ch.aplu.raspisim.*;

public class Gear1
{
  public Gear1()
  {
    Robot robot = new Robot();
    Gear gear = new Gear();

    gear.forward();
    Tools.delay(2000);
    robot.exit();
  }

  public static void main(String[] args)
  {
    new Gear1();
  }
}