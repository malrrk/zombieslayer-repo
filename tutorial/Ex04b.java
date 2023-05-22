// Ex04b.java
// Like Ex04a, window undecorated, use Exit button to terminate

import ch.aplu.jgamegrid.*;
import java.awt.Color;

public class Ex04b extends GameGrid implements GGButtonListener
{
  private GGButton exitButton = new GGButton("sprites/ggExitButtonA.gif");

  public Ex04b()
  {
    super(10, 10, 50, Color.green, null, false, true);  // Undecorated
    setBgColor(Color.darkGray);
    addActor(exitButton, new Location(9, 9));
    exitButton.addButtonListener(this);
    for (int i = 0; i < 10; i++)
      addActor(new Rock(), getRandomEmptyLocation());
    for (int i = 0; i < 20; i++)
      addActor(new Hazelnut(), getRandomEmptyLocation());
    addActor(new Hamster(), getRandomEmptyLocation());
    show();
    doRun();
  }

  public void buttonPressed(GGButton button)
  {
  }

  public void buttonReleased(GGButton button)
  {
  }

  public void buttonClicked(GGButton button)
  {
     System.exit(0);
  }

  public static void main(String[] args)
  {
    new Ex04b();
  }
}
