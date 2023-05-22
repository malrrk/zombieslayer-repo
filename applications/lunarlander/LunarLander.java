// LunarLander.java
// Lunar lander

import ch.aplu.jgamegrid.*;

public class LunarLander extends GameGrid
{
  public LunarLander()
  {
    super(600, 600, 1, null, "sprites/moon.gif", false);
    setSimulationPeriod(50);
    Lander lander = new Lander();
    addActor(lander, new Location(330, 100));
    addKeyListener(lander);
    setTitle("Moon Lander (www.aplu.ch) -- Use cursor up/down to increase/decrease thrust");
    show();
    delay(3000);
    doRun();
  }

  public static void main(String[] args)
  {
    new LunarLander();
  }
}
