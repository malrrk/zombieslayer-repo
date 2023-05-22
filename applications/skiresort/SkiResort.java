// SkiResort.java

import java.awt.Color;
import java.awt.Font;
import java.util.Arrays;
import java.util.Collections;
import ch.aplu.jgamegrid.*;

public class SkiResort extends GameGrid
{
  private final int platterNumber = 20;
  private final int platterPeriod = 35;
  private final int nbSkiers = 24;
  private Skier[] skiers = new Skier[nbSkiers];
  private InfoBoard infoBoard;
  private SkiLift skiLift;

  public SkiResort()
  {
    super(800, 600, 1, null, "sprites/skiresort.png", true);
    setSimulationPeriod(40);
    skiLift = new SkiLift(platterNumber, platterPeriod, this);
    infoBoard = new InfoBoard(this, skiLift);
    reset();
    show();
    setTitle("Ski Resort Simulation With Slow And Fast Skiers");
    addStatusBar(30);
    setStatusText("Fast skiers (red) are 3 times as fast as slow skiers (blue)",  
      new Font("SansSerif", Font.BOLD, 15), Color.black);    
    infoBoard.start();
  }

  public void reset()
  {
    removeAllActors();

    Arrays.fill(skiers, null);
    // the first half of the skiers are slow:
    for (int i = 0; i < nbSkiers / 2; i++)
      skiers[i] = new Skier(this, infoBoard, true);
    // The other half is fast:
    for (int i = nbSkiers / 2; i < nbSkiers; i++)
      skiers[i] = new Skier(this, infoBoard, false);

    addActor(skiLift, new Location(135, 297));
    skiLift.show();
    skiLift.purgeQueue();

    // Mix slow and fast skiers
    Collections.shuffle(Arrays.asList(skiers));

    for (int i = 0; i < skiers.length; i++)
    {
      addActor(skiers[i], new Location(-50, -50)); //out of view
      skiers[i].setState(Skier.State.inQueue);
      skiLift.put(skiers[i]);
    }
    setPaintOrder(SkiLift.class, Skier.class, EmptyPlatter.class);
  }
  
  public SkiLift getSkiLift()
  {
    return skiLift;
  }  

  public static void main(String[] args)
  {
    new SkiResort();

  }
}
