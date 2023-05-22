// BuddyServer.java

import ch.aplu.tcp.*;
import java.util.*;
import ch.aplu.util.*;
import java.awt.*;

public class BuddyServer extends TcpBridge implements TcpBridgeListener
{
  private ArrayList<String> playerList = new ArrayList<String>();
  private String serverName;

  public BuddyServer(String sessionID, String gameRoom, String serverName)
  {
    super(sessionID, serverName);
    this.serverName = serverName;
    addTcpBridgeListener(this);
    ArrayList<String> connectList = connectToRelay(6000);
    if (connectList.isEmpty())
    {
      ch.aplu.util.Console console = new ch.aplu.util.Console();
      System.out.println("Connection to relay failed");
      return;
    }
    if (!connectList.get(0).equals(serverName))
    {
      System.out.println("A server instance is already running.");
      disconnect();
    }
    else
    {
      final ch.aplu.util.Console console =
        new ch.aplu.util.Console(new Position(0, 0),
        new Size(300, 400), new Font("Courier.PLAIN", 10, 10));
      console.addExitListener(new ExitListener()
      {
        public void notifyExit()
        {
          console.end();
        }
      });
      System.out.println("Server in game room '" + gameRoom + "' running.");
    }
  }

  public void notifyRelayConnection(boolean connected)
  {
  }

  public void notifyAgentConnection(String agentName, boolean connected)
  {
    if (!connected && playerList.contains(agentName))
    {
      System.out.println("Player: " + agentName + " disconnected");
      if (playerList.contains(agentName))
        sendCommandToGroup(Buddy.Command.DISCONNECT,
          TcpTools.stringToIntAry(agentName));
      playerList.remove(agentName);
    }
    else
    {
      if (playerList.size() >= Buddy.NB_PLAYERS)
        return;
      
      System.out.println("Player: " + agentName + " connected");
      playerList.add(agentName);
      if (playerList.size() == Buddy.NB_PLAYERS)
      {
        String names = "";
        for (int i = 0; i < playerList.size(); i++)
        {
          names += playerList.get(i);
          if (i < playerList.size() - 1)
            names += ',';
        }
        int[] data = TcpTools.stringToIntAry(names);
        sendCommandToGroup(Buddy.Command.REPORT_NAMES, data);
      }
    }
  }

  public void pipeRequest(String source, String destination, int[] indata)
  {
  }
 
  private void sendCommandToGroup(int command, int... data)
  {
    for (String nickname : playerList)
      sendCommand(serverName, nickname, command, data);
  }
}
