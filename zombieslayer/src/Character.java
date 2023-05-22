import awt.event.KeyListner;
import ch.aplu.*;
public class Character {
    public boolean keyPressed(KeyEvent evt)
    {
        Location next = null;
        switch (evt.getKeyCode())
        {
            case KeyEvent.VK_W:

                System.out.print("up");

                break;
            case KeyEvent.VK_D:
                System.out.print("right");

                break;
            case KeyEvent.VK_A:
                System.out.print("left");
                break;
            case KeyEvent.VK_S:
                System.out.print("down");
               
                break;
        }
}
