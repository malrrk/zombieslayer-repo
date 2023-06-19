package src;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
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
    int zeith;
    Rectangle playerr;
    Rectangle  zr;
    Rectangle  turmr;
    Rectangle item;

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
        zeith = 0;
        x = y = Settings.getx0y0();
        zr = new Rectangle(0,0,12,18);
        playerr = new Rectangle(0, 0, 12, 18);
        turmr = new Rectangle(Settings.getx0y0(),Settings.getx0y0(),41,50);
        item = new Rectangle(x-2,y+9,15,11);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 1, 1, 1);
        //batch.setRegion(0);
        //batch.drawRegion(0,1,1);
        if (!turm.lebent()) {
            cam.positionSet(x,y);
            game.batch.lol(cam.positionSet(x, y));
            tot();
        } else {
            player.move();
            x = player.x;
            y = player.y;

            item.setPosition(x-2,y+4);
            game.batch.maps();
            game.batch.lol(cam.positionSet((int )x, (int) y));
            zeit = zeit + Gdx.graphics.getDeltaTime();
            game.batch.drawManyPlantsNew();
            game.batch.drawTower();
            game.batch.drawCharacter(player.getStatus(), player.picNr(), (int) x, (int) y);
            game.batch.schrift((int) player.getLeben(), (int) zeit, (int) turm.getlebenTurma(), (int) x, (int) y, player.getKills());
            playerr.setPosition(x, y);
            item.setPosition(x,y+9);
            if ((int) zeit - zeith >3) {
                z.z();
                zeith = (int) zeit;
            }

            for (int i = 0; i < z.zahler2-1; i++) {

                if (z.lebt(i)) {
                    game.batch.drawCharacter(1, 1, (int) z.mx(i), (int) z.my(i));
                    zr.setPosition(z.mx(i), z.my(i));
                    if (playerr.overlaps(zr)) {
                        player.hurt();
                    }
                    if (turmr.overlaps(zr)) {
                        turm.hurt();
                    }
                    if (item.overlaps(zr)) {
                        if (Gdx.input.isKeyPressed(Input.Keys.K)) {
                            z.hurt(i);
                        }
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
                        player.setStatus(12);
                        Settings.setHurtf(0.5);
                    }
                    if(player.getKills() > 69){
                        player.setLeben(1);
                    }
                    if(player.getKills() > 99){
                        player.setStatus(13);
                        Settings.setHurtf(0.2);
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
        if (!player.lebet()) {
            x = 2048;
            y = 2048;
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
        //game.batch.gameOver((int)x, (int)x);
        //game.batch.endschrift((int)x,(int)y);
        game.setScreen(new GameOverScreen(game, player.getKills(), zeit));


    }
}