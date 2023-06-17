package src;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;

public class Hostile {
    float xz;
    float yz ;
    int leben;
    Vector vector;
    int zahlert;
    Friendly turm;


    public Hostile(float x,float y ){
             yz = y;
             xz = x;
            leben = 10;
            vector = new Vector2(x,y);

    }

    public float getx(){
       if (xz<0)
       { xz = xz+Settings.getZspeed() * Gdx.graphics.getDeltaTime();}
       else if( xz==0)
       {}
       else{xz = xz-Settings.getZspeed() * Gdx.graphics.getDeltaTime();}
        return xz;
    }
    public float gety() {
        if ((int) yz < 0) {
            yz = yz + Settings.getZspeed() * Gdx.graphics.getDeltaTime();
            System.out.print(yz);
        } else if ((int) yz == 0) {} else {
                yz = yz - Settings.getZspeed() * Gdx.graphics.getDeltaTime();
            }
            return yz;
        }



    public void hurt(){
        leben = leben -1;
    }


}
