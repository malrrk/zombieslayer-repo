// Knife.java

import ch.aplu.jgamegrid.*;

public class Knife extends Actor
{
  private boolean inUse = false;

  public Knife()
  {
    super("sprites/knife.gif");
  }

   public boolean isInUse()
  {
    return inUse;
  }

  public void use(boolean b)
  {
    inUse = b;
    if (inUse)
      hide();
    else
      show();
    gameGrid.refresh();
  }
}
