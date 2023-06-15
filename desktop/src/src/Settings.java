package src;

public class Settings {
    private static int speed = 100;
    private static int lebenTurm = 100;
    private static int leben= 100;

//
public void setSpeed(int speed) {
    this.speed = speed;
}
    public static int getSpeed()
    {
        return speed;
    }
    public static int getlebenTurm()
    {
        return lebenTurm;
    }
    public static int getLeben()
    {
        return leben;
    }
}