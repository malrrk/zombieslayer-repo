// SimEx13.java
// Creating obstacles programmatically

import ch.aplu.robotsim.*;
import ch.aplu.jgamegrid.*;
import java.awt.*;

public class SimEx13
{
  public SimEx13()
  {
    LegoRobot robot = new LegoRobot();
    Gear gear = new Gear();
    TouchSensor ts = new TouchSensor(SensorPort.S3);
    robot.addPart(gear);
    robot.addPart(ts);
    gear.setSpeed(30);
    gear.forward();
    while (true)
    {
      if (ts.isPressed())
      {
        gear.backward(1200);
        gear.left(750);
        gear.forward();
      }
    }
  }

  public static void main(String[] args)
  {
    new SimEx13();
  }

  // ------------------ Environment --------------------------
  private static GGBitmap bar(int width, int length, Color color)
  {
    GGBitmap bm = new GGBitmap(width, length);
    bm.setPaintColor(color);
    bm.fillRectangle(new Point(0, 0), new Point(width - 1, length - 1));
    return bm;
  }

  private static GGBitmap circle(int radius,  Color color)
  {
    GGBitmap bm = new GGBitmap(2 * radius, 2 * radius);
    bm.setPaintColor(color);
    bm.setLineWidth(3);
    bm.drawCircle(new Point(radius, radius), radius - 1);
    return bm;
  }
  
  static
  {
    RobotContext.setStartPosition(300, 200);
    RobotContext.setStartDirection(30);
    RobotContext.showNavigationBar();
    RobotContext.useObstacle(bar(300, 20, Color.red), 250, 150);
    RobotContext.useObstacle(bar(300, 20, Color.green), 250, 350);
    RobotContext.useObstacle(bar(20, 300, Color.blue), 150, 250);
    RobotContext.useObstacle(bar(20, 300, Color.yellow), 350, 250);
    RobotContext.useObstacle(circle(20, Color.black), 250, 250);
  }

}
