package src;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;


public class Main extends ApplicationAdapter {

		Sprites batch;
		//Texture img;
		Player player;


		@Override
		public void create () {
			batch = new Sprites();
			batch.drawRegion ( 0,1,1);
			//img = new Texture("badlogic.jpg");
			player = new Player();

		}

		@Override
		public void render () {
			ScreenUtils.clear(1, 0, 0, 1);
			batch.setRegion(0);
			batch.drawRegion(1,1,1);
			batch.drawCharacter(0, 0);
			player.move();
		}

		@Override
		public void dispose () {
			batch.dispose();
			///img.dispose();
		}


}
