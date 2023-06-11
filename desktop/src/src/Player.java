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
    if (Gdx.input.isKeyPressed(Input.Keys.W)) {return 1;}
    if (Gdx.input.isKeyPressed(Input.Keys.A)) {return 0;}
    if (Gdx.input.isKeyPressed(Input.Keys.S)){return 0;}
    if (Gdx.input.isKeyPressed(Input.Keys.D)){return 0;}
return 0;
}

}