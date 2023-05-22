// FifteenPuzzle.java
// Sliding block puzzle, show only solvable initial conditions

import ch.aplu.jgamegrid.*;

public class FifteenPuzzle extends GameGrid
{
  public FifteenPuzzle()
  {
    super(4, 4, 60, RED, false);
    setTitle("FifteenPuzzle V2.0");
    setSimulationPeriod(100);
    NumberBlock[] stones = new NumberBlock[15];
    for (int i = 0; i < 15; i++)
    {
      stones[i] = new NumberBlock(i);
      addMouseListener(stones[i], GGMouse.lPress | GGMouse.lDrag | GGMouse.lRelease);
      addActor(stones[i], getRandomEmptyLocation());
    }
    
    while (getParity() % 2 == 0)
    {
      // Game is not solvable, must do some random shuffling
      NumberBlock randomStone = stones[(int)(Math.random() * 15)];
      randomStone.setLocation(getRandomEmptyLocation());
    }
    
    // Game solvable now
    show();
    doRun();
  }
  
  /*
   * To check if an arrangement of NumberStones is solvable, its parity has
   * to be computed. See http://de.wikipedia.org/wiki/15-Puzzle.
   * In two lines:
   * - If the parity is odd, the arrangement can be rearranged to 1-2-3-4 ... 14-15-[ ].
   * - If the parity is even, this can not be done.
   * @return the parity, that has to be checked for oddity.
   */
  private int getParity()
  {
    int n1 = 0;
    for (int y = 0; y < 4; y++)
    {
      for (int x = 0; x < 4; x++)
      {
        NumberBlock block = (NumberBlock)getOneActorAt(new Location(x, y));
        if (block == null) // skip the gap
          continue;
        NumberBlock next = getNextBlock(block);
        while (next != null)
        {
          if (next.getNum() < block.getNum())  // not yet in order
            n1++;
          next = getNextBlock(next);
        }
      }
    }
    int n2 = getEmptyLocations().get(0).getY();
    return n1 + n2;
  }
  
  private NumberBlock getNextBlock(NumberBlock block)
  // Returns the next block in the left to right, top to bottom order
  // null if at end
  {
    NumberBlock nextStone = null;
    int nextX = block.getX();
    int nextY = block.getY();
    while (nextStone == null)
    {
      nextX++;
      if (nextX > 3)
      {
        nextX = 0;
        nextY += 1;
      }
      if (nextY > 3)
        return null;  // Last position
      nextStone = (NumberBlock)getOneActorAt(new Location(nextX, nextY));
    }
    return nextStone;
  }


  public static void main(String[] args)
  {
    new FifteenPuzzle();
  }
}
