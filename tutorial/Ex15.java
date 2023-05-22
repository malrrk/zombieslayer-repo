// Ex15.java
// CheckButtons

import ch.aplu.jgamegrid.*;
import java.awt.*;

public class Ex15 extends GameGrid implements GGCheckButtonListener
{
  private Actor car = new Actor(true, "sprites/redcar.gif");
  private GGCheckButton cb1 = new GGCheckButton("Run");
  private GGCheckButton cb2 = new GGCheckButton("Fast", true);

  public Ex15()
  {
    super(300, 300, 1, false);
    setSimulationPeriod(50);
    setBgColor(Color.white);
    addActor(car, new Location(150, 50));
    addActor(cb1, new Location(150, 140));
    cb1.addCheckButtonListener(this);
    addActor(cb2, new Location(150, 160));
    cb2.addCheckButtonListener(this);
    show();
  }

  public void act()
  {
    car.move();
    car.turn(2.5);
  }

  public void buttonChecked(GGCheckButton button, boolean checked)
  {
    if (button == cb1)
    {
      if (checked)
        doRun();
      else
        doPause();
    }
    if (button == cb2)
    {
      if (checked)
        setSimulationPeriod(50);
      else
        setSimulationPeriod(100);
    }
  }

  public static void main(String[] args)
  {
    new Ex15();
  }
}
