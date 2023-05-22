// SoundLevelMeter.java
// Sound level meter, without video window
// aplu5.jar library needed
// The kinect microphone must be your default audio input source and
// the volume has to be set by the system sound panel

import ch.aplu.util.*;
import ch.aplu.kinect.*;
import java.awt.Color;

public class SoundLevelMeter implements SoundLevelListener
{
  private GPanel p = new GPanel(0, 100, 0, 100);
  
  public SoundLevelMeter()
  {
    p.title("Kinect Sound Level Meter. Initializing...");
    Kinect kinect = 
      new Kinect("kinecthandler", "", 0, 0, 0, 0, 0, 1000);  // Invisible
    kinect.addSoundLevelListener(this);
    p.delay(3000);
    p.color(Color.black);
    p.rectangle(45, 0, 55, 100);
    p.enableRepaint(false);
    p.title("Kinect Sound Level Meter. Ready.");
  }
  
  public int soundLevel(int level)
  {
    p.clear();
    p.color(Color.red);
    p.lineWidth(40);
    p.line(50, 0, 50, level);
    p.color(Color.black);
    p.lineWidth(1);
    p.rectangle(45, 0, 55, 100);
    p.repaint();
    return 100;
  }
  
  public static void main(String args[])
  {
    new SoundLevelMeter();
  }
}
