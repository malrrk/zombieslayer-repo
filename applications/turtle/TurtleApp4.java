// TurtleApp4.java
// The most classic turtle program, recursion

import ch.aplu.jgamegrid.*;

public class TurtleApp4 extends TurtlePane
{
  private Turtle t = new Turtle();

  public TurtleApp4()
  {
    super(false); // No navigation
    addActor(t, new Location(300, 300));
    tree(128);
  }

  private void tree(int s)
  {
    if (s < 16)
      return;
    t.forward(s);
    t.left(45);
    tree(s / 2);
    t.right(90);
    tree(s / 2);
    t.left(45);
    t.back(s);
    refresh();  // To see the turtle working
  }

  public static void main(String[] args)
  {
    new TurtleApp4();
  }
}
