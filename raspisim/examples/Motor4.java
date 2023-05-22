// Motor4.java
// Motors forward-backward with same speed

import ch.aplu.raspisim.*;

public class Motor4
{
  public Motor4()
  {
    Robot robot = new Robot();
    Motor motA = new Motor(Motor.LEFT);
    Motor motB = new Motor(Motor.RIGHT);
//    motA.setSpeed(70);
//    motB.setSpeed(70);
    
    motA.forward();
    motB.forward();
    Tools.delay(2000);

    motB.backward();
    Tools.delay(2000);
    
    robot.exit();
  }

  public static void main(String[] args)
  {
    new Motor4();
  }
}