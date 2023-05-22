// ChatClient.java

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import ch.aplu.tcp.*;

public class ChatClient extends JFrame
  implements KeyListener, TcpNodeListener
{
  private static final String VERSION = "1.5";
  private static final String sessionIDPrefix = "uquenduiu7234234%*2";
  private static final String sessionID = "room1";
  private static final String defaultNickname = "Max";
  private String line = "";
  private JPanel contentPane;
  private JPanel textPanel = new JPanel();
  private JPanel controlPanel = new JPanel();
  private JTextArea inputArea = new JTextArea(10, 40);
  private JScrollPane inputScrollPane =
    new JScrollPane(
    inputArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
  private JTextArea outputArea = new JTextArea(10, 40);
  private JScrollPane outputScrollPane =
    new JScrollPane(
    outputArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
  private JTextArea statusArea = new JTextArea(5, 40);
  private JScrollPane statusScrollPane =
    new JScrollPane(
    statusArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
  private JButton connectBtn = new JButton("Connect      ");
  private JTextField sessionEntry = new JTextField(10);
  private JLabel sessionLbl = new JLabel("SessionID:");
  private JTextField nicknameEntry = new JTextField(10);
  private JLabel nicknameLbl = new JLabel("Nickname:");
  private ChatClient chatClient;
  private TcpNode tcpNode;

  public ChatClient()
  {
    super("Chat Client V" + VERSION);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    initGUI();
    initActions();
    pack();
    setLocation(10, 10);
    setVisible(true);
    chatClient = this;
  }

  private void initGUI()
  {
    setResizable(false);
    contentPane = (JPanel)getContentPane();
    contentPane.setLayout(new BorderLayout());

    textPanel.setLayout(new BorderLayout());
    textPanel.add(outputScrollPane, BorderLayout.NORTH);
    textPanel.add(inputScrollPane, BorderLayout.CENTER);
    textPanel.add(statusScrollPane, BorderLayout.SOUTH);

    sessionEntry.setText(sessionID);
    nicknameEntry.setText(defaultNickname);
    controlPanel.add(connectBtn);
    controlPanel.add(sessionLbl);
    controlPanel.add(sessionEntry);
    controlPanel.add(nicknameLbl);
    controlPanel.add(nicknameEntry);

    contentPane.add(textPanel, BorderLayout.NORTH);
    contentPane.add(controlPanel, BorderLayout.SOUTH);
    inputArea.setEditable(false);
    statusArea.setEditable(false);
    outputArea.addKeyListener(this);
    setOutputEnabled(false);
  }

  private void initActions()
  {
    connectBtn.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        if (tcpNode == null)
        {
          inputArea.setText("");
          outputArea.setText("");
          statusArea.setText("");
          String id = sessionEntry.getText().trim();
          String nickname = nicknameEntry.getText().trim();
          if (nickname.length() < 3)
          {
            statusArea.setText("Illegal nickname (>2 ASCII chars required).");

            return;
          }
          connectBtn.setEnabled(false);
          int port = 80;
          tcpNode = new TcpNode();
          tcpNode.addTcpNodeListener(chatClient);
          append(statusArea, "Trying to connect to host '" +
           tcpNode.getRelay() + "' (port: " + tcpNode.getPort() + ") using nickname " + nickname + "...");

          tcpNode.connect(sessionIDPrefix + id, nickname, 1, 1, 1);
        }
        else
        {
          tcpNode.disconnect();
          tcpNode = null;
        }
      }
    });
  }

  public void append(final JTextArea area, final String text, final boolean eol)
  {
    EventQueue.invokeLater(new Runnable()
    {
      public void run()
      {
        area.append(text);
        if (eol)
          area.append("\n");
        scrollToEnd(area);
      }
    });
  }

  public void append(final JTextArea area, final String text)
  {
    append(area, text, true);
  }

  public void setOutputEnabled(boolean enable)
  {
    outputArea.setEnabled(enable);
    if (enable)
      outputArea.requestFocus();
  }

  private void scrollToEnd(JTextArea ta)
  {
    try
    {
      ta.setCaretPosition(
        ta.getLineEndOffset(ta.getLineCount() - 1));
    }
    catch (BadLocationException ex)
    {
      append(statusArea, ex.toString());
    }
  }

  public void keyPressed(KeyEvent evt)
  {
    char ch;
    if (evt.getKeyCode() == 8 && line.length() > 0)
      line = line.substring(0, line.length() - 1);
    else
    {
      if (evt.getKeyCode() == 0 || evt.getKeyCode() > 31)
      {
        ch = evt.getKeyChar();
        line += ch;
      }
    }

    if (evt.getKeyCode() == '\n')
    {
      tcpNode.sendMessage(line);
      line = "";
    }
  }

  public void keyReleased(KeyEvent evt)
  {
  }

  public void keyTyped(KeyEvent evt)
  {
  }

  public void messageReceived(String sender, String text)
  {
    append(inputArea, sender + ":--> " + text);
  }

  public void statusReceived(String text)
  {
    append(statusArea, text);
  }

  public void nodeStateChanged(TcpNodeState state)
  {
    switch (state)
    {
      case DISCONNECTED:
        append(statusArea, "Connection broken.");
        connectBtn.setText("Connect     ");
        connectBtn.setEnabled(true);
        tcpNode = null;
        setOutputEnabled(false);
        break;
      case CONNECTING:
        append(statusArea, "Connection underway...");
        break;
      case CONNECTED:
        append(statusArea, "Connection established.");
        connectBtn.setText("Disconnect");
        connectBtn.setEnabled(true);
        setOutputEnabled(true);
        break;
    }
  }

  public static void main(String[] args)
  {
    EventQueue.invokeLater(new Runnable()
    {
      public void run()
      {
        new ChatClient();
      }
    });
  }
}
