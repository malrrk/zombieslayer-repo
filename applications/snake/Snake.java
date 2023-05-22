// Snake.java

import ch.aplu.jgamegrid.*;
import java.util.*;
import java.awt.event.KeyEvent;

class Snake extends Actor
{
  private ArrayList<Tail> tailList = new ArrayList<Tail>();
  private int tailSize = 0;
  private long startTime;

  public Snake()
  {
    super(true, "sprites/snakeHead.gif");
    startTime = new Date().getTime();
  }

  public void act()
  {
    if (gameGrid.kbhit())
    {
      switch (gameGrid.getKeyCode())
      {
        case KeyEvent.VK_UP:
          setDirection(Location.NORTH);
          break;
        case KeyEvent.VK_LEFT:
          setDirection(Location.WEST);
          break;
        case KeyEvent.VK_RIGHT:
          setDirection(Location.EAST);
          break;
        case KeyEvent.VK_DOWN:
          setDirection(Location.SOUTH);
          break;
        default:
          return;
      }
    }

    int lastIndex = tailList.size() - 1;
    Location lastLocation = getLocation();
    if (lastIndex > -1)
    {
      lastLocation = tailList.get(lastIndex).getLocation();
      for (int i = lastIndex; i > 0; i--)
        tailList.get(i).setLocation(tailList.get(i - 1).getLocation());
      tailList.get(0).setLocation(getLocation());
    }
    move();
    if (!isInGrid())
    {
      gameOver("Out of Playground. " + getState());
      return;
    }

    Actor a = gameGrid.getOneActorAt(getLocation(), Tail.class);
    if (a != null)
    {
      gameOver("Head on Tail. " + getState());
      return;
    }

    tryToEat(lastLocation);
  }

  private void gameOver(String text)
  {
    gameGrid.setTitle(text);
    gameGrid.removeAllActors();
    gameGrid.addActor(new Actor("sprites/gameover.png"), new Location(10, 8));
    gameGrid.doPause();
    delay(5000);
    gameGrid.doReset();
    gameGrid.doRun();
  }

  private void tryToEat(Location lastLocation)
  {
    Actor actor = gameGrid.getOneActorAt(getLocation(), Food.class);
    if (actor != null)
    {
      Tail newTail = new Tail();
      gameGrid.addActor(newTail, lastLocation);
      tailList.add(newTail);
      tailSize++;
      gameGrid.setTitle(getState());
      actor.removeSelf();
      gameGrid.addActor(new Food(), gameGrid.getRandomEmptyLocation());
    }
  }

  private String getState()
  {
    long currentTime = (new Date().getTime() - startTime) / 1000;
    return tailSize + " tail segment(s) at " + currentTime + " s";
  }
}
