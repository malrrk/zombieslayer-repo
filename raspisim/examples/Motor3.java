// Motor3.java
// Motors forward with different speed

import ch.aplu.raspisim.*;

public class Motor3
{
  public Motor3()
  {
    Robot robot = new Robot();
    Motor motA = new Motor(Motor.LEFT);
    Motor motB = new Motor(Motor.RIGHT);
    
    motA.forward();
    motB.forward();
    Tools.delay(2000);

    motA.setSpeed(70);
    Tools.delay(2000);
    
    motB.setSpeed(70);
    Tools.delay(2000);

    robot.exit();
  }

  public static void main(String[] args)
  {
    new Motor3();
  }
}