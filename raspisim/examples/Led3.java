// Led3.java
// Leds mit Gear

import ch.aplu.raspisim.*;

public class Led3
{
  public Led3()
  {
    Robot robot = new Robot();
    Gear gear = new Gear();
    Led.setColorAll("white");
    gear.leftArc(0.5);
    for (int i = 0; i < 4; i++)
    {
      Tools.delay(2000);
      Led.setColorAll("black");
      Tools.delay(2000);
      Led.setColorAll("white");
    }
    System.out.println("All done");
    robot.exit();
  }

  public static void main(String[] args)
  {
    new Led3();
  }
}
