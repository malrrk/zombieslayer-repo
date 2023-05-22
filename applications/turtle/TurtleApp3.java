// TurtleApp3.java
// Click button Step: shows that the moving turtle is on top of other turtles

import ch.aplu.jgamegrid.*;

public class TurtleApp3 extends TurtlePane
{
  private Turtle[] family = new Turtle[5];
  private int id = 0;

  public TurtleApp3()
  {
    super(true); // Show navigation
    for (int i = 0; i < 5; i++)
    {
      family[i] = new Turtle("sprites/turtle" + i + ".gif");
      addActor(family[i], new Location(300 + 10 * i, 500));
    }
  }

  public void act()
  {
    family[id].forward(10);
    family[id].left(4);
    id++;
    if (id == 5)
      id = 0;
  }

  public static void main(String[] args)
  {
    new TurtleApp3();
  }
}
