// Ex28b.java
// Sound from application jar (MP3)
// Put the following jars in the runtime classpath:
// jl1.0.1.jar
// mp3spi1.9.4.jar
// tritonus_share.jar

import ch.aplu.util.*;
import ch.aplu.jgamegrid.*;
import java.awt.event.KeyEvent;

public class Ex28b extends GameGrid
  implements GGKeyListener, SoundPlayerListener
{
  private SoundPlayerExt player;
  private int volume = 1000;
  private GGTextField tf1;
  private GGTextField tf2;
  private GGTextField tf3;

  public Ex28b()
  {
    super(400, 60, 1, false);
    getBg().clear(WHITE);
    addKeyListener(this);
    tf1 = new GGTextField(this,
      "Keys: G (Go), S (Stop), P (Pause). UP (Vol+), DOWN (Vol-)",
      new Location(10, 10), false);
    tf2 = new GGTextField(this, "", new Location(10, 30), false);
    tf3 = new GGTextField(this, "", new Location(10, 50), false);
    tf1.show();
    tf2.show();
    tf3.show();
    show();
    // Use sound ressource from application JAR
    player = new SoundPlayerExt(this, "wav/kid.mp3");
    player.addSoundPlayerListener(this);
    player.setVolume(volume);
  }

  public boolean keyPressed(KeyEvent evt)
  {
    switch (evt.getKeyCode())
    {
      case KeyEvent.VK_G:
        player.play();
        tf2.setText("PLAY");
        break;

      case KeyEvent.VK_S:
        player.stop();
        tf2.setText("STOP");
        break;

      case KeyEvent.VK_P:
        player.pause();
        tf2.setText("PAUSE");
        break;

      case KeyEvent.VK_DOWN:
        if (volume != 500)
          volume -= 10;
        player.setVolume(volume);
        tf2.setText("Volume: " + volume);
        break;

      case KeyEvent.VK_UP:
        if (volume != 1000)
          volume += 10;
        player.setVolume(volume);
        tf2.setText("Volume: " + volume);
        break;
    }
    refresh();
    return true;
  }

  public boolean keyReleased(KeyEvent evt)
  {
    return false;
  }

  public void notifySoundPlayerStateChange(int reason, int mixerIndex)
  {
    switch (reason)
    {
      case 0:
        tf3.setText("Playing from start");
        break;

      case 1:
        tf3.setText("Resume playing after pause");
        break;

      case 2:
        tf3.setText("Pausing");
        break;

      case 3:
        tf3.setText("Stopping");
        break;

      case 4:
        tf3.setText("End of sound resource");
        break;
    }
    refresh();
  }

  public static void main(String[] args)
  {
    new Ex28b();
  }

}
