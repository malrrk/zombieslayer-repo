package src;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;

public class Camera {
    public Matrix4 matrix;

    private OrthographicCamera cam;
    public Camera(float x, float y){


        cam = new OrthographicCamera(320, 180 );
        cam.position.set(x+10, y-20, 0);

        cam.update();
        matrix = cam.combined;

    }
    public Matrix4 positionSet(float x, float y)
    {

        cam.position.set(x+10, y+20, 0);
        cam.update();
        matrix = cam.combined;
        return matrix;
    }

}
