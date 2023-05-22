// TcpBattleshipApp.java

import ch.aplu.jgamegrid.*;
import java.awt.*;
import java.awt.event.*;
import ch.aplu.util.*;
import ch.aplu.tcp.*;
import javax.swing.JOptionPane;

public class TcpBattleshipApp extends GameGrid
  implements GGMouseListener, ActionListener, TcpNodeListener
{
  private volatile boolean doDeploy = false;
  private volatile boolean isMyMove = false;
  private volatile boolean isOver = false;
  private volatile boolean isDeployed = false;
  private volatile boolean isPartnerDeployed = false;
  private final String msgDeploy = "Deploy your ships";
  private final String msgPartnerDeploy = "Wait partner deploy";
  protected final String msgMyMove = "Click to fire!";
  protected final String msgPartnerMove = "Wait for enemy bomb!";
  private final String msgPartnerWait = "Wait partner log-in";
  private final String msgGameRunning = "Game in process!";
  private String msgDeployInfo = "Deploy your fleet now!\n"
    + "Use the red marker to drag the ship.\n"
    + "While dragging, press the cursor\nup/down key to rotate the ship.\n\n"
    + "Press 'Continue' to start the game.";
  private Location currentLoc;
  private String sessionIDPrefix = "zu7qwe=^)6d";
  private String sessionID;
  private final String nickname = "Battleship_Player";
  private TcpNode node = new TcpNode();
  private ModelessOptionPane status;
  private Ship[] fleet = new Ship[5];

  public TcpBattleshipApp()
  {
    super(10, 10, 25, Color.black, null, false, 4);  // Only 4 rotated sprites
    setBgColor(Color.blue);
    setSimulationPeriod(50);
    String sessionID =
      requestEntry("Enter the session ID (ASCII >3 chars):");
    sessionID = sessionIDPrefix + sessionID;
    node.addTcpNodeListener(this);
    addMouseListener(this, GGMouse.lPress);

    show();
    doRun();
    setTitle("Connecting...");
    node.connect(sessionID, nickname, 1, 1, 1);
    Monitor.putSleep(10000);
    if (node.getNodeState() != TcpNodeState.CONNECTED)
    {
      ModelessOptionPane errorPane =
        new ModelessOptionPane("Failed to connect to relay\n" + node.getRelay());
      errorPane.setTitle("Fatal Error");
    }
    else
    {
      while (!doDeploy)
        TcpTools.delay(10);
      deploy();
    }
  }

  private String requestEntry(String prompt)
  {
    String entry = "";
    while (entry.length() < 3)
    {
      entry = JOptionPane.showInputDialog(null, prompt, "");
      if (entry == null)
        System.exit(0);
    }
    return entry.trim();
  }

  public void reset()
  {
    removeAllActors();

    fleet[0] = new Carrier();
    fleet[1] = new Battleship();
    fleet[2] = new Destroyer();
    fleet[3] = new Submarine();
    fleet[4] = new PatrolBoat();

    for (int i = 0; i < fleet.length; i++)
    {
      addActor(fleet[i], new Location(0, 2 * i));
      addMouseListener(fleet[i], GGMouse.lPress | GGMouse.lDrag | GGMouse.lRelease);
      addKeyListener(fleet[i]);
    }
  }

  private void deploy()
  {
    reset();
    status =
      new ModelessOptionPane(getPosition().x - 300, getPosition().y, msgDeployInfo,
      null, "Continue");
    status.addActionListener(this);
    setTitle(msgDeploy);
    Monitor.putSleep();  // Wait for dialog to be closed
    status.setVisible(false);
    for (Ship ship : fleet)
    {
      ship.show(0);
      ship.setMouseEnabled(false);
    }
    isDeployed = true;
    node.sendIntMessage(-1);  // Report 'I am deployed'
    if (isPartnerDeployed)
    {
      isMyMove = false;  // Start play
      setTitle(msgPartnerMove);
    }
    else
      setTitle(msgPartnerDeploy);
  }

  public void actionPerformed(ActionEvent evt)
  {
    Monitor.wakeUp();
  }

  public void messageReceived(String sender, String text)
  {
    int[] data = TcpTools.splitToIntAry(text, "&");
    if (data.length == 1)  // Enemy reports damage or readyness
    {
      if (data[0] == -1)   // Got report 'partner ready'
      {
        if (isDeployed)  // Start game
        {
          isMyMove = true;
          setTitle(msgMyMove);
        }
        else
          isPartnerDeployed = true;
      }
      else
      {
        markLocation(data[0]);
        if (!isOver)
          setTitle(msgPartnerMove);
      }
    }
    else  // Enemy fired, answer what kind of damage in your fleet
    {
      Location loc = new Location(data[0], data[1]);
      node.sendIntMessage(createReply(loc));
      if (!isOver)
      {
        isMyMove = true;
        setTitle(msgMyMove);
        setMouseEnabled(true);
      }
    }
  }

  public void nodeStateChanged(TcpNodeState state)
  {
    if (state == TcpNodeState.CONNECTED)
      Monitor.wakeUp();
  }

  public void statusReceived(String text)
  {
    if (text.indexOf("In session:--- (0)") != -1) // Logged-in
      setTitle(msgPartnerWait);
    else if (text.indexOf("In session:--- (1)") != -1 || // Partner present
      text.indexOf("New node:--- Battleship_Player(1)") != -1) // Partner logged-in
    {
      doDeploy = true;
    }
    else if (text.indexOf("In session:--- ") != -1)
    {
      setTitle(msgGameRunning);
      System.exit(0);
    }
    else if (text.indexOf("Disconnected:--- Battleship_Player") != -1
      || text.indexOf("Disconnected:--- Battleship_Player(1)") != -1)
    {
      setTitle("Partner disconnected");
      delay(4000);
      System.exit(0);
    }
  }

  public boolean mouseEvent(GGMouse mouse)
  {
    if (!isMyMove)
      return false;
    isMyMove = false;
    currentLoc = toLocationInGrid(mouse.getX(), mouse.getY());
    node.sendIntMessage(currentLoc.x, currentLoc.y);
    return false;
  }

  protected void markLocation(int k)
  {
    switch (k)
    {
      case 0: // miss
        addActor(new Actor("sprites/miss.gif"), currentLoc);
        break;
      case 1: // hit
        addActor(new Actor("sprites/hit.gif"), currentLoc);
        break;
      case 2:  // sunk
        addActor(new Actor("sprites/sunk.gif"), currentLoc);
        break;
      case 3: // allsunk
        isOver = true;
        removeAllActors();
        addActor(new Actor("sprites/gameover.gif"), new Location(5, 2));
        addActor(new Actor("sprites/winner.gif"), new Location(5, 6));
        setTitle("Game over. You won.");
        setMouseEnabled(false);
        break;
    }
  }

  private int createReply(Location loc)
  {
    for (Actor a : getActors(Ship.class))
    {
      Damage damage = ((Ship)a).hit(loc);
      switch (damage)
      {
        case HIT:
          return 1;
        case SUNK:
          return 2;
        case ALLSUNK:
          isOver = true;
          removeAllActors();
          addActor(new Actor("sprites/gameover.gif"), new Location(5, 2));
          addActor(new Actor("sprites/allsunk.gif"), new Location(5, 6));
          setTitle("Game over. You lost");
          return 3;
      }
    }
    // MISS
    addActor(new Water(), loc);
    return 0;
  }

  public static void main(String[] args)
  {
    new TcpBattleshipApp();
  }
}
