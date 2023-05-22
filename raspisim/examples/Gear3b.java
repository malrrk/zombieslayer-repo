// Gear3b.java
// Gear, speed

import ch.aplu.raspisim.*;

public class Gear3b
{
  public Gear3b()
  {
    Robot robot = new Robot();
    Gear gear = new Gear();
    while (!robot.isEscapeHit())
    {
      gear.forward(2000);
      gear.left(550); // last value for square
//      gear.left(554); // last value for square
    }
    
    robot.exit();
  }
  
  public static void main(String[] args)
  {
    new Gear3b();
  }
}
