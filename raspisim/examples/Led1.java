// Led1.java
// Leds

import ch.aplu.raspisim.*;

public class Led1
{
  public Led1()
  {
    Robot robot = new Robot();
    /*
    while (!robot.isEnterHit());
    Led[] leds = new Led[4];
    for (int id = 0; id < 4; id++)
      leds[id] = new Led(id);

    while (!robot.isEnterHit());
    for (int id = 0; id < 4; id++)
    {  
       leds[id].setColor("green");
       while (!robot.isEnterHit());
    }
    Led.clearAll();
    System.out.println("All done");
    robot.exit();
      */
  }

  public static void main(String[] args)
  {
    new Led1();
  }
}
