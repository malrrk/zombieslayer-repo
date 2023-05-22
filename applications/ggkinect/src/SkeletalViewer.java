// SkelatalViewer.java
// Display skeleton with GameGrid        

import ch.aplu.jgamegrid.*;
import ch.aplu.kinect.*;
import java.awt.*;
import ch.aplu.ggkinect.*;
import javax.swing.JOptionPane;

public class SkeletalViewer extends GameGrid implements KinectCloseListener
{
  private String dllPath = 
    Kinect.is64bit()? "KinectHandler64" : "KinectHandler";
 
  public SkeletalViewer()
  {
    super(640, 454, 1, null, false);  // GameGrid window
    setPosition(640, 20);
    GGBackground bg = getBg();
    bg.clear(new Color(128, 255, 128));
    setTitle("GameGrid Kinect - Waiting for valid skeleton...");

    GGKinect kinect = new GGKinect(dllPath, "Video Frame",
      0, 20, 640, 480, // Position and size
      GGKinect.DecorationStyle.STANDARD);
    if (!kinect.isInitialized())
    {
      kinect.setVisible(false);
      JOptionPane.showMessageDialog(null, "Initializing of Kinect failed.");
      System.exit(0);
    }

    kinect.addCloseListener(this);
    show();

    Point3D[] joints = new Point3D[20]; // Initializes 20 skeleton joints
    for (int i = 0; i < 20; i++)
      joints[i] = new Point3D();
    kinect.getJoints(joints, 0); // Blocks undefinitely 
    int rc = 0;                  // until skeleton is valid

    // Tracking loop
    while (true)
    {
      bg.clear();
      if (rc != -1)  // Valid skeleton
      {
        setTitle("Valid Skeleton");
        kinect.drawSkeleton(this, joints, 5, Color.red);
        kinect.drawJoints(this, joints, 5, Color.black);
      }
      else
        setTitle("Waiting For Valid  Skeleton");
      refresh();
      rc = kinect.getJoints(joints, 20);  // Waits maximum 200 ms
    }
  }

  public void notifyClose()
  {
    System.exit(0);
  }

  public static void main(String args[])
  {
    new SkeletalViewer();
  }
}
