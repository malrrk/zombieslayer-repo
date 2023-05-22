// Led4.java
// Leds blinking

import ch.aplu.raspisim.*;

public class Led4
{
  public Led4()
  {
    Robot robot = new Robot();
    Led[] leds = new Led[4];
    for (int id = 0; id < 4; id++)
      leds[id] = new Led(id);
    leds[0].startBlinker("white", "black", 500, 1000, 5, true);
    robot.exit();
    System.out.println("All done");
  }

  public static void main(String[] args)
  {
    new Led4();
  }
}
