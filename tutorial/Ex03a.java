// Ex03a.java
// Horizontal mirror
// Create GameGrid instance explicitely
// Use GGActListerer and GGResetListener

import ch.aplu.jgamegrid.*;
import java.awt.Color;

public class Ex03a implements GGActListener
{
  private Actor nemo = new Actor("sprites/nemo.gif");

  public Ex03a()
  {
    GameGrid gg = new GameGrid(10, 10, 60, Color.red, "sprites/reef.gif");
    gg.addActor(nemo, new Location(2, 4));
    gg.addActListener(this);
    gg.show();
  }

  // Called in every simulation cycle
  public void act()
  {
    nemo.move();
    if (!nemo.isMoveValid())
    {
      nemo.turn(180);
      nemo.setHorzMirror(!nemo.isHorzMirror());
    }
  }

  public static void main(String[] args)
  {
    new Ex03a();
  }
}
