// TurtleApp1.java
// A classic turtle program

import ch.aplu.jgamegrid.*;

public class TurtleApp1 extends TurtlePane
{
  private Turtle anna = new Turtle();
  private int step = 10;

  public TurtleApp1()
  {
    addActor(anna, new Location(300, 300));
  }

  public void act()
  {
    anna.forward(step);
    anna.left(90);
    step += 10;
  }

  public void reset()
  {
    step = 10;
  }

  public static void main(String[] args)
  {
    new TurtleApp1();
  }
}
