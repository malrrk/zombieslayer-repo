// DiningCouple.java

import ch.aplu.jgamegrid.*;

public class DiningCouple extends GameGrid
{
  private final int delayTimeMan = 1000;
  private final int delayTimeWoman = 3000;

  public DiningCouple()
  {
    super(500, 300, 1, null, false);
    addActor(new Actor("sprites/table.gif"), new Location(250, 200));

    Fork fork = new Fork();
    addActor(fork, new Location(230, 200));
    Knife knife = new Knife();
    addActor(knife, new Location(270, 200));

    Person man = new Person("man", fork, knife, delayTimeMan);
    Person woman = new Person("woman", fork, knife, delayTimeWoman);
    addActor(man, new Location(170, 80));
    addActor(woman, new Location(330, 80));
    show();
    delay(2000);

    man.eat();
    woman.eat();

    while (true)
    {
     Thread.State manState = man.getThread().getState();
      Thread.State womanState = woman.getThread().getState();
      setTitle("Thread States: Man: " + manState + ", Woman: " + womanState);
      delay(200);
    }
  }

  public static void main(String[] args)
  {
    new DiningCouple();
  }
}
