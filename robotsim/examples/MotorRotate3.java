// SimEx1.java
// Motors

import ch.aplu.robotsim.*;

public class MotorRotate3
{
  public MotorRotate3()
  {
    LegoRobot robot = new LegoRobot();
    Motor motA = new Motor(MotorPort.A);  // left motor
    robot.addPart(motA);
    Motor motB = new Motor(MotorPort.B);
    robot.addPart(motB);
    motB.setSpeed(30);
    motA.rotateTo(300, false);
    motB.rotateTo(300, true);
    Tools.delay(2000);
//    motA.rotateTo(300);
    motA.rotateTo(200, false);
    motB.rotateTo(200, true);
    System.out.println("done");
    robot.exit();
  }

  public static void main(String[] args)
  {
    new MotorRotate3();
  }
}
