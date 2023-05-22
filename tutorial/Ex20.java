// Ex20.java
// Multithreading: clicking rapidly will cause out of range exception

import ch.aplu.jgamegrid.*;
import java.awt.*;

public class Ex20 extends GameGrid implements GGMouseListener
{
  private int index = 0;
  private Actor actors[] = new Actor[2];

  // Runs in application thread
  public Ex20()
  {
    super(600, 600, 1, null, false);
    setBgColor(Color.blue);
    setSimulationPeriod(30);
    addMouseListener(this, GGMouse.lPress);
    actors[0] = new Actor("sprites/ball_0.gif");
    actors[1] = new Actor("sprites/ball_1.gif");
    for (Actor actor : actors)
      addActor(actor, new Location(300, 300));
    show();
    doRun();
  }

  // Runs in EDT
  public boolean mouseEvent(GGMouse mouse)
  {
    index++;  // animation thread may interrupt EDT,
              // it may "see": index = 2
    doSomething();
    if (index == 2)
      index = 0;
    return true;
  }

  private void doSomething()
  {
    delay(50);
  }

  // Runs in animate thread
  public void act()
  {
    Actor actor = actors[index];
    actor.move();
    if (!actor.isInGrid())
      actor.setDirection(actor.getDirection() + 180);
  }

  public static void main(String[] args)
  {
    new Ex20();
  }
}
