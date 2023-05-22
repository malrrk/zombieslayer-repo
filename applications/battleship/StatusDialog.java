// StatusDialog.java


import javax.swing.*;
import java.awt.event.*;
import ch.aplu.util.*;

class StatusDialog extends JDialog implements ActionListener
{
  private JOptionPane optionPane;

  public StatusDialog(final int ulx, final int uly, final boolean hasButton)
  {
    if (SwingUtilities.isEventDispatchThread())
    {
      init(ulx, uly, hasButton);
    }
    else
    {
      try
      {
        SwingUtilities.invokeAndWait(new Runnable()
        {
          public void run()
          {
            init(ulx, uly, hasButton);
          }
        });
      }
      catch (Exception ex)
      {
      }
    }
  }

  public void actionPerformed(ActionEvent evt)
  {
    Monitor.wakeUp();
  }

  private void init(int ulx, int uly, boolean hasButton)
  {
    setTitle("Status Information");
    optionPane = new JOptionPane("");
    optionPane.setOptionType(JOptionPane.DEFAULT_OPTION);
    optionPane.setMessageType(JOptionPane.INFORMATION_MESSAGE);
    if (hasButton)
    {
      JButton jButton = new JButton("Continue");
      jButton.addActionListener(this);
      Object[] options = new Object[] {jButton};
      optionPane.setOptions(options);
    }
    else
      optionPane.setOptions(new Object[] {});
    optionPane.setInitialSelectionValue(null);
    setContentPane(optionPane);
    pack();
    setLocation(ulx, uly);
    setVisible(true);
  }

  public void setText(String text)
  {
    setText(text, false);
  }

  public void setText(final String text, final boolean adjust)
  {
    if (SwingUtilities.isEventDispatchThread())
    {
      optionPane.setMessage(text);
      if (adjust)
        pack();
    }
    else
    {
      try
      {
        SwingUtilities.invokeAndWait(new Runnable()
        {
          public void run()
          {
            optionPane.setMessage(text);
            if (adjust)
              pack();
          }
        });
      }
      catch (Exception ex)
      {
      }
    }
  }

  protected void processWindowEvent(WindowEvent e)
  {
    if (e.getID() == WindowEvent.WINDOW_CLOSING)
    {
      System.exit(0);
    }
  }
}
