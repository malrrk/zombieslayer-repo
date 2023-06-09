package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;


public class Player {
    private float leben;
    private int kills;
    private int status;
    public final Rectangle hitbox;
    public float x;
    public float y;

    private boolean hurt = false;

    private float speedDiagonalFac = 0;

    int picNr = 3;

    Sprites batch;

    public Player(Sprites batch, float x, float y) {
        this.x = x;
        this.y = y;
        this.batch = batch;
        leben = Settings.getLeben();
        kills = 0;
        status = 2;

        hitbox = new Rectangle(this.x, this.y, 12, 18);
    }

    public void setHurt(boolean hurt){this.hurt = hurt;}

    public boolean isHurt(){
        return hurt;
    }

    public boolean playerAlive() {
        if (leben > 0) {

            return true;
        }
        leben = Settings.getLeben();
        return false;
    }

    public void move() {
        // right, left movement

            if (Gdx.input.isKeyPressed(Input.Keys.A)&& x > 187 ) {
                x -= Settings.getSpeed() * Gdx.graphics.getDeltaTime();
                speedDiagonalFac = -Settings.getSpeedDiagonal();
            }

            if (Gdx.input.isKeyPressed(Input.Keys.D)&&x < 3900) {
                x += Settings.getSpeed() * Gdx.graphics.getDeltaTime();
                speedDiagonalFac = Settings.getSpeedDiagonal();
            }

            //System.out.println(speedDiagonalFac);

            // up, down movement
            if (Gdx.input.isKeyPressed(Input.Keys.W)&& y < 3950) {
                y += Settings.getSpeed() * Gdx.graphics.getDeltaTime() * Math.abs(speedDiagonalFac);
                x -= (Settings.getSpeed() - (Settings.getSpeed() * Math.abs(speedDiagonalFac))) * Gdx.graphics.getDeltaTime() * (Math.abs(speedDiagonalFac) / speedDiagonalFac);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S)&&y>90) {
                y -= Settings.getSpeed() * Gdx.graphics.getDeltaTime() * Math.abs(speedDiagonalFac);
                x -= (Settings.getSpeed() - (Settings.getSpeed() * Math.abs(speedDiagonalFac))) * Gdx.graphics.getDeltaTime() * (Math.abs(speedDiagonalFac) / speedDiagonalFac);
            }

        speedDiagonalFac = 1;

        hitbox.setPosition(x, y);
    }

public int getSpriteNr(){
    if (Gdx.input.isKeyPressed(Input.Keys.W)){
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {return 7;}
    if (Gdx.input.isKeyPressed(Input.Keys.D)) {return 1;}
    else {return 0;}}

    if (Gdx.input.isKeyPressed(Input.Keys.A)){
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {return 7;}
    if (Gdx.input.isKeyPressed(Input.Keys.S)) {return 5;}
    else {return 6;}}

    if (Gdx.input.isKeyPressed(Input.Keys.S)){
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {return 5;}
    if (Gdx.input.isKeyPressed(Input.Keys.D)) {return 3;}
    else {return 4;}}

    if (Gdx.input.isKeyPressed(Input.Keys.D)){
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {return 1;}
    if (Gdx.input.isKeyPressed(Input.Keys.S)) {return 3;}
    else {return 2;}}
return 4;
}
public void addlives(){
    if (leben>Settings.getLeben()){
    leben += 1;}
}

    public int checkHit(int status){
        if(Gdx.input.isKeyPressed(Input.Keys.K)) {
            if (status == 2) {
                return 10;
            } else if (status == 3) {
                return 10;
            } else if (status == 7) {
                return 11;
            } else if (status == 8) {
                return 12;
            } else if (status == 9) {
                return 13;
            }
        }
        return status;
    }


public void setLeben(int i){
   if((leben + i) < 25){ leben = leben + i;}
   else{leben = 25;}
}
public float getLeben() {
    return leben;
}
public void hurt(){
    leben -= (0.1 * Settings.getHurtf());

}
public void addKill(){
    kills++;
}
public int getKills(){
    return kills;
}
public void setStatus(int s){ status = s;}
    public int getStatus(){return checkHit(status);}


}