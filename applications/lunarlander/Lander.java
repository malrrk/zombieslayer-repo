// Lander.java
// Used for LunarLander

import ch.aplu.util.*;
import ch.aplu.jgamegrid.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Lander extends Actor implements GGKeyListener
{
  private final double startFuel = 1000;  // Amount of fuel at start
  private final double fuelFactor = 0.5; // Fuel consumtion per simulation period and thrust level
  private final double amax = 1.6; // Free acceleration on moon
  private double x;  // Position in east direction
  private double y;  // Position in downward direction
  private double v;  // Speed in downward direction
  private double a;  // Accelleration in downward direction
  private int z; // Thrust level
  private double fuel; // Remaining fuel
  private Actor debris = new Actor("sprites/landerdebris.gif");
  private Actor thrust = new Actor("sprites/thrust.gif", 9);
  private boolean isLanded = false;
  private HiResTimer timer = new HiResTimer();
  private SoundPlayer player = new SoundPlayer(this, "wav/jet.wav");
  private boolean fuelExpired;
  private boolean[] isPlayed = new boolean[12];
  private String[] fuelLevel = new String[12];

  public Lander()
  {
    super("sprites/lander.gif");
    player.setVolume(0);
    for (int i = 0; i < 12; i++)
      fuelLevel[i] = "wav/fuel" + i + ".wav";
  }

  public void reset()
  {
    GameGrid gg = gameGrid;
    setDirection(Location.SOUTH);
    x = getLocationStart().x;
    y = getLocationStart().y;
    v = 0;
    a = amax;
    fuel = startFuel;
    fuelExpired = false;
    show();
    setActEnabled(true);
    if (debris.gameGrid == null)  // not yet added to GameGrid
      gg.addActor(debris, new Location());
    debris.hide();
    if (thrust.gameGrid == null) // not yet added to GameGrid
      gg.addActor(thrust, new Location());
    thrust.hide();
    isLanded = false;
    gg.setBgColor(java.awt.Color.black);
    timer.start();
    player.setVolume(0);
    player.playLoop();
    for (int i = 0; i < 12; i++)
      isPlayed[i] = false;
    gg.playSound(this, fuelLevel[11]);
  }

  public void act()
  {
    GameGrid gg = gameGrid;
    double vdisp = (int)(100 * v) / 100.0;
    double h = 490 - y;
    String s;
    if (fuelExpired)
      s = String.format("h = %10.2f m     v = %10.2f m/s    a = %10.2f m/s^2    fuel = %10.0f kg (expired)", h, v, a, fuel);
    else
      s = String.format("h = %10.2f m     v = %10.2f m/s    a = %10.2f m/s^2    fuel = %10.0f kg", h, v, a, fuel);
    gg.setTitle(s);
    double dt = 2 * gg.getSimulationPeriod() / 1000.0;  // Time scaled: * 2
    v = v + a * dt;
    y = y + v * dt;
    setLocation(new Location((int)x, (int)y));
    thrust.setLocation(new Location((int)x, (int)y + 57));
    fuel = fuel - z * fuelFactor;
    for (int i = 2; i <= 10; i++)
    {
      if (fuel <= (i - 1) * 100.0 && fuel > (i - 2) * 100.0)
      {
        if (!isPlayed[i])
        {
          gg.playSound(this, fuelLevel[i]);
          isPlayed[i] = true;
        }
      }
    }
    if (fuel <= 50 && fuel > 0)
    {
      if (!isPlayed[1])
      {
        gg.playSound(this, fuelLevel[1]);
        isPlayed[1] = true;
      }
    }
    if (fuel == 0)
    {
      if (!isPlayed[0])
      {
        gg.playSound(this, fuelLevel[0]);
        isPlayed[0] = true;
      }
    }

    if (fuel <= 0)
    {
      fuel = 0;
      z = 0;
      a = amax;
      setThrust(0);
      player.setVolume(0);
      fuelExpired = true;
    }

    if (getLocation().y > 490 && !isLanded)
    {
      gg.setTitle("Touchdown!");
      if (v > 10.0)
      {
        debris.setLocation(new Location(getLocation().x, getLocation().y + 30));
        debris.show();
        hide();
        gg.getBg().drawText("Sorry! Crashed with speed: " + vdisp + " m/s", new Point(20, 300));
        gg.playSound(this, GGSound.EXPLODE);
      }
      else
      {
        long time = timer.getTime();
        gg.getBg().drawText("Congratulation! Landed with speed: " + vdisp + " m/s", new Point(20, 300));
        gg.getBg().drawText("Time used: " + time / 1000000 + " s", new Point(20, 350));
        gg.getBg().drawText("Remaining fuel: " + (int)fuel + " kg", new Point(20, 400));
        gg.playSound(this, GGSound.FADE);
      }
      player.stop();
      gg.getBg().drawText("Press any key...", new Point(20, 450));
      setActEnabled(false);
      z = 0;
      setThrust(0);
      isLanded = true;
    }
  }

  public boolean keyPressed(KeyEvent evt)
  {
    if (!gameGrid.isRunning())
      return true;

    if (isLanded)
    {
      reset();
      gameGrid.doRun();
      return true;
    }

    double da = 0.4;
    switch (evt.getKeyCode())
    {
      case KeyEvent.VK_UP:
        a -= da;
        z += 1;
        break;
      case KeyEvent.VK_DOWN:
        a += da;
        z -= 1;
        if (a > amax)
        {
          a = amax;
          z = 0;
        }
        break;
    }
    if (a == amax)
    {
      setThrust(0);
      player.setVolume(0);
    }
    else
    {
      player.setVolume((int)(-100 * a) + 820);
      setThrust(z);
    }
    return true;  // Consume
  }

  public boolean keyReleased(KeyEvent evt)
  {
    return true;
  }

  private void setThrust(int i)
  {
    if (i < 0)
      i = 0;
    if (i > 8)
      i = 8;
    thrust.show(i);
  }
}
