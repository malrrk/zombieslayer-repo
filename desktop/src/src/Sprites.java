package src;

import static com.badlogic.gdx.graphics.g2d.SpriteBatch.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;

public class Sprites extends SpriteBatch{

    private Texture textureAtlas; //collection for all sprites
    private TextureRegion region; //portion of the atlas
    private int[][] list; //list of coordinates for regions
    private int spriteNumber; //from 0 to 7, referring W - AW  clockwise
    private int status; //from 1 to 9, referring to (1)zombie, (2)basic, (3)sword, (4)plate, (5)shield,
                                               // (6)plateshield, (7)platesword, (8)shieldsword, (9)plateshieldsword

    SpriteBatch batch;


// test begin


    public Sprites() {
        textureAtlas = new Texture(Gdx.files.internal("testatlas.png")); // file in assets folder
        spriteNumber = 0;
        status = 0;
        batch = new SpriteBatch();
        setRegionNew(0, 0);

        list = new int[4][2];
        list[0][0] = 0; // spriteNumber 0 = S
        list[0][1] = 0;
        list[1][0] = 32; // spriteNumber 1 = W
        list[1][1] = 0;
        list[2][0] = 0; // spriteNumber 2 = D
        list[2][1] = 32;
        list[3][0] = 32; // spriteNumber 3 = A
        list[3][1] = 32;

    }


    public void drawRegion(int spriteNr, float printAtx, float printAty){ //prints a region at the passed position
        setRegion(spriteNr);
        batch.begin();
        batch.enableBlending();
        batch.draw(region, printAtx, printAty);
        batch.end();
    }



    public void setRegion(int spriteNr){ //selects the region belonging to a certain sprite
        region = new TextureRegion(textureAtlas, list[spriteNr][0], list[spriteNr][1], 32, 32);
    }


    //test end




    //methods doing actual stuff
    public void drawCharacter(int status, int spriteNumber, int x, int y){
        setCharacterSprite(status, spriteNumber);
        drawRegionNew(x, y);
    }
    public void drawCharacter(int x, int y){
        setCharacterSprite();
        drawRegionNew(x, y);
    }


    //getter
    public int getStatus(){return status;}
    public int getSpriteNumber() {return spriteNumber;}

    public TextureRegion getRegion(){return region;}


    //setter
    public void setStatus(int sts){
        status = sts;
    }
    public void setSpriteNumber(int spriteNr){
        spriteNumber = spriteNr;
    }

    public void setRegionNew(int x, int y){ //sets the region to a square of 32x32 at given position
        region = new TextureRegion(textureAtlas, x, y, 32, 32);
    }


    //auxiliary
    public void clearScreen(){
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    public void drawRegionNew(float x, float y){ //draws the current region at given postion
        batch.begin();
        batch.enableBlending();
        batch.draw(getRegion(),  x,  y);
        batch.end();
    }

    public void setCharacterSprite(int status, int spriteNumber){
        setRegionNew(spriteNumber * 32,status * 32);
    }

    public void setCharacterSprite(){
        setRegionNew(getSpriteNumber() * 32, getStatus() * 32);
    }

    public void dispose(){
        batch.dispose();
    }


}
// end sprites



