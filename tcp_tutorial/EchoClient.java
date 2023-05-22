// EchoClient.java
// Sends message to echo server EchoServer and waits for reception of echo
// Simulates a delay due to long lasting program code

import ch.aplu.tcp.*;
import ch.aplu.util.*;

public class EchoClient extends Console
  implements TcpNodeListener
{
  private final String sessionID = "echo&1&&asdf87u823";
  private final String nickname = "echoclient";
  private TcpNode tcpNode = new TcpNode();
  private boolean replied;
 
  public EchoClient()
  {
    tcpNode.addTcpNodeListener(this);
    System.out.println("Trying to connect to "
      + tcpNode.getRelay() + " using port " + tcpNode.getPort());
    tcpNode.connect(sessionID, nickname, 2, 2, 2);
    Monitor.putSleep();
    setTitle("Echo Client Ready");
    int n = 1;
    while (true)
    {
      replied = false;
      String msg = "#" + n++;
      tcpNode.sendMessage(msg);
      System.out.println("\nMessage sent: " + msg);
      System.out.println("Working now...");
      // Simulate some work
      // Set this delay time to 1000 for a 'fast' client and
      // the corresponding delay time in EchoServer to 5000 for a 'lazy' server
      TcpTools.delay(5000);  
      System.out.println("Working done.");
      System.out.println("Waiting for reply if necessary...");
      // Wait until work is finished AND got reply
      while (!replied)
        TcpTools.delay(1);
    }
  }

  public void nodeStateChanged(TcpNodeState state)
  {
    switch (state)
    {
      case DISCONNECTED:
        System.out.println("State changed: DISCONNECTED");
        break;
      case CONNECTING:
        System.out.println("State changed: CONNECTING");
        break;
      case CONNECTED:
        System.out.println("State changed: CONNECTED");
        Monitor.wakeUp();
        break;
    }
  }

  public void messageReceived(String sender, String text)
  {
    System.out.println("Message received: " + text);
    replied = true;
  }

  public void statusReceived(String text)
  {
    System.out.println("Status: " + text);
  }

  public static void main(String[] args)
  {
    new EchoClient();
  }
}
