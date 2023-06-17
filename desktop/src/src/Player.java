package src;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;


public class Player  {
    private float leben;

public Player(){
    leben = Settings.getLeben();


}
public float move1(){

    if(Gdx.input.isKeyPressed(Input.Keys.A)) {
        return -Settings.getSpeed() * Gdx.graphics.getDeltaTime();}
    if (Gdx.input.isKeyPressed(Input.Keys.D)) {

        return Settings.getSpeed() * Gdx.graphics.getDeltaTime();}
    //colision detaction
    //leben = leben - getDamage;

    return 0;
}
    public float move2(){

    if(Gdx.input.isKeyPressed(Input.Keys.W))
    { return + Settings.getSpeed() * Gdx.graphics.getDeltaTime();
    }
    if(Gdx.input.isKeyPressed(Input.Keys.S))
    { return - Settings.getSpeed() * Gdx.graphics.getDeltaTime();
    }
    return 0;
}
public int pic(){
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
public float getLeben() {
    return leben;
}
public void hurt(){
    leben -= 0.1;
}

}