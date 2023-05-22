// SendLine.java
// Connect to TcpRelay and exchange message lines

/* Remarks:
- The relay makes the node names unique by appending an increasing node
  version number (n) to the requested node name "Max"
- Data is sent to all nodes in the session (except the sending node).
  Several instances of SendLine may be started with the same node name,
  because the relay appends (n) to the node names to make them unique
- When a message or status is received, the callbacks messageReceived()
  or statusReceived() are triggered
- If a new program instance in the same session starts or a program
  instance dies, a status message is sent to all connected nodes.
  Nothing happens if you send the data and you are the only node in
  the session. The data is not buffered somewhere, it just gets lost
 */

import ch.aplu.tcp.*;
import ch.aplu.util.*;

public class SendLine extends Console
{
  // -------------- Inner class MyTcpNodeAdapter --------
  private class MyTcpNodeAdapter extends TcpNodeAdapter
  {
    public void messageReceived(String sender, String text)
    {
      println("Message from " + sender + ": " + text);
    }

    public void statusReceived(String text)
    {
      println("Status: " + text);
    }
  }
  // -------------- End of inner class ------------------

  private final String sessionID = "a17&&32";
  private final String nickname = "Max";

  public SendLine()
  {
    TcpNode tcpNode = new TcpNode();
    tcpNode.addTcpNodeListener(new MyTcpNodeAdapter());
    tcpNode.connect(sessionID, nickname);
    setTitle("Enter line to sent to all connected nodes");
    while (true)
      tcpNode.sendMessage(readLine());
  }

  public static void main(String[] args)
  {
    new SendLine();
  }
}
