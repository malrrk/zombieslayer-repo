// Ex05.java
// Use of CapturedActor
// Sprite images from http://www.brackeen.com/javagamebook

import ch.aplu.jgamegrid.*;

public class Ex05 extends GameGrid
{
  private CapturedActor head =
    new CapturedActor(false, "sprites/head_0.gif", 1);

  public Ex05()
  {
    super(800, 600, 1);
    setSimulationPeriod(40);
    setBgColor(new java.awt.Color(100, 100, 100));
    addActor(head, new Location(400, 200), 66);
    show();
  }

  public void act()
  {
    head.move(10);
  }

  public static void main(String[] args)
  {
    new Ex05();
  }
}
