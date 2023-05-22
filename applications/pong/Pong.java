// Pong.java
// Pong, a simple tennis game

import ch.aplu.jgamegrid.*;

public class Pong extends GameGrid
{
  private static int width = 600;
  private static int height = 300;

  public Pong()
  {
    super(width, height, 1, null, "sprites/tenniscourt.gif", false);
 //   playSound(this, GGSound.DUMMY);
    setSimulationPeriod(15);
    LeftPaddle leftPaddle = new LeftPaddle(height);
    RightPaddle rightPaddle = new RightPaddle(height);
    addActor(leftPaddle, new Location(20, height / 2));
    addActor(rightPaddle, new Location(width - 20, height / 2));
    addKeyRepeatListener(leftPaddle);
    addKeyRepeatListener(rightPaddle);
    Tennisball tennisball = new Tennisball(width, height);
    addActor(tennisball, new Location(width / 2, height / 2));

    leftPaddle.addCollisionActor(tennisball);
    rightPaddle.addCollisionActor(tennisball);
    setTitle("Left Player use keys Q, A; Right Player use keys P, L");
    show();
    delay(5000);
    doRun();

    while (true)
    {
      delay(1000);
      setTitle("Score -- Left Player: " + leftPaddle.getNbHits() +
        " hits, Right Player: " + rightPaddle.getNbHits() + " hits");
    }
  }

  public static void main(String[] args)
  {
    new Pong();
  }
}
