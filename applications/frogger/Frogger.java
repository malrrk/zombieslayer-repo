// Frogger.java

import ch.aplu.jgamegrid.*;
import java.awt.Point;

public class Frogger extends GameGrid
{
  public Frogger()
  {
    super(800, 600, 1, null, "sprites/lane.gif", false);
    playSound(this, GGSound.DUMMY);
    setSimulationPeriod(40);
    Frog frog = new Frog();
    addActor(frog, new Location(400, 560), Location.NORTH);
    frog.setCollisionRectangle(new Point(0, 0), 30, 30);

    Car[] cars = new Car[20];
    for (int i = 0; i < 10; i++)
    {
      cars[i] = new Car("sprites/car" + i + ".gif");
      cars[i].setHorzMirror(true);
      frog.addCollisionActor(cars[i]);
    }
    for (int i = 0; i < 10; i++)
    {
      cars[10 + i] = new Car("sprites/car" + i + ".gif");
      frog.addCollisionActor(cars[10 + i]);
    }

    for (int i = 0; i < 5; i++)
      addActor(cars[i], new Location(350 * i, 90), Location.WEST);
    for (int i = 5; i < 10; i++)
      addActor(cars[i], new Location(350 * (i - 5), 350), Location.WEST);
    for (int i = 10; i < 15; i++)
      addActor(cars[i], new Location(350 * (i - 10), 220), Location.EAST);
    for (int i = 15; i < 20; i++)
      addActor(cars[i], new Location(350 * (i - 15), 480), Location.EAST);

    addKeyRepeatListener(frog);
    setTitle("Frogger (www.aplu.ch) -- Use 4 cursor keys to move the frog");
    show();
    doRun();
  }

  public static void main(String[] args)
  {
    new Frogger();
  }
}
