// LightsOut.java
// Solving algorithm from David Guichard, Whitman College, USA
// with all my appreciation

import ch.aplu.jgamegrid.*;
import ch.aplu.util.*;
import java.util.*;
import java.awt.*;

public class LightsOut extends GameGrid implements
  GGMouseListener, GGButtonListener, GGToggleButtonListener,
  Cleanable, GGWindowStateListener
{
  private final String info =
    "Welcome to Lights Out!\n" +
    "The game consists of a 5 by 5 grid of lights; when the game starts,\n" +
    "a set of these lights are switched on. Clicking one of the lights will\n" +
    "toggle it and the four lights adjacent to it on and off.\n\n" +
    "The aim is to switch all the lights off, preferably in as few mouse\n" +
    "clicks as possible.\n\n" +
    "Button action:\n" +
    "- Restart: Generates a random winnable pattern\n" +
    "- Solve: Shows a possible solution marked by stars\n" +
    "- Select: User selectable pattern. Click lamps and release button\n" +
    "- Help: Shows this information panel\n\n" +
    "Lights Out was invented 1989 by a group of people consisting of\n" +
    "Avi Olti, Gyora Benedek, Zvi Herman, Revital Bloomberg,\n" +
    "Avi Weiner and Michael Ganor.\n\n" +
    "This Java version was developed by Aegidius Pluess using the JGameGrid\n" +
    "framework (www.aplu.ch/jgamegrid). The game solving code implements\n" +
    "a smart algorithm from David Guichard, Whitman College, USA.\n" +
    "With thanks to the author.";
  private ModelessOptionPane infoPane = null;
  private GameGrid buttonBox = new GameGrid(120, 200, 1, null, null, false, true);
  private GGButton restartBtn = new GGButton("sprites/restartbutton.gif", true);
  private GGButton solveBtn = new GGButton("sprites/solvebutton.gif", true);
  private GGToggleButton selectBtn = new GGToggleButton("sprites/selectbutton.gif", true);
  private GGButton helpBtn = new GGButton("sprites/helpbutton.gif", true);
  private Actor stateText = new Actor("sprites/stateText.gif", 3);
  private boolean[][] board = new boolean[5][5];
  private boolean isSelecting = false;
  //
  // The hint matrix was computed in Maple; multiplying it by
  // a vector representing the current board gives a vector
  // representing the moves necessary to produce the current
  // board; since everything is done mod 2, this also gives the
  // moves necessary to solveBtn the game.
  private int[][] hint_matrix =
  {
    {
      0, 1, 1, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0
    },
    {
      1, 1, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0
    },
    {
      1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0
    },
    {
      1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1
    },
    {
      0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 1, 1, 0
    },
    {
      0, 0, 1, 0, 1, 0, 1, 1, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0
    },
    {
      0, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 0, 0, 1, 0, 1, 1, 1, 0, 0, 0, 1, 0
    },
    {
      1, 0, 1, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 1, 1, 0, 1
    },
    {
      0, 0, 1, 0, 0, 0, 1, 1, 1, 0, 1, 0, 0, 1, 1, 1, 0, 0, 1, 0, 0, 1, 1
    },
    {
      1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 1, 0, 0, 1
    },
    {
      0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 1, 1, 1, 1, 1, 0, 0, 1, 0
    },
    {
      0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1
    },
    {
      0, 1, 1, 0, 1, 1, 0, 0, 0, 1, 1, 0, 1, 1, 0, 0, 0, 1, 0, 0, 1, 1, 0
    },
    {
      1, 1, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0
    },
    {
      1, 1, 0, 0, 1, 0, 0, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 0, 1, 0, 1, 0, 0
    },
    {
      0, 0, 1, 0, 0, 0, 1, 1, 1, 0, 1, 0, 0, 0, 1, 1, 0, 1, 0, 1, 1, 0, 1
    },
    {
      0, 0, 1, 1, 0, 0, 1, 0, 0, 1, 1, 1, 0, 0, 1, 0, 1, 1, 1, 0, 0, 0, 1
    },
    {
      0, 0, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 0, 1, 1, 0, 1, 1, 1, 0, 0
    },
    {
      0, 1, 1, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 1, 1, 0, 1, 1, 1, 0, 0, 0, 1
    },
    {
      1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 1, 0, 1
    },
    {
      0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1, 1, 1, 0
    },
    {
      0, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1
    },
    {
      0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0, 1, 0, 0, 0, 1, 1, 0, 1, 1, 0, 1, 0
    },
  };
  // These magic vectors allow us to find a shortest possible
  // solution.
  private int[] n1 =
  {
    1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1
  };
  private int[] n2 =
  {
    1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1
  };

  public LightsOut()
  {
    super(5, 5, 50, null, false);
    setTitle("LightsOut (JGameGrid)");
    for (int i = 0; i < 5; i++)
    {
      for (int k = 0; k < 5; k++)
      {
        Actor actor = new Actor("sprites/lightout.gif", 2);
        addActor(actor, new Location(i, k));
        board[i][k] = false;
      }
    }
    addMouseListener(this, GGMouse.lPress);
    addWindowStateListener(this);
    initButtonBox();
    show();
  }

  private void initButtonBox()
  {
    buttonBox.setBgColor(Color.gray);
    buttonBox.getBg().drawRectangle(new Point(0, 0), new Point(120, 200));
    buttonBox.addActor(restartBtn, new Location(60, 30));
    restartBtn.addButtonListener(this);
    buttonBox.addActor(solveBtn, new Location(60, 70));
    solveBtn.addButtonListener(this);
    buttonBox.addActor(selectBtn, new Location(60, 110));
    selectBtn.addToggleButtonListener(this);
    buttonBox.addActor(helpBtn, new Location(60, 150));
    helpBtn.addButtonListener(this);
    buttonBox.addActor(stateText, new Location(60, 180));
    stateText.show(1);
    buttonBox.setPosition(getPosition().x + 300, getPosition().y);
    buttonBox.show();
  }

  public boolean mouseEvent(GGMouse mouse)
  {
    if (isSelecting)
    {
      Location location = toLocationInGrid(mouse.getX(), mouse.getY());
      Actor a = getOneActorAt(location);
      a.showNextSprite();
      toggleBox(location.x, location.y);
      if (isSolvable())
        stateText.show(1);
      else
        stateText.show(0);
      buttonBox.refresh();
    }
    else
    {
      solveBtn.setEnabled(false);
      stateText.hide();
      buttonBox.refresh();
      Location location = toLocationInGrid(mouse.getX(), mouse.getY());
      ArrayList<Location> locations = new ArrayList<Location>();
      locations.add(new Location(location.x, location.y));
      locations.add(new Location(location.x, location.y - 1));
      locations.add(new Location(location.x, location.y + 1));
      locations.add(new Location(location.x - 1, location.y));
      locations.add(new Location(location.x + 1, location.y));
      for (Location loc : locations)
      {
        Actor a = getOneActorAt(loc);
        if (a != null)
          a.showNextSprite();
      }
      Actor a = getOneActorAt(location, Select.class);
      if (a != null)
        a.removeSelf();
    }
    refresh();
    if (isDone())
    {
      stateText.show(2);
      buttonBox.refresh();
    }
    return true;
  }

  private boolean isDone()
  {
    for (int i = 0; i < 5; i++)
      for (int k = 0; k < 5; k++)
        if (getOneActorAt(new Location(i, k)).getIdVisible() == 0)
          return false;
    return true;
  }

  private void startGame(boolean initRandom)
  {
    int i, k;
    for (i = 0; i < 5; i++)
    {
      for (k = 0; k < 5; k++)
      {
        board[i][k] = true;
        Actor a = getOneActorAt(new Location(i, k), Select.class);
        if (a != null)
          a.removeSelf();
        a = getOneActorAt(new Location(i, k));
        a.show(1);
      }
    }
    if (initRandom)
    {
      solveBtn.setEnabled(true);
      stateText.show(1);
      buttonBox.refresh();
      for (i = 0; i < 23; i++)
      {
        if (rand01())
          doMove(i % 5, i / 5);
      }
    }
    else
      refresh();
  }

  private boolean rand01()
  {
    if (Math.random() > 0.5)
      return true;
    else
      return false;
  }

  private void doMove(int i, int k)
  {
    if (0 <= i && i <= 4 && 0 <= k && k <= 4)
    {
      toggleBox(i, k);
      toggleBox(i + 1, k);
      toggleBox(i - 1, k);
      toggleBox(i, k + 1);
      toggleBox(i, k - 1);
    }
  }

  private void toggleBox(int i, int k)
  {
    if (0 <= i && i <= 4 && 0 <= k && k <= 4)
      if (board[i][k] = !board[i][k])
        getOneActorAt(new Location(i, k)).show(1);
      else
        getOneActorAt(new Location(i, k)).show(0);
    refresh();
  }

  private boolean isSolvable()
  {
    int[] current_state = new int[25];
    int i, dotprod;

    for (i = 0; i < 25; i++)
    {
      if (board[i % 5][i / 5])
      {
        current_state[i] = 0;
      }
      else
      {
        current_state[i] = 1;
      }
    }

    dotprod = 0;
    for (i = 0; i < 25; i++)
    {
      dotprod = (dotprod + current_state[i] * n1[i]) % 2;
    }

    if (dotprod != 0)
    {
      return false;
    }

    dotprod = 0;
    for (i = 0; i < 25; i++)
    {
      dotprod = (dotprod + current_state[i] * n2[i]) % 2;
    }

    if (dotprod != 0)
    {
      return false;
    }
    else
    {
      return true;
    }
  }

  private void hint()
  {
    int[] current_state = new int[23];
    int[] hint_vector = new int[25];
    int[] best = new int[25];
    int[] next = new int[25];
    int i, j;

    for (i = 0; i < 25; i++)
    {
      hint_vector[i] = 0;
    }
    for (i = 0; i < 23; i++)
    {
      if (board[i % 5][i / 5])
      {
        current_state[i] = 0;
      }
      else
      {
        current_state[i] = 1;
      }
    }
    for (i = 0; i < 23; i++)
    {
      for (j = 0; j < 23; j++)
      {
        hint_vector[i] =
          (hint_vector[i] + current_state[j] * hint_matrix[i][j]) % 2;
      }
    }

    // Now we have a working hint vector, but we test h+n1, h+n2
    // and h+n1+n2 to see which has the lowest weight, giving a
    // shortest solution.

    acpy(hint_vector, best);
    acpy(hint_vector, next);
    addto(next, n1);
    if (wt(next) < wt(best))
    {
      acpy(next, best);
    }
    acpy(hint_vector, next);
    addto(next, n2);
    if (wt(next) < wt(best))
    {
      acpy(next, best);
    }
    acpy(hint_vector, next);
    addto(next, n1);
    addto(next, n2);
    if (wt(next) < wt(best))
    {
      acpy(next, best);
    }

    for (i = 0; i < 25; i++)
    {
      if (best[i] == 1)
      {
        addActor(new Select(), new Location(i % 5, i / 5));
      }
      else
      {
        Actor a = getOneActorAt(new Location(i % 5, i / 5), Select.class);
        if (a != null)
          a.removeSelf();
      }
    }
    refresh();
  }

  private void acpy(int[] src, int[] dest)
  {
    int i;
    for (i = 0; i < src.length; i++)
    {
      dest[i] = src[i];
    }
  }

  private void addto(int[] src, int[] v)
  {
    int i;
    for (i = 0; i < src.length; i++)
    {
      src[i] = (src[i] + v[i]) % 2;
    }
  }

  private int wt(int[] v)
  {
    int i, t = 0;
    for (i = 0; i < v.length; i++)
    {
      t = t + v[i];
    }
    return t;
  }

  public void buttonPressed(GGButton button)
  {
  }

  public void buttonReleased(GGButton button)
  {
  }

  public void buttonClicked(GGButton button)
  {
    if (button == restartBtn)
      startGame(true);
    if (button == solveBtn)
      hint();
    if (button == helpBtn)
    {
      if (infoPane == null)
      {
        infoPane = new ModelessOptionPane(20, 20, info, null);
        infoPane.addCleanable(this);
      }
      else
        infoPane.setVisible(true);
    }
  }

  public void clean()
  {
    infoPane.setVisible(false);
  }

  public void buttonToggled(GGToggleButton toggleButton, boolean toggled)
  {
    if (toggled)
    {
      isSelecting = true;
      restartBtn.setEnabled(false);
      solveBtn.setEnabled(false);
      startGame(false);
      stateText.hide();
      buttonBox.refresh();
    }
    else
    {
      isSelecting = false;
      restartBtn.setEnabled(true);
      if (isSolvable())
        solveBtn.setEnabled(true);
    }
  }

  public void windowMoved(int x, int y)
  {
    buttonBox.setPosition(x + 300, y);
  }

  public void windowIconified()
  {
    buttonBox.hide();
  }

  public void windowDeiconified()
  {
    buttonBox.show();
  }

  public void windowActivated()
  {
    buttonBox.show();
  }

  public void windowDeactivated()
  {
  }

  public static void main(String[] args)
  {
    new LightsOut();
  }
}
