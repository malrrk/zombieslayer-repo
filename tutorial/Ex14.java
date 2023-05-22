// Ex14.java
// ToggleButtons

import ch.aplu.jgamegrid.*;

public class Ex14 extends GameGrid implements GGToggleButtonListener
{
  private Actor lamp1 = new Actor("sprites/lamp.gif", 2);
  private Actor lamp2 = new Actor("sprites/lamp.gif", 2);
  private GGToggleButton tb1 = new GGToggleButton("sprites/commutator.gif", true);
  private GGToggleButton tb2 = new GGToggleButton("sprites/commutator.gif", true);

  public Ex14()
  {
    super(496, 325, 1, false);
    addActor(lamp1, new Location(124, 162));
    addActor(lamp2, new Location(372, 162));
    addActor(tb1, new Location(124, 270));
    addActor(tb2, new Location(372, 270));
    tb1.addToggleButtonListener(this);
    tb2.addToggleButtonListener(this);
    show();
  }

  public void buttonToggled(GGToggleButton button, boolean toggled)
  {
    if (button == tb1)
      lamp1.show(toggled ? 1 : 0);
    if (button == tb2)
      lamp2.show(toggled ? 1 : 0);
    refresh();
  }

  public static void main(String[] args)
  {
    new Ex14();
  }
}
