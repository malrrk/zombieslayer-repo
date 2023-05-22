// CatGame.java
// Tile images images from "Brackeen, Developing Games in Java"

import ch.aplu.jgamegrid.*;
import ch.aplu.util.*;
import java.util.*;
import java.awt.*;

public class CatGame extends GameGrid
{
  private final int nbHorzTiles = 30;
  private final int nbVertTiles = 5;
  private final int tileSize = 64;
  private Dog[] dogs = new Dog[2];
  private Cat cat;
  GGTileMap tm;
  private boolean isFliesEnabled = false;
  private int nbEatenFlies = 0;
  private final String info =
    "Welcome to the Cat Game!\n\n" +
    "The cat wants to move to right where it finds delicious\n" +
    "flies to eat. The dogs are dangerous, so the cat\n" +
    "should keep away from their heads.\n\n" +
    "Use the left and right cursor keys to move the cat,\n" +
    "the space key will make the cat jump.\n\n" +
    "There is only a limited number of flies and the cat\n" +
    "must catch them with his mouth.\n\n" +
    "The aim is to eat as much flies as possible\n" +
    "(the number of eaten flies is shown in the title bar).\n\n" +
    "If a dog catches you, ESC brings you back to the start.\n" +
    "Good luck!";

  public CatGame()
  {
    super(640, 320, 1, null, "sprites/clouds.gif", false);
    setTitle("CatGame - www.aplu.ch/jgamegrid");
    new ModelessOptionPane(20, 20, info, null);
    setSimulationPeriod(50);

    tm = createTileMap(nbHorzTiles, nbVertTiles, tileSize, tileSize);
    for (int i = 0; i < nbHorzTiles; i++)
      tm.setImage("sprites/tile_B.gif", i, 4);

    // first bumb at i = 2, 3
    tm.setImage("sprites/tile_G.gif", 2, 4);
    tm.setImage("sprites/tile_H.gif", 3, 4);

    tm.setImage("sprites/tile_E.gif", 2, 3);
    tm.setImage("sprites/tile_F.gif", 3, 3);

    // second bumb at i = 10, 11
    tm.setImage("sprites/tile_G.gif", 10, 4);
    tm.setImage("sprites/tile_H.gif", 11, 4);

    tm.setImage("sprites/tile_C.gif", 10, 3);
    tm.setImage("sprites/tile_D.gif", 11, 3);

    tm.setImage("sprites/tile_E.gif", 10, 2);
    tm.setImage("sprites/tile_F.gif", 11, 2);

    // third bumb at i = 20, 21, 22, 23
    tm.setImage("sprites/tile_G.gif", 20, 4);
    tm.setImage("sprites/tile_A.gif", 21, 4);
    tm.setImage("sprites/tile_A.gif", 22, 4);
    tm.setImage("sprites/tile_H.gif", 23, 4);

    tm.setImage("sprites/tile_E.gif", 20, 3);
    tm.setImage("sprites/tile_G.gif", 21, 3);
    tm.setImage("sprites/tile_H.gif", 22, 3);
    tm.setImage("sprites/tile_F.gif", 23, 3);

    tm.setImage("sprites/tile_E.gif", 21, 2);
    tm.setImage("sprites/tile_F.gif", 22, 2);

    dogs[0] = new Dog(this);
    dogs[0].addCollisionTile(new Location(3, 3));
    dogs[0].addCollisionTile(new Location(10, 3));
    dogs[0].setCollisionCircle(0, new Point(22, -25), 10);
    dogs[0].setCollisionCircle(1, new Point(22, -25), 10);
    addActor(dogs[0], new Location(350, 225));

    dogs[1] = new Dog(this);
    dogs[1].addCollisionTile(new Location(11, 3));
    dogs[1].addCollisionTile(new Location(20, 3));
    dogs[1].setCollisionCircle(0, new Point(28, -16), 15);
    dogs[1].setCollisionCircle(1, new Point(28, -16), 15);
    addActor(dogs[1], new Location(850, 220));

    cat = new Cat(this);
    addActor(cat, new Location(30, 235));
    addKeyRepeatListener(cat);
    cat.setCollisionRectangle(new Point(0, 0), 35, 55);

    ArrayList<Location> tiles = new ArrayList<Location>();
    // First bumb
    tiles.add(new Location(2, 3));
    tiles.add(new Location(3, 3));

    // Second bumb
    tiles.add(new Location(10, 3));
    tiles.add(new Location(10, 2));
    tiles.add(new Location(11, 3));
    tiles.add(new Location(11, 2));

    // Third bumb
    tiles.add(new Location(20, 3));
    tiles.add(new Location(21, 2));
    tiles.add(new Location(22, 2));
    tiles.add(new Location(23, 3));

    // Comment out these lines for development tests
    cat.addCollisionTiles(tiles);
    cat.addCollisionActor(dogs[0]);
    cat.addCollisionActor(dogs[1]);

    show();
    doRun();
    Monitor.putSleep();
    int nbFlies = 0;
    while (nbFlies < 50)
    {
      Location randomLocation = 
        new Location((int)(400 + 200 * Math.random()), 250);
      Fly fly = new Fly(this);
      addActor(fly, randomLocation);
      fly.addCollisionActor(cat);
      delay((long)(200 + 1000 * Math.random()));
      nbFlies++;
    }
  }

  public void reset()
  {
    tm.setPosition(new Point(0, 0));
    setBgImagePos(new Point(0, 0));
  }

  public void setFliesEnabled(boolean enabled)
  {
    isFliesEnabled = true;
    Monitor.wakeUp();
    cat.setCollisionCircle(new Point(16, -15), 5);
  }

  public boolean isFliesEnabled()
  {
    return isFliesEnabled;
  }

  public void increaseEatenFlies()
  {
    nbEatenFlies++;
    setTitle("Flies eaten: " + nbEatenFlies);
  }

  public Dog[] getDogs()
  {
    return dogs;
  }

  public Cat getCat()
  {
    return cat;
  }

  public static void main(String[] args)
  {
    new CatGame();
  }
}

