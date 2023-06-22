package src;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Align;

public class MainMenuScreen implements Screen{

    private static final int EXIT_BUTTON_WIDTH = 177;
    private static final int EXIT_BUTTON_HEIGHT = 50;
    private static final int PLAY_BUTTON_WIDTH = 196;
    private static final int PLAY_BUTTON_HEIGHT = 50;
    private static final int EXIT_BUTTON_X = 20;
    private static final int EXIT_BUTTON_Y = 100;
    private static final int PLAY_BUTTON_X = 420;
    private static final int PLAY_BUTTON_Y = 100;
    private static final int SCREEN_HEIGHT = 480;
    private Music menu_music;


    Texture playButton;

    Texture exitButton;

    Main game;


    public MainMenuScreen(Main game){
        this.game = game;

        playButton = new Texture("PLAY.png");
        exitButton = new Texture("EXIT.png");

        if(game.music) {
            menu_music = Gdx.audio.newMusic(Gdx.files.getFileHandle("music/resistance-142554.mp3", Files.FileType.Internal));
            menu_music.setVolume(0.5f);
            menu_music.play();
            menu_music.setLooping(true);
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();

        game.batch.draw(playButton, PLAY_BUTTON_X, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
        game.batch.draw(exitButton, EXIT_BUTTON_X, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);

        if (Gdx.input.isTouched()){
            if (Gdx.input.getX() > PLAY_BUTTON_X && Gdx.input.getX() < PLAY_BUTTON_X + PLAY_BUTTON_WIDTH && Gdx.input.getY() > (SCREEN_HEIGHT - PLAY_BUTTON_HEIGHT - PLAY_BUTTON_Y) && Gdx.input.getY() < (SCREEN_HEIGHT - PLAY_BUTTON_Y)){
                if(game.music) {menu_music.stop();}
                game.setScreen(new MainGameScreen(game));
            }else if (Gdx.input.getX() > EXIT_BUTTON_X && Gdx.input.getX() < EXIT_BUTTON_X + EXIT_BUTTON_WIDTH && Gdx.input.getY() > (SCREEN_HEIGHT - EXIT_BUTTON_HEIGHT - EXIT_BUTTON_Y) && Gdx.input.getY() < (SCREEN_HEIGHT - EXIT_BUTTON_Y)){
                Gdx.app.exit();
            }
        }

        game.batch.end();

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

    }
}
