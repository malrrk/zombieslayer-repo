package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

import java.awt.*;

public class BasicZombie extends Hostile{

    private float x_vector;
    private float y_vector;
    private float remDistance = 0; //remaining distance to move until another vector is calculated

    private final float randomFac = 2;

    private int spriteNr = 0;
    public boolean moving;


    public BasicZombie(float x, float y) {
        super((int) x, (int) y, Settings.getLeben());
        x_vector = calculateVector_x(x, y, Settings.getx0y0(), Settings.getx0y0());
        y_vector = calculateVector_y(x, y, Settings.getx0y0(), Settings.getx0y0());
        moving = true;
        float vectorAverage = (Math.abs(x_vector) + Math.abs(y_vector)) / 2;

        if (y_vector / vectorAverage > 0.75) {
            if (x_vector / vectorAverage > 0.75) {
                spriteNr = 1; // rechts oben
            } else if (x_vector / vectorAverage < -0.75) {
                spriteNr = 7; // links oben
            } else {
                spriteNr = 6; //oben
            }
        } else if (y_vector / vectorAverage < -0.75) {
            if (x_vector / vectorAverage > 0.75) {
                spriteNr = 3; // rechts unten
            } else if (x_vector / vectorAverage < -0.75) {
                spriteNr = 5; // links unten
            } else {
                spriteNr = 4; //unten
            }
        } else {
            if (x_vector / vectorAverage > 0.75) {
                spriteNr = 2; //rechts
            } else {
                spriteNr = 6; //links
            }
            health = Settings.getLeben();

        }
    }

    //public boolean checkCollision(HitboxRect hitbox){
    //    return this.hitbox.checkCollision(hitbox);
    //}

    public float getx(){
        if (moving)
        {

            return x += x_vector * Gdx.graphics.getDeltaTime()*0.5;}
        else {return x;}
    }
    public float gety() {
        if (moving)
        {return y+= y_vector * Gdx.graphics.getDeltaTime()*0.5;}
        else {return y;}
    }



    public void hurt(){
        health -= 1;
    }
    public int getLeben()
    {return health;}
    public int getSpriteNr(){
        return spriteNr;
    }

    public float calculateVector_y(float x_start, float y_start, float x_end, float y_end){
        float vectorFac = (float) (Settings.getSpeed()/Math.sqrt(Math.pow(x_end - x_start, 2) + Math.pow(y_end - y_start, 2)));

        return (y_end - y_start) * vectorFac;
    }


    public float lengthVector(float x_vector, float y_vector){
        return (float) Math.sqrt(Math.pow(x_vector, 2) + Math.pow(y_vector, 2));
    }
    public float calculateVector_x(float x_start, float y_start, float x_end, float y_end){
        float vectorFac = (float) (Settings.getSpeed()/Math.sqrt(Math.pow(x_end - x_start, 2) + Math.pow(y_end - y_start, 2)));

        return (x_end - x_start) * vectorFac;
    }
    public void setMovingtrue(){
        moving = true;
    }
    public void setMovingfalse(){
        moving = false;
    }
    public int getSpiritnNr(){
        return spriteNr;
    }
    }

