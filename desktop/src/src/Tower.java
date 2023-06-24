package src;

import com.badlogic.gdx.math.Rectangle;

public class Tower  extends Friendly{

    public final Rectangle hitbox;

    public Tower() {
        super();
        this.health  = Settings.getLebenTurm();

        hitbox = new Rectangle(Settings.getx0y0(),Settings.getx0y0(),41,50);

    }

    public void add(){
        if (health > Settings.getLebenTurm())
        { health += 1;}
    }


}
