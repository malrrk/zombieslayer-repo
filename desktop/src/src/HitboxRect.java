package src;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class HitboxRect {

    float x;
    float y;
    float width;
    float height;

    public Rectangle rect;


    public HitboxRect(float x, float y, float width, float height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        rect = new Rectangle(this.x, this.y, this.width, this.height);

    }

    public void moveTo(float x, float y){
        this.x = x;
        this.y = y;
    }
    public void move(float x, float y){
        this.x += x;
        this.y += y;
    }

    public boolean checkCollision(HitboxRect rect){
        return Intersector.overlaps(rect.rect, this.rect);
    }

    public boolean checkCollision(Circle circle){
        return false;
    }

}
