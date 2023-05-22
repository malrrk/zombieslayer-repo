// Ex18Demo.java
// Demonstration for Ex18 without need of Xbox controller

import ch.aplu.jgamegrid.*;

public class Ex18Demo extends GameGrid implements GGActorCollisionListener
{
  private final int xStart = 400;
  private final int yStart = 265;
  private final Actor nozzle = new Actor(true, "sprites/nozzle.gif", 2);

  private int nbEaten = 0;
  private int nbGluttons = 0;
  private volatile boolean doCreate = false;

  public Ex18Demo()
  {
    super(800, 530, 1, null, "sprites/background.gif", false);
    setSimulationPeriod(50);
    playSound(this, GGSound.DUMMY);
    addActor(nozzle, new Location(xStart, yStart));

    show();
    doRun();
    delay(2000);
    while (true)
    {
      delay(400);
      setTitle("Gluttons: " + nbGluttons + ". Eaten Ants: " + nbEaten);
    }
  }

  public void act()
  {
    if (getNbCycles() % 30 == 0)
    {
      doCreate = true;
    }

    if (getNbCycles() % 2 == 0)
    {
      nozzle.setDirection(nozzle.getDirection() + 2);
      refresh();
    }

    if (getNbCycles() % 200 == 0)
    {
      nozzle.setLocation(new Location(200 + (int)(400 * Math.random()),
        250 + (int)(200 * Math.random())));
      refresh();
    }

    if (getNbCycles() % 5 == 0)  // Slow down ant creation
    {
      Ant ant = new Ant();
      ant.addCollisionActors(getActors(Glutton.class));
      ant.addActorCollisionListener(this);
      ant.setSlowDown(4);
      addActor(ant, getRandomLocation(), 360 * Math.random());
    }

    if (doCreate) // Create glutton
    {
      nozzle.show(1);
      new Thread()
      {
        public void run()
        {
          delay(300);
          nozzle.show(0);
        }
      }.start();
      doCreate = false;
      Glutton glutton = new Glutton();
      nbGluttons++;
      glutton.addCollisionActors(getActors(Ant.class));
      glutton.addActorCollisionListener(this);
      addActor(glutton, nozzle.getLocation(), nozzle.getDirection());
    }
  }

  public int collide(Actor a1, Actor a2)
  {
    playSound(this, GGSound.PING);
    if (a1 instanceof Ant)
      removeActor(a1);
    if (a2 instanceof Ant)
      removeActor(a2);
    nbEaten++;
    return 0;
  }

  public static void main(String[] args)
  {
    new Ex18Demo();
  }
}
