// FrameTutorial.java

import ch.aplu.jgamegrid.*;
import javax.swing.*;
import java.awt.*;

public class FrameTutorial extends JFrame
{
  private final String version = "1.01";
  
  public FrameTutorial()
  {
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setResizable(false);
    setLocation(10, 10);
    setTitle("FrameTutorial V" + version + 
      ", (Design: Carlo Donzelli, Implementation: Aegidius Pluess)");
    GamePane gp = new GamePane();
    getContentPane().add(gp, BorderLayout.WEST);
    NavigationPane np = new NavigationPane();
    getContentPane().add(np, BorderLayout.EAST);

    pack();  // Must be called before actors are added!

    np.setGamePane(gp);
    np.createGui();
    gp.setNavigationPane(np);
    gp.createGui();
  }

  public static void main(String[] args)
  {
    EventQueue.invokeLater(new Runnable()
    {
      public void run()
      {
        new FrameTutorial().setVisible(true);
      }

    });
  }

}