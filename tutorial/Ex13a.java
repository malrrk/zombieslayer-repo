// Ex13a.java
// GGButtonOverListener, modify active area

import ch.aplu.jgamegrid.*;
import java.awt.*;

public class Ex13a extends GameGrid
  implements GGButtonListener, GGButtonOverListener
{
  private TextActor ta = new TextActor("Java is fun");
  private Cursor stdCursor;

  public Ex13a()
  {
    super(400, 200, 1, false);
    setBgColor(Color.white);
    GGButton buttonMan = new GGButton("sprites/buttonman.gif", true);
    addActor(buttonMan, new Location(200, 100));
    buttonMan.addButtonListener(this);
    buttonMan.addButtonOverListener(this);
    buttonMan.setHotspotArea(new Point(25, -30), 25, 25);
    addActor(ta, new Location(240, 40));
    ta.hide();
    stdCursor = getCursor();
    show();
  }

  public void buttonPressed(GGButton button)
  {
    ta.show();
    refresh();
  }

  public void buttonReleased(GGButton button)
  {
    ta.hide();
    refresh();
  }

  public void buttonClicked(GGButton button)
  {
  }

  public void buttonEntered(GGButton button)
  {
    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
  }

  public void buttonExited(GGButton button)
  {
    setCursor(stdCursor);
  }

  public static void main(String[] args)
  {
    new Ex13a();
  }
}
