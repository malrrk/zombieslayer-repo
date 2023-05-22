// Motor2.java

import ch.aplu.raspisim.*;

public class Motor2
{
  public Motor2()
  {
    Robot robot = new Robot();
    Motor motL = new Motor(Motor.RIGHT);
    Motor motR = new Motor(Motor.LEFT);

    motL.forward();
    motR.backward();
    Tools.delay(2000);

    motR.setSpeed(20);
    motR.forward();
    Tools.delay(2000);
    
    robot.exit();
  }

  public static void main(String[] args)
  {
    new Motor2();
  }
}
