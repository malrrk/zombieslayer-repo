package src;

import com.badlogic.gdx.Gdx;

public class BasicZombie extends Hostile{

    int leben;

    public BasicZombie(int x, int y) {
        super(x, y);

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
        System.out.print(leben);
    }
    public int getLeben()
    {return leben;}
}
