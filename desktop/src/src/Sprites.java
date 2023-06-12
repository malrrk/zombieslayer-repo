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
    private Texture itemAtlas;

    private Texture characterAtlas; //collection for all sprites
    private TextureRegion region; //portion of the atlas
    private int spriteNr; //from 0 to 7, referring W - AW  clockwise
    private int status; //from 1 to 9, referring to (1)zombie, (2)basic, (3)sword, (4)plate, (5)shield,
                                               // (6)plateshield, (7)platesword, (8)shieldsword, (9)plateshieldsword
    private SpriteBatch batch;


// test begin


    public Sprites() {
        map = new Texture(Gdx.files.internal("map.png"));
        itemAtlas = new Texture(Gdx.files.internal("itematlas.png"));
        characterAtlas = new Texture(Gdx.files.internal("testatlas.png")); // files in assets folder
        spriteNr = 0;
        status = 0;
        batch = new SpriteBatch();
        setRegionNew(0, 0);


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

    public void setRegionNew(int spriteNr, int status) { //sets the region to a square of 32x32 from given sprite
        region = new TextureRegion(characterAtlas, 32 * spriteNr, 32 * status, 32, 32);
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
        setRegionNew(spriteNr,status);
    }

    public void setCharacterSprite(){
        setRegionNew(getSpriteNr(), getStatus());
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
        batch.draw(characterAtlas, versuch1.x, versuch1.y);
        batch.end();
    }
    public void lol(Matrix4 k){
        batch.setProjectionMatrix(k);
    }

    public void drawItem(int itemNr, int x, int y){
        setRegionItem(0, itemNr, 1);
        drawRegionNew(x, y);
    }

    public void setRegionItem(int x, int y, int size) { //sets the region to a square at given position
        if (size > 0) {
            region = new TextureRegion(characterAtlas, size * 32 * x, size * 32 * y, size * 32, size * 32);
        }
    }
    public void drawPlant(int type, boolean normal, int x, int y){

        if(normal){


            }

        else{
          //  if(type < 4){
                //small plants
           // }
          //  if(type >= 4 && type < 8){
                //medium
           // }
           // if(type >= 8){
                //large plants
            //}
        }
    }
}
// end sprites



