  // Button1.java

import ch.aplu.raspisim.*;

public class Button1
{
  public Button1()
  {
    Robot robot = new Robot();
    Gear gear = new Gear();
    gear.setSpeed(70);
    gear.leftArc(0.5);
    while (!robot.isEscapeHit())
      continue;
    robot.exit();
  }

  public static void main(String[] args)
  {
    new Button1();
  }
}
