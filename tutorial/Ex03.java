// Ex03.java
// No fish class, overrides GameGrid.act() and GameGrid.reset()

import ch.aplu.jgamegrid.*;
import java.awt.Color;

public class Ex03 extends GameGrid
{
  private Actor nemo = new Actor("sprites/nemo.gif");

  public Ex03()
  {
    super(10, 10, 50, Color.red, "sprites/reef.gif");
    addActor(nemo, new Location(2, 4));
    show();
  }

  // Called in every simulation cycle
  public void act()
  {
    nemo.move();
    if (!nemo.isMoveValid())
    {
      nemo.turn(180);
      nemo.setHorzMirror(!nemo.isHorzMirror()) ;
    }
  }

  public static void main(String[] args)
  {
    new Ex03();
  }
}
