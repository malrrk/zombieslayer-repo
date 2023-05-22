// CameraMover.java
// Move camera and take snap shot

import ch.aplu.jgamegrid.*;
import ch.aplu.kinect.*;
import java.awt.*;
import java.awt.image.*;
import ch.aplu.ggkinect.*;
import javax.swing.JOptionPane;

public class CameraMover extends GameGrid
  implements KinectCloseListener, GGButtonListener
{
  private String dllPath =
    Kinect.is64bit() ? "KinectHandler64" : "KinectHandler";
  private GGKinect kinect;
  private final String title = "Kinect Video Frame";
  private static final int captionHeight = 19;
  private final int ulx = 0;
  private final int uly = 20;
  private static int width = 640;
  private static int height = 480 + captionHeight;
  private GGButton snapShotBtn = new GGButton("sprites/snapshot.png");
  private GGButton grabBtn = new GGButton("sprites/grab.png");
  private GGButton upBtn = new GGButton("sprites/camup.png");
  private GGButton downBtn = new GGButton("sprites/camdwn.png");
  private int cameraElevationAngle;
  private boolean isGrabbed = false;
  private GGTextField tf = new GGTextField(this, "", new Location(300, 500), true);

  public CameraMover()
  {
    super(640, 520, 1, null, false);
    setPosition(ulx + width, uly);
    GGBackground bg = getBg();
    bg.setPaintColor(Color.black);
    bg.clear(new Color(128, 255, 128));
    bg.drawRectangle(new Point(0, 0), new Point(640, 470));
    addActor(snapShotBtn, new Location(26, 500));
    addActor(grabBtn, new Location(80, 500));
    addActor(upBtn, new Location(134, 500));
    addActor(downBtn, new Location(188, 500));
    setTitle("KinectJLib - SnapShot/Grab/Move Example (www.aplu.ch)");
    snapShotBtn.addButtonListener(this);
    grabBtn.addButtonListener(this);
    upBtn.addButtonListener(this);
    downBtn.addButtonListener(this);

    kinect = new GGKinect(dllPath, title, ulx, uly, width, height,
      GGKinect.DecorationStyle.STANDARD);
    kinect.addCloseListener(this);
    show();
    cameraElevationAngle = kinect.getCameraElevationAngle();
    System.out.println("Current camera elevation angle: "
      + cameraElevationAngle);
    System.out.println("Maximum camera elevation angle: "
      + kinect.getCameraMaximumElevationAngle());
    System.out.println("Minimum camera elevation angle: "
      + kinect.getCameraMinimumElevationAngle());
  }

  public void notifyClose()
  {
    System.exit(0);
  }

  public void buttonClicked(GGButton button)
  {
  }

  public void buttonReleased(GGButton button)
  {
  }

  public void buttonPressed(GGButton button)
  {
    if (button == snapShotBtn)
    {
      if (isGrabbed)
      {
        String imageFile = "c:\\snapshot.png";
        kinect.doPNGSnapShot(imageFile);
        tf.setText("Image copied to " + imageFile);
        tf.show();
      }
      else
      {
        tf.setText("Grab an image first");
        tf.show();
      }
    }

    if (button == grabBtn)
    {
      BufferedImage bi = kinect.getImage();
      getBg().drawImage(bi);
      isGrabbed = true;
      tf.setText("");
    }
    if (button == upBtn)
    {
      cameraElevationAngle += 4;
      boolean rc = kinect.setCameraElevationAngle(cameraElevationAngle);
      if (rc)
        System.out.println("New angle: " + cameraElevationAngle);
      else
      {
        System.out.println("Failed to set new angle");
        cameraElevationAngle -= 4;
      }
    }
    if (button == downBtn)
    {
      cameraElevationAngle -= 4;
      boolean rc = kinect.setCameraElevationAngle(cameraElevationAngle);
      if (rc)
        System.out.println("New angle: " + cameraElevationAngle);
      else
      {
        System.out.println("Failed to set new angle");
        cameraElevationAngle += 4;
      }
    }
  }

  public static void main(String args[])
  {
    new CameraMover();
  }

}
