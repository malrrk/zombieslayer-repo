// MyGame.java
// GameGrid with one actor
// nb of horiz. cells: 10
// nb of vert. cells: 10
// size of cells: 60 x 60 pixels
// color of grid: red
// background image reef.gif, size: 601 x 601 pixels

import ch.aplu.jgamegrid.*;

public class MyGame extends GameGrid
{
  public MyGame()
  {
    super(10, 10, 60, java.awt.Color.red, "sprites/reef.gif");
    Fish nemo = new Fish();
    addActor(nemo, new Location(2, 4));
    show();
  }

  public static void main(String[] args)
  {
    new MyGame();
  }
}
