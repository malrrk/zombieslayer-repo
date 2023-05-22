// Bomb.java
// Used for SpaceInvader

import ch.aplu.jgamegrid.*;

public class Bomb extends Actor
{
  public Bomb()
  {
    super("sprites/bomb.gif");
  }

  public void reset()
  {
    setDirection(Location.NORTH);
  }

  public void act()
  {
    // Acts independently searching a possible target and bring it to explosion
    move();
    if (gameGrid.removeActorsAt(getLocation(), Alien.class) != 0)
    {
      Explosion explosion = new Explosion();
      gameGrid.addActor(explosion, getLocation());
      removeSelf();
      return;
    }
    if (getLocation().y < 5)
      removeSelf();
  }
}
