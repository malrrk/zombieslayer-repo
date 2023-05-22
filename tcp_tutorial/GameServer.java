// GameServer.java
// Example of a very simple game server

import ch.aplu.tcp.*;
import java.util.*;
import ch.aplu.util.*;

public class GameServer extends TcpBridge
{
  private static String sessionID;
  private final static String myname = "Kathrin";
  private ArrayList<String> gamers = new ArrayList<String>();
  private static Console c = new Console();

  static
  {
    c.print("Enter a unique session ID: ");
    sessionID = c.readLine();
  }

  public GameServer()
  {
    super(sessionID, myname);
    addTcpBridgeListener(new TcpBridgeAdapter()
    {
      public void pipeRequest(String source, String destination, int[] data)
      {
        for (String gamer : gamers)
        {
          if (!gamer.equals(source))  // Do not echo
          {
            c.println("pipe: " + source + "--->" + gamer);
            send(source, gamer, data);
          }
        }
      }

      public void notifyAgentConnection(String agentName, boolean connected)
      {
        if (connected)
        {
          c.println("Gamer " + agentName + " connected.");
          gamers.add(agentName);
        }
        else
        {
          c.println("Gamer " + agentName + " disconnected.");
          gamers.remove(agentName);
        }
      }
    });
    c.print("Trying to connect to relay " + getRelay() + "...");
    if (connectToRelay(5000).isEmpty())
    {
      c.println("Failed");
      System.exit(1);
    }
    else
      c.println("OK\nReady for service.");
  }

  public static void main(String[] args)
  {
    new GameServer();
  }
}
