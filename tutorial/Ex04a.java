// Ex04a.java
// Hamster eating nuts

import ch.aplu.jgamegrid.*;
import java.awt.Color;

public class Ex04a extends GameGrid
{
  public Ex04a()
  {
    super(10, 10, 50, Color.green);
    setBgColor(Color.darkGray);
    for (int i = 0; i < 10; i++)
      addActor(new Rock(), getRandomEmptyLocation());
    for (int i = 0; i < 20; i++)
      addActor(new Hazelnut(), getRandomEmptyLocation());
    addActor(new Hamster(), getRandomEmptyLocation());
    show();
  }

  public static void main(String[] args)
  {
    new Ex04a();
  }
}
