// Piano.java
// Imlementation of a simular project from Greenfoot, www.greenfoot.org
// see textbook: Kölling, Introduction to Programming with Greenfoot, Chapter 5
// Swiss german keyboard
// Use UTF-8 character encoding

import ch.aplu.jgamegrid.*;

public class Piano extends GameGrid
{
  private char[] whiteKeys =    // adapt to your keyboard
  {
    'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'ö', 'ä', '$'
  };
  private String[] whiteNotes =
  {
    "3c", "3d", "3e", "3f", "3g", "3a", "3b", "4c", "4d", "4e", "4f", "4g"
  };
  private char[] blackKeys =   // adapt to your keyboard
  {
    'w', 'e', ' ', 't', 'z', 'u', ' ', 'o', 'p', ' ', 'ü'
  };
  private String[] blackNotes =
  {
    "3c#", "3d#", "", "3f#", "3g#", "3a#", "", "4c#", "4d#", "", "4f#"
  };

  public Piano()
  {
    super(800, 340, 1, null, "sprites/wood.gif", false);
    makeKeys();
    show();
  }

  private void makeKeys()
  {
    // Make the white keys
    for (int i = 0; i < whiteKeys.length; i++)
    {
      PianoKey key = new PianoKey(whiteKeys[i], whiteNotes[i] + ".wav",
        "white-key.gif", "white-key-down.gif");
      addActor(key, new Location(54 + (i * 63), 140));
      addKeyListener(key);  // Multiple KeyListeners
    }

    // Make the black keys
    for (int i = 0; i < whiteKeys.length - 1; i++)
    {
      if (blackKeys[i] != ' ')
      {
        PianoKey key = new PianoKey(blackKeys[i], blackNotes[i] + ".wav",
          "black-key.gif", "black-key-down.gif");
        addActor(key, new Location(85 + (i * 63), 86));
        addKeyListener(key);
      }
    }
  }

  public static void main(String[] args)
  {
    new Piano();
  }
}
