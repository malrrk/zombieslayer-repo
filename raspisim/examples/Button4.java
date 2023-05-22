// Button4.java

import ch.aplu.raspisim.*;

public class Button4 implements ButtonListener
{
  private int n = 0;

  public Button4()
  {
    Robot robot = new Robot();
    robot.addButtonListener(this);
    while (n < 4)
      Tools.delay(100);
    System.out.println("All done");
    robot.exit();
  }

  public void buttonEvent(int event)
  {
    if (event == SharedConstants.BUTTON_PRESSED)
      System.out.println("pressed");
    else if (event == SharedConstants.BUTTON_RELEASED)
      System.out.println("released");
    else if (event == SharedConstants.BUTTON_LONGPRESSED)
    {
      System.out.println("long pressed");
      n += 1;
    }
    System.out.println("n:" + n);
  }

  public static void main(String[] args)
  {
    new Button4();
  }
}
