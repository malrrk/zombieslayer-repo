// SimEx7a.java
// One light sensor, polling

import ch.aplu.robotsim.*;
import ch.aplu.jgamegrid.*;
import java.awt.*;

public class SimEx7a 
{
  private Gear gear = new Gear();

  public SimEx7a()
  {
    LegoRobot robot = new LegoRobot();
    LightSensor ls = new LightSensor(SensorPort.S3);
    robot.addPart(gear);
    robot.addPart(ls);
    gear.leftArc(0.8);
    while (!robot.isEscapeHit())
    {
      int v = ls.getValue();
      System.out.println("v = " + v);
      Tools.delay(500);
    }  
  }


  public static void main(String[] args)
  {
    new SimEx7a();
  }

 // ------------------ Environment --------------------------
  private static void _init(GameGrid gg)
  {
    GGBackground bg = gg.getBg();
    bg.setPaintColor(Color.gray);
    bg.fillRectangle(new Point(50, 350), new Point(450, 300));

    int g = 100;
    bg.setPaintColor(new Color(g, g, g));
    bg.fillRectangle(new Point(50, 300), new Point(450, 250));
    
    g = 60;
    bg.setPaintColor(new Color(g, g, g));
    bg.fillRectangle(new Point(50, 250), new Point(450, 200));
    
    g = 30;
    bg.setPaintColor(new Color(g, g, g));
    bg.fillRectangle(new Point(50, 200), new Point(450, 150));

    bg.setPaintColor(Color.black);
    bg.fillRectangle(new Point(50, 150), new Point(450, 100));
  }

  static
  {
    RobotContext.setStartPosition(250, 370);
    RobotContext.setStartDirection(0);
  }
}
