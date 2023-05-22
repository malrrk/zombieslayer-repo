// Player.java
/* Game rules implemented:
 * - Last trick (when all cards are played) gives additional 10 points
 * - Going out by clicking goOutBtn only after your trick
 * - When going out and not reached 66, the opponent gains score of player
 */

import ch.aplu.tcp.*;
import ch.aplu.util.*;
import java.awt.*;

public class Player
{
  protected String VERSION = "1.1";
  protected int playerId = -1;  // Not yet playing
  protected int partnerId = -1; // ditto
  protected String playerName;
  protected String partnerName;
  protected CardTable cardTable;
  protected boolean isDealer;
  protected boolean isPartnerReady = false;
  protected CommunicationHandler handler;
  protected int sixtysix = -1;
  protected int playerTotalScore = 0;
  protected int partnerTotalScore = 0;
  protected boolean replay = false;
  protected boolean partnerReplay = false;
  protected final String rules =
    "Deal:\n"
    + "Dealer is determined randomly when the game is started.\n"
    + "The deal then alternates between players.\n"
    + "Each player is dealt six cards and the top card of\n"
    + "the remaining deck is turned face-up to show the\n"
    + "trump suit. The remaining talon is placed crosswise\n"
    + "on the trump card.\n"
    + "Card values:\n"
    + "Ace: 11, King: 4, Queen: 3, Jack: 2, Ten: 10, Nine: 0\n"
    + "Play:\n"
    + "A trick is taken by the highest card of the suit\n"
    + "or by the highest trump. Until the talon is empty,\n"
    + "there is no obligation to follow suit or to trump.\n"
    + "Talon empty:\n"
    + "The rules of play change to become more strict. Players\n"
    + "now must follow suit and take the trick if possible.\n"
    + "Closing:\n"
    + "When a player thinks to have a total of at least 66 points,\n"
    + "he presses the '66' button after taking a trick. If he fails\n"
    + "to reach 66 card-points his opponent scores the points.\n"
    + "Scoring:\n"
    + "One game point if the opponent has 33 or more card points.\n"
    + "Two game points if the opponent won at least one trick and\n"
    + "has 0 to 32 card points.\n"
    + "Three game points if the opponent won no tricks at all.\n"
    + "When played to the end, the last trick counts 10 points.\n"
    + "If both players have 65 points, the game is a draw.\n"
    + "(Marriage, trump nine and talon locking not implemented)";

  public Player()
  {
    handler = new CommunicationHandler(this);
    handler.establishConnection();
  }

  protected void prepareCardTable()
  {
    cardTable = new CardTable(handler, this);
    cardTable.setTitle(playerName + " (entered "
      + (playerId == 0 ? "first" : "second") + "). Partner: " + partnerName);
    cardTable.setStatusText("Determine dealer...");
    TcpTools.delay(2000);  // It takes some time
    determineDealer();
    if (playerId == 1)
      return;
    while (true)
    {
      if (replay && partnerReplay)
      {
        replay = false;
        partnerReplay = false;
        if (isDealer)
        {
          isDealer = false;
          handler.sendMessage("q");  // I am the dealer
        }
        else
        {
          isDealer = true;
          handler.sendMessage("p");  // Partner is dealer
        }
      }
      TcpTools.delay(100);
    }
  }

  private void determineDealer()
  {
    // First player takes a random number 0..1. If > 0.5 he is the dealer
    if (playerId == 0)
    {
      if (Math.random() > 0.5)
      {
        isDealer = true;
        handler.sendMessage("p");
      }
      else
      {
        isDealer = false;
        handler.sendMessage("q");
      }
    }
  }

  public static void main(String[] args)
  {
    new Player();
  }
}
