// Gear2.java

import ch.aplu.robotsim.*;

class Gear2
{
  Gear2()
  {
    LegoRobot robot = new LegoRobot();
    Gear gear = new Gear();
    robot.addPart(gear);
    gear.forward(2000);
    gear.leftArc(0.1);
    Tools.delay(2000);
    robot.exit();
  }

  public static void main(String[] args)
  {
    new Gear2();
  }
}
