package src;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;


public class Main extends ApplicationAdapter {

		SpriteBatch batch;
		Texture img;
		Player player;


		@Override
		public void create () {
			batch = new SpriteBatch();
			img = new Texture("badlogic.jpg");
			player = new Player();

		}

		@Override
		public void render () {
			ScreenUtils.clear(1, 0, 0, 1);
			batch.begin();


			batch.draw(img, 0, 0);

			batch.end();

			player.move();
		}

		@Override
		public void dispose () {
			batch.dispose();
			img.dispose();
		}


}
