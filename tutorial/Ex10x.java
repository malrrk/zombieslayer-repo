// Ex10x.java
// Set act order

import ch.aplu.jgamegrid.*;

public class Ex10x extends GameGrid
{
  public Ex10x()
  {
    super(10, 10, 60, java.awt.Color.red);
    addActor(new Bear(Dog.class), new Location(0, 5));
    addActor(new Dog(Bear.class), new Location(9, 5), 180);
    setActOrder(Bear.class);
    show();
  }

  public static void main(String[] args)
  {
    new Ex10x();
  }
}
