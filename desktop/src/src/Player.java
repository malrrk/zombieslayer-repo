package src;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class Player  {

public Player(){

}
public float move1(){

    if(Gdx.input.isKeyPressed(Input.Keys.A)) {
        return -Settings.getSpeed() * Gdx.graphics.getDeltaTime();}
    if (Gdx.input.isKeyPressed(Input.Keys.D)) {

        return Settings.getSpeed() * Gdx.graphics.getDeltaTime();}

    return 0;
}
    public float move2(){

    if(Gdx.input.isKeyPressed(Input.Keys.W))
    { return + Settings.getSpeed()* Gdx.graphics.getDeltaTime();
    }
    if(Gdx.input.isKeyPressed(Input.Keys.S))
    { return - Settings.getSpeed()* Gdx.graphics.getDeltaTime();
    }
    return 0;
}
public int pic(){
    if (Gdx.input.isKeyPressed(Input.Keys.W)) {return 0;}
    if (Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.D)) {return 1;}
    if (Gdx.input.isKeyPressed(Input.Keys.D)) {return 2;}
    if (Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.D)) {return 3;}
    if (Gdx.input.isKeyPressed(Input.Keys.S)) {return 4;}
    if (Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.A)) {return 5;}
    if (Gdx.input.isKeyPressed(Input.Keys.A)) {return 6;}
    if (Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.A)) {return 7;}
return 0;
}

}