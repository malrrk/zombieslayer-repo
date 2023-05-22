// Ex01.java
// GameGrid with one actor
// nb of horiz. cells: 10
// nb of vert. cells: 10
// size of cells: 60 x 60 pixels
// color of grid: red
// background image reef.gif, size: 601 x 601 pixels

import ch.aplu.jgamegrid.*;

public class Ex01
{
  public Ex01()
  {
    GameGrid gg =
      new GameGrid(10, 10, 60, java.awt.Color.red, "sprites/reef.gif");
    gg.addActor(new Clownfish(), new Location(2, 4));
    gg.show();
  }

  public static void main(String[] args)
  {
    new Ex01();
  }
}
