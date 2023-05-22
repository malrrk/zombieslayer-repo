// Button2.java

import ch.aplu.raspisim.*;

public class Button2
{
  public Button2()
  {
    Robot robot = new Robot();
    Gear gear = new Gear();
    while (!robot.isEscapeHit())
    {
      if (robot.isUpHit())
        gear.forward();
      else if (robot.isDownHit())
        gear.backward();
      else if (robot.isLeftHit())
        gear.leftArc(0.1);
      else if (robot.isRightHit())
        gear.rightArc(0.1);
      else if (robot.isEnterHit())
        gear.stop();
    }
    robot.exit();
  }

  public static void main(String[] args)
  {
    new Button2();
  }
}
