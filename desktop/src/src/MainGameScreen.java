package src;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;

public class MainGameScreen implements Screen{

    //Sprites batch;
    Player player;
    Camera cam;
    float x;
    float y;
    int intX;
    int intY;
    Tower tower;
    float zeit;
    Hostilehilfsklasse z;
    int zombieTimer;
    Rectangle  zombieRectangle;
    Rectangle item;
    Sound zombieDied = Gdx.audio.newSound(Gdx.files.internal("music/Zombie_02.mp3"));;

    Texture OVERLAY;

    Main game;

    ArrayList<RedZombie> RedZombiesList;

    public MainGameScreen(Main game){
        this.game = game;
        //batch = new Sprites();

        //batch.drawRegion ( 0,1,1);
        //img = new Texture("badlogic.jpg");
        player = new Player(game.batch, Settings.getx0y0(), Settings.getx0y0());
        tower = new Tower();
        cam = new Camera(x,y);
        z = new Hostilehilfsklasse();
        zombieTimer = 0;
        x = y = Settings.getx0y0();
        zombieRectangle = new Rectangle(0,0,12,18);
        item = new Rectangle(x-2,y+9,15,11);

        if(game.music) {
            Music menu_music = Gdx.audio.newMusic(Gdx.files.getFileHandle("music/where-the-brave-may-live-forever-viking-background-music-109867.mp3", Files.FileType.Internal));
            menu_music.setVolume(0.2f);
            menu_music.play();
            menu_music.setLooping(true);

        }

        OVERLAY = new Texture("BLACK_OVERLAY.png");

        RedZombiesList = new ArrayList<>();

        //RedZombiesList.add(new RedZombie(x - 300, y + 200));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 1, 1, 1);
        //batch.setRegion(0);
        //batch.drawRegion(0,1,1);
        if (!tower.alive()) {
            cam.positionSet(x,y);
            game.batch.kombinieren(cam.positionSet(x, y));
            tot();
        } else {
            player.move();
            x = player.x;
            y = player.y;
            intX = Math.round(x);
            intY = Math.round(y);

            //RedZombiesList.get(0).move();

            //x = RedZombiesList.get(0).x;
            //y = RedZombiesList.get(0).y;

            //RedZombiesList.get(0).move();


            item.setPosition(x-2,y+4);
            game.batch.maps();
            this.draw();
            game.batch.kombinieren(cam.positionSet(intX, intY));
            game.batch.drawCharacter(player.getStatus(), player.getSpriteNr(), intX, intY);
            game.batch.drawText((int) player.getLeben(), (int) zeit, (int) tower.getHealth(), intX, intY, player.getKills());
            zeit = zeit + Gdx.graphics.getDeltaTime();
            item.setPosition(x,y+9);
            if ((int) zeit - zombieTimer >3) {
                z.spawnZombies();
                zombieTimer = (int) zeit;
            }

            for (int i = 0; i < z.counter2-1; i++) {

                if (z.zombieAlive(i)) {
                    zombieRectangle.setPosition(z.mx(i), z.my(i));
                    if(tower.hitbox.overlaps(zombieRectangle)) {
                        tower.hurt(0.1f);
                    }
                    if (Gdx.input.isKeyPressed(Input.Keys.K) && item.overlaps(zombieRectangle)) {
                        z.hurt(i);
                        game.batch.drawCharacter(1, 1 + 8, (int) z.mx(i), (int) z.my((i)));

                    }
                    else{
                        game.batch.drawCharacter(1, 1, (int)z.mx(i), (int)z.my((i)));
                    }

                    if(player.hitbox.overlaps(zombieRectangle)){
                        player.hurt();
                        game.batch.drawCharacter(player.getStatus(), player.getSpriteNr() + 8, (int)x, (int)y);

                    }



                }


                else{
                    z.remove(i);
                    zombieDied.play(1.0f);
                    player.addKill();
                    if(player.getKills() > 0){
                        player.setStatus(3);
                        Settings.setZleben(20);
                    }

                    if(player.getKills() > 4){
                        player.setStatus(8);
                        Settings.setHurtf(1.5);
                    }
                    if(player.getKills() > 24){
                        Settings.setZleben(15);
                    }

                    if(player.getKills() > 39){
                        player.setStatus(9);
                        Settings.setHurtf(0.5);
                    }
                    if(player.getKills() > 69){
                        player.setLeben(1);
                    }

                    if(player.getKills() > 149){
                        Settings.setZleben(10);
                    }
                    if(player.getKills() > 199){
                        Settings.setLebenTurm(50);
                    }

                }
            }


        }
        if (!player.playerAlive()) {
            player.x = 2048;
            player.y = 2048;
            delay(300);
        }

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        game.batch.dispose();
    }

    public void tot() {
        game.setScreen(new GameOverScreen(game, player.getKills(), zeit));
    }
    public void draw(){
        game.batch.drawManyPlantsNew();
        game.batch.drawTower();

        /* game.batch.batch.begin();
        game.batch.batch.draw(OVERLAY, intX - 305, intY - 220, 620, 480);
        game.batch.batch.end();*/

    }
}