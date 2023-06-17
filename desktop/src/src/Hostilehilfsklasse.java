package src;

public class Hostilehilfsklasse {
    Hostile[] sammlung;
    public int zahler;

    public Hostilehilfsklasse() {
        sammlung = new Hostile[Settings.getZombieZahl1()];
        zahler= 0;


    }

    public float mx(int i) {


                return sammlung[i].getx();
            }

    public float my(int i){

            return sammlung[i].gety();

    }
    public void z(){
            if (zahler==49)
            {}
            else{sammlung[zahler] = new Hostile((float) Math.random() * 100, (float) Math.random() * 100);

                zahler++;}


        }


}

