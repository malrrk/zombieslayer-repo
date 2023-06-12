package src;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.OrthographicCamera;

import com.badlogic.gdx.utils.viewport.Viewport;


public class Main extends ApplicationAdapter {

		Sprites batch;
		//Texture img;
		Player player;
		OrthographicCamera cam;
		float x;
		float y;
		Viewport viewport;
		@Override
		public void create () {
			batch = new Sprites();
			batch.drawRegion ( 0,1,1);
			//img = new Texture("badlogic.jpg");
			player = new Player();
			cam = new OrthographicCamera(200,200);
			cam.position.set(x+10,y+10,0);




		}

		@Override
		public void render () {

			ScreenUtils.clear(0, 0, 0, 1);
			//batch.setRegion(0);
			//batch.drawRegion(0,1,1);


			x=x+ player.move1();
			y = y+player.move2();

			cam.position.set(x+10,y+10,0);
			cam.update();
			batch.test();
			Matrix4 matrix = cam.combined;
			batch.lol(matrix);
			batch.begin();
			batch.drawCharacter(0, player.pic(),(int) x, (int) y);
			System.out.print(x);

			batch.end();



			//cam.positionSet((int) x,(int) y);


		}

		@Override
		public void dispose () {
			batch.dispose();
			///img.dispose();
		}



}
