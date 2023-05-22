// SimEx1.java
// Motors

import ch.aplu.robotsim.*;

public class Motor2
{
  public Motor2()
  {
    LegoRobot robot = new LegoRobot();
    Motor motA = new Motor(MotorPort.A);  // left motor
    robot.addPart(motA);
    Motor motB = new Motor(MotorPort.B);
    robot.addPart(motB);
    motA.setSpeed(35);
    
    motA.forward();
    motB.forward();
    Tools.delay(3000);

    robot.exit();
  }

  public static void main(String[] args)
  {
    new Motor2();
  }
}
