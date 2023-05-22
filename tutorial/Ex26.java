// Ex26.java

/*
 Implemented algorithm see
 
 http://en.wikipedia.org/wiki/Flood_fill

 Flood-fill (node, target-color, replacement-color):
 1. If the color of node is not equal to target-color, return.
 2. Set the color of node to replacement-color.
 3. Perform Flood-fill (one step to the east of node, target-color, replacement-color).
 Perform Flood-fill (one step to the south of node, target-color, replacement-color).
 Perform Flood-fill (one step to the west of node, target-color, replacement-color).
 Perform Flood-fill (one step to the north of node, target-color, replacement-color).
 4. Return.
 */
import ch.aplu.jgamegrid.*;
import java.awt.Color;
import ch.aplu.util.Monitor;

public class Ex26 extends GameGrid
  implements GGMouseListener
{
  private final Color borderColor = Color.red;
  private final Color oldColor = Color.black;
  private final Color newColor = Color.green;
  private Location startLocation;
  private GGBackground bg;

  public Ex26()
  {
    super(10, 10, 60, Color.darkGray, false);
    setTitle("Drag to create a closed region. Click inside to fill.");
    bg = getBg();
    addMouseListener(this, GGMouse.lDrag | GGMouse.lClick);
    show();
    Monitor.putSleep();
    floodFill(startLocation, oldColor, newColor);
  }

  private void floodFill(Location cell, Color oldColor, Color newColor)
  {
    if (!bg.getColor(cell).equals(oldColor))
      return;
    bg.fillCell(cell, newColor);
    refresh();
    delay(300);
    for (int i = 0; i < 4; i++)
    {
      Location loc = cell.getNeighbourLocation(i * 90);
      if (isInGrid(loc))
        floodFill(loc, oldColor, newColor);
    }
  }

  public boolean mouseEvent(GGMouse mouse)
  {
    Location loc = toLocationInGrid(mouse.getX(), mouse.getY());
    switch (mouse.getEvent())
    {
      case GGMouse.lDrag:
        bg.fillCell(loc, borderColor);
        refresh();
        break;

      case GGMouse.lClick:
        startLocation = loc;
        setMouseEnabled(false);
        Monitor.wakeUp();
        break;
    }
    return true;
  }

  public static void main(String[] args)
  {
    new Ex26();
  }

}
