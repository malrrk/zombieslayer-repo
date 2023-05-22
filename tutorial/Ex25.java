// Ex25.java
// GGProgressBar

import ch.aplu.jgamegrid.*;
import java.awt.*;

public class Ex25 extends GameGrid
{
  public Ex25()
  { 
    super(10, 10, 60, Color.red, false);
    getBg().clear(Color.white);
    show();
   
    int value = 0;
    int index = 0;

    GGTextField tf = 
      new GGTextField(this, "Loading actors now...", new Location(7, 9), true);
   tf.setLocationOffset(new Point(-50, -20));
   tf.show();
    
    GGProgressBar bar = new GGProgressBar(this, new Location(7, 9), 200, 20);
    bar.setUnit("%");
    while (value <= 100)
    {
      bar.setValue(value);
      value += 1;
      if (value % 10 == 0)
      {
        Actor a = new Actor("sprites/car" + index + ".gif");
        addActor(a, new Location(1, index));
        index++;
      }  
      delay(50);
    }
    tf.setText("All done");
  }  
 
  public static void main(String args[])
  {
    new Ex25();
  }
}
