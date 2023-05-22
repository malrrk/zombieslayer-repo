// Fork.java

import ch.aplu.jgamegrid.*;

public class Fork extends Actor
{
  private boolean inUse = false;

  public Fork()
  {
    super("sprites/fork.gif");
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
