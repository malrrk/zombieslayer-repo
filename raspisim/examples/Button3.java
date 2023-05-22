// Button3.java

import ch.aplu.raspisim.*;

public class Button3
{
  public Button3()
  {
    Robot robot = new Robot();
    Gear gear = new Gear();
    boolean isRunning = true;
    while (isRunning)
    {
      if (robot.isButtonHit())
      {
        switch (robot.getHitButtonID())
        {
          case BrickButton.ID_DOWN:
            gear.backward();
            System.out.println("down");
            break;
          case BrickButton.ID_UP:
            gear.forward();
            System.out.println("up");
            break;
          case BrickButton.ID_LEFT:
            gear.left();
            System.out.println("left");
            break;
          case BrickButton.ID_RIGHT:
            gear.right();
            System.out.println("right");
            break;
          case BrickButton.ID_ENTER:
            gear.stop();
            System.out.println("enter");
            break;
          case BrickButton.ID_ESCAPE:
            System.out.println("escape");
            isRunning = false;
            break;
        }
      }
    }
    robot.exit();
  }

  public static void main(String[] args)
  {
    new Button3();
  }
}
