// Ex29a.java
// Test exception

import ch.aplu.jgamegrid.*;

public class Ex29a extends GameGrid
{
  public Ex29a()
  {
    super(10, 10, 60);
    show();
    Actor a = new Actor("sprites/nemox.gif");
    addActor(a, new Location(2, 2));
  }

  public static void main(String[] args)
  {
    new Ex29a();
  }

}
