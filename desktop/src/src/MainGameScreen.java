package src;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;
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

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;

public class MainGameScreen implements Screen{

    //Sprites batch;
    Player player;
    Camera cam;
    float x;
    float y;
    Friendly turm;
    float zeit;
    Hostilehilfsklasse z;
    int zombieTimer;
    Rectangle playerRectangle;
    Rectangle  zombieRectangle;
    Rectangle  turmRectangle;
    Rectangle item;

    Texture OVERLAY;

    Main game;

    public MainGameScreen(Main game){
        this.game = game;
        //batch = new Sprites();

        //batch.drawRegion ( 0,1,1);
        //img = new Texture("badlogic.jpg");
        player = new Player(game.batch, Settings.getx0y0(), Settings.getx0y0());
        turm = new Friendly();
        cam = new Camera(x,y);
        z = new Hostilehilfsklasse();
        zombieTimer = 0;
        x = y = Settings.getx0y0();
        zombieRectangle = new Rectangle(0,0,12,18);
        playerRectangle = new Rectangle(0, 0, 12, 18);
        turmRectangle = new Rectangle(Settings.getx0y0(),Settings.getx0y0(),41,50);
        item = new Rectangle(x-2,y+9,15,11);

        if(game.music) {
            Music menu_music = Gdx.audio.newMusic(Gdx.files.getFileHandle("music/where-the-brave-may-live-forever-viking-background-music-109867.mp3", Files.FileType.Internal));
            menu_music.setVolume(0.2f);
            menu_music.play();
            menu_music.setLooping(true);
        }

        OVERLAY = new Texture("BLACK_OVERLAY.png");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 1, 1, 1);
        //batch.setRegion(0);
        //batch.drawRegion(0,1,1);
        if (turm.lebent()) {
            cam.positionSet(x,y);
            game.batch.kombinieren(cam.positionSet(x, y));
            tot();
        } else {
            player.move();
            x = player.x;
            y = player.y;

            item.setPosition(x-2,y+4);
            game.batch.maps();
            game.batch.kombinieren(cam.positionSet((int )x, (int) y));
            zeit = zeit + Gdx.graphics.getDeltaTime();
            draw();
            playerRectangle.setPosition(x, y);
            item.setPosition(x,y+9);
            if ((int) zeit - zombieTimer >3) {
                z.spawnZombies();
                zombieTimer = (int) zeit;
            }

            for (int i = 0; i < z.counter2-1; i++) {

                if (z.zombieAlive(i)) {
                    zombieRectangle.setPosition(z.mx(i), z.my(i));
                    if(turmRectangle.overlaps(zombieRectangle)) {
                        turm.hurt();
                    }
                    if (Gdx.input.isKeyPressed(Input.Keys.K) && item.overlaps(zombieRectangle)) {
                            z.hurt(i);
                            game.batch.drawCharacter(1, 1 + 8, (int) z.mx(i), (int) z.my((i)));
                    }
                    else{
                            game.batch.drawCharacter(1, 1, (int)z.mx(i), (int)z.my((i)));
                        }

                    if(playerRectangle.overlaps(zombieRectangle)){
                        player.hurt();
                        game.batch.drawCharacter(player.getStatus(), player.getSpriteNr() + 8, (int)x, (int)y);

                    }



                }


                else{
                    z.remove(i);
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
        game.batch.drawCharacter(player.getStatus(), player.getSpriteNr(), (int) x, (int) y);
        game.batch.drawText((int) player.getLeben(), (int) zeit, (int) turm.getlebenTurma(), (int) x, (int) y, player.getKills());

        /* game.batch.batch.begin();
        game.batch.batch.draw(OVERLAY, (int) x - 305, (int) y - 220, 620, 480);
        game.batch.batch.end();*/

    }
}