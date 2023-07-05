package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;

public class Hostile {
    protected float x;
    protected float y ;

    protected float x_vector;
    protected float y_vector;
    protected int health;
    protected int spriteNr = 0;


    public Hostile(float x, float y, int health){
        this.y = y;
        this.x = x;
        this.health = health;

    }
    public void move(){
        x += x_vector * Gdx.graphics.getDeltaTime();
        y += y_vector * Gdx.graphics.getDeltaTime();
    }


    public void hurt(){health -= 1;}

    public int getSpriteNr(){
        return spriteNr;
    }
    public int calculateSpriteNr(float x_vector, float y_vector){

        float vectorAverage = (Math.abs(x_vector) + Math.abs(y_vector))/2;

        if(y_vector/vectorAverage > 0.75){
            if(x_vector/vectorAverage > 0.75){
                return 1; // rechts oben
            }else if(x_vector/vectorAverage < -0.75){
                return 7; // links oben
            }else{
                return 0; //oben
            }
        }else if(y_vector/vectorAverage < -0.75){
            if(x_vector/vectorAverage > 0.75){
                return 3; // rechts unten
            }else if(x_vector/vectorAverage < -0.75){
                return 5; // links unten
            }else{
                return 4; //unten
            }
        }else{
            if(x_vector/vectorAverage > 0.75){
                return 2; //rechts
            }else{
                return 6; //links
            }
        }
    }

    public float calculateVector_y(float x_start, float y_start, float x_end, float y_end){
        float vectorFac = (float) (Settings.getSpeed()/Math.sqrt(Math.pow(x_end - x_start, 2) + Math.pow(y_end - y_start, 2)));

        return (y_end - y_start) * vectorFac;
    }

    public float calculateVector_x(float x_start, float y_start, float x_end, float y_end){
        float vectorFac = (float) (Settings.getSpeed()/Math.sqrt(Math.pow(x_end - x_start, 2) + Math.pow(y_end - y_start, 2)));

        return (x_end - x_start) * vectorFac;
    }

    public boolean alive(){
        return health >= 0;
    }


    public int getHealth() {return health;}

    public float lengthVector(float x_vector, float y_vector){
        return (float) Math.sqrt(Math.pow(x_vector, 2) + Math.pow(y_vector, 2));
    }
}
