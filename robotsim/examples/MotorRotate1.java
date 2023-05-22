// MotorRotate1.java
// 1 motor

import ch.aplu.robotsim.*;

public class MotorRotate1
{
  public MotorRotate1()
  {
    LegoRobot robot = new LegoRobot();
//    Motor mot = new Motor(MotorPort.A);  // left motor
    Motor mot = new Motor(MotorPort.B);  // right motor
    robot.addPart(mot);
    mot.rotateTo(300);
    Tools.delay(2000);
    mot.rotateTo(300);
//    mot.rotateTo(-300);
    System.out.println("done");
    robot.exit();
  }

  public static void main(String[] args)
  {
    new MotorRotate1();
  }
}
