// Gear4.java
// Gear, leftArc

import ch.aplu.raspisim.*;

public class Gear5
{
  public Gear5()
  {
    Robot robot = new Robot();
    Gear gear = new Gear();
    gear.forward(1000);
    gear.leftArc(-0.2);
    Tools.delay(3000);
    robot.exit();
  }

  public static void main(String[] args)
  {
    new Gear5();
  }
}