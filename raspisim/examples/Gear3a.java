// Gear3a.java
// Gear, speed

import ch.aplu.raspisim.*;

public class Gear3a
{
  public Gear3a()
  {
    Robot robot = new Robot();
    Gear gear = new Gear();
    gear.setSpeed(20);
//    gear.setSpeed(100);
    while (!robot.isEscapeHit())
    {
      gear.forward();
      Tools.delay(2000);
      gear.left();
//      Tools.delay(550);
      Tools.delay(554); // last value for square
    }
    
    robot.exit();
  }
  
  public static void main(String[] args)
  {
    new Gear3a();
  }
}
