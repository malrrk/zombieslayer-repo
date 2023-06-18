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

import static com.badlogic.gdx.math.MathUtils.clamp;
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
	Rectangle  zr;
	Rectangle  turmr;
	Rectangle item;


	@Override
	public void create() {
		batch = new Sprites();

		//batch.drawRegion ( 0,1,1);
		//img = new Texture("badlogic.jpg");
		player = new Player();
		turm = new Friendly();
		cam = new OrthographicCamera(320, 180);
		cam.position.set(x + 10, y + 10, 0);
		z = new Hostilehilfsklasse();
		zeith = 0;
		x = y = 2048;
		zr = new Rectangle(0,0,12,18);
		playerr = new Rectangle(0, 0, 12, 18);
		turmr = new Rectangle(2048,2048,41,50);
		item = new Rectangle(x-2,y+9,15,11);//item = new Polygon(new float[]{0,0,bounds.width,0,bounds.width,bounds.height,0,bounds.height});
		//item.setOrigin(bounds.width/2, bounds.height/2);
		//item.setRotation(125);


	}

	@Override
	public void render() {

		ScreenUtils.clear(1, 1, 1, 1);
		//batch.setRegion(0);
		//batch.drawRegion(0,1,1);ass
		if (turm.lebent()) {
			tot();
		} else {
			x = x + player.move1();

			y = y + player.move2();
			item.setPosition(x-2,y+4);
			cam.position.set(x + 10, y + 10, 0);
			cam.update();
			batch.maps();
			Matrix4 matrix = cam.combined;
			batch.lol(matrix);
			zeit = zeit + Gdx.graphics.getDeltaTime();

			batch.drawManyPlantsNew();
			batch.drawTower();
			batch.drawCharacter(player.getStatus(), player.picNr(), (int) x, (int) y);
			batch.schrift((int) player.getLeben(), (int) zeit, (int) turm.getlebenTurma(), (int) x, (int) y, player.getKills());
			playerr.setPosition(x, y);
			item.setPosition(x,y+9);
			if ((int) zeit - zeith >3) {
				z.z();
				zeith = (int) zeit;
			}
			System.out.print(z.zahler2);
			for (int i = 0; i < z.zahler2-1; i++) {

				if (z.lebt(i)) {
					batch.drawCharacter(1, 1, (int) z.mx(i), (int) z.my(i));
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
					}
					if(player.getKills() > 5){
						player.setStatus(10);
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
	public void dispose() {
		batch.dispose();
		///img.dispose();
	}

	public void tot() {
		cam.position.set(0, 0, 0);

		cam.update();
		Matrix4 matrix = cam.combined;
		batch.lol(matrix);
		batch.drawGameOver(0, 0);
		batch.endschrift();
		if (Gdx.input.isKeyPressed(Input.Keys.E)) {
			zeit = 0;
			create();
		}

	}

}
