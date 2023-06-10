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

    if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
        //this.x -= 200* Gdx.graphics.getDeltaTime();
    System.out.print (" links");
    if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
        //this.x += 200 * Gdx.graphics.getDeltaTime();
    System.out.print( "rechts");
}

}