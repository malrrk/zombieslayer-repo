package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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

public class Sprites extends SpriteBatch {

    private int[][] x_y_Plants_0; //x and y coordinates of the plants
    private int[][] x_y_Plants_1; //x and y coordinates of the plants
    private int[][] x_y_Plants_2; //x and y coordinates of the plants
    private int[][] x_y_Plants_3; //x and y coordinates of the plants
    private int[][] x_y_Plants_4; //x and y coordinates of the plants
    private int[][] x_y_Plants_5; //x and y coordinates of the plants
    private Texture gameOver; //game over frames

    private Texture map;
    private Texture plantatlas; //collection of all plants and the statue
    private Texture itemAtlas; //collection off all items

    private Texture characterAtlas; //collection for all sprites
    private TextureRegion region; //portion of the atlas
    private int spriteNr; //describes the direction the player is facing
    private int status; //describes the current items the player is holding

    public SpriteBatch batch;
    public BitmapFont font;


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


        x_y_Plants_0 = new int[800][3];
        x_y_Plants_1 = new int[300][3];
        x_y_Plants_2 = new int[200][3];
        x_y_Plants_3 = new int[200][3];
        x_y_Plants_4 = new int[150][3];
        x_y_Plants_5 = new int[ 50][3];

        for (int[] ints : x_y_Plants_0) {

            ints[0] = (int) (Math.random() * 3800) + 100;
            ints[1] = (int) (Math.random() * 3800) + 100;

            if(ints[0] > 3030){
                ints[2] = 2;
            }else{
                ints[2] = 1;
            }
        }
        for (int[] ints : x_y_Plants_1) {

            ints[0] = (int) (Math.random() * 3800) + 100;
            ints[1] = (int) (Math.random() * 3800) + 100;

            if(ints[0] > 3030){
                ints[2] = 2;
            }else{
                ints[2] = 1;
            }
        }
        for (int[] ints : x_y_Plants_2) {

            ints[0] = (int) (Math.random() * 3800) + 100;
            ints[1] = (int) (Math.random() * 3800) + 100;

            if(ints[0] > 3030){
                ints[2] = 2;
            }else{
                ints[2] = 1;
            }
        }
        for (int[] ints : x_y_Plants_3) {

            ints[0] = (int) (Math.random() * 3800) + 100;
            ints[1] = (int) (Math.random() * 3800) + 100;

            if(ints[0] > 3030){
                ints[2] = 2;
            }else{
                ints[2] = 1;
            }
        }
        for (int[] ints : x_y_Plants_3) {

            ints[0] = (int) (Math.random() * 3800) + 100;
            ints[1] = (int) (Math.random() * 3800) + 100;

            if(ints[0] > 3030){
                ints[2] = 2;
            }else{
                ints[2] = 1;
            }
        }
        for (int[] ints : x_y_Plants_4) {

            ints[0] = (int) (Math.random() * 3800) + 100;
            ints[1] = (int) (Math.random() * 3800) + 100;

            if(ints[0] > 3030){
                ints[2] = 2;
            }else{
                ints[2] = 1;
            }
        }
        for (int[] ints : x_y_Plants_5) {

            ints[0] = (int) (Math.random() * 3800) + 100;
            ints[1] = (int) (Math.random() * 3800) + 100;

            if(ints[0] > 3030){
                ints[2] = 2;
            }else{
                ints[2] = 1;
            }
        }
    }


    //methods
    public void drawCharacter(int status, int spriteNr, int x, int y) { //draws character from custom status and spriteNr at x and y coordinates
        setRegionCharacter(spriteNr, status);
        drawRegionNew(x, y);
    }

    public void drawCharacter(int x, int y) { //draws character sprite from preset status and spriteNr at x and y coordinates
        setRegionCharacter(getSpriteNr(), getStatus());
        drawRegionNew(x, y);
    }

    public void drawItem(int itemNr, int x, int y) { //draws item at given coordinates
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
        this.status = status;
    }

    public void setSpriteNr(int spriteNr) {
        this.spriteNr = spriteNr;
    }

    public void setRegionCharacter(int spriteNr, int status) { //sets the region to a square of 32x32 from given sprite
        setRegionCommon(characterAtlas, spriteNr, status, 1, 0, 0);
        setSpriteNr(spriteNr);
        setStatus(status);
    }


    public void setRegionCommon(Texture texture, int x, int y, int size, int offsetx, int offsety) { //sets the region to a square of 32 * the size with the given offset at the coordinates x and y
        if (size > 0) {
            region = new TextureRegion(texture, (size * 32 * x) + offsetx, (size * 32 * y) + offsety, size * 32, size * 32);
        }
    }

    public void setRegionItem(int x, int y) {
        setRegionCommon(itemAtlas, x, y, 1, 0, 0);
    }

    public void setRegionPlant(int plantType, int plantNr) {
        setRegionCommon(plantatlas, plantNr, plantType, 2, 0, 0);
    }


    //auxiliary

    public void drawRegionNew(float x, float y) { //draws the current region at given postion
        batch.begin();
        batch.enableBlending();
        batch.draw(getRegion(), x, y);
        batch.end();
    }

    public void dispose() {
        batch.dispose();
    }

    public void maps() { //draws map
        setRegionCommon(map, 0, 0, 128, 0, 0);
        drawRegionNew(0, 0);
    }

    public void kombinieren(Matrix4 k) {
        batch.setProjectionMatrix(k);
    }


    public void drawPlant(int plantType, int plantNr, int x, int y) { //draws a single plant, aux mehtod

        if (plantType == 5) {
            if (plantNr < 2) {
                setRegionCommon(plantatlas, 0, 2, 4, 0, 64);
            } else {
                setRegionCommon(plantatlas, 1, 2, 4, 0, 64);
            }
        } else {
            setRegionPlant(plantType, plantNr);
        }
        drawRegionNew(x, y);
    }

    public void drawManyPlantsNew() { //draws plants at randomized locations

        for (int[] ints : x_y_Plants_0) {

            drawPlant(0, ints[2], ints[0], ints[1]);
        }

        for (int[] ints : x_y_Plants_1) {

            drawPlant(1, ints[2], ints[0], ints[1]);
        }

        for (int[] ints : x_y_Plants_2) {

            drawPlant(2, ints[2], ints[0], ints[1]);
        }

        for (int[] ints : x_y_Plants_3) {

            drawPlant(3, ints[2], ints[0], ints[1]);
        }

        for (int[] ints : x_y_Plants_4) {

            drawPlant(4, ints[2], ints[0], ints[1]);
        }

        for (int[] ints : x_y_Plants_5) {

            drawPlant(5, ints[2], ints[0], ints[1]);
        }
    }


    public void drawTower() { //draws the tower
        drawPlant(1, 4, 2048, 2048);

    }

    public void drawRegionGameOver(int x, int y, int drawx, int drawy) { //selects the frame for game over screen
        region = new TextureRegion(gameOver, x, y, 320, 180);
        drawRegionNew(drawx, drawy);
    }

    public void drawGameOver(int x, int y) { //draws game over screen, not used
        for (int i = 0; i < 4; i++) {
            for (int k = 0; k < 12; k++) {
                if (i < 3) {
                    drawRegionGameOver(k * 320, 360 + i * 180, x, y);
                    delay(150);
                }
                if (i == 3) {
                    while (k < 6) {
                        drawRegionGameOver(k * 320, 720, x, y);
                        delay(150);
                    }
                }
            }
        }
        drawRegionGameOver(320, 720, x, y);

    }

    public void drawText(int leben, float zeit, int lebenTurm, int x, int y, int kills) { //creates numbers in the corner

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

    public void drawScoreKillsGameOver(int kills, float time, int kills_x, int kills_y, int time_x, int time_y) {
        batch.begin();
        font.draw(batch, String.valueOf(kills), kills_x, kills_y);
        font.draw(batch, String.valueOf(time), time_x, time_y);
        batch.end();
    }


    public void moveAnimation(int status, int spriteNr) {

    }




}   // end sprites

