package src;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;

public class Sprites extends SpriteBatch{
    private Rectangle versuch1;

    private Texture map;

    private Texture textureAtlas; //collection for all sprites
    private TextureRegion region; //portion of the atlas
    private int[][] list; //list of coordinates for regions
    private int spriteNr; //from 0 to 7, referring W - AW  clockwise
    private int status; //from 1 to 9, referring to (1)zombie, (2)basic, (3)sword, (4)plate, (5)shield,
                                               // (6)plateshield, (7)platesword, (8)shieldsword, (9)plateshieldsword
    private SpriteBatch batch;


// test begin


    public Sprites() {
        map = new Texture(Gdx.files.internal("map.png"));
        textureAtlas = new Texture(Gdx.files.internal("testatlas.png")); // files in assets folder
        spriteNr = 0;
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


    public void drawRegion(int spriteNr, float x, float y){ //prints a region at the passed position
        setRegion(spriteNr);
        batch.begin();
        batch.enableBlending();
        batch.draw(region, x, y);
        batch.end();
    }



    public void setRegion(int spriteNr){ //selects the region belonging to a certain sprite
        region = new TextureRegion(textureAtlas, list[spriteNr][0], list[spriteNr][1], 32, 32);
    }


    //test end




    //methods doing actual stuff
    public void drawCharacter(int status, int spriteNr, int x, int y){
        setCharacterSprite(status, spriteNr);
        drawRegionNew(x, y);
    }
    public void drawCharacter(int x, int y){
        setCharacterSprite();
        drawRegionNew(x, y);
    }


    //getter
    public int getStatus(){return status;}
    public int getSpriteNr() {return spriteNr;}

    public TextureRegion getRegion(){return region;}


    //setter
    public void setStatus(int status){
        status = status;
    }
    public void setSpriteNr(int spriteNr){
        spriteNr = spriteNr;
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

    public void setCharacterSprite(int status, int spriteNr){
        setRegionNew(spriteNr * 32,status * 32);
    }

    public void setCharacterSprite(){
        setRegionNew(getSpriteNr() * 32, getStatus() * 32);
    }

    public void dispose(){
        batch.dispose();
    }

    public void test(){
        versuch1 = new Rectangle();

        versuch1.x = 20;
        versuch1.y = 20;
        versuch1.width = 64;
        versuch1.height = 64;
        batch.begin();
        batch.draw(textureAtlas, versuch1.x, versuch1.y);
        batch.end();
    }
    public void lol(Matrix4 k){
        batch.setProjectionMatrix(k);
    }

    public void drawItem(int itemNr, int x, int y){
        setRegionNew(256, itemNr * 32);
        drawRegionNew(x, y);
    }

    public void drawPlant(boolean normal, int x, int y){

        if(normal){
            //print normal plants
        }
        else{
            //print cursed plants
        }
    }
}
// end sprites



