package src;

public class Friendly {
    private int lebenTurm;
    public Friendly() {
      lebenTurm  = Settings.getlebenTurm();

    }
    public int getlebenTurma(){
        return lebenTurm;
    }
    public void damage(){
        lebenTurm = lebenTurm-1;
    }


}
