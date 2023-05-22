// Ex28.java
// System sounds

import ch.aplu.jgamegrid.*;
import java.awt.event.KeyEvent;

public class Ex28 extends GameGrid
  implements GGKeyListener
{
  private GGTextField tf1;
  private GGTextField tf2;

  public Ex28()
  {
    super(300, 60, 1, false);
    getBg().clear(WHITE);
    addKeyListener(this);
    tf1 = new GGTextField(this, "System Sound Demo. Active keys: A to D",
      new Location(10, 10), false);
    tf2 = new GGTextField(this, "Currently playing: DUMMY",
      new Location(10, 30), false);
    tf1.show();
    tf2.show();
    playSound(GGSound.DUMMY);  // Speeds-up start of first sound clip
    show();
  }

  public boolean keyPressed(KeyEvent evt)
  {
    switch (evt.getKeyCode())
    {
      case KeyEvent.VK_A:
        playSound(GGSound.BOING);
        showInfo("BOING");
        break;
      case KeyEvent.VK_B:
        playSound(GGSound.FADE);
        showInfo("FADE");
        break;
      case KeyEvent.VK_C:
        playSound(GGSound.NOTIFY);
        showInfo("NOTIFY");
        break;
      case KeyEvent.VK_D:
        playSound(GGSound.EXPLODE);
        showInfo("EXPLODE");
        break;
    }
    refresh();
    return true;
  }

  public boolean keyReleased(KeyEvent evt)
  {
    return false;
  }

  private void showInfo(String text)
  {
    tf2.setText("Currently playing: " + text);
  }

  public static void main(String[] args)
  {
    new Ex28();
  }

}
