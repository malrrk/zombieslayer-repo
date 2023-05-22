// Skier.java

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;

public class Skier extends Actor
{
  public enum State
  {
    onLift, onTop, onTrack, inQueue
  };
  
  private State state;
  private int angle, angle2;
  private final InfoBoard infoBoard;
  private final SkiResort skiResort;
  private double posX, posY;
  private final boolean slow;
  private boolean trackCountReported = false;

  public Skier(SkiResort skiResort, InfoBoard infoBoard, boolean slow)
  {
    super(true, "sprites/skier.png", 6);
    this.skiResort = skiResort;
    this.infoBoard = infoBoard;
    this.slow = slow;
  }

  public void reset()
  {
    state = State.inQueue;
  }

  private void ride()
  {
    if (slow && nbCycles % 3 != 0)  // Adjust speed difference here
      return;
    // out of track at right
    if (getX() > 700)
      angle = 2 + (int)(2 * Math.random());
    // out of track at left
    else if (getX() < 330)
      angle = -2 - (int)(2 * Math.random());
    else if (angle2 > 70)
      angle = -2 - (int)(2 * Math.random());
    else if (angle2 < -70)
      angle = 2 + (int)(2 * Math.random());
    angle2 += angle;
    setDirection(angle2 + 90);
    move();
  }

  public void act()
  {
    SkiLift skiLift = skiResort.getSkiLift();
    infoBoard.incTotalTime();
    if (state == State.onTrack)
    {
      if (!trackCountReported)
      {
        infoBoard.incTrackCount(slow);
        trackCountReported = true;
      }
      infoBoard.incSkiingTime(slow);
      ride();
      if (getY() > 575)
      {
        state = State.inQueue;
        trackCountReported = false;
        skiLift.put(this);
      }
    }

    else if (state == State.onLift)
    {
      infoBoard.incLiftTime(slow);
      useLift();
      if (getY() < 50)
      {
        state = State.onTop;
        setDirection(-3);
        setHorzMirror(true);
        if (slow)
          show(2);
        else
          show(3);
      }
    }
    
    else if (state == State.onTop)
    {
      infoBoard.incLiftTime(slow);
      if (getX() > 350) // start ride
      {
        angle2 = (int)(120 * Math.random() - 60);
        if (angle2 < 0)
          angle = 2;
        else
          angle = -2;
        state = State.onTrack;
        setHorzMirror(false);
        if (slow)
          show(0);
        else
          show(1);
      }
      else // move to right
      {
        setPosition(posX + SkiLift.liftDirectionTop[0] * SkiLift.liftSpeed,
          posY + SkiLift.liftDirectionTop[1] * SkiLift.liftSpeed);
        setLocation(posToLoc());
      }
    }
    
    else if (state == State.inQueue)
      infoBoard.incWaitingTime(slow);

  }

  private void useLift()
  {
    setPosition(posX + SkiLift.liftDirection[0] * SkiLift.liftSpeed,
      posY + SkiLift.liftDirection[1] * SkiLift.liftSpeed);
    setLocation(posToLoc());
  }

  public void setState(State state)
  {
    this.state = state;
  }

  public State getState()
  {
    return state;
  }

  public void setPosition(double x, double y)
  {
    this.posX = x;
    this.posY = y;
  }

  public Location posToLoc()
  {
    return new Location((int)(posX), (int)(posY));
  }

  public boolean isSlow()
  {
    return slow;
  }
}
