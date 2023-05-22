// Infrared6.java
// Two floor sensors, track follower

import ch.aplu.jgamegrid.GGBackground;
import ch.aplu.jgamegrid.GameGrid;
import ch.aplu.raspisim.*;
import java.awt.Color;
import java.awt.Point;

public class Infrared6
{
  public Infrared6()
  {
    Robot robot = new Robot();
    Gear gear = new Gear();
    InfraredSensor is1 = new InfraredSensor(InfraredSensor.IR_LINE_LEFT);
    InfraredSensor is2 = new InfraredSensor(InfraredSensor.IR_LINE_RIGHT);
    gear.setSpeed(60);
    gear.forward();

    double r = 0.4;
    while (!robot.isEscapeHit())
    {
      int v1 = is1.getValue();
      int v2 = is2.getValue();
      if (v1 == 0 && v2 == 0)
        gear.forward();
      if (v1 == 0 && v2 == 1)
        gear.leftArc(r);
      if (v1 == 1 && v2 == 0)
        gear.rightArc(r);
      if (v1 == 1 && v2 == 1)
        gear.backward();
    }
    robot.exit();
  }

  public static void main(String[] args)
  {
    new Infrared6();
  }

  // ------------------ Environment --------------------------
  private static void _init(GameGrid gg)
  {
    GGBackground bg = gg.getBg();
    bg.setPaintColor(Color.black);
    bg.fillArc(new Point(250, 150), 120, 0, 360);
    bg.setPaintColor(Color.white);
    bg.fillArc(new Point(250, 150), 60, 0, 360);
    bg.setPaintColor(Color.black);
    bg.fillArc(new Point(250, 350), 120, 0, 360);
    bg.setPaintColor(Color.white);
    bg.fillArc(new Point(250, 350), 60, 0, 360);
  }

  static
  {
    RobotContext.setStartDirection(10);
  }
}
