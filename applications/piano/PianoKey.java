// PianoKey.java
// Used for Piano

import ch.aplu.jgamegrid.*;
import java.awt.event.KeyEvent;

public class PianoKey extends Actor implements GGKeyListener
{
  private boolean isDown;
  private char keyChar;
  private String soundFile;

  public PianoKey(char keyChar, String soundFile, String img1, String img2)
  {
    super("sprites/" + img1, "sprites/" + img2);  // Actor with two sprites
    this.keyChar = keyChar;
    this.soundFile = soundFile;
  }

   public boolean keyPressed(KeyEvent evt)
   {
     if (!isDown && evt.getKeyChar() == keyChar)
     {
       isDown = true;
       gameGrid.playSound(this, "wav/" + soundFile);
       show(1);
       gameGrid.refresh();
     }
     return false;
   }

   public boolean keyReleased(KeyEvent evt)
   {
     if (isDown && evt.getKeyChar() == keyChar)
     {
       isDown = false;
       show(0);
       gameGrid.refresh();
     }
     return false;
   }
}
