// Clock.java
// Because we increase the number of rotated sprite images to 120, the 
// the size of the Java heap must be increased by using java -Xms32m -Xmx512m
// You can set this either in the Java Control Panel or on the command line,
// depending on the environment you run your application.

import ch.aplu.jgamegrid.*;
import java.awt.Color;
import java.util.Calendar;

public class Clock extends GameGrid
{
  private static boolean debug = false;
  private Actor sec = new Actor(true, "sprites/sec.gif");
  private Actor min = new Actor(true, "sprites/min.gif");
  private Actor hour = new Actor(true, "sprites/hour.gif");
  private int time = 0;
  private int hours;
  private int minutes;
  private int seconds;

  public Clock()
  {
    // 120 sprites images gives a 3 degree resolution (0.5 min)
    super(604, 604, 1, null, "sprites/clockface.gif", debug, 120);
    getBg().clear(Color.white);
    addActor(hour, new Location(302, 302));
    addActor(min, new Location(302, 302));
    addActor(sec, new Location(302, 302));
    show();
    doRun();
  }

  public void act()
  {
    if (debug)
    {
      time++;
      seconds = time % 60;
      minutes = (time / 60) % 60;
      hours = (time / 3600) % 12;
    }
    else
    {
      Calendar cal = Calendar.getInstance();
      hours = cal.get(Calendar.HOUR_OF_DAY);
      minutes = cal.get(Calendar.MINUTE);
      seconds = cal.get(Calendar.SECOND);
    }
    sec.setDirection(6.0 * seconds);
    min.setDirection(6.0 * minutes);
    hour.setDirection(30.0 * hours + 0.5 * minutes - 1.5);
  }

  public static void main(String[] args)
  {
    new Clock();
  }
}
