// Body.java
// Used in Ex12.java

public class Body extends CapturedActor
{

  public Body()
  {
    super(false, "sprites/ball.gif", 2);
  }
  
  public void act()
  {
    super.move(1);
  }
}
