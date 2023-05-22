// MotorContinueRelative1.java
// 1 motor

import ch.aplu.robotsim.*;

public class MotorContinueRelative1
{
  public MotorContinueRelative1()
  {
    LegoRobot robot = new LegoRobot();
    Motor mot = new Motor(MotorPort.A);  // left motor
    robot.addPart(mot);
    mot.rotateTo(300);
    Tools.delay(2000);
    mot.continueRelativeTo(100);
//    mot.continueRelativeTo(-100);
    System.out.println("done");
    robot.exit();
  }

  public static void main(String[] args)
  {
    new MotorContinueRelative1();
  }
}
