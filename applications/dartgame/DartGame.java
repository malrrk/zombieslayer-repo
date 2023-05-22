// DartGame.java
// Move dart game

import ch.aplu.jgamegrid.*;
import java.util.*;
import java.awt.*;

public class DartGame extends GameGrid implements GGActorCollisionListener
{
  public DartGame()
  {
    super(600, 600, 1, null, "sprites/sky1.gif", false);
    playSound(this, GGSound.DUMMY);
    setSimulationPeriod(10);
    Dart dart = new Dart();
    addMouseListener(dart, GGMouse.lDrag);
    dart.addActorCollisionListener(this);
    dart.setCollisionSpot(new Point(30, 0));

    for (int i = 0; i < 10; i++)
    {
      Balloon balloon = new Balloon();
      balloon.setCollisionImage();
      addActor(balloon, new Location((int)(500*Math.random()), 100 + (int)(400*Math.random())));
      dart.addCollisionActor(balloon);
    }
    addActor(dart, new Location(100 + (int)(400 * Math.random()), 550));

    show();
    doRun();
    while (true)
    {
      int nb = 0;
      ArrayList<Actor> list = getActors(Balloon.class);
      for (Actor a : list)
      {
        if (!a.isActorCollisionEnabled() && a.isVisible())
          nb++;
      }
      setTitle(nb + " balloon(s) in heaven");
      delay(200);
    }
  }

   public int collide(Actor actor1, Actor actor2)
  {
    playSound(this, GGSound.PING);
    actor2.hide();
    return 0;
  }

  public static void main(String[] args)
  {
    new DartGame();
  }
}
