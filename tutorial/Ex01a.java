// Ex01a.java
// Test for closing mode

import ch.aplu.jgamegrid.*;

public class Ex01a extends GameGrid
{
  public Ex01a()
  {
    super(10, 10, 60, java.awt.Color.red, "sprites/reef.gif");
    setClosingMode(ClosingMode.NothingOnClose);
    addActor(new Clownfish(), new Location(2, 4));
    show();
  }

  public static void main(String[] args)
  {
    new Ex01a();
  }
}
