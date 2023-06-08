package com.badlogic.gdx.graphics.g2d;

import static com.badlogic.gdx.graphics.g2d.SpriteBatch.*;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class Sprites{
    private Texture textureAtlas; //atlas for all sprites
    private TextureRegion region; //portion of the atlas
    private int[][] list; //list of coordinates for regions
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


    public void drawRegion(int spriteNumber, float printAtx, float printAty){ //prints a region at the passed position
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        selectRegion(spriteNumber);

        batch.begin();
        batch.enableBlending();
        batch.draw(region, float x, float y);
        batch.end();
    }

    public void selectRegion(int spriteNumber){ //selects the region belonging to a certain sprite
        region = new TextureRegion(textureAtlas, list[spriteNumber][0], list[spriteNumber][1], 32, 32);

    }

