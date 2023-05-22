// MemoryCard.java

import ch.aplu.jgamegrid.*;

class MemoryCard extends Actor
{
  private int id;

  public MemoryCard(int id)
  {
    super("sprites/card" + id + ".gif", "sprites/cardback.gif");
//    super("sprites/cardback.gif", "sprites/card" + id + ".gif"); // For tests
    this.id = id;
  }

  public int getId()
  {
    return id;
  }
}
