// BallWorld.java
// Used in Brownian.java

import ch.aplu.jgamegrid.*;
import java.awt.*;

public class BallWorld implements GGActorCollisionListener
{
  private final int nbball = 50;
  private Ball ball[] = new Ball[nbball];

  public static void create(GameGrid gg)
  {
    new BallWorld().initBodies(gg);
  }
  
  private void initBodies(GameGrid gg)
  {
    for (int i = 0; i < nbball; i++)
    {
      ball[i] = new Ball();

      // Put them at random locations, but apart of each other
      boolean ok = false;
      Location loc = new Location(0, 0);
      while (!ok)
      {
        ok = true;
        loc = gg.getRandomLocation();
        for (int k = 0; k < i; k++)
        {
          int dx = ball[k].getLocation().x - loc.x;
          int dy = ball[k].getLocation().y - loc.y;
          if (dx * dx + dy * dy < 400)
            ok = false;
        }
      }
      gg.addActor(ball[i], loc, gg.getRandomDirection());

      // Select collision area
      ball[i].setCollisionCircle(new Point(0, 0), 8);

      // Select collision listener (application class)
      ball[i].addActorCollisionListener(this);

      // Give speed in groups of 5
      if (i < 10)
        ball[i].setSlowDown(1);
      else if (i < 15)
        ball[i].setSlowDown(2);
      else if (i < 25)
        ball[i].setSlowDown(3);
      else if (i < 30)
        ball[i].setSlowDown(5);
      else if (i < 35)
        ball[i].setSlowDown(6);
      else if (i < 30)
        ball[i].setSlowDown(7);
      else if (i < 35)
        ball[i].setSlowDown(8);
      else if (i < 45)
        ball[i].setSlowDown(9);
      else
        ball[i].setSlowDown(10);
    }
    //  Define collision partners, do not duplicate, only indices:
    // 0,1 ; 0,2 ; ... 0,nbball-1
    // 1,2 ; 1,3 ; ... 1,nbball-1
    // ...
    for (int i = 0; i < nbball; i++)
      for (int k = i + 1; k < nbball; k++)
        ball[i].addCollisionActor(ball[k]);
    ball[0].show(1);  // One ball is green
  }

  // Collision callback: simply exchange direction and speed
  public int collide(Actor a, Actor b)
  {
    double dir1 = a.getDirection();
    double dir2 = b.getDirection();
    int sd1 = a.getSlowDown();
    int sd2 = b.getSlowDown();
    a.setDirection(dir2);
    a.setSlowDown(sd2);
    b.setDirection(dir1);
    b.setSlowDown(sd1);
    return 5;  // Wait a moment until collision is rearmed
  }
}
