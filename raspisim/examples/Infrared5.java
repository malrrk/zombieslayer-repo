// Infrared5.java
// One floor sensor, polling

import ch.aplu.jgamegrid.GGBackground;
import ch.aplu.jgamegrid.GameGrid;
import ch.aplu.raspisim.*;
import java.awt.Color;
import java.awt.Point;

public class Infrared5
{
  public Infrared5()
  {
    Robot robot = new Robot();
    Gear gear = new Gear();
//    InfraredSensor is = new InfraredSensor(InfraredSensor.IR_LINE_LEFT);
    InfraredSensor is = new InfraredSensor(InfraredSensor.IR_LINE_RIGHT);
    gear.setSpeed(20);
    gear.forward();

    double r = 0.3;
    while (!robot.isEscapeHit())
    {
      int v = is.getValue();
      System.out.println("v = " + v);
      Tools.delay(1000);
    }
    robot.exit();
  }

  public static void main(String[] args)
  {
    new Infrared5();
  }

  // ------------------ Environment --------------------------
  private static void _init(GameGrid gg)
  {
    GGBackground bg = gg.getBg();
    bg.setPaintColor(Color.gray);
    bg.fillRectangle(new Point(50, 350), new Point(450, 300));

    int g = 100;
    bg.setPaintColor(new Color(g, g, g));
    bg.fillRectangle(new Point(50, 300), new Point(450, 250));
    
    g = 60;
    bg.setPaintColor(new Color(g, g, g));
    bg.fillRectangle(new Point(50, 250), new Point(450, 200));
    
    g = 30;
    bg.setPaintColor(new Color(g, g, g));
    bg.fillRectangle(new Point(50, 200), new Point(450, 150));

    bg.setPaintColor(Color.black);
    bg.fillRectangle(new Point(50, 150), new Point(450, 100));
  }

  static
  {
    RobotContext.setStartPosition(50, 400);
    RobotContext.setStartDirection(-45);
  }
}
