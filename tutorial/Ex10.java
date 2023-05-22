// Ex10.java
// Demonstrates act order: last added actor acts first

import ch.aplu.jgamegrid.*;

public class Ex10 extends GameGrid
{
  public Ex10()
  {
    super(10, 10, 50, java.awt.Color.red);
    addActor(new Bear(Dog.class), new Location(0, 5));
    addActor(new Dog(Bear.class), new Location(9, 5), 180);
//    setActOrder(Bear.class);
    show();
  }

  public static void main(String[] args)
  {
    new Ex10();
  }
}
