// Ex09.java
// Tile map

import ch.aplu.jgamegrid.*;
import java.awt.*;

public class Ex09 extends GameGrid implements GGTileCollisionListener
{
  private final int nbHorzTiles = 49;
  private final int nbVertTiles = 5;
  private final int tileSize = 20;
  private GGTileMap tm =
    createTileMap(nbHorzTiles, nbVertTiles, tileSize, tileSize);
  private final int xMapStart = 20;
  private final int yMapStart = 40;
  private final int xMapEnd = -380;
  private int xMap = xMapStart;
  private int dx = -1;
  private Ball[] balls = new Ball[4];

  public Ex09()
  {
    super(620, 180, 1, false);
    setBgColor(java.awt.Color.blue);
    setSimulationPeriod(50);
    tm.setPosition(new Point(xMapStart, yMapStart));

    for (int n = 0; n < 4; n++)
    {
      balls[n] = new Ball();
      addActor(balls[n], getRandomLocation(), getRandomDirection());
      balls[n].setCollisionCircle(new Point(0, 0), 8);
      balls[n].addTileCollisionListener(this);
    }
    balls[2].show(1);
    balls[3].show(1);

    createText();
    show();
    doRun();
  }

  public int collide(Actor actor, Location location)
  {
    int d = 10;
    Point ballCenter = toPoint(actor.getLocation());
    Point brickCenter = toPoint(location);
    if (ballCenter.y < brickCenter.y - d ||
      ballCenter.y > brickCenter.y + d)
      actor.setDirection(340 - actor.getDirection());
    // 340 and not 360 to give some variations
    return 5;
  }

  public void act()
  {
    xMap += dx;
    if (xMap == xMapStart || xMap == xMapEnd)
      dx = -dx;
    tm.setPosition(new Point(xMap, yMapStart));
  }

  private void createText()
  {
    Typesetter ts = new Typesetter();
    letter(0, ts.j);
    letter(5, ts.g);
    letter(11, ts.a);
    letter(17, ts.m);
    letter(24, ts.e);
    letter(30, ts.g);
    letter(36, ts.r);
    letter(42, ts.i);
    letter(45, ts.d);
  }

  private void letter(int x, String s)
  {
    int w = s.length() / 5;
    for (int k = 0; k < 5; k++)
      for (int i = 0; i < w; i++)
        if (s.charAt(k * w + i) == 'x')
          showBrick(x + i, k);
  }

  public void showBrick(int i, int k)
  {
    tm.setImage("sprites/brick.gif", i, k);
    for (int n = 0; n < 4; n++)
      balls[n].addCollisionTile(new Location(i, k));
  }

  public static void main(String[] args)
  {
    new Ex09();
  }
}

