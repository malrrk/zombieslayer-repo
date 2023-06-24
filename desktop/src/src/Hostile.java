package src;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;

public class Hostile {
    protected float x;
    protected float y ;
    protected int health;


    public Hostile(float x, float y ){
        this.y = y;
        this.x = x;

    }


    public void hurt(){
        health -= 1;
        //System.out.print(health);
    }


    public int getLeben()
    {return health;}
}
