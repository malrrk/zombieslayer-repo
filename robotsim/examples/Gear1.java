// Gear1.java
// Gear

import ch.aplu.robotsim.*;

public class Gear1
{
  public Gear1()
  {
    LegoRobot robot = new LegoRobot();
    Gear gear = new Gear();
    robot.addPart(gear);

    for (int i = 0; i < 4; i++)
    {
      gear.forward();
      Tools.delay(2000);
      gear.left();
      Tools.delay(550);
    }
    gear.stop();

  }

  public static void main(String[] args)
  {
    new Gear1();
  }
}
