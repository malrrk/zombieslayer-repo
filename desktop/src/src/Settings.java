package src;

import java.lang.Math;

public class Settings {
    private static int speed = 100;
    private static int lebenTurm = 100;
    private static int leben = 25;
    private static int Anzahlz1 = 50;
    private static int zspeed = 50;
    private static int zleben = 25;
    private static double hurtf = 3;
    private static int x0= 2048;
    private static int speedDiagonal;

    //
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public static int getSpeed() {
        return speed;
    }
    public static float getSpeedDiagonal() {
        return (float) (speed/(Math.sqrt(2 * Math.pow(speed, 2))));
    }

    public static int getLebenTurm() {
        return lebenTurm;
    }
    public static void setLebenTurm(int i){lebenTurm = i;}

    public static int getLeben() {
        return leben;
    }

    public static int getZombieZahl1() {
        return Anzahlz1;
    }

    public static int getZspeed() {
        return zspeed;
    }
    public static int getZleben(){return zleben;}
    public static void setZleben(int i){zleben = i;}
    public static void setHurtf(double i){hurtf = i;}
    public static double getHurtf(){ return hurtf;}
    public static int getx0y0(){return x0;}
}