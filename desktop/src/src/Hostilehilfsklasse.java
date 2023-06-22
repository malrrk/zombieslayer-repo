package src;

import java.sql.Array;
import java.util.ArrayList;

public class Hostilehilfsklasse {
    ArrayList<BasicZombie> sammlung;
    public int counter;
    public int counter2;

    public Hostilehilfsklasse() {
        sammlung = new ArrayList<>();
        counter = 0;
        counter2 = 0;

    }
    public float mx(int i) {


        return sammlung.get(i).getx();
    }

    public float my(int i) {

        return sammlung.get(i).gety();

    }

    public void spawnZombies() {
        if (counter2 <= 49) {

            sammlung.add(new BasicZombie((int) Math.random() * 2*2048, (int) Math.random() * 2* 2048));

            counter2++;
            counter++;}
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

counter2--;    }

}



