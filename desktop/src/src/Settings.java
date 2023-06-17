package src;

public class Settings {
    private static int speed = 100;
    private static int lebenTurm = 100;
    private static int leben = 100;
    private static int Anzahlz1 = 50;
    private static int zspeed = 25;

    //
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public static int getSpeed() {
        return speed;
    }

    public static int getlebenTurm() {
        return lebenTurm;
    }

    public static int getLeben() {
        return leben;
    }

    public static int getZombieZahl1() {
        return Anzahlz1;
    }

    public static int getZspeed() {
        return zspeed;
    }
}