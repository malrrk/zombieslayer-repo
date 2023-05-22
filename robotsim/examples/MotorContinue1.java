// MotorContinue1.java
// 1 motor

import ch.aplu.robotsim.*;

public class MotorContinue1
{
  public MotorContinue1()
  {
    LegoRobot robot = new LegoRobot();
//    Motor mot = new Motor(MotorPort.A);  // left motor
    Motor mot = new Motor(MotorPort.B);  // right motor
    robot.addPart(mot);
    mot.rotateTo(300);
    Tools.delay(2000);
    mot.continueTo(500);
//    mot.continueTo(100);
    System.out.println("done");
    robot.exit();
  }

  public static void main(String[] args)
  {
    new MotorContinue1();
  }
}
