// GGSnake.java

import ch.aplu.jgamegrid.*;
import java.awt.Color;

public class GGSnake extends GameGrid
{
  private int startingSpeed = 400;
  private int gameNb = 1;

  public GGSnake()
  {
    super(20, 20, 20, null, false);
    setBgColor(new Color(100, 144, 100));
    reset();
    show();
    doRun();
  }

  public void reset()
  {
    removeAllActors();
    if (gameNb == 6)
      gameNb = 1;
    setSimulationPeriod(startingSpeed - 50 * gameNb);
    setTitle("Game #" + gameNb + ". Use cursor keys to move the snake");
    gameNb++;
    Snake snake = new Snake();
    addActor(snake, new Location(10, 10));
    addActor(new Food(), getRandomEmptyLocation());
    snake.setDirection(Location.NORTH);
  }

  public static void main(String[] args)
  {
    new GGSnake();
  }
}
