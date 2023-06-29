package com.mygdx.game;

public class Friendly {

    protected float health;

    public Friendly(){

    }

    public float getHealth(){return health;}

    public void setHealth(float health) {this.health = health;}

    public void hurt(float damage){health -= damage;}

    public boolean alive(){
        return health >= 0;
    }

}
