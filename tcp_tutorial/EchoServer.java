// EchoServer.java
// Sends back the received line to EchoClient 
// Simulates a delay due to long lasting program code

import ch.aplu.tcp.*;
import ch.aplu.util.*;

public class EchoServer extends Console
  implements TcpNodeListener
{
  private final String sessionID = "echo&1&&asdf87u823";
  private final String nickname = "echoserver";
  private TcpNode tcpNode = new TcpNode();

  public EchoServer()
  {
    tcpNode.addTcpNodeListener(this);
    System.out.println("Trying to connect to "
      + tcpNode.getRelay() + " using port " + tcpNode.getPort());
    tcpNode.connect(sessionID, nickname, 2, 2, 2);
    Monitor.putSleep();
    setTitle("Echo Sender Ready");
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
    System.out.println("\nMessage received: " + text);
    System.out.println("Working now...");
    // Simulate some work
    // Set this delay time to 5000 for a 'lazy' server and
    // the corresponding delay time in EchoClient to 1000 for a 'fast' client
    TcpTools.delay(1000);  // Simulate some work, adapt
    System.out.println("Working done");
    tcpNode.sendMessage(text);   // Echo it
  }

  public void statusReceived(String text)
  {
    System.out.println("Status: " + text);
  }

  public static void main(String[] args)
  {
    new EchoServer();
  }
}
