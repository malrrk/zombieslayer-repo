package src;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;

public class Camera {
    public Matrix4 combined;
    private OrthographicCamera cam;
    public Camera(){

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        // Constructs a new OrthographicCamera, using the given viewport width and height
        // Height is multiplied by aspect ratio.
        cam = new OrthographicCamera(10, 10 * (h / w));

        cam.position.set(0, 0, 0);
        System.out.print ("ewew");
        cam.update();
    }
    public void positionSet(int x, int y)
    {
        cam.position.set(x, y, 0);
    }

    public void



}
