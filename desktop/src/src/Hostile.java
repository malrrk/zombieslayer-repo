package src;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;

public class Hostile {
    float x;
    float y ;
    int leben;


    public Hostile(int x,int y ){
        this.y = y;
        this.x = x;

    }

    public float getX(){
        return x;
    }
    public float getY() {
        return y;
    }

    public void hurt(){
        leben = leben -1;
        System.out.print(leben);
    }


    public int getLeben()
    {return leben;}
}
