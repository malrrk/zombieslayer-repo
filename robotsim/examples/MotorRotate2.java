// MotorRotate2.java
// 2 motors

import ch.aplu.robotsim.*;

public class MotorRotate2
{
  public MotorRotate2()
  {
    LegoRobot robot = new LegoRobot();
    Motor motA = new Motor(MotorPort.A);  // left motor
    robot.addPart(motA);
    Motor motB = new Motor(MotorPort.B);
    robot.addPart(motB);
    motB.rotateTo(300);
    Tools.delay(2000);
    motB.rotateTo(300);
 //   motB.rotateTo(-300);
    System.out.println("done");
    robot.exit();
  }

  public static void main(String[] args)
  {
    new MotorRotate2();
  }
}
