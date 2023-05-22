// BtServerSE.java
// Data exchange with BtClientApp or BtClientSE

import java.io.*;
import javax.bluetooth.*;
import ch.aplu.bluetooth.*;
import ch.aplu.util.Console;
import ch.aplu.util.ExitListener;
import ch.aplu.util.Monitor;

public class BtServerSE implements BtListener, ExitListener
{
  private class ReceiverThread extends Thread
  {
    private boolean isRunning;

    public void run()
    {
      isRunning = true;
      while (isRunning)
      {
        try
        {
          int data = dis.readInt();
          c.println("Got data: " + data);
          if (data == disconnectTag)
          {
            c.println("Got disconnect notification");
            isRunning = false;
            continue;
          }
          // Echo inverse
          dos.writeInt(-data);
          dos.flush();
        }
        catch (IOException ex)
        {
          c.println("IOExeption in blocking readInt()");
          isRunning = false;
        }
      }
      c.println("Receiver thread terminated");
      Monitor.wakeUp(); // Wake-up sleeping notifiyConnection()
    }

  }

  //
  private final String serviceName = "BtServer";
  private final int disconnectTag = 9999;
  private BluetoothServer bs;
  private DataInputStream dis;
  private DataOutputStream dos;
  private int nbConnections = 0;
  private boolean isClientConnected = false;
  private Console c;

  public BtServerSE()
  {
    c = Console.init();
    c.addExitListener(this);
    c.println("Creating server with Bluetooth name '"
      + BluetoothFinder.getLocalBluetoothName() + "'");
    bs = new BluetoothServer(serviceName, this, true);
    c.println("Waiting for a Bluetooth client to connect ");
    c.println("exposing service '" + serviceName + "'");
  }

  public void notifyConnection(RemoteDevice rd, InputStream is, OutputStream os)
  {
    c.println("notifyClientConnection() starting");
    nbConnections++;
    try
    {
      c.println("Client '" + rd.getFriendlyName(false) + "' connected as # " + nbConnections);
    }
    catch (IOException ex)
    {
      c.println("Got exception while retrieving client info");
      return;
    }
    isClientConnected = true;
    c.println("Retrieving input and output streams");
    dis = new DataInputStream(is);
    dos = new DataOutputStream(os);
    new ReceiverThread().start();
    c.println("Starting receiver thread");
    c.println("notifyClientConnection() blocking now");
    Monitor.putSleep();  // Thread goes to sleep
    c.println("notifyClientConnection() wakes up");
    c.println("Lost client connection. Server will close streams and socket");
    c.println("Waiting for next client...");
    isClientConnected = false;
  }

  // Callback when close button is hit
  public void notifyExit()
  {
    if (isClientConnected)
      sendDisconnectTag();
    bs.close();  // Announce termination to client
    bs.cancel(); // Shut down waiting server
    System.exit(0);
  }

  private void sendDisconnectTag()
  {
    try
    {
      dos.writeInt(disconnectTag);  // Disconnect notification
      dos.flush();
    }
    catch (IOException ex)
    {
    }
  }

  public static void main(String[] args)
  {
    new BtServerSE();
  }

}
