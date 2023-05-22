// Led4.java
// Leds blinking

import ch.aplu.raspisim.*;

public class Led6
{
  public Led6()
  {
    Robot robot = new Robot();
    Led[] leds = new Led[4];
    for (int id = 0; id < 4; id++)
      leds[id] = new Led(id);
    leds[0].startBlinker("white", "black", 500, 1000);
    Tools.delay(5000);
    robot.exit();
    System.out.println("All done");
  }

  public static void main(String[] args)
  {
    new Led6();
  }
}
