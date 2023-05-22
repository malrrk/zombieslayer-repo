// SendInteger.java
// Two nodes of the same type are connected via the relay and data consisting
// of some random numbers are exchanged.

/* Remarks:
- The relay makes the node names unique by appending an increasing node
  version number (n) to the requested node name "Richard"
- The doubles are sent in a single message line separated by '&'.
  On the receiving side, TcpTools.split() is used, to split them
- Because connect returns immediately, we wait until the connection is
  confirmed or a timeout is reached by calling putSleep(long timeout)
- Data is sent to all nodes in the session (except the sending node).
  Several instances of SendInteger may be started with the same node name,
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

public class SendInteger extends TcpNode
  implements TcpNodeListener
{
  private final static int nb = 10;
  private final String myNodeName = "Richard";
  private Console c = new Console();

  public SendInteger()
  {
    c.print("Enter a unique session ID: ");
    String sessionID = c.readLine();
    addTcpNodeListener(this);
    c.println("Connecting to relay '" + getRelay() + "'...");
    connect(sessionID, myNodeName, 2, 2, 2);
    // Halt the thread until connected or timeout
    Monitor.putSleep(4000);
    if (getNodeState() != TcpNodeState.CONNECTED)
      c.println("Connection failed (timeout).");
    else
    {
      while (true)
      {
        c.println("Press any key to send data...");
        c.getKeyWait();
        c.println("Sending " + nb + " doubles...");
        StringBuffer sb = new StringBuffer();
        double sum = 0;
        for (int i = 0; i < nb; i++)
        {
          double r = Math.random();
          sum += r;
          sb.append(r + "&");
        }
        sendMessage(sb.toString());
        c.println("sum: " + sum);
      }
    }
  }

  public void nodeStateChanged(TcpNodeState state)
  {
    if (state == TcpNodeState.CONNECTED)
    {
      c.println("Connection establised.");
      Monitor.wakeUp();
    }
    if (state == TcpNodeState.DISCONNECTED)
      c.println("Connection broken.");
  }

  public void messageReceived(String sender, String text)
  {
    String[] s = TcpTools.split(text, "&");
    int nb = s.length;
    double sum = 0;
    c.println("Received from '" + sender + "':");
    for (int i = 0; i < nb; i++)
    {
      double r = Double.parseDouble(s[i]);
      sum += r;
      c.println(r);
    }
    c.println(nb + " doubles. Sum: " + sum);
  }

  public void statusReceived(String text)
  {
    c.println("Status received: " + text);
  }

  public static void main(String[] args)
  {
    new SendInteger();
  }
}
