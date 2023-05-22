// FlyingBird.java
// Flying and whistling bird 
// Use GGNavigationListener, playSound(), playLoop()

import ch.aplu.jgamegrid.*;
import ch.aplu.util.*;  // Needed for SoundPlayer

public class FlyingBird extends GameGrid
{
  public FlyingBird()
  {
    super(600, 400, 1, "sprites/sunset.gif");
    playSound(this, GGSound.DUMMY);  // Use sound system to get better response
    setBgColor(java.awt.Color.white);
    setSimulationPeriod(50);
    addActor(new Bird(), new Location(50, 200));
    addNavigationListener(new GGNavigationAdapter()
    {
      SoundPlayer player = null;
      public boolean started()
      {
        if (player == null)
          player = playLoop(this, GGSound.BIRD);  // Return value used
        else                              // to invoke SoundPlayer's methods
          player.playLoop();
        return false;  // Don't consume
      }
      
      public boolean paused()
      {
        if (player != null)
          player.stop();
        return false;
      }

      public boolean resetted()
      {
        if (player != null)
          player.stop();
        return false;
      }

      public boolean stepped()
      {
        if (player == null)
          player = playSound(this, GGSound.BIRD);
        else
        {
          player.stop();  // Stop looping sound
          player.play();
        }
        return false;
      }
    });
    show();
  }

  public static void main(String[] args)
  {
    new FlyingBird();
  }
}
