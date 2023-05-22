// MotorContinueRelative2.java
// 2 motors

import ch.aplu.robotsim.*;

public class MotorContinueRelative2
{
  public MotorContinueRelative2()
  {
    LegoRobot robot = new LegoRobot();
    Motor motA = new Motor(MotorPort.A);  // left motor
    robot.addPart(motA);
    Motor motB = new Motor(MotorPort.B);
    robot.addPart(motB);
    motB.rotateTo(300);
    Tools.delay(2000);
    motB.continueRelativeTo(100);
//    motB.continueRelativeTo(-100);
    System.out.println("done");
    robot.exit();
  }

  public static void main(String[] args)
  {
    new MotorContinueRelative2();
  }
}
