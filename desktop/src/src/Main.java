package src;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;


public class Main extends ApplicationAdapter {

		Sprites batch;
		//Texture img;
		Player player;
		Camera cam;
		float x;
		float y;
		@Override
		public void create () {
			batch = new Sprites();
			batch.drawRegion ( 0,1,1);
			//img = new Texture("badlogic.jpg");
			player = new Player();
			cam = new Camera();


		}

		@Override
		public void render () {
			ScreenUtils.clear(1, 0, 0, 1);
			//batch.setRegion(1);
			//batch.drawRegion(1,1,1);

			//batch.setProjectionMatrix(cam.combined);
			x=x+ player.move1();


			y = y+player.move2();
			batch.drawCharacter(0, player.pic(),(int) x, (int) y);
			cam.positionSet((int) x,(int) y);
			//batch.lol(cam.combined);
		}

		@Override
		public void dispose () {
			batch.dispose();
			///img.dispose();
		}



}
