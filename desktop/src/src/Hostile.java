package src;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;

public class Hostile {
    float xz;
    float yz ;
    int leben;
    Vector vector;

    Friendly turm;


    public Hostile(float x,float y ){
             yz = y;
             xz = x;
            leben = Settings.getZleben();
            vector = new Vector2(x,y);

    }

    public float getx(){
       if (xz<2048)
       { xz = xz+Settings.getZspeed() * Gdx.graphics.getDeltaTime();}
       else if( xz==2048)
       {}
       else{xz = xz-Settings.getZspeed() * Gdx.graphics.getDeltaTime();}
        return xz;
    }
    public float gety() {
        if ((int) yz < 2060) {
            yz = yz + Settings.getZspeed() * Gdx.graphics.getDeltaTime();

        } else if ((int) yz == 2060) {} else {
                yz = yz - Settings.getZspeed() * Gdx.graphics.getDeltaTime();
            }
            return yz;
        }



    public void hurt(){
        leben = leben -1;
        System.out.print(leben);
    }
    public int getLeben()
    {return leben;}


}
