package src;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class Player  {
private float x;
private float y;
public void Player(){
    x= 10;
    y=5;
}
public void move(){

    if(Gdx.input.isKeyPressed(Input.Keys.A))
    { this.x -= Settings.getSpeed()* Gdx.graphics.getDeltaTime();
    System.out.print (" links");}
    if(Gdx.input.isKeyPressed(Input.Keys.W))
    { this.y += Settings.getSpeed()* Gdx.graphics.getDeltaTime();
        System.out.print (" hoch");}
    if(Gdx.input.isKeyPressed(Input.Keys.S))
    { this.y -= Settings.getSpeed()* Gdx.graphics.getDeltaTime();
        System.out.print (" runter");}
    if(Gdx.input.isKeyPressed(Input.Keys.D))
    { this.x += Settings.getSpeed() * Gdx.graphics.getDeltaTime();
    System.out.print( "rechts");}
}

}