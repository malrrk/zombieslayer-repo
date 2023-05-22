// Ex13.java
// GGButtons

import ch.aplu.jgamegrid.*;
import java.awt.Color;

public class Ex13 extends GameGrid implements GGButtonListener
{
  private GGButton infoBtn = new GGButton("sprites/infobutton.gif", true);
  private int lineNb = 0;

  public Ex13()
  {
    super(400, 200, 1, false);
    setBgColor(new Color(180, 180, 180));
    addActor(new Actor("sprites/buttonbar.gif"), new Location(200, 25));
    addActor(infoBtn, new Location(100, 25));
    infoBtn.addButtonListener(this);
    show();
  }

  public void buttonPressed(GGButton button)
  {
    display("Pressed");
  }

  public void buttonReleased(GGButton button)
  {
    display("Released");
  }

  public void buttonClicked(GGButton button)
  {
    display("Clicked");
  }

  private void display(String text)
  {
    if (lineNb > 10)
      erase();
    addActor(new TextActor(text), new Location(50, 70 + 10 * lineNb++));
  }

  private void erase()
  {
    removeActors(TextActor.class);
    refresh();
    lineNb = 0;
  }

  public static void main(String[] args)
  {
    new Ex13();
  }
}
