// Button5.java
// Leds

import ch.aplu.raspisim.*;

public class Button5 implements ButtonListener
{
  private int n = 0;
  private boolean isRunning = true;
  private Led[] leds = new Led[4];

  public Button5()
  {
    RobotContext.setStatusText("Press/LongPress Pushbutton (Escape Key)");
    Robot robot = new Robot();
    robot.addButtonListener(this);
    for (int id = 0; id < 4; id++)
      leds[id] = new Led(id);
    leds[0].setColor("green");    
    while (isRunning)
      Tools.delay(100);
    Led.setColorAll("red");    
    robot.exit();
  }

  public void buttonEvent(int event)
  {
    if (event == SharedConstants.BUTTON_PRESSED)
    {
        n  = (n + 1) % 4;
        turnOn(n);
     }      
     else if (event == SharedConstants.BUTTON_LONGPRESSED)
        isRunning = false;
  }
  
  private void turnOn(int id)
  {  
    Led.clearAll();
    leds[id].setColor("white");
  }

  public static void main(String[] args)
  {
    new Button5();
  }
  
  // ------------------ Environment --------------------------
  static
  {
    RobotContext.showStatusBar(20);
  }
  
}
