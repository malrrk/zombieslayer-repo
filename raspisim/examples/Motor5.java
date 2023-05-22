// Motor5.java
// Motors forward-backward with different speed

import ch.aplu.raspisim.*;

public class Motor5
{
  public Motor5()
  {
    Robot robot = new Robot();
    Motor motA = new Motor(Motor.LEFT);
    Motor motB = new Motor(Motor.RIGHT);
    
    motA.setSpeed(10);
    motB.setSpeed(10);

    motA.forward();
    motB.forward();
    Tools.delay(2000);

    motB.setSpeed(100);
    motB.backward();
    Tools.delay(2000);
    
    robot.exit();
  }

  public static void main(String[] args)
  {
    new Motor5();
  }
}