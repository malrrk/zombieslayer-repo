// Ex29.java
// Test exception

import ch.aplu.jgamegrid.*;

public class Ex29 extends GameGrid
{
  public Ex29()
  {
    super(10, 10, 60);
    show();
    delay(2000);
    throw new RuntimeException("hello exception");
  }

  public static void main(String[] args)
  {
    new Ex29();
  }

}
