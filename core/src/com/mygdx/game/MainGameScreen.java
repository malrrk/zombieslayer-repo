package com.mygdx.game;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;

public class MainGameScreen implements Screen{

    //Sprites batch;
    Player player;
    Random rand = new Random();

    float x;
    float y;
    int intX;
    int intY;
    Tower tower;
    float zeit;
    Hostilehilfsklasse z;
    int zombieTimer;
    Camera  cam;

    ArrayList<Sound> zombieDied;
    Rectangle  zombieRectangle;
    Rectangle  RedzombieRectangle;
    Rectangle item;

    Texture OVERLAY;

    Main game;

    ArrayList<RedZombie> RedZombiesList;

    static MySQL db;

    private static String u;
    private static int t;
    private static int k;

    public MainGameScreen(Main game, Camera cam){
        this.game = game;
        //batch = new Sprites();

        //batch.drawRegion ( 0,1,1);
        //img = new Texture("badlogic.jpg");
        player = new Player(game.batch, Settings.getx0y0(), Settings.getx0y0());
        tower = new Tower();
        z = new Hostilehilfsklasse();
        this.cam = cam;
        zombieTimer = 0;
        x = y = Settings.getx0y0();
        zombieRectangle = new Rectangle(0,0,12,18);
        RedzombieRectangle = new Rectangle(0,0,12,18);
        item = new Rectangle(x-2,y+9,15,11);

        u = "chris";
        t = 203;
        k = 42;

        //zombieDied = Gdx.audio.newSound(Gdx.files.internal("sounds/zombie-02.mp3"));


        if(!game.music) {
            Music menu_music = Gdx.audio.newMusic(Gdx.files.getFileHandle("music/where-the-brave-may-live-forever-viking-background-music-109867.mp3", Files.FileType.Internal));
            menu_music.setVolume(0.2f);
            menu_music.play();
            menu_music.setLooping(true);

        }

        zombieDied = new ArrayList<>();
        zombieDied.add(Gdx.audio.newSound(Gdx.files.internal("sounds/zombieDied01.mp3")));
        zombieDied.add(Gdx.audio.newSound(Gdx.files.internal("sounds/zombieDied02.mp3")));
        zombieDied.add(Gdx.audio.newSound(Gdx.files.internal("sounds/zombieDied03.mp3")));

        OVERLAY = new Texture("BLACK_OVERLAY.png");

        RedZombiesList = new ArrayList<>();
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
            game.batch.maps();
            player.move();
            x = player.x;
            y = player.y;
            intX = Math.round(x);
            intY = Math.round(y);


            item.setPosition(x-2,y+4);
            zeit = zeit + Gdx.graphics.getDeltaTime();
            item.setPosition(x,y+9);
            this.draw();

            if ((int) zeit - zombieTimer >2) {
                z.spawnZombies();
                zombieTimer = (int) zeit;

                RedZombiesList.add(new RedZombie());
            }



                for (Iterator<RedZombie> zombieIterator = RedZombiesList.iterator(); zombieIterator.hasNext();) {

                    RedZombie zombie = zombieIterator.next();

                    if (zombie.alive()) {
                        RedzombieRectangle.setPosition(zombie.x, zombie.y);
                        if (tower.hitbox.overlaps(RedzombieRectangle)) {
                            tower.hurt(0.1f);
                        } else {
                            zombie.move();
                            RedzombieRectangle.setPosition(zombie.x, zombie.y);
                        }

                        if (Gdx.input.isKeyPressed(Input.Keys.K) && item.overlaps(RedzombieRectangle)) {
                            zombie.hurt();
                            game.batch.drawCharacter(0, zombie.getSpriteNr() + 8, (int) zombie.x, (int) zombie.y);

                        } else {
                            game.batch.drawCharacter(0, zombie.getSpriteNr(), (int) zombie.x, (int) zombie.y);
                        }

                        if (player.hitbox.overlaps(RedzombieRectangle)) {
                            player.hurt();
                            player.setHurt(true);

                        }
                    } else {
                        RedzombieRectangle.setPosition(0, 0);
                        zombieIterator.remove();
                        zombieDied.get(rand.nextInt(3)).play(Settings.getVolume());
                        player.addKill();
                    }
                }


            for (int i = 0; i < z.counter2 - 1; i++) {

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
                        player.setHurt(true);

                    }
                }


                else {
                    z.remove(i);
                    zombieDied.get(rand.nextInt(3)).play(Settings.getVolume());
                    player.addKill();
                }

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
            game.batch.kombinieren(cam.positionSet(intX, intY));
            if(player.isHurt()){
                game.batch.drawCharacter(player.getStatus(), player.getSpriteNr() + 8, (int)x, (int)y);//hier auch intX und intY
            }else{
                game.batch.drawCharacter(player.getStatus(), player.getSpriteNr(), intX, intY);
            }
            player.setHurt(false);

            game.batch.drawText((int) player.getLeben(), (int) zeit, (int) tower.getHealth(), intX, intY, player.getKills());


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

    public void tot(){
        game.setScreen(new GameOverScreen(game, player.getKills(), zeit, cam));
    }
    public void draw(){
        game.batch.drawManyPlantsNew();
        game.batch.drawTower();

        /* game.batch.batch.begin();
        game.batch.batch.draw(OVERLAY, intX - 305, intY - 220, 620, 480);
        game.batch.batch.end();*/

    }
    public void addData(String u, int t, int k) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        try {
            MySQL.connect();
            MySQL.update("INSERT INTO table(Username, Time, Kills) VALUES(" + u + "," + t + "," + k + ")");
            MySQL.disconnect();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

}