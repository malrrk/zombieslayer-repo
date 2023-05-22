// Ball.java
// Used in Ex09.java

public class Ball extends CapturedActor
{

  public Ball()
  {
    super(false, "sprites/ball.gif", 2);
  }
  
  public void act()
  {
    move(10);
  }
}
