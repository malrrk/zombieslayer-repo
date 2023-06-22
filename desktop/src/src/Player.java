package src;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;


public class Player  {
    private float leben;
    private int kills;
    private int status;
    public float x;
    public float y;
    int picNr = 3;

    Sprites batch;

    public Player(Sprites batch, float x, float y){
        this.x = x;
        this.y = y;
        this.batch = batch;
    leben = Settings.getLeben();
    kills = 0;
    status = 2;
}
public boolean playerAlive() {
    if (leben >= 0) {

        return true;
    }
    leben = 25;
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
public int getSpriteNr(){
    if (Gdx.input.isKeyPressed(Input.Keys.W)){
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {picNr = 7;}
    if (Gdx.input.isKeyPressed(Input.Keys.D)) {picNr = 1;}
    else {picNr = 0;}}

    if (Gdx.input.isKeyPressed(Input.Keys.A)){
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {picNr = 7;}
    if (Gdx.input.isKeyPressed(Input.Keys.S)) {picNr = 5;}
    else {picNr = 6;}}

    if (Gdx.input.isKeyPressed(Input.Keys.S)){
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {picNr = 5;}
    if (Gdx.input.isKeyPressed(Input.Keys.D)) {picNr = 3;}
    else {picNr = 4;}}

    if (Gdx.input.isKeyPressed(Input.Keys.D)){
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {picNr = 1;}
    if (Gdx.input.isKeyPressed(Input.Keys.S)) {picNr = 3;}
    else {picNr = 2;}}
return picNr;
}
public void addlives(){
    if (leben>Settings.getLeben()){
    leben += 1;}
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
    public int getStatus(){return status;}


}