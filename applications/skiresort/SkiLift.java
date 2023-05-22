// SkiLift.java

import java.util.LinkedList;
import java.util.NoSuchElementException;
import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;

public class SkiLift extends Actor
{
  public final static double[] liftStart =
  {
    44, 575
  };
  public static final double liftSpeed = 0.6;
  public static final double[] liftDirection =
  {
    .95, -3
  };
  public static final double[] liftDirectionTop =
  {
    3.3, -0.31
  };

  private final int platterPeriod;
  private final Skier[] skiers;
  private final SkiResort skiResort;
  private final static LinkedList<Skier> queue = new LinkedList<Skier>();
  private final static Location queueStartingPoint = new Location(50, 573);
  private final static int queueWaitingDistance = 30;

  public SkiLift(int platterNumber, int platterPeriod, SkiResort skiResort)
  {
    super("sprites/skilift.png");
    this.platterPeriod = platterPeriod;
    this.skiResort = skiResort;
    skiers = new Skier[platterNumber];
    for (int i = 0; i < platterNumber; i++)
      skiers[i] = null;
  }

  public void act()
  {
    Skier skier;
    if ((this.getNbCycles() % platterPeriod == 0))
    {
      if (queue.isEmpty())
      {
        skiResort.addActor(new EmptyPlatter(),
          new Location(-50, -50));
        skiResort.setPaintOrder(SkiLift.class, Skier.class,
          EmptyPlatter.class);
      }
      else
      {
        // take first skier of queue and place him on lift
        skier = get();
        skier.setState(Skier.State.onLift);
        skier.setPosition(liftStart[0], liftStart[1]);
        // Advance skiers in queue
        for (int i = 0; i < queue.size(); i++)
        {
          int oldX = queue.get(i).getX();
          queue.get(i).setX(oldX - queueWaitingDistance);
        }
      }
    }

  }

  public void put(Skier skier)
  {
    if (!queue.isEmpty())
    {
      int posXOfLastSkierInQueue = queue.getLast().getX();
      skier.setLocation(new Location(posXOfLastSkierInQueue
        + queueWaitingDistance, queueStartingPoint.y));
    }
    else
      // Empty queue
      skier.setLocation(queueStartingPoint);
    queue.add(skier);
    if (skier.isSlow())
      skier.show(2);
    else
      skier.show(3);
    skier.setDirection(0);
  }

  public Skier get()
  // Returns first skier in queue or null im empty
  {
    Skier skier = null;
    try
    {
      skier = queue.removeFirst();
      if (skier.isSlow())
        skier.show(4);
      else
        skier.show(5);
    }
    catch (NoSuchElementException ex)
    {
    }
    return skier;
  }

  public int getSlowSkiersInQueue()
  {
    int slowSkiers = 0;
    for (Skier skier : queue)
      if (skier.isSlow())
        slowSkiers++;
    return slowSkiers;
  }

  public void purgeQueue()
  {
    queue.clear();
  }
}
