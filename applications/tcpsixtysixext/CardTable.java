// CardTable.java

import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.*;
import ch.aplu.tcp.*;
import ch.aplu.util.*;
import java.awt.*;

public class CardTable extends CardGame implements GGButtonListener
{
  class MyCardValues implements Deck.CardValues
  {
    public int[] values(Enum suit)
    {
      int[] defaultValues = new int[]
      {
        11, 4, 3, 2, 10, 0
      };
      return defaultValues;
    }
  }

  public static enum Suit
  {
    SPADES, HEARTS, DIAMONDS, CLUBS
  }

  public static enum Rank
  {
    ACE, KING, QUEEN, JACK, TEN, NINE
  }
  
  enum BridgeDeck
  {
    spades, hearts, diamonds, clubs
  }

  enum FrenchDeck
  {
    pik, herz, kreuz, karo
  }
  
  enum GermanDeck
  {
    eichel, rosen, schilten, schellen
  }
  //
  private Deck deck;
  private final int nbPlayers = 2;
  private final Location[] handLocations =
  {
    new Location(300, 525),
    new Location(300, 75),
  };
  private final Location[] bidLocations =
  {
    new Location(300, 350),
    new Location(300, 250),
  };
  private final Location[] storeLocations =
  {
    new Location(530, 500),
    new Location(70, 100),
  };
  private final Location[] storeViewLocations =
  {
    new Location(300, 470),
    new Location(300, 130),
  };
  private final Location[] talonLocations = 
  {
    new Location(150, 300),
    new Location(450, 300)
  };
  private final int packetSize = 3;
  private Hand[] hands = new Hand[nbPlayers];
  private Hand[] bids = new Hand[nbPlayers];
  private Hand[] stores = new Hand[nbPlayers];
  private Hand[] storeViews = new Hand[nbPlayers];
  private Hand talon;
  private Card trumpCard;
  private CommunicationHandler handler;
  private final Player player;
  private int nbTransferredCards;
  private int idForehand;
  private int idRoundWinner;
  private GGButton goOutBtn = new GGButton("sprites/sixtysix.gif", true);
  private GGButton nextBtn = new GGButton("sprites/next.gif", true);
  private GGButton helpBtn = new GGButton("sprites/help.gif", true);
  private Location goOutBtnLocation = new Location(30, 540);
  private Location nextBtnLocation = new Location(30, 500);
  private Location helpBtnLocation = new Location(30, 580);
  private Location btnHideLocation = new Location(-100, -100);
  private int idLastRound = -1;
  private TextActor gameOver;
  private Location gameOverLocation = new Location(210, 300);
  private Location myScoreLocation = new Location(210, 350);
  private Location partnerScoreLocation = new Location(210, 380);
  private ModelessOptionPane mop;

  public CardTable(CommunicationHandler handler, Player player)
  {
    super(600, 600, 30);
    this.handler = handler;
    this.player = player;
    addActor(goOutBtn, btnHideLocation);
    goOutBtn.addButtonListener(this);
    addActor(helpBtn, helpBtnLocation);
    helpBtn.addButtonListener(this);
    addActor(nextBtn, btnHideLocation);
    nextBtn.addButtonListener(this);
    String cardType = player.readProperty("Deck");
    if (cardType.equals("French"))
      deck = new Deck(Suit.values(), Rank.values(), 
        FrenchDeck.values(), "cover", new MyCardValues());
    else if (cardType.equals("German"))
      deck = new Deck(Suit.values(), Rank.values(), 
        GermanDeck.values(), "cover", new MyCardValues());
    else
      deck = new Deck(Suit.values(), Rank.values(), 
        BridgeDeck.values(), "cover", new MyCardValues());
    talon = new Hand(deck);
  }

  protected void dealOut()
  {
    Hand fullHand = deck.toHand(true);
    String deckMessage = "d,";  // Command 'Send deck'
    for (int i = 0; i < fullHand.getNumberOfCards(); i++)
      deckMessage += fullHand.get(i).getCardNumber() + ",";  // Comma separated
    handler.sendMessage(deckMessage);
    initHands(deckMessage);
  }

  protected void initHands(String deckMessage)
  {
    int[] cardNumbers = TcpTools.splitToIntAry(deckMessage.substring(2), ",");
    for (int i = 0; i < cardNumbers.length; i++)
      talon.insert(cardNumbers[i], false);
    talon.setView(this, new StackLayout(talonLocations[player.playerId]));
    talon.setVerso(true);
    talon.draw();

    for (int i = 0; i < nbPlayers; i++)
    {
      // Each player has his hand with same index
      hands[i] = new Hand(deck);
      hands[i].setView(this, new RowLayout(handLocations(i), 300, 180 * flip(i)));
      storeViews[i] = new Hand(deck);
      storeViews[i].setView(this, new RowLayout(storeViewLocations(i), 400, 180 * flip(i)));
    }

    for (int nbBatch = 0; nbBatch < 2; nbBatch++)
    {
      for (int i = 0; i < nbPlayers; i++)
      {
        for (int k = 0; k < packetSize; k++)
        {
          hands[i].insert(talon.getLast(), false);
          talon.removeLast(false);
        }
        CardCover cardCover = 
          new CardCover(this, talonLocations[player.playerId], deck, 1, 0);
        cardCover.slideToTarget(handLocations(i), 10, true, true);
        cardCover.removeSelf();
        if (flip(i) == 0)
        {
          hands[i].setVerso(false);
          hands[i].sort(Hand.SortType.RANKPRIORITY, false);
        }
        else
          hands[i].setVerso(true);
        hands[i].draw();
      }
    }

    // Show trump
    trumpCard = talon.getLast().cloneAndAdd(90);
    trumpCard.getCardActor().setX(trumpCard.getCardActor().getX()
      - (player.playerId == 0 ? 20 : -20));

    trumpCard.setVerso(false);
    talon.removeLast(true);

    if (player.isDealer)
    {
      handler.sendMessage("x");  // Game table ready
      setPartnerDraw();
    }

    initBids();
    initStores();
    goOutBtn.setLocation(goOutBtnLocation);
    enableGoOutBtn(false);
  }

  protected void enableGoOutBtn(boolean enabled)
  {
    goOutBtn.setEnabled(enabled);
  }

  private int flip(int id)
  {
    if (player.playerId == 0)
      return id;
    return (id + 1) % nbPlayers;
  }

  protected void stopGame(String client)
  {
    setStatusText(client + " disconnected. Game stopped.");
    setMouseEnabled(false);
    doPause();
  }

  protected void setMyDraw()
  {
    setStatusText("It's your draw. "
      + "Double click on one of your cards to play it.");
    hands[player.playerId].setTouchEnabled(true);
  }

  protected void setPartnerDraw()
  {
    setStatusText("Wait for you draw.");
  }

  private void initBids()
  {
    for (int i = 0; i < nbPlayers; i++)
    {
      bids[i] = new Hand(deck);
      bids[i].setView(this, new StackLayout(bidLocations(i)));
      bids[i].addCardListener(new CardAdapter()
      {
        public void atTarget(Card card, Location loc)
        {
          nbTransferredCards++;
          if (nbTransferredCards == 2)
            Monitor.wakeUp();  // All cards in store->continue
        }
      });

      hands[i].setTargetArea(new TargetArea(bidLocations(i)));
    }

    hands[player.playerId].addCardListener(new CardAdapter()
    {
      public void leftDoubleClicked(Card card)
      {
        if (bids[player.partnerId].isEmpty())
          idForehand = player.playerId;
        else
        {
          idForehand = player.partnerId;
          Card foreHandCard = bids[player.partnerId].getFirst();
          if (!checkRules(card, foreHandCard))
            return;
        }
        enableGoOutBtn(false);
        hands[player.playerId].setTouchEnabled(false);
        handler.sendMessage("b," + card.getCardNumber());
        card.setVerso(false);
        card.transfer(bids[player.playerId], true);
        if (!bids[0].isEmpty() && !bids[1].isEmpty())
          transferToWinner();
        else
          setPartnerDraw();
      }
    });
  }

  protected void moveToBid(int cardNumber)
  {
    Card card = hands[player.partnerId].getCard(cardNumber);
    card.setVerso(false);
    card.transfer(bids[player.partnerId], true);
    if (!bids[0].isEmpty() && !bids[1].isEmpty())
      transferToWinner();
    else
      setMyDraw();
  }

  private boolean checkRules(Card card, Card forehandCard)
  {
    // Returns true, of rules correct

    /* ------------------- Rules --------------------
     * Must play higher card of same suit
     * If not available -> must play lower card of same suit
     * If not available -> must play trump
     * If not available -> can play any card
     * ----------------------------------------------*/

    if (talon.isEmpty())  // Must check rules only if talon is empty
    {
      Hand higher = new Hand(deck);
      for (Card c : hands[player.playerId].getCardList())
      {
        if (c.getSuit() == forehandCard.getSuit()
          && c.getRankId() < forehandCard.getRankId())
          higher.insert(c.clone(), false);
      }
      Hand lower = new Hand(deck);
      for (Card c : hands[player.playerId].getCardList())
      {
        if (c.getSuit() == forehandCard.getSuit()
          && c.getRankId() > forehandCard.getRankId())
          lower.insert(c.clone(), false);
      }
      Hand trumps = new Hand(deck);
      for (Card c : hands[player.playerId].getCardList())
      {
        if (c.getSuit() == trumpCard.getSuit())
          trumps.insert(c.clone(), false);
      }

      if (!higher.isEmpty()) // Must play card with same suit and higher rank
      {
        if (higher.getCardList().contains(card))
          return true;
        else
        {
          setStatusText("Illegal. Must play higher card of same rank.");
          return false;
        }
      }
      else if (!lower.isEmpty()) // Must play card with same suit and lower rank
      {
        if (lower.getCardList().contains(card))
          return true;
        else
        {
          setStatusText("Illegal. Must play lower card of same rank.");
          return false;
        }
      }
      else if (!trumps.isEmpty()) // Must play trump
      {
        if (trumps.getCardList().contains(card))
          return true;
        else
        {
          setStatusText("Illegal. Must play trump.");
          return false;
        }
      }
      else
        return true;
    }
    return true;
  }

  private void initStores()
  {
    for (int i = 0; i < nbPlayers; i++)
    {
      stores[i] = new Hand(deck);
      stores[i].setView(this, new StackLayout(storeLocations(i)));
    }
  }

  private void transferToWinner()
  {
    nbTransferredCards = 0;
    idRoundWinner = getIdWinner();
    setStatusText("Round winner: " + idToName(idRoundWinner));
    delay(2000);
    transferToStore(idRoundWinner);
    if (hands[0].isEmpty() && hands[1].isEmpty())  // All cards played
    {
      idLastRound = idRoundWinner;
      showScore();
    }
  }

  private int getIdWinner()
  {
    int idAfterhand = getIdPartner(idForehand);
    Card forehandCard = bids[idForehand].getFirst();
    Card afterhandCard = bids[idAfterhand].getFirst();

    // One card is trump -> trump wins
    if (forehandCard.getSuit() == trumpCard.getSuit()
      && afterhandCard.getSuit() != trumpCard.getSuit())
      return idForehand;

    if (forehandCard.getSuit() != trumpCard.getSuit()
      && afterhandCard.getSuit() == trumpCard.getSuit())
      return idAfterhand;

    // Afterhand has same suit as forehand (trump or not) -> higher rank wins
    if (forehandCard.getSuit() == afterhandCard.getSuit())
    {
      if (forehandCard.getRankId() < afterhandCard.getRankId())
        return idForehand; // higher rank has lower rankId
      return idAfterhand;  // Same rankId not possible
    }
    else  // Afterhand has another suit -> forehand wins
      return idForehand;
  }

  private void transferToStore(int idPlayer)
  {
    for (int i = 0; i < nbPlayers; i++)
    {
      bids[i].setTargetArea(new TargetArea(storeLocations(idPlayer)));
      Card c = bids[i].getLast();
      if (c == null)
        continue;
      c.setVerso(true);
      bids[i].transferNonBlocking(c, stores[idPlayer], false);
    }
    Monitor.putSleep();  // Wait until all cards are transferred to store

    if (hands[0].isEmpty() && hands[1].isEmpty())   // all cards played
      return;

    takeFromTalon(idPlayer);

    if (idPlayer == player.playerId)  // idPlayer is round winner
    {
      setMyDraw();
      enableGoOutBtn(true);  // Round winner may go out
    }
    else
      setPartnerDraw();
  }

  private void takeFromTalon(int idPlayer)
  {
    if (talon.isEmpty())
      return;
    Card c = talon.getLast();
    if (idPlayer == player.playerId)
      c.setVerso(false);
    // First the winner
    talon.setTargetArea(new TargetArea(handLocations(idPlayer)));
    talon.transfer(c, hands[idPlayer], true);
    // Then the partner
    if (talon.isEmpty()) // Reinsert trump card
    {
      trumpCard.setVerso(true);
      talon.insert(trumpCard, false);
    }
    c = talon.getLast();
    if (idPlayer != player.playerId)
      c.setVerso(false);
    talon.setTargetArea(new TargetArea(handLocations(getIdPartner(idPlayer))));
    talon.transfer(c, hands[getIdPartner(idPlayer)], true);
  }

  private int getIdPartner(int idPlayer)
  {
    return (idPlayer + 1) % 2;
  }

  private Location bidLocations(int i)
  {
    return bidLocations[flip(i)];
  }

  private Location handLocations(int i)
  {
    return handLocations[flip(i)];
  }

  private Location storeViewLocations(int i)
  {
    return storeViewLocations[flip(i)];
  }

  private Location storeLocations(int i)
  {
    return storeLocations[flip(i)];
  }

  protected void showScore()
  {
    for (int i = 0; i < nbPlayers; i++)
    {
      storeViews[i].insert(stores[i], true);
      storeViews[i].setVerso(false);
      storeViews[i].setTouchEnabled(true);
    }

    int myScore;
    int partnerScore;
    String text;
    if (player.sixtysix == -1)  // All cards played
      text = "All cards played";
    else
      text = idToName(player.sixtysix) + " goes out";
    gameOver = new TextActor(false, text, Color.yellow,
      new Color(255, 255, 255, 0), new Font("Arial", Font.PLAIN, 24));
    addActor(gameOver, gameOverLocation);
    setPaintOrder(TextActor.class);  // Set on top
    int myCardPoints = stores[player.playerId].getScore();
    int partnerCardPoints = stores[player.partnerId].getScore();

    if (player.sixtysix == -1)  // All cards played
    {
      if (idLastRound == player.playerId)
        myCardPoints += 10;  // 10 points more for last trick
      if (idLastRound == player.partnerId)
        partnerCardPoints += 10;  // ditto
      myScore = getMyScore(partnerCardPoints);
      partnerScore = getPartnerScore(myCardPoints);
      text = "All cards played. My card points: " + myCardPoints
        + (myCardPoints < 66 ? " (< 66)" : " (>= 66)")
        + ". Partner card points: " + partnerCardPoints;
    }
    else if (player.sixtysix == player.playerId) // I am going out
    {
      text = "I go out. My card points: " + myCardPoints
        + (myCardPoints < 66 ? " (< 66)" : " (>= 66)")
        + ". Partner card points: " + partnerCardPoints;
      int score = getScore(myCardPoints, player.partnerId, partnerCardPoints);
      if (score > 0)
      {
        myScore = score;
        partnerScore = 0;
      }
      else
      {
        myScore = 0;
        partnerScore = -score;
      }
    }
    else  // Partner goes out
    {
      text = "Partner goes out. His/her card points: " + partnerCardPoints
        + (partnerCardPoints < 66 ? " (< 66)" : " (>= 66)")
        + ". My card points: " + myCardPoints;
      int score = getScore(partnerCardPoints, player.playerId, myCardPoints);
      if (score > 0)
      {
        myScore = 0;
        partnerScore = score;
      }
      else
      {
        myScore = -score;
        partnerScore = 0;
      }
    }
    player.playerTotalScore += myScore;
    player.partnerTotalScore += partnerScore;
    setStatusText(text);
    String myText = "My score: " + player.playerTotalScore
      + " ( + " + myScore + " )";
    String partnerText = "Partner score: " + player.partnerTotalScore
      + " ( + " + partnerScore + " )";
    TextActor myScoreText = new TextActor(false, myText, Color.white,
      new Color(255, 255, 255, 0), new Font("Arial", Font.PLAIN, 16));
    TextActor partnerScoreText = new TextActor(false, partnerText, Color.white,
      new Color(255, 255, 255, 0), new Font("Arial", Font.PLAIN, 16));
    addActor(myScoreText, myScoreLocation);
    addActor(partnerScoreText, partnerScoreLocation);
    nextBtn.setLocation(nextBtnLocation);
  }

  private int getScore(int goOutCardPoints, int idPartner, int partnerCardPoints)
  // Returns negative score, if the points of the goOut player is <66 
  {
    int score = 0;
    if (stores[idPartner].isEmpty())
      score = 3;
    else if (partnerCardPoints < 33)
      score = 2;
    else
      score = 1;
    if (goOutCardPoints >= 66)
      return score;
    else
      return -score;
  }

  private int getMyScore(int partnerCardPoints)
  // Used when all cards are played
  {
    int score = 0;
    if (stores[player.partnerId].isEmpty())
      score = 3;
    else if (partnerCardPoints < 33)
      score = 2;
    else if (partnerCardPoints < 65)
      score = 1;
    return score;
  }

  private int getPartnerScore(int myCardPoints)
  // Used when all cards are played
  {
    int score = 0;
    if (stores[player.playerId].isEmpty())
      score = 3;
    else if (myCardPoints < 33)
      score = 2;
    else if (myCardPoints < 65)
      score = 1;
    return score;
  }

  private String idToName(int idPlayer)
  {
    if (idPlayer == player.playerId)
      return player.playerName;
    if (idPlayer == player.partnerId)
      return player.partnerName;
    return "";
  }

  public void buttonPressed(GGButton button)
  {
  }

  public void buttonReleased(GGButton button)
  {
  }

  public void buttonClicked(GGButton button)
  {
    if (button == goOutBtn)
    {
      synchronized (player) // Because message thread and button thread
      // access player.sixtysix
      {
        enableGoOutBtn(false);
        if (player.playerId == 0 && player.sixtysix == -1)
        {
          player.sixtysix = 0;
          handler.sendMessage("w");  // 0 is sixtysix
          showScore();
        }
        else
          handler.sendMessage("u");  // Send request
      }
    }
    if (button == nextBtn)
      clear();
    if (button == helpBtn)
      showRules();
  }

  private void clear()
  {
    goOutBtn.setLocation(btnHideLocation);
    player.sixtysix = -1;
    nextBtn.setLocation(btnHideLocation);
    for (int i = 0; i < nbPlayers; i++)
    {
      hands[i].removeAll(false);
      stores[i].removeAll(false);
      storeViews[i].removeAll(false);
    }
    talon.removeAll(false);
    trumpCard.getCardActor().removeSelf();
    removeActors(TextActor.class);
    setStatusText("Next game. Waiting for partner to confirm");
    if (player.playerId == 0)
      player.replay = true;
    else
      handler.sendMessage("n");
  }

  private void showRules()
  {
    if (mop == null)
      mop = new ModelessOptionPane(getFrame(), 0, 0,
        new Dimension(360, 600), true);
    else
      mop.setVisible(true);
    mop.setText(player.rules);
    mop.setTitle("Game Rules");
    mop.addCleanable(
      new Cleanable()
      {
        public void clean()
        {
          mop.setVisible(false);
        }
      });
  }
}
