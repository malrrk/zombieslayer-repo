package src;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;

public class Sprites {
    private Rectangle versuch1;
    private double[] randomx;
    private double[] randomy;
    private Texture gameOver;

    private Texture map;
    private Texture plantatlas;
    private Texture itemAtlas;

    private Texture characterAtlas; //collection for all sprites
    private TextureRegion region; //portion of the atlas
    private int spriteNr; //from 0 to 7, referring W - AW  clockwise
    private int status; //from 1 to 9, referring to (1)zombie, (2)basic, (3)sword, (4)plate, (5)shield,
    // (6)plateshield, (7)platesword, (8)shieldsword, (9)plateshieldsword
    private SpriteBatch batch;
    BitmapFont font;
    private Rectangle rectanglePlayer;
    private Rectangle rectangleZombie;


// test begin


    public Sprites() {
        gameOver = new Texture(Gdx.files.internal("game over.png"));
        map = new Texture(Gdx.files.internal("map.png"));
        plantatlas = new Texture(Gdx.files.internal("plantatlas.png"));
        itemAtlas = new Texture(Gdx.files.internal("itematlas.png"));
        characterAtlas = new Texture(Gdx.files.internal("characteratlas.png")); // files in assets folder
        spriteNr = 0;
        status = 0;
        batch = new SpriteBatch();
        font = new BitmapFont();
        rectanglePlayer = new Rectangle();
        randomx = new double[2000];
        randomy = new double[2000];

        for(int i = 0; i < 2000; i++){
            randomx[i] = MathUtils.random();
            randomy[i] = MathUtils.random();
        }


    }
    //test end


    //methods to actually use
    public void drawCharacter(int status, int spriteNr, int x, int y) {
        setCharacterSprite(status, spriteNr);
        drawRegionNew(x, y);
    }

    public void drawCharacter(int x, int y) {
        setCharacterSprite();
        drawRegionNew(x, y);
    }

    public void drawItem(int itemNr, int x, int y){
        setRegionItem(0, itemNr);
        drawRegionNew(x, y);
    }


    //getter
    public int getStatus() {
        return status;
    }

    public int getSpriteNr() {
        return spriteNr;
    }

    public TextureRegion getRegion() {
        return region;
    }


    //setter
    public void setStatus(int status) {
        status = status;
    }

    public void setSpriteNr(int spriteNr) {
        spriteNr = spriteNr;
    }

    public void setRegionCharacter(int spriteNr, int status) { //sets the region to a square of 32x32 from given sprite
        setRegionCommon(characterAtlas, spriteNr, status, 1, 0, 0);
        setSpriteNr(spriteNr);
        setStatus(status);
    }


    //auxiliary
    public void clearScreen() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    public void drawRegionNew(float x, float y) { //draws the current region at given postion
        batch.begin();
        batch.enableBlending();
        batch.draw(getRegion(), x, y);
        batch.end();
    }

    public void setCharacterSprite(int status, int spriteNr) {
        setRegionCharacter(spriteNr, status);
        setSpriteNr(spriteNr);
        setStatus(status);
    }

    public void setCharacterSprite() {
        setRegionCharacter(getSpriteNr(), getStatus());
    }

    public void dispose() {
        batch.dispose();
    }

    public void maps() {
        batch.begin();
        batch.draw(map, 0, 0);
        batch.end();
    }

    public void lol(Matrix4 k) {
        batch.setProjectionMatrix(k);
    }


    public void setRegionCommon(Texture texture, int x, int y, int size, int offsetx, int offsety) { //sets the region to a square at given position
        if (size > 0) {
            region = new TextureRegion(texture, (size * 32 * x) + offsetx, (size * 32 * y) + offsety, size * 32, size * 32);
        }
    }

    public void setRegionItem(int x, int y) {
        setRegionCommon(itemAtlas, x, y, 1, 0, 0);
    }



    public void setRegionPlant(int plantType, int plantNr) {
        setRegionCommon(plantatlas,plantNr, plantType, 2, 0, 0);
    }
    public void drawPlant(int plantType, int plantNr, int x, int y){
        setRegionPlant(plantType, plantNr);
        drawRegionNew(x, y);
    }

    public void drawManyPlantsNew() {
int q;
        for (int j = 0; j < 800; j++) {

                        drawPlant(0, compare(((int) (randomx[j] * 3900) + 100), j), (int) (randomx[j] * 3900) + 100, (int) (randomy[j] * 3900) + 100);
                    }


        for (int j = 0; j < 300; j++) {

                    drawPlant(1, compare(((int) (randomx[j + 800] * 3900) + 100), j), (int) (randomx[j + 800] * 3900) + 100, (int) (randomy[j + 800] * 3900) + 100);
                }

                for (int j = 0; j < 200; j++) {

                    drawPlant(2, compare(((int) (randomx[j + 1100] * 3900) + 100), j), (int) (randomx[j + 1100] * 3900) + 100, (int) (randomy[j + 1100] * 3900) + 100);
                }


                for (int j = 0; j < 200; j++) {

                    drawPlant(3, compare(((int) (randomx[j + 1300] * 3900) + 100), j), (int) (randomx[j + 1300] * 3900) + 100, (int) (randomy[j + 1300] * 3900) + 100);
                }


                for (int j = 0; j < 150; j++) {

                    drawPlant(4, compare(((int) (randomx[j + 1500] * 3900) + 100), j), (int) (randomx[j + 1500] * 3900) + 100, (int) (randomy[j + 1500] * 3900) + 100);
                }


                for (int j = 0; j < 150; j++) {

                    drawPlant(5, compare(((int) (randomx[j + 1650] * 3900) + 100), j), (int) (randomx[j + 1650] * 3900) + 100, (int) (randomy[j + 1650] * 3900) + 100);
                }



    }
    public int compare(int abscissa, int j) {
        int q;
        if (abscissa < 2980) {
            q = (int) (randomx[j] * 2);
        } else {
            if (((int) (randomx[j] * 2) == 1)){
                q = 3;
            } else {
                q = 2;
            }
        }
        return q;
    }
    public void drawTower(){
        drawPlant(1, 4, 2048, 2048);

    }

    public void drawRegionGameOver(int x, int y, int drawx, int drawy){
        region = new TextureRegion(gameOver, x, y, 320, 180);
        drawRegionNew(drawx, drawy);
    }

    public void drawGameOver(int x, int y){
        for(int i = 0; i < 4; i++){
            for(int k = 0; k < 12; k++){
                if(i < 3){
                    drawRegionGameOver( k * 320, 360 + i * 180, x, y);
                    delay(150);
                }
                if(i == 3){
                    while(k < 6){
                        drawRegionGameOver(k * 320, 900, x, y);
                        delay(150);
                    }
                }
            }
        }
        drawRegionGameOver(320 ,225, x, y);

    }
    public void gameOver(int x, int y){
        drawRegionGameOver(100, 100, x, y);
    }

    public void schrift(int leben, float zeit, int lebenTurm, int x, int y, int kills) {

        String tmp = String.valueOf(leben);
        String tmp1 = String.valueOf((int) zeit);
        String tmp2 = String.valueOf(lebenTurm);
        String tmp3 = String.valueOf(kills);


        batch.begin();
        font.draw(batch, tmp1, x + 140, y + 100);
        font.draw(batch, tmp, x - 140, y + 100);
        font.draw(batch, tmp2, x - 140, y + 80);
        font.draw(batch, tmp3, x + 140, y + 80);
        batch.end();
    }
    public void endschrift(int x, int y){
        batch.begin();
        font.draw(batch,"druecke E zum starten",x,y);
        batch.end();
    }
}


// end sprites



