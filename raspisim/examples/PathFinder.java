// PathFinder.java

import ch.aplu.raspisim.*;

public class PathFinder
{
  public PathFinder()
  {
    Robot robot = new Robot();
    Gear gear = new Gear();
    InfraredSensor is1 = new InfraredSensor(InfraredSensor.IR_LINE_LEFT);
    InfraredSensor is2 = new InfraredSensor(InfraredSensor.IR_LINE_RIGHT);
    gear.setSpeed(30);
    gear.forward();

    double r = 0.06;
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
    new PathFinder();
  }

  // ------------------ Environment --------------------------
  static
  {
    RobotContext.useBackground("sprites/track.gif");
  }
}
