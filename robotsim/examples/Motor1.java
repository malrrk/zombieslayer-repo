// Motor1.java
// Motors

import ch.aplu.robotsim.*;

public class Motor1
{
  public Motor1()
  {
    LegoRobot robot = new LegoRobot();
    Motor motB = new Motor(MotorPort.B);
    robot.addPart(motB);
    Motor motA = new Motor(MotorPort.A);  // left motor
    robot.addPart(motA);
    
    System.out.println("1---- L-Forward, R-Forward");
    motA.forward();
    motB.forward();
    while (!robot.isEscapeHit())
      Tools.delay(100);

    System.out.println("2---- L-Forward, R-Stopped");
    motA.forward();
    motB.stop();
    while (!robot.isEscapeHit())
      Tools.delay(100);

    System.out.println("3---- L-Forward, R-Backward");
    motA.forward();
    motB.backward();
    while (!robot.isEscapeHit())
      Tools.delay(100);

    System.out.println("4---- L-Stopped, R-Forward");
    motA.stop();
    motB.forward();
    while (!robot.isEscapeHit())
      Tools.delay(100);

    System.out.println("5---- L-Backward, R-Forward");
    motA.backward();
    motB.forward();
    while (!robot.isEscapeHit())
      Tools.delay(100);

    System.out.println("6---- L-Backward, R-Backward");
    motA.backward();
    motB.backward();
    while (!robot.isEscapeHit())
      Tools.delay(100);


    robot.exit();
  }

  public static void main(String[] args)
  {
    new Motor1();
  }
}
