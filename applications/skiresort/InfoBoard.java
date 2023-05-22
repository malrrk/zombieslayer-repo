// InfoBoard.java

import ch.aplu.jgamegrid.GameGrid;
import java.awt.Color;
import java.awt.Font;

public class InfoBoard extends Thread
{
  private SkiResort skiResort;
  private SkiLift skiLift;
  double totalTime = 0;
  int waitingTimeSlow = 0;
  int waitingTimeFast = 0;
  int skiingTimeSlow = 0;
  int skiingTimeFast = 0;
  int liftTimeSlow = 0;
  int liftTimeFast = 0;
  int trackCountSlow = 0;
  int trackCountFast = 0;

  public InfoBoard(SkiResort skiResort, SkiLift skiLift)
  {
    this.skiResort = skiResort;
    this.skiLift = skiLift;
    purgeInfoBoard();
  }

  public void run()
  {
    while (true)
    {
      GameGrid.delay(200);
      if (totalTime == 0)
        continue;
      double averageWaitTimeSlow = waitingTimeSlow / totalTime;
      double averageWaitTimeFast = waitingTimeFast / totalTime;
      double averageSkiingTimeSlow = skiingTimeSlow / totalTime;
      double averageSkiingTimeFast = skiingTimeFast / totalTime;
      double averageLiftTimeSlow = liftTimeSlow / totalTime;
      double averageLiftTimeFast = liftTimeFast / totalTime;
      String s = String.format("[Time %% waiting,riding,skiing (#descents)] "
        + "SLOW: [%4.2f%%, %4.2f%%, %4.2f%% (%d)] - "
        + "FAST: [%4.2f%%, %4.2f%%, %4.2f%% (%d)]",
        averageWaitTimeSlow, averageLiftTimeSlow, averageSkiingTimeSlow, trackCountSlow,
        averageWaitTimeFast, averageLiftTimeFast, averageSkiingTimeFast, trackCountFast);
      skiResort.setStatusText(s, new Font("SansSerif", Font.BOLD, 15), Color.black);
    }
  }

  public void incWaitingTime(boolean slow)
  {
    if (slow)
      waitingTimeSlow += 1;
    else
      waitingTimeFast += 1;
  }

  public void incSkiingTime(boolean slow)
  {
    if (slow)
      skiingTimeSlow += 1;
    else
      skiingTimeFast += 1;
  }

  public void incLiftTime(boolean slow)
  {
    if (slow)
      liftTimeSlow += 1;
    else
      liftTimeFast += 1;
  }

  public void incTrackCount(boolean slow)
  {
    if (slow)
      trackCountSlow += 1;
    else
      trackCountFast += 1;
  }

  public void incTotalTime()
  {
    totalTime += 0.5; // same for slow and fast
  }

  private void purgeInfoBoard()
  {
    totalTime = 0;
    waitingTimeSlow = 0;
    waitingTimeFast = 0;
    skiingTimeSlow = 0;
    skiingTimeFast = 0;
    liftTimeSlow = 0;
    liftTimeFast = 0;
    trackCountSlow = 0;
    trackCountFast = 0;
  }
}
