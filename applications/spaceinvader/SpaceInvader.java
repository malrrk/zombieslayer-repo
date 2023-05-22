// SpaceInvader.java
// Sprite images from http://www.cokeandcode.com/tutorials
// Nice example how the different actor classes: SpaceShip, Bomb, SpaceInvader, Explosion
// act almost independently of each other. This decoupling simplifies the logic of the application

import ch.aplu.jgamegrid.*;
import java.awt.event.KeyEvent;
import java.awt.*;

public class SpaceInvader extends GameGrid implements GGKeyListener
{
  private final int nbRows = 3;
  private final int nbCols = 11;

  public SpaceInvader()
  {
    super(200, 100, 5, false);
    setSimulationPeriod(50);
    for (int i = 0; i < nbRows; i++)
    {
      for (int k = 0; k < nbCols; k++)
      {
        Alien alien = new Alien();
        addActor(alien, new Location(100 - 5 * nbCols + 10 * k, 10 + 10 * i));
      }
    }
    SpaceShip ss = new SpaceShip();
    addActor(ss, new Location(100, 90));
    addKeyRepeatListener(ss);
    setKeyRepeatPeriod(100);
    addKeyListener(this);
    getBg().setFont(new Font("SansSerif", Font.PLAIN, 12));
    getBg().drawText("Use <- -> to move, spacebar to shoot", new Point(400, 330));
    getBg().drawText("Press any key to start...", new Point(400, 350));
    show();
  }

  public boolean keyPressed(KeyEvent evt)
  {
    if (!isRunning())
    {
      setBgColor(java.awt.Color.black);  // Erase text
      doRun();
    }
    return false;  // Do not consume key
  }

  public boolean keyReleased(KeyEvent evt)
  {
    return false;
  }

  public static void main(String[] args)
  {
    new SpaceInvader();
  }

}
