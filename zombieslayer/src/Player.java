import ch.aplu.jgamegrid.*;
import java.awt.event.KeyEvent;
import java.awt.Color;
public class Player  extends Actor implements GGKeyListener  {


    public player ()
    {
        super(true,"" );
    }
    public boolean keyPressed(KeyEvent evt)
    {
        Location next = null;
        switch (evt.getKeyCode())
        {
            case KeyEvent.VK_W:

                System.out.print("up");
                next = getLocation().getNeighbourLocation(Location.EAST);
                setDirection(Location.NORTH);
                break;
            case KeyEvent.VK_D:
                System.out.print("right");
                next = getLocation().getNeighbourLocation(Location.EAST);
                setDirection(Location.EAST);
                break;
            case KeyEvent.VK_A:
                System.out.print("left");
                next = getLocation().getNeighbourLocation(Location.EAST);
                setDirection(Location.WEST);
                break;
            case KeyEvent.VK_S:
                System.out.print("down");
                next = getLocation().getNeighbourLocation(Location.EAST);
                setDirection(Location.SOUTH);
                break;
        }
        if (next != null && canMove(next))
        {
            setLocation(next);
            return true;
        }
        move();
        return  true;
    }


    public boolean keyReleased(KeyEvent evt)
    {
        return true;
    }
    private boolean canMove(Location location)
    {
        //Color c = gameGrid.getBg().getColor(location);
        //if (c.equals(Color.black))
        //    return false;
        //else
        return true;
}

}