package com.mygdx.game;

import com.badlogic.gdx.Gdx;

public class RedZombie extends Hostile{

    public RedZombie(){
        //super(Settings.getx0y0() + 100, Settings.getx0y0() + 200, Settings.getLeben());
        super((int) (Math.random() * 2 * 2048), (int) (Math.random() * 2 * 2048), 0);
        x_vector = calculateVector_x(x, y, Settings.getx0y0(), Settings.getx0y0());
        y_vector = calculateVector_y(x, y, Settings.getx0y0(), Settings.getx0y0());

        spriteNr = calculateSpriteNr(x_vector, y_vector);
    }



    /* doesnt work yet
    private float remDistance = 0; //remaining distance to move until another vector is calculated

    private final float randomFac = 2;
    public void move(){
        if(remDistance > 0) {
            x += x_vector * Gdx.graphics.getDeltaTime();
            y += y_vector * Gdx.graphics.getDeltaTime();
            remDistance -= Settings.getSpeed() * Gdx.graphics.getDeltaTime();
        }else{
            calculateVector();
        }
    }
    public void calculateVector(){
        float distanceAdd = (float)(lengthVector(Settings.getx0y0() - x, Settings.getx0y0() - y) * Math.random()/randomFac);
        x_vector = (Settings.getx0y0() + distanceAdd) - x;
        y_vector = (Settings.getx0y0() + distanceAdd) - y;

        float vectorAverage = (Math.abs(x_vector) + Math.abs(y_vector))/2;

        if(x_vector/vectorAverage > 0.5){
            x_vector = Settings.getSpeed() * (Math.abs(x_vector)/x_vector);
        }else{
            x_vector = 0;
        }

        if(y_vector/vectorAverage > 0.5){
            y_vector = Settings.getSpeed() * (Math.abs(y_vector)/y_vector);
        }else{
            y_vector = 0;
        }

        remDistance = (float) (lengthVector(x_vector, y_vector) * Math.random()/randomFac);

    }*/
}
