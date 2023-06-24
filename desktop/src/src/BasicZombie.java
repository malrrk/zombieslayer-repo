package src;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

import java.awt.*;

public class BasicZombie extends Hostile{

    int leben;


    public BasicZombie(float x, float y) {
        super((int)x, (int)y);

        leben = Settings.getLeben();

    }

    public float getx(){
        if (x<2048)
        { x = x+Settings.getZspeed() * Gdx.graphics.getDeltaTime();}
        else if(x==2048)
        {}
        else{x = x-Settings.getZspeed() * Gdx.graphics.getDeltaTime();}

        return x;
    }
    public float gety() {
        if ((int) y < 2060) {
            y = y + Settings.getZspeed() * Gdx.graphics.getDeltaTime();

        } else if ((int) y == 2060) {} else {
            y = y - Settings.getZspeed() * Gdx.graphics.getDeltaTime();
        }

        return y;
    }



    public void hurt(){
        leben = leben -1;
    }
    public int getLeben()
    {return leben;}
}
