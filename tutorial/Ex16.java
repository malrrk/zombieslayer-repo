// Ex16.java
// RadioButtons

import ch.aplu.jgamegrid.*;
import java.awt.*;

public class Ex16 extends GameGrid implements GGRadioButtonListener
{
  private Actor car = new Actor(true, "sprites/car.gif", 3);
  private GGRadioButton redBtn = new GGRadioButton("Red", true);
  private GGRadioButton greenBtn = new GGRadioButton("Green");
  private GGRadioButton blueBtn = new GGRadioButton("Blue");

  public Ex16()
  {
    super(300, 300, 1, false);
    setSimulationPeriod(50);
    setBgColor(Color.white);
    addActor(car, new Location(150, 50));
    addActor(redBtn, new Location(150, 140));
    addActor(greenBtn, new Location(150, 160));
    addActor(blueBtn, new Location(150, 180));
    GGRadioButtonGroup group =
      new GGRadioButtonGroup(redBtn, greenBtn, blueBtn);
    group.addRadioButtonListener(this);
    show();
    doRun();
  }

  public void act()
  {
    car.move();
    car.turn(2.5);
  }

  public void buttonSelected(GGRadioButton button, boolean checked)
  {
    if (button == redBtn)
      car.show(0);
    if (button == greenBtn)
      car.show(1);
    if (button == blueBtn)
      car.show(2);
  }

  public static void main(String[] args)
  {
    new Ex16();
  }
}
