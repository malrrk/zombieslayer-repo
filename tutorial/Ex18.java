// Ex18.java
// Use of XBox controller
// See Ex18Demo for a demonstration without Xbox

/*
 * This program design shows how to decouple code for different tasks
 * - Initializing in application constructor
 * - Nozzle actions in controller callbacks, some delegated further
 * - Glutton movement in class Glutton
 * - Ant movement in class Ant
 * - Glutton/Ant collision in GGActorCollisionListener callback
 * - Ant and glutton creation in application's act()
 * 
 * Be careful: Do not modify/access actor list in several threads without
 * synchronizing. 
 *
 * Because all act() and collision callbacks are executed in the same
 * game thread, no synchronization is needed here.
 */
import ch.aplu.jgamegrid.*;
import ch.aplu.xboxcontroller.*;

public class Ex18 extends GameGrid implements GGActorCollisionListener
{
  private final int xStart = 400;
  private final int xInc = 350;
  private final int yStart = 265;
  private final int yInc = 200;
  private final String connectInfo = "Connected -- LeftThumb to move, LeftTrigger to turn, X to create";
  private final Actor nozzle = new Actor(true, "sprites/nozzle.gif", 2);
  private double mag = 0;
  private double dir = 0;
  private int nbEaten = 0;
  private int nbGluttons = 0;
  private Ex18 appl;

  public Ex18()
  {
    super(800, 530, 1, null, "sprites/background.gif", false);
    setSimulationPeriod(50);
    playSound(this, GGSound.DUMMY);
    addActor(nozzle, new Location(xStart, yStart));
    XboxController xc = new XboxController();
    if (!xc.isConnected())
      setTitle("Xbox controller not connected");
    else
      setTitle(connectInfo);
    xc.setLeftThumbDeadZone(0.1);

    // Register controller callbacks
    xc.addXboxControllerListener(new XboxControllerAdapter()
    {
      public void leftThumbMagnitude(double magnitude)
      {
        mag = magnitude;
        setPosition();
      }

      public void leftThumbDirection(double direction)
      {
        dir = direction;
        setPosition();
      }

      public void leftTrigger(double value)
      {
        nozzle.setDirection(360 * value);
        refresh();
      }

      public void buttonX(boolean pressed)
      {
        if (pressed)
        {
          nozzle.show(1);
          Glutton glutton = new Glutton();
          nbGluttons++;
          glutton.addCollisionActors(getActors(Ant.class));
          glutton.addActorCollisionListener(appl);
          addActor(glutton, nozzle.getLocation(), nozzle.getDirection());
        }
        else
          nozzle.show(0);
      }

      public void isConnected(boolean connected)
      {
        if (connected)
          setTitle(connectInfo);
        else
          setTitle("Connection lost");
        GameGrid.delay(2000);
      }
    });

    appl = this;
    show();
    doRun();
    delay(2000);
    while (true)
    {
      delay(400);
      if (xc.isConnected())
        setTitle("Gluttons: " + nbGluttons + ". Eaten Ants: " + nbEaten);
    }
  }

  public void act()
  {
    if (getNbCycles() % 5 == 0)  // Slow down ant creation
    {
      Ant ant = new Ant();
      ant.addCollisionActors(getActors(Glutton.class));
      ant.addActorCollisionListener(this);
      ant.setSlowDown(4);
      addActor(ant, getRandomLocation(), 360 * Math.random());
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

  private void setPosition()
  {
    int x = (int)(xStart + xInc * toX(mag, dir));
    int y = (int)(yStart - yInc * toY(mag, dir));
    nozzle.setLocation(new Location(x, y));
    refresh();
  }

  private double toX(double r, double phi)
  {
    return r * Math.sin(Math.toRadians(phi));
  }

  private double toY(double r, double phi)
  {
    return r * Math.cos(Math.toRadians(phi));
  }

  public static void main(String[] args)
  {
    new Ex18();
  }
}
