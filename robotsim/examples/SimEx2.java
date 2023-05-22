// SimEx2.java
// Gear

import ch.aplu.robotsim.*;

public class SimEx2
{
  public SimEx2()
  {
    LegoRobot robot = new LegoRobot();
    Gear gear = new Gear();
    robot.addPart(gear);

    gear.forward();
    Tools.delay(2000);
    gear.left(2000);
    gear.forward(2000);
    gear.leftArc(0.2, 2000);
    gear.forward(2000);
    gear.leftArc(-0.2, 2000);
    robot.exit();
  }

  public static void main(String[] args)
  {
    new SimEx2();
  }
}