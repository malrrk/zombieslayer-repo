// Person.java

import ch.aplu.jgamegrid.*;

public class Person extends Actor implements Runnable
{
  private String gender;
  private Fork fork;
  private Knife knife;
  private int delayTime;
  private boolean haveFork = false;
  private boolean haveKnife = false;
  private Thread t;

  public Person(String gender, Fork fork, Knife knife, int delayTime)
  {
    super(gender.equals("man") ? "sprites/man.gif" : "sprites/woman.gif", 4);
    this.gender = gender;
    this.fork = fork;
    this.knife = knife;
    this.delayTime = delayTime;
  }

  public Thread getThread()
  {
    return t;
  }

  public void eat()
  {
    t = new Thread(this);
    t.start();
  }

  private boolean requestFork()
  {
    if (fork.isInUse())
      return false;
    fork.use(true);
    return true;
  }

  private boolean releaseFork()
  {
    if (!fork.isInUse())
      return false;
    fork.use(false);
    return true;
  }

  private boolean requestKnife()
  {
    if (knife.isInUse())
      return false;
    knife.use(true);
    return true;
  }

  private boolean releaseKnife()
  {
    if (!knife.isInUse())
      return false;
    knife.use(false);
    return true;
  }

  public void run()
  {
    while (true)
    {
      synchronized (gameGrid)
      {
        if (!haveFork)
        {
          haveFork = requestFork();
          if (haveFork && !haveKnife)
          {
            show(1);
            gameGrid.refresh();
          }
        }
        delay(delayTime);  // Time to wait before requesting knife
        if (!haveKnife)
        {
          haveKnife = requestKnife();
          if (haveKnife && !haveFork)
          {
            show(2);
            gameGrid.refresh();
          }
        }

        if (haveFork && haveKnife)  // We have fork and knife, so we can eat
        {
          show(3);
          gameGrid.refresh();
          int delay = (int)(delayTime * (Math.random() + 1));
          delay(delay);  // Eating time somewhat random

          releaseFork();
          haveFork = false;
          show(2);
          gameGrid.refresh();
          delay(delayTime);  // Time to wait to give back knife

          releaseKnife();
          haveKnife = false;
          show(0);
          gameGrid.refresh();
        }
      }
      delay(1000);  // Time to wait until next request
    }
  }
} 
