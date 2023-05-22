// SkelatalViewer3D.java
// Display skeleton with GameGrid        

import ch.aplu.jgamegrid.*;
import ch.aplu.kinect.*;
import java.awt.*;
import ch.aplu.ggkinect.*;
import javax.swing.JOptionPane;

public class SkeletalViewer3D extends GameGrid implements KinectCloseListener
{
   private String dllPath = 
    Kinect.is64bit()? "KinectHandler64" : "KinectHandler";
  
  public SkeletalViewer3D()
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

    Point3D[] joint3Ds = new Point3D[20]; // Initializes 20 skeleton joints
    for (int i = 0; i < 20; i++)
      joint3Ds[i] = new Point3D();
    kinect.getJoints(joint3Ds, 0); // Blocks undefinitely 
    int rc = 0;                  // until skeleton is valid

    // Tracking loop
    while (true)
    {
      bg.clear();
      if (rc != -1)  // Valid skeleton
      {
        setTitle("Valid Skeleton");
        kinect.drawSkeleton(this, joint3Ds, 5, Color.red);
        kinect.drawJoints(this, joint3Ds, 5, Color.black, 2000, 1000);
      }
      else
        setTitle("Waiting For Valid  Skeleton");
      refresh();
      rc = kinect.getJoints(joint3Ds, 20);  // Waits maximum 200 ms
    }
  }
  
  public void notifyClose()
  {
    System.exit(0);
  }

  public static void main(String args[])
  {
    new SkeletalViewer3D();
  }
}
