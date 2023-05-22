// Creature.java
// Used for GameOfLife

import ch.aplu.jgamegrid.*;
import java.util.ArrayList;

public class Creature extends Actor
{
  protected boolean isAlive = false;

  public Creature()
  {
    super("sprites/creature.gif");
  }
}
