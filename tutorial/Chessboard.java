// Chessboard.java

import ch.aplu.jgamegrid.*;

public class Chessboard extends GameGrid
{
  public Chessboard()
  {
    super(500, 500);
    show();
    GGPanel p = getPanel();
    p.window(0, 8, 0, 8);

    for (int i = 0; i < 8; i++)
      for (int j = 0; j < 8; j++)
        if ((i + j) % 2 == 0)
          p.rectangle(i, j, i + 1, j + 1, true);
    p.line(0, 8, 8, 8);
  }

  public static void main(String[] args)
  {
    new Chessboard();
  }
}
