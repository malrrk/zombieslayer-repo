package com.badlogic.gdx.graphics.g2d;

import static com.badlogic.gdx.graphics.g2d.SpriteBatch.*;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class Sprites{
    private Texture textureAtlas; //collection for all sprites
    private TextureRegion region; //portion of the atlas
    private int[][] list; //list of coordinates for regions
    private int spriteNumber; //from 0 to 7, referring W - AW  clockwise
    private int status; //from 1 to 9, referring to (1)zombie, (2)basic, (3)sword, (4)plate, (5)shield,
                                                  // (6)plateshield, (7)platesword, (8)shieldsword, (9)plateshieldsword



// test begin


    public Sprites{
        textureAtlas = new Texture(Gdx.files.internal("testatlas.png")); // file in assets folder
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
        batch.draw(region, float x, float y);
        batch.end();
    }



    public void setRegion(int spriteNr){ //selects the region belonging to a certain sprite
        region = new TextureRegion(textureAtlas, list[spriteNr][0], list[spriteNr][1], 32, 32);
    }


    //test end




    public region getRegion{
    return region;
    }


    public void setSpriteNumber(int spriteNr){
        spriteNumber = spriteNr;
    }
    public int getSpriteNumber{
        return spriteNumber;
    }


    public void setStatus(int sts){
        status = sts;
    }
    private int getStatus{
        return status;
    }

    public void setRegionNew(int x, int y){ //sets the region to a square of 32x32 at given position
        region = new TextureRegion(texture, x, y, 32, 32);
    }

    public void drawRegionNew(float x, float y){ //draws the current region at given postion
        batch.begin();
        batch.enableBlending();
        batch.draw(getRegion(), float x, float y);
        batch.end();
    }

    public void clear(){
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    public void setCharacterSprite(int status, int spriteNumber){
        setRegionNew(spriteNumber * 32,status * 32);
    }
    public void setCharacterSprite(){
        setRegion(getSpriteNumber() * 32, getStatus() * 32);
    }
}
// end class



