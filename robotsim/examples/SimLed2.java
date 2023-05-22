// SimEx2.java
// Gear

import ch.aplu.robotsim.*;

public class SimLed2
{
  public SimLed2()
  {
    LegoRobot robot = new LegoRobot();
    Gear gear = new Gear();
    robot.addPart(gear);
    for (int i = 0; i < 4; i++)
    {
      if (i == 1)
        robot.setLED(1);
      else if (i == 2)
        robot.setLED(4);
      else if (i == 3)
        robot.setLED(7);
      gear.forward();
      Tools.delay(2000);
      gear.left();
      Tools.delay(550);
    }
    gear.stop();
    robot.setLED(0);

  }

  public static void main(String[] args)
  {
    new SimLed2();
  }
}
