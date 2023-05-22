// TurtlePane.java
// Used for TurtleApp's
// A game grid for turtles

import ch.aplu.jgamegrid.*;
import java.awt.Color;

public class TurtlePane extends GameGrid
{
  public TurtlePane(boolean showNavigation)
  {
    super(600, 600, 1, null, showNavigation);
    setBgColor(Color.white);
    getBg().setPaintColor(Color.black);
    setSimulationPeriod(50);
    show();
  }

  public TurtlePane()
  {
    this(true);
  }

  public void reset()
  {
    setBgColor(Color.white);
  }

}
