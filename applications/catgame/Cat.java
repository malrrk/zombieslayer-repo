// Cat.java
// Used in CatGame.java

import ch.aplu.jgamegrid.*;
import java.awt.event.KeyEvent;
import java.awt.*;

public class Cat extends Actor 
  implements GGKeyRepeatListener, GGTileCollisionListener, GGActorCollisionListener
{
  private Location location;
  private boolean downward;
  private boolean forward;
  private boolean backward;
  private boolean onTop;
  private CatGame cg;
  private int tmX;
  private int tmY;
  private boolean isGameOver;

  public Cat(CatGame cg)
  {
    super("sprites/cat.gif", 3);
    this.cg = cg;
    addTileCollisionListener(this);
  }

  public void reset()
  {
    tmX = 0;
    tmY = 0;
    downward = false;
    forward = false;
    backward = false;
    onTop = false;
    isGameOver = false;
    setHorzMirror(false);
    location = getLocationStart();
  }

  public void act()
  {
    // -------- Move cat ----------
    if (downward && !onTop)
    {
      location.y += 5;
      if (location.y == 235)
        downward = false;
      setLocation(location);
    }
    if (forward)
    {
      forward = false;
      location.x += 10;
      if (!downward || onTop)
        show((getIdVisible() == 0 ? 1 : 0));
      setLocation(location);
    }
    if (backward)
    {
      backward = false;
      if (!downward || onTop)
        show((getIdVisible() == 0 ? 1 : 0));
      location.x -= 10;
      if (cg.isFliesEnabled())
        location.x = Math.max(380, location.x);  // inhibit to go back
      setLocation(location);
    }
    onTop = false;

    // -------- Move tile map and background image ------------
    if (cg.isFliesEnabled())  // Inhibit movement of map
      return;
    GGTileMap tm = cg.getTileMap();
    Dog[] dogs = cg.getDogs();
    location = getLocation();
    if (location.x > 400)
    {
      // Move tile map
      tmX -= 5;
      if (tmX < -1280)
        tmX = -1280;
      else
      {
        location.x = 400;
        setLocation(location);
        tm.setPosition(new Point(tmX, tmY));
        cg.setBgImagePos(new Point(tmX, 0));
        dogs[0].setX(dogs[0].getX() - 5);
        dogs[1].setX(dogs[1].getX() - 5);
      }
      
    }
    if (location.x < 300)
    {
      // Move tile map
      tmX += 5;
      if (tmX > 0)
        tmX = 0;
      else
      {
        location.x = 300;
        setLocation(location);
        tm.setPosition(new Point(tmX, tmY));
        gameGrid.setBgImagePos(new Point(tmX, 0));
        dogs[0].setX(dogs[0].getX() + 5);
        dogs[1].setX(dogs[1].getX() + 5);
      }
    }
    if (tmX < -1180)
      cg.setFliesEnabled(true);

  }

  public void keyRepeated(int keyCode)
  {
    switch (keyCode)
    {
      case KeyEvent.VK_SPACE:
        if (downward && !onTop)
          return;
        location = getLocation();
        location.y = 50;
        setLocation(location);
        downward = true;
        break;
      case KeyEvent.VK_LEFT:
        location = getLocation();
        setHorzMirror(true);
        backward = true;
        break;
      case KeyEvent.VK_RIGHT:
        location = getLocation();
        if (location.x > 600)
          return;
        setHorzMirror(false);
        forward = true;
        break;
      case KeyEvent.VK_ESCAPE:
        if (isGameOver)
        {
          cg.doReset();
          cg.doRun();
          cg.setTitle("CatGame - www.aplu.ch/jgamegrid");
        }
        break;
    }
  }

  // Assumed to be called after act()
  public int collide(Actor actor, Location location)
  {
    int a = 10;  // distance to jump back
    // First bumb
    if (location.equals(new Location(2, 3)))
      walk(-a, 170);
    if (location.equals(new Location(3, 3)))
      walk(a, 170);

    // Second bumb
    if (location.equals(new Location(10, 3)) ||
      location.equals(new Location(10, 2)))
      walk(-a, 110);
    if (location.equals(new Location(11, 3)) ||
      location.equals(new Location(11, 2)))
      walk(a, 110);

    // Third bumb lower part
    if (location.equals(new Location(20, 3)))
      walk(-a, 170);
    if (location.equals(new Location(23, 3)))
      walk(a, 170);
    
    // Third bumb upper part
    if (location.equals(new Location(21, 2)))
      walk(-a, 110);
    if (location.equals(new Location(22, 2)))
      walk(a, 110);

    return 0;
  }

  private void walk(int distance, int height)
  {
    if (getLocation().y > height)
      setX(getX() + distance);
    else
      onTop = true;
  }

  public int collide(Actor a1, Actor a2)
  {
    a1.hide();
    a2.show(2);
    cg.doPause();  // The current simulation cycle is run to the end
    cg.setTitle("Game over. Press ESC to restart.");
    isGameOver = true;
    return 0;
  }
}


