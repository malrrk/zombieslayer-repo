package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

import java.awt.*;

public class BasicZombie extends Hostile{

    //public final HitboxRect hitbox;

    public BasicZombie(float x, float y) {
        super((int)x, (int)y, Settings.getLeben());

        health = Settings.getLeben();

    }

    //public boolean checkCollision(HitboxRect hitbox){
    //    return this.hitbox.checkCollision(hitbox);
    //}

    public float getx(){
        if (x<2073)
        { x = x+Settings.getZspeed() * Gdx.graphics.getDeltaTime();}
        else if(x==2073)
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
        health -= 1;
    }
    public int getLeben()
    {return health;}
}
