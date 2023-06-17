package src;

import java.util.ArrayList;

public class Hostile {
    float xz;
    float yz ;
    int leben;


    public Hostile(float x,float y ){
             yz = y;
             xz = x;
            leben = 100;

    }



    public float getx(){
        return xz;
    }
    public float gety(){
        return yz;
    }


}
