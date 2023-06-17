package src;

public class Friendly {
    private int lebenTurm;
    public Friendly() {
      lebenTurm  = Settings.getlebenTurm();

    }
    public int getlebenTurma(){
        return lebenTurm;
    }
    public void hurt(){
        lebenTurm = lebenTurm-1;
    }
    public boolean lebent(){
        if (lebenTurm<=0)
        {
            return true;
        }
        return false;
    }


}
