// OptionPane.java

import ch.aplu.jgamegrid.*;
import java.awt.*;

public class OptionPane extends GameGrid implements GGRadioButtonListener
{
  private GGRadioButton rb1 = new GGRadioButton("Bridge", Color.black, Color.lightGray, true);
  private GGRadioButton rb2 = new GGRadioButton("French", Color.black, Color.lightGray);
  private GGRadioButton rb3 = new GGRadioButton("German", Color.black, Color.lightGray);
  private GGButton okBtn = new GGButton("sprites/okBtn30.gif");

  public OptionPane()
  {
    super(200, 140, 1, null, false);
    setPosition(30, 30);
    addExitListener(new GGExitListener()
    {
      public boolean notifyExit()
      {
        hide();
        return false;
      }
    });
    setBgColor(Color.lightGray);
    setTitle("Select Card Deck");
    addActor(rb1, new Location(50, 20));
    addActor(rb2, new Location(50, 50));
    addActor(rb3, new Location(50, 80));
    GGRadioButtonGroup group = new GGRadioButtonGroup();
    group.addRadioButtonListener(this);
    group.add(rb1);
    group.add(rb2);
    group.add(rb3);
    group.addRadioButtonListener(this);
    addActor(okBtn, new Location(175, 110));
    okBtn.addButtonListener(new GGButtonListener()
    {
      public void buttonClicked(GGButton button)
      {
        hide();
      }

      public void buttonPressed(GGButton button)
      {
      }

      public void buttonReleased(GGButton button)
      {
      }
    });
    show();
  }

  public void buttonSelected(GGRadioButton button, boolean selected)
  {
    boolean rc = false;
    if (button == rb1)
      rc = Player.writeProperty("Bridge");
    if (button == rb2)
      rc = Player.writeProperty("French");
    if (button == rb3)
      rc = Player.writeProperty("German");
    if (!rc)
      setTitle("Can't store options");
  }
}
