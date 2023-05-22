// Ex08a.java
// Precise collision detection using IMAGE type
// Drag dart to make the balloon burst
// Use application thread to reset initial situation using putSleep(), wakeUp()
// Play a system sound using playSound()

import ch.aplu.jgamegrid.*;
import ch.aplu.util.*;
import java.awt.*;

public class Ex08a extends GameGrid implements GGActorCollisionListener
{
  public Ex08a()
  {
    super(400, 300, 1, false);
    setTitle("Move dart with mouse to pick the balloon");
    setSimulationPeriod(50);
    playSound(GGSound.DUMMY);  // Speeds-up first sound
    setBgColor(new Color(182, 220, 234));

    Dart dart = new Dart();
    addActor(dart, new Location(50, 50));
    addMouseListener(dart, GGMouse.lDrag);
    dart.addActorCollisionListener(this);
    dart.setCollisionSpot(new Point(30, 0)); // Endpoint of dart needle

    Actor balloon = new Actor("sprites/balloon.gif", 2);
    balloon.setCollisionImage(0);  // Select IMAGE type of detection
    addActor(balloon, new Location(300, 200));
    dart.addCollisionActor(balloon);
    show();
    doRun();
    while (true)
    {
      Monitor.putSleep();             // Stop processing
      dart.hide();                    // Hide dart
      balloon.show(1);                // Show exlode image
      playSound(GGSound.PING);        // Play ping
      dart.setLocation(dart.getLocationStart());   // Init dart
      dart.setDirection(dart.getDirectionStart()); // ditto
      delay(1000);                    // Wait a moment
      balloon.show(0);                // Show heart image
      dart.show();                    // Show dart
      setMouseEnabled(true);          // Enable mouse events
    }
  }

  public int collide(Actor actor1, Actor actor2)
  {
    setMouseEnabled(false);  // Inhibit further mouse events
    Monitor.wakeUp();        // Resume processing
    return 0;
  }

  public static void main(String[] args)
  {
    new Ex08a();
  }
}
