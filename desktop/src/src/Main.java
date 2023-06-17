package src;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;


public class Main extends ApplicationAdapter {

		Sprites batch;
		//Texture img;
		Player player;
		OrthographicCamera cam;
		float x;
		float y;
		Friendly turm;
		float zeit;
		Hostilehilfsklasse z;
		int zeith;
		Rectangle playerr;
		Rectangle zr;
		Rectangle turmr;

		@Override
		public void create () {
			batch = new Sprites();

			//batch.drawRegion ( 0,1,1);
			//img = new Texture("badlogic.jpg");
			player = new Player();
			turm = new Friendly();
			cam = new OrthographicCamera(200,200);
			cam.position.set(x+10,y+10,0);
			z = new Hostilehilfsklasse();
			zeith= 0;
			x=y=2048;
			zr= new Rectangle(0,0,12,18);
			playerr= new Rectangle (0,0,12,18);
			turmr = new Rectangle(2048,2048,41,50);



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
			batch.maps();
			Matrix4 matrix = cam.combined;
			batch.lol(matrix);
			batch.kollision(x,y);

			batch.schrift((int)player.getLeben(), zeit+= Gdx.graphics.getDeltaTime(),turm.getlebenTurma(), (int)x,(int)y);
			batch.drawManyPlantsNew();
			batch.drawTower();
			batch.drawCharacter(12, player.pic(),(int) x, (int) y);
			playerr.setPosition(x,y);
			if ((int)zeit- zeith>5){
				z.z();
				zeith = (int) zeit;
			}
			for (int i=0; i < z.zahler;i++)
			{
				batch.drawCharacter(1,1,(int)z.mx(i),(int)z.my(i));
				zr.setPosition(z.mx(i),z.my(i));
				if (playerr.overlaps(zr))
				{
					player.hurt();
				}
				if (turmr.overlaps(zr))
				{
					turm.hurt();
			}
			}
			if (player.lebenp()==true){
				x= 2048;
				y = 2048;
				delay(300);
			}
			if (turm.lebent()==true){
				batch.dispose();
			}

		}

		@Override
		public void dispose () {
			batch.dispose();
			///img.dispose();
		}



}
