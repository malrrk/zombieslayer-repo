// Car.java
// Used for Frogger game

import ch.aplu.jgamegrid.*;

public class Car extends Actor
{
  public Car(String imagePath)
  {
    super(imagePath);
  }

  public void act()
  {
    move();
    if (getLocation().x < -100)
      setX(1650);
    if (getLocation().x > 1650)
      setX(-100);
  }
}
