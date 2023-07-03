package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;

import static com.badlogic.gdx.math.MathUtils.clamp;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;


public class Main extends Game {

	private Stage stage;
	private Camera cam;

	public boolean music = true;
	public Sprites batch;
	@Override
	public void create() {
		batch = new Sprites();
		cam = new Camera(Settings.getx0y0(),Settings.getx0y0());
		stage = new Stage();
		this.setScreen(new MainMenuScreen(this, cam));
	}

	@Override
	public void render() {
		super.render();
	}



	@Override
	public void dispose() {
		///img.dispose();
	}

}
