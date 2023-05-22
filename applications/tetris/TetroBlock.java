// TetroBlock.java

import ch.aplu.jgamegrid.*;

public class TetroBlock extends Actor
{
  private Location[] relLoc = new Location[4];

  public TetroBlock(int blockId, Location[] relLoc)
  {
    super("sprites/tetroblock" + blockId + ".gif");
    this.relLoc = relLoc.clone();
  }

  public Location getRelLoc(int rotId)
  {
    return relLoc[rotId];
  }
}
