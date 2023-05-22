// TurtleApp2.java
// The most classic turtle program, no use of act()

import ch.aplu.jgamegrid.*;

public class TurtleApp2 extends TurtlePane
{
  public TurtleApp2()
  {
    super(false);  // No navigation
    Turtle anna = new Turtle();
    addActor(anna, new Location(300, 300));
    for (int step = 10; step <= 400; step += 10)
    {
      anna.forward(step);
      anna.left(90);
      refresh();  // Must repaint the game grid
    }
  }

  public static void main(String[] args)
  {
    new TurtleApp2();
  }
}
