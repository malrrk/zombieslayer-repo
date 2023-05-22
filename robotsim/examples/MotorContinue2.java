// MotorContinue2.java
// 2 motors

import ch.aplu.robotsim.*;

public class MotorContinue2
{
  public MotorContinue2()
  {
    LegoRobot robot = new LegoRobot();
    Motor motA = new Motor(MotorPort.A);  // left motor
    robot.addPart(motA);
    Motor motB = new Motor(MotorPort.B);
    robot.addPart(motB);
    motB.rotateTo(300);
    Tools.delay(2000);
    motB.continueTo(500);
//    motB.continueTo(100);
    System.out.println("done");
    robot.exit();
  }

  public static void main(String[] args)
  {
    new MotorContinue2();
  }
}
