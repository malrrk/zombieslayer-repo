// BtPeerEx1.java

import ch.aplu.jgamegrid.*;
import java.awt.Color;
import ch.aplu.bluetooth.*;
import java.io.*;
import java.util.Properties;
import javax.bluetooth.RemoteDevice;
import javax.swing.JOptionPane;


public class BtPeerEx1 extends GameGrid
  implements GGMouseListener, BtPeerListener, GGExitListener
{
  private enum State
  {
    READY, GAME_OVER, CONNECTION_LOST
  }

  private interface Command
  {
    int all_sunk = -1;
    int sunk = -2;
    int disconnect = -3;
  }

  private final boolean isVerbose = true;
  private final String serviceName = "BtPeerEx1";
  private BluetoothPeer bp;
  private volatile State state = State.READY;
  private final String msgMyShoot = "Click a cell to fire";
  private final String msgEnemyShoot = "Please wait enemy bomb";
  private boolean isWinner;
  private Location touchLocation;
  private final String userHome = System.getProperty("user.home");
  private final String fs = System.getProperty("file.separator");
  private String propPath = userHome + fs + "BtPeerEx1.properties";
  private File propFile = new File(propPath);
  private Properties prop = new Properties();

  public BtPeerEx1()
  {
    super(10, 1, 40, Color.red, false);
    show();
    addMouseListener(this, GGMouse.lClick);
    addExitListener(this);
    addStatusBar(30);
    deploy();
    connect();
    while (true)
    {
      if (state == State.GAME_OVER)
      {
        deploy();
        if (isWinner)
        {
          setStatusText("You won! " + msgEnemyShoot);
          setMouseEnabled(false);
        }
        else
        {
          setStatusText("You lost! " + msgMyShoot);
          setMouseEnabled(true);
        }
        state = State.READY;
      }
      delay(500);
    }
  }

  private void deploy()
  {
    removeAllActors();
    for (int i = 0; i < 3; i++)
      addActor(new Ship(), getRandomEmptyLocation());
  }

  public boolean mouseEvent(GGMouse mouse)
  {
    setMouseEnabled(false);
    touchLocation = toLocationInGrid(mouse.getX(), mouse.getY());
    addActor(new Actor("sprites/marker.gif"), touchLocation);
    int[] data = new int[1];
    data[0] = touchLocation.x; // cell coordinate
    bp.sendDataBlock(data);
    setStatusText(msgEnemyShoot);
    return false;
  }

  private void connect()
  {
    setStatusText("BtBattleShip");
    Properties prop = loadProperties();
    String btName = "";
    if (prop != null)
    {
      btName = prop.getProperty("BluetoothName");
      if (btName == null)
        btName = "";
      else
        btName = btName.trim();
    }
    String partnerName = askName(btName);
    setProperty("BluetoothName", partnerName);
    setStatusText("Searching for partner name '" + partnerName + "' ...");

    // Try to get device from paired devices database
    RemoteDevice rd = BluetoothFinder.searchPreknownDevice(partnerName);
    if (rd == null)
    {
      System.out.println("Sorry. Device '" + partnerName + "' not paired.");
      System.out.println("Please perform Bluetooth device pairing!");
      setStatusText("Device '" + partnerName + "' not paired. Perform pairing");
      return;
    }
    else
    {
      System.out.println("Paired device found. Searching service now...");
      setStatusText("Paired device found. Searching service now...");
    }
    setStatusText("Trying to connect to '" + partnerName + "' ...");
    bp = new BluetoothPeer(partnerName, serviceName, this, isVerbose);
    if (bp.isConnected())
    {
      setStatusText("Connection established. " + msgMyShoot);
      setMouseEnabled(true);
    }
    else
      setStatusText("'" + partnerName + "' not found. Waiting as server '"
        + BluetoothFinder.getLocalBluetoothName() + "' ...");
  }

  public void notifyConnection(boolean connected)
  {
    if (connected)
    {
      setStatusText("Connection established. Wait for enemy's shoot");
    }
    else
    {
      setStatusText("Connection lost. Press [HOME] and restart the app.");
      state = State.CONNECTION_LOST;
      setMouseEnabled(false);
    }
  }

  // Signal to server that we quit
  public boolean notifyExit()
  {
    // Because closing the Bluetooth socket is not
    // detected by every partner, we send an 'disconnect' command
    if (bp != null && bp.isConnected())
    {
      sendCommand(Command.disconnect);
      bp.releaseConnection();
      state = State.CONNECTION_LOST;
    }
    return true;
  }

  public void receiveDataBlock(int[] data)
  {
    int x = data[0];
    // Command dispatcher
    switch (data[0])
    {
      case Command.disconnect:
        notifyConnection(false);
        return;
      case Command.all_sunk:
        state = State.GAME_OVER;
        isWinner = true;
        return;
      case Command.sunk:
        addActor(new Actor("sprites/hit.gif"), touchLocation);
        return;

      default: // Got 'location' command
        Actor actor = getOneActorAt(new Location(x, 0), Ship.class);
      {
        if (actor != null)
        {
          actor.removeSelf();
          refresh();
          sendCommand(Command.sunk);
        }
      }
      break;
    }

    if (getNumberOfActors(Ship.class) == 0)
    {
      sendCommand(Command.all_sunk);
      state = State.GAME_OVER;
      isWinner = false;
      return;
    }

    setStatusText(msgMyShoot);
    setMouseEnabled(true);
  }

  private void sendCommand(int command)
  {
    int[] data = new int[1];
    data[0] = command;
    bp.sendDataBlock(data);
  }

  private String askName(String btName)
  {
    setStatusText("Select partner's Bluetooth name");
    String prompt = "Enter Partner's Bluetooth Name";
    String serverName;
    do
    {
      serverName = JOptionPane.showInputDialog(null, prompt, btName);
      if (serverName == null)
        System.exit(0);
    }
    while (serverName.trim().length() == 0);
    serverName = serverName.trim();
    return serverName;
  }

  private Properties loadProperties()
  {
    // Return null, if error
    if (!propFile.exists())
    {
      try
      {
        propFile.createNewFile();
      }
      catch (IOException ex)
      {
        return null;
      }
    }
    FileInputStream fis = null;
    try
    {
      fis = new FileInputStream(propFile);
      prop.load(fis);
    }
    catch (IOException ex)
    {
      return null;
    }
    finally
    {
      try
      {
        fis.close();
      }
      catch (Exception ex)
      {
      }
    }
    return prop;
  }

  private void setProperty(String key, String value)
  {
    if (prop == null)
      return;
    prop.setProperty(key, value);
    try
    {
      FileOutputStream fos = new FileOutputStream(propFile);
      prop.store(fos, null);
      fos.close();
    }
    catch (IOException ex)
    {
      System.out.println("Can't set property " + key);
    }
  }

  public static void main(String[] args)
  {
    new BtPeerEx1();
  }

}

// ------------------ Class Ship ------------------
class Ship extends Actor
{
  public Ship()
  {
    super("sprites/ship.gif");
  }

}
