// SimEx2.java
// Gear

import ch.aplu.robotsim.*;

public class SimLed3
{
  public SimLed3()
  {
    LegoRobot robot = new LegoRobot();
    Gear gear = new Gear();
    robot.addPart(gear);
    
    robot.setLED(1);
    Tools.delay(5000);
    robot.setLED(4);
    Tools.delay(5000);
    robot.setLED(7);
    Tools.delay(5000);

    robot.setLED(2);
    Tools.delay(5000);
    robot.setLED(5);
    Tools.delay(5000);
    robot.setLED(8);
    Tools.delay(5000);
    
    robot.setLED(3);
    Tools.delay(5000);
    robot.setLED(6);
    Tools.delay(5000);
    robot.setLED(9);
    Tools.delay(5000);
    robot.setLED(0);
  }

  public static void main(String[] args)
  {
    new SimLed3();
  }
}
