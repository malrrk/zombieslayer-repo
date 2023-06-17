package src;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;

public class Hostile {
    float xz;
    float yz ;
    int leben;
    Vector vector;


    public Hostile(float x,float y ){
             yz = y;
             xz = x;
            leben = 100;
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
    public float gety(){
        if (yz<0)
        {yz= yz+Settings.getZspeed() * Gdx.graphics.getDeltaTime();}
        else if (yz==0)
        {}
        else{yz= yz-Settings.getZspeed() * Gdx.graphics.getDeltaTime();}
        return yz ;
    }



}
