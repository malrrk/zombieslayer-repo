package src;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;


public class Player  {
    private float health;
    private int kills;
    private int status;
    public float x;
    public float y;

    Sprites batch;

public Player(Sprites batch, float x, float y){
    this.x = x;
    this.y = y;
    this.batch = batch;

    health = Settings.getLeben();
    kills = 0;
    status = 2;


}
public boolean lebet() {
    if (health >= 0) {

        return true;
    }
    health = 100;
    return false;
}

public void move(){
    // right, left movement
    if(Gdx.input.isKeyPressed(Input.Keys.A)) {
        x -= Settings.getSpeed() * Gdx.graphics.getDeltaTime();}
    if (Gdx.input.isKeyPressed(Input.Keys.D)) {
        x += Settings.getSpeed() * Gdx.graphics.getDeltaTime();}

    // up, down movement
    if(Gdx.input.isKeyPressed(Input.Keys.W)) {
        y += Settings.getSpeed() * Gdx.graphics.getDeltaTime();
    }
    if(Gdx.input.isKeyPressed(Input.Keys.S)) {
        y -= Settings.getSpeed() * Gdx.graphics.getDeltaTime();
    }
}

public void draw(){
    batch.drawCharacter(status, picNr(), (int) x, (int) y);
}

public int picNr(){
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
return 0;
}

public void setLeben(int i){
   if((health + i) < 100){ health = health + i;}
   else{health = 100;}
}
public float getLeben() {
    return health;
}
public void hurt(){
    health -= (0.1 * Settings.getHurtf());
}
public void addKill(){
    kills++;
}
public int getKills(){
    return kills;
}
public void setStatus(int s){ status = s;}
    public int getStatus(){return status;}


}