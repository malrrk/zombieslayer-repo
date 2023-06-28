package src;

import com.badlogic.gdx.Gdx;

public class RedZombie extends Hostile{

    private float x_vector;
    private float y_vector;
    private float remDistance = 0; //remaining distance to move until another vector is calculated

    private final float randomFac = 2;

    private int spriteNr = 0;

    public RedZombie(){
        //super(Settings.getx0y0() + 100, Settings.getx0y0() + 200, Settings.getLeben());
        super((int) (Math.random() * 2 * 2048), (int) (Math.random() * 2 * 2048), Settings.getZLeben());
        x_vector = calculateVector_x(x, y, Settings.getx0y0(), Settings.getx0y0());
        y_vector = calculateVector_y(x, y, Settings.getx0y0(), Settings.getx0y0());

        float vectorAverage = (Math.abs(x_vector) + Math.abs(y_vector))/2;

        if(y_vector/vectorAverage > 0.75){
            if(x_vector/vectorAverage > 0.75){
                spriteNr = 8; // rechts oben
            }else if(x_vector/vectorAverage < -0.75){
                spriteNr = 7; // links oben
            }else{
                spriteNr = 6; //oben
            }
        }else if(y_vector/vectorAverage < -0.75){
            if(x_vector/vectorAverage > 0.75){
                spriteNr = 5; // rechts unten
            }else if(x_vector/vectorAverage < -0.75){
                spriteNr = 4; // links unten
            }else{
                spriteNr = 3; //unten
            }
        }else{
            if(x_vector/vectorAverage > 0.75){
                spriteNr = 2; //rechts
            }else{
                spriteNr = 1; //links
            }
        }

    }
    public void hurt(){
        health -= 1;
    }


    public void move(){
        x += x_vector * Gdx.graphics.getDeltaTime();
        y += y_vector * Gdx.graphics.getDeltaTime();
    }

    public int getSpriteNr(){
        return spriteNr;
    }

    public float calculateVector_y(float x_start, float y_start, float x_end, float y_end){
        float vectorFac = (float) (Settings.getSpeed()/Math.sqrt(Math.pow(x_end - x_start, 2) + Math.pow(y_end - y_start, 2)));

        return (y_end - y_start) * vectorFac;
    }

    public float calculateVector_x(float x_start, float y_start, float x_end, float y_end){
        float vectorFac = (float) (Settings.getSpeed()/Math.sqrt(Math.pow(x_end - x_start, 2) + Math.pow(y_end - y_start, 2)));

        return (x_end - x_start) * vectorFac;
    }


    public float lengthVector(float x_vector, float y_vector){
        return (float) Math.sqrt(Math.pow(x_vector, 2) + Math.pow(y_vector, 2));
    }




    public void move2(){
        if(remDistance > 0) {
            x += x_vector * Gdx.graphics.getDeltaTime();
            y += y_vector * Gdx.graphics.getDeltaTime();
            remDistance -= Settings.getSpeed() * Gdx.graphics.getDeltaTime();
        }else{
            calculateVector();
        }
    }
    public void calculateVector(){
        float distanceAdd = (float)(lengthVector(Settings.getx0y0() - x, Settings.getx0y0() - y) * Math.random()/randomFac);
        x_vector = (Settings.getx0y0() + distanceAdd) - x;
        y_vector = (Settings.getx0y0() + distanceAdd) - y;

        float vectorAverage = (Math.abs(x_vector) + Math.abs(y_vector))/2;

        if(x_vector/vectorAverage > 0.5){
            x_vector = Settings.getSpeed() * (Math.abs(x_vector)/x_vector);
        }else{
            x_vector = 0;
        }

        if(y_vector/vectorAverage > 0.5){
            y_vector = Settings.getSpeed() * (Math.abs(y_vector)/y_vector);
        }else{
            y_vector = 0;
        }

        remDistance = (float) (lengthVector(x_vector, y_vector) * Math.random()/randomFac);

    }
}
