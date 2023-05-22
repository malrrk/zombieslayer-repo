// Player.java

import ch.aplu.tcp.*;
import ch.aplu.util.*;
import javax.swing.JOptionPane;

public class CommunicationHandler extends TcpNode implements TcpNodeListener
{
  private String sessionID = "xy21/6a%%sadf3afdf&%ççz&123";
  private String nickname;
  private ModelessOptionPane mop;
  private final Player player;
  private boolean isRoomReady = false;

  public CommunicationHandler(Player player)
  {
    this.player = player;
    addTcpNodeListener(this);
  }

  public void establishConnection()
  {
    String gameRoom = requestEntry("Enter a unique room name (ASCII 3..15 chars):");
    sessionID = sessionID + gameRoom;
    nickname = requestEntry("Enter your name (ASCII 3..15 chars):");
    mop = new ModelessOptionPane("Trying to connect to relay...");
    connect(sessionID, nickname, 1, 1, 1);
    // Blocks until two players are in room
    Monitor.putSleep();
    mop.setVisible(false);
    isRoomReady = true;
    player.prepareCardTable();
  }

  public void nodeStateChanged(TcpNodeState state)
  {
    if (state == TcpNodeState.DISCONNECTED)
    {
      mop.setText("Connection to relay failed. Terminating now...");
      TcpTools.delay(3000);
      System.exit(0);
    }
  }

  public void messageReceived(String sender, String text)
  {
    switch (text.charAt(0))
    {
      case 'p':  // Partner is the dealer
        player.isDealer = false;
        showStatus("Partner is the dealer");
        sendMessage("r"); // ready
        break;
      case 'q':  // I am the dealer
        player.isDealer = true;
        showStatus("I am the dealer");
        sendMessage("r"); // ready
        player.cardTable.dealOut();
        break;
      case 'r':  // Partner ready
        showStatus(player.isDealer ? "I am the dealer" : "Partner is dealer");
        if (player.isDealer)
          player.cardTable.dealOut();
        break;
      case 'd':  // Deck data
        player.cardTable.initHands(text);
        break;
      case 'x':
        // Dealer's initHands() terminated, 
        // my initHands() has terminated for sure because running in same thread
        player.cardTable.setMyDraw();
        break;
      case 'b':  // Move to bid
        int[] cardNumbers = TcpTools.splitToIntAry(text.substring(2), ",");
        player.cardTable.moveToBid(cardNumbers[0]);
        break;
      case 'u':  // Request 'sixtysix' from player 1, only player 0 gets it
        synchronized (player) // Because message thread and button thread
                              // access player.sixtysix
        {
          if (player.sixtysix == -1);
          {
            sendMessage("v");  // Confirm
            player.cardTable.enableGoOutBtn(false);
            player.sixtysix = 1;
            player.cardTable.showScore();
          }
        }
        break;
      case 'v':  // Confirm 'sixtysix', only player 1 gets it
        player.sixtysix = 1;
        player.cardTable.showScore();
        break;
      case 'w': // 0 says 'sixtysix', only player 1 gets it
        player.sixtysix = 0;
        player.cardTable.enableGoOutBtn(false);
        player.cardTable.showScore();
        break;
      case 'n':  // Next game requested, only player 0 gets it
        player.partnerReplay = true;
        break;
    }
  }

  public void statusReceived(String text)
  {
    // Get attributed name from relay
    int index = text.indexOf("Personal node:--- ");
    if (index != -1)
      player.playerName = text.substring(index + 18);

    // Info to connecting node
    if ((index = text.indexOf("In session:--- ")) != -1)
    {
      if (text.substring(index + 15, index + 18).equals("(0)")) // 1st node
      {
        player.playerId = 0;
        player.partnerId = 1;
        mop.setText("Connection established. Name: " + player.playerName
          + "\nWaiting for parter ...");
        mop.setTitle("JGameGrid Sixty-Six (V" + player.VERSION + ")");
      }
      else if (text.substring(index + 15, index + 18).equals("(1)")) // 2nd node
      {
        player.playerId = 1;
        player.partnerId = 0;
        player.partnerName = text.substring(index + 20);
        Monitor.wakeUp();
      }
      else // Additional node -> refused
      {
        mop.setText("Game room full. Terminating now...");
        TcpTools.delay(3000);
        System.exit(0);
      }
    }

    // Info to already connected nodes 
    if ((index = text.indexOf("New node:--- ")) != -1)
    {
      if (!isRoomReady)  // Do not register refused nodes
      {
        player.partnerName = text.substring(index + 13);
        Monitor.wakeUp();
      }
    }

    // Info when a node disconnects
    if (text.equals("Disconnected:--- " + player.partnerName))
    {
      showStatus("Partner " + player.partnerName + " disconnected. Terminating application...");
      TcpTools.delay(3000);
      System.exit(0);
    }
  }

  private void showStatus(String msg)
  {
    player.cardTable.setStatusText(msg);
  }
  
  private String requestEntry(String prompt)
  {
    String entry = "";
    while (entry.length() < 3 || entry.length() > 15)
    {
      entry = JOptionPane.showInputDialog(null, prompt, "");
      if (entry == null)
        System.exit(0);
    }
    return entry.trim();
  }
}
