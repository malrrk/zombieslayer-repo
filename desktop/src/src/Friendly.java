package src;

public class Friendly {
    private float lebenTurm;
    public Friendly() {
      lebenTurm  = Settings.getLebenTurm();

    }
    public float getlebenTurma(){
        return lebenTurm;
    }
    public void hurt(){
        lebenTurm -= 0.1;
    }
    public boolean lebent(){
        if (lebenTurm<=0)
        {
            return true;
        }
        return false;
    }
    public void setLebenTurm(){
        lebenTurm= 100;
    }
    public void add(){
        if (lebenTurm> Settings.getLebenTurm())
        { lebenTurm= lebenTurm+1;}
    }


}
