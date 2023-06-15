package src;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.ScreenUtils;


public class Main extends ApplicationAdapter {

		Sprites batch;
		//Texture img;
		Player player;
		OrthographicCamera cam;
		float x;
		float y;
		Friendly turm;
		float zeit;

		@Override
		public void create () {
			batch = new Sprites();

			//batch.drawRegion ( 0,1,1);
			//img = new Texture("badlogic.jpg");
			player = new Player();
			turm = new Friendly();
			cam = new OrthographicCamera(200,200);
			cam.position.set(x+10,y+10,0);




		}

		@Override
		public void render () {

			ScreenUtils.clear(0, 0, 0, 1);
			//batch.setRegion(0);
			//batch.drawRegion(0,1,1);


			x = x + player.move1();
			y = y + player.move2();

			cam.position.set(x+10,y+10,0);
			cam.update();
			batch.test();
			Matrix4 matrix = cam.combined;
			batch.lol(matrix);
			//batch.begin();
			batch.drawCharacter(2, player.pic(),(int) x, (int) y);
			batch.kollsion();

			batch.schrift(player.getLeben(), zeit+= Gdx.graphics.getDeltaTime(),turm.getlebenTurma(), (int)x,(int)y);
			batch.drawCharacter(12, player.pic(),(int) x, (int) y);
			System.out.print(x);

			//batch.end();



			//cam.positionSet((int) x,(int) y);


		}

		@Override
		public void dispose () {
			batch.dispose();
			///img.dispose();
		}



}
