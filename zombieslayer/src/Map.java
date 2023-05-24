import ch.aplu.jgamegrid.*;
import java.awt.event.KeyEvent;
import java.awt.Color;
public class map extends GameGrid {
    private grafik frog;
    public map() {
        super(255, 255, 255, Color.red, "", false);
        grafik frog = new grafik();
        addActor(frog,new Location(0,0));
        addKeyListener(frog);
        show();
        doRun();
    }
    public void mach(){


    }
    public  void turn4444(){
    }
}