// Ex24.java
// Dynamically created chips

import ch.aplu.jgamegrid.*;
import java.awt.*;

public class Ex24 extends GameGrid implements GGMouseTouchListener
{
  private Point hotSpot;
  private Location startLocation;
  private final Location stackLocation = new Location(0, 0);
  private int nbMoves = 0;

  public Ex24()
  {
    super(2, 2, 80, Color.red, false);
    setTitle("Color Sorter");
    setBgColor(Color.gray);
    addStatusBar(25);
    setStatusText("Drag chips to labeled box!"); 

    TextActor[] labels =
    {
      new TextActor("Red"), new TextActor("Yellow"),
      new TextActor("Green"), new TextActor("Blue")
    };

    for (int x = 0; x < 2; x++)
    {
      for (int y = 0; y < 2; y++)
      {
        int index = 2 * x + y;
        addActor(labels[index], new Location(x, y));
        labels[index].setLocationOffset(new Point(-15, -30));
      }
    }

    for (int i = 0; i < 10; i++)
    {
      int colorId = (int)(4 * Math.random());
      ChipActor chip = new ChipActor(getColor(colorId));
      addActor(chip, stackLocation);
      chip.addMouseTouchListener(this,
        GGMouse.lPress | GGMouse.lDrag | GGMouse.lRelease, true);
    }
    show();
  }

  public void mouseTouched(Actor actor, GGMouse mouse, Point spot)
  {
    switch (mouse.getEvent())
    {
      case GGMouse.lPress:
        startLocation = toLocation(mouse.getX(), mouse.getY());
        actor.setOnTop();
        hotSpot = spot;
        break;
      case GGMouse.lDrag:
        Point imageCenter =
          new Point(mouse.getX() - hotSpot.x, mouse.getY() - hotSpot.y);
        actor.setPixelLocation(imageCenter);
        refresh();
        break;
      case GGMouse.lRelease:
        if (spot.x == -1)  // Cursor is outside image
          actor.setLocation(startLocation);
        else
        {
          actor.setPixelLocation(new Point(mouse.getX(), mouse.getY()));
          nbMoves++;
          if (isOver())
            setStatusText("Total #: " + nbMoves + ". Done.");
          else
            setStatusText("#: " + nbMoves);
        }
        actor.setLocationOffset(new Point(0, 0));
        hotSpot = null;
        refresh();
        break;
    }
  }

  private Color getColor(int colorId)
  {
    switch (colorId)
    {
      case 0:  // Red
        return new Color(255, 0, 0);
      case 1:  // Yellow
        return new Color(255, 255, 0);
      case 2: // Green
        return new Color(0, 150, 0);
      case 3:  // Blue
        return new Color(0, 180, 255);
    }
    return Color.black;
  }

  private boolean isOver()
  {
    for (int x = 0; x < 2; x++)
    {
      for (int y = 0; y < 2; y++)
      {
        int index = 2 * x + y;
        for (Actor actor : getActorsAt(new Location(x, y), ChipActor.class))
        {
          ChipActor chip = (ChipActor)actor;
          if (chip.getColor().getRGB() != getColor(index).getRGB())
            return false;
        }
      }
    }
    return true;
  }

  public static void main(String[] args)
  {
    new Ex24();
  }
}
