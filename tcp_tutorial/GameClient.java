// GameClient.java
// Example of a very simple game client

import java.util.*;
import ch.aplu.tcp.*;
import ch.aplu.util.*;

public class GameClient extends TcpAgent
{
  private final static String server = "Kathrin";
  private final static Console c = new Console();
  private static String sessionID;

  static
  {
    c.print("Enter a unique session ID: ");
    sessionID = c.readLine();
  }

  public GameClient()
  {
    super(sessionID, server);
    c.print("What is your name? ");
    String source = c.readLine();
    addTcpAgentListener(new TcpAgentAdapter()
    {
      public void dataReceived(String source, int[] data)
      {
        c.println("Dice data received from " + source + ":");
        for (int z : data)
          c.println(z);
      }
    });
    c.print(source + " connecting to relay " + getRelay() + "...");
    if (connectToRelay(source, 6000).isEmpty())
    {
      c.println("Failed");
      return;
    }
    c.println("OK");
    while (true)
    {
      c.println("Press any key to throw 2 dices...");
      c.getKeyWait();
      Random r = new Random();
      int[] data =
      {
        1 + r.nextInt(6), 1 + r.nextInt(6)
      };
      send("", data);
      c.println("Dice data sent: ");
      for (int z : data)
        c.println(z);
    }
  }

  public static void main(String[] args)
  {
    new GameClient();
  }
}
