// SimLed1.java

import ch.aplu.robotsim.*;

public class SimLed1
{
  public SimLed1()
  {
    LegoRobot robot = new LegoRobot();
//    Gear gear = new Gear();
//    robot.addPart(gear);
//    robot.setLED(8);

    for (int i = 0; i < 2; i++)
    { 
      System.out.println(i);
      robot.setLED(i);
      Tools.delay(3000);
    } 
      
    robot.exit();

  }

  public static void main(String[] args)
  {
    new SimLed1();
  }
}
