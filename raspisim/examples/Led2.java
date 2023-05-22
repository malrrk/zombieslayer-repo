// Led2.java
// Leds

import ch.aplu.raspisim.*;

public class Led2
{
  public Led2()
  {
    Robot robot = new Robot();
    while (!robot.isEnterHit());
    Led led = new Led(Led.LED_LEFT);
    System.out.println("Led created");
    while (!robot.isEnterHit());
    led.setColor("yellow");
    System.out.println("Led yellow");
    while (!robot.isEnterHit());
    led.setColor(255, 0, 0);
    System.out.println("Led red");
    while (!robot.isEnterHit());
    Led.clearAll();
    System.out.println("All cleared");
    robot.exit();
  }

  public static void main(String[] args)
  {
    new Led2();
  }
}
