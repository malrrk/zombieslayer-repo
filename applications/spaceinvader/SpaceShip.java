// SpaceShip.java
// Used for SpaceInvader

import ch.aplu.jgamegrid.*;
import java.awt.event.KeyEvent;
import java.awt.*;

public class SpaceShip extends Actor implements GGKeyRepeatListener
{
  private int nbShots = 0;
  private int isGameOver = 0;

  public SpaceShip()
  {
    super("sprites/spaceship.gif");
  }

  public void act()
  {
    if (isGameOver > 0)
      return;
    GameGrid gg = gameGrid;
    Location location = getLocation();
    if (gg.getNumberOfActorsAt(location, Alien.class) > 0)
    {
      gg.removeAllActors();
      gg.addActor(new Actor("sprites/explosion2.gif"), location);
      isGameOver = 1;
      return;
    }
    if (gg.getNumberOfActors(Alien.class) == 0)
    {
      gg.getBg().drawText("Number of shots: " + nbShots, new Point(10, 30));
      gg.getBg().drawText("Game constructed with JGameGrid (www.aplu.ch)", new Point(10, 50));
      gg.addActor(new Actor("sprites/you_win.gif"), new Location(100, 60));
      isGameOver = 2;
      return;
    }
  }

  public void keyRepeated(int keyCode)
  {
    if (isGameOver == 1)
      return;

    Location next = null;
    switch (keyCode)
    {
      case KeyEvent.VK_LEFT:
        next = getLocation().getAdjacentLocation(Location.WEST);
        moveTo(next);
        break;

      case KeyEvent.VK_RIGHT:
        next = getLocation().getAdjacentLocation(Location.EAST);
        moveTo(next);
        break;

      case KeyEvent.VK_SPACE:
        Bomb bomb = new Bomb();
        gameGrid.addActor(bomb, getLocation());
        nbShots++;
        break;
    }
  }

  private void moveTo(Location location)
  {
    if (location.x > 10 && location.x < 190)
      setLocation(location);
  }
}
