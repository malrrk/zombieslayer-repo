package src;

import java.sql.Array;
import java.util.ArrayList;

public class Hostilehilfsklasse {
    ArrayList<Hostile> sammlung;
    public int zahler;
    public int zahler2;

    public Hostilehilfsklasse() {
        sammlung = new ArrayList<>();
        zahler = 49;

    }
    public float mx(int i) {


        return sammlung.get(i).getx();
    }

    public float my(int i) {

        return sammlung.get(i).gety();

    }

    public void spawnZombies() {
        if (zahler2 <= 49) {

            sammlung.add(new Hostile((float) Math.random() * 2*2048, (float) Math.random() * 2* 2048));

            zahler2++;
            zahler++;}
        else{}
        }



    public boolean hurt(int i) {
        sammlung.get(i).hurt();
        if (sammlung.get(i).getLeben() == 0) {
            return false;
        }
        return true;
    }

    public boolean zombieAlive(int i) {
        if (sammlung.get(i).getLeben() == 0) {
            return false;
        }
        {
            return true;
        }

    }
    public void remove(int i){
        sammlung.remove(i);

zahler2--;    }

}



