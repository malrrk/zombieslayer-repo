package src;

public class Tower  extends Friendly{

    public Tower() {
        super();
        this.health  = Settings.getLebenTurm();

    }

    public void add(){
        if (health > Settings.getLebenTurm())
        { health += 1;}
    }


}
