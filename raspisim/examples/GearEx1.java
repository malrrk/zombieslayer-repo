import ch.aplu.raspisim.*;

public class GearEx1
{
  public GearEx1()
  {
    Robot robot = new Robot();
    Gear gear = new Gear();

    gear.forward(2000);
    gear.left(1000);
    gear.backward(2000);
    gear.leftArc(0.5, 3000);
    robot.exit();
  }

  public static void main(String[] args)
  {
    new GearEx1();
  }
}