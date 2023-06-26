package src;

import java.sql.Array;
import java.util.ArrayList;

public class Hostilehilfsklasse {
    public ArrayList<BasicZombie> sammlung;
    public int counter;
    public int counter2;


    public Hostilehilfsklasse() {
        sammlung = new ArrayList<>();
        counter = 49;

    }
    public float mx(int i) {
        return sammlung.get(i).getx();
    }

    public float my(int i) {

        return sammlung.get(i).gety();

    }

    public BasicZombie getZombie_at_index(int i){
        return sammlung.get(i);
    }

    public void spawnZombies() {
        if (counter2 <= 49) {

            sammlung.add(new BasicZombie((float) Math.random() * 2*2048, (float) Math.random() * 2* 2048));

            counter2++;
            counter++;
        }
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

        counter2--;
    }


}



