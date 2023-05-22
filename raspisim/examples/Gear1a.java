// Gear1a.java
// Global Gear

import ch.aplu.raspisim.*;

public class Gear1a
{
  Gear gear = new Gear();

  public Gear1a()
  {
    Robot robot = new Robot();

    gear.forward();
    Tools.delay(2000);
    robot.exit();
  }

  public static void main(String[] args)
  {
    new Gear1a();
  }
}