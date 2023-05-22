// Ex04.java
// Cell collisions

import ch.aplu.jgamegrid.*;

public class Ex04 extends GameGrid
{
  public Ex04()
  {
    super(10, 10, 50, java.awt.Color.red);
    addActor(new Bear(Leaf.class), new Location(0, 0));
    for (int i = 0; i < 10; i++)
      addActor(new Leaf(), getRandomEmptyLocation());
    show();
  }

  public static void main(String[] args)
  {
    new Ex04();
  }
}
