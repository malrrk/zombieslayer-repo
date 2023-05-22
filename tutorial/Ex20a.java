// Ex20a.java
// Synchronization removes crash

import ch.aplu.jgamegrid.*;
import java.awt.*;

public class Ex20a extends GameGrid implements GGMouseListener
{
  private volatile int index = 0;
  private Actor actors[] = new Actor[2];

  public Ex20a()
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

  public synchronized boolean mouseEvent(GGMouse mouse)
  {
    index++;
    doSomething();
    if (index == 2)
      index = 0;
    return true;
  }

  private void doSomething()
  {
    delay(50);
  }

  public synchronized void act()
  {
    Actor actor = actors[index];
    actor.move();
    if (!actor.isInGrid())
      actor.setDirection(actor.getDirection() + 180);
  }

  public static void main(String[] args)
  {
    new Ex20a();
  }
}
