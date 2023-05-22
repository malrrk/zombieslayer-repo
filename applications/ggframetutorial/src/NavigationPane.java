// NavigationPane.java

import ch.aplu.jgamegrid.*;
import java.awt.*;
import ch.aplu.util.*;

class NavigationPane extends GameGrid
  implements GGButtonListener, GGCheckButtonListener
{
  private class SimulatedPlayer extends Thread
  {
    public void run()
    {
      while (true)
      {
        Monitor.putSleep();
        handBtn.show(1);
        roll();
        delay(200);
        handBtn.show(0);
      }
    }

  }

  private final Location handBtnLocation = new Location(110, 170);
  private final Location dieBoardLocation = new Location(100, 280);
  private final Location pipsLocation = new Location(70, 330);
  private final Location statusLocation = new Location(20, 390);
  private final Location statusDisplayLocation = new Location(100, 390);
  private final Location scoreLocation = new Location(20, 500);
  private final Location scoreDisplayLocation = new Location(100, 500);
  private final Location resultLocation = new Location(20, 565);
  private final Location resultDisplayLocation = new Location(100, 565);
  private GamePane gp;
  private GGButton handBtn = new GGButton("sprites/handx.gif");
  private GGTextField pipsField;
  private GGTextField statusField;
  private GGTextField resultField;
  private GGTextField scoreField;
  private GGCheckButton autoChk =
    new GGCheckButton("Auto Run", YELLOW, TRANSPARENT, false);
  private final Location autoChkLocation = new Location(15, 445);
  private int nbRolls = 0;
  private volatile boolean isGameOver = false;
  private boolean isAuto = false;

  NavigationPane()
  {
    setSimulationPeriod(200);
    setBgImagePath("sprites/navigationpane.png");
    setCellSize(1);
    setNbHorzCells(200);
    setNbVertCells(600);
    doRun();
    new SimulatedPlayer().start();
  }

  void setGamePane(GamePane gp)
  {
    this.gp = gp;
  }

  void createGui()
  {
    addActor(new Actor("sprites/dieboard.gif"), dieBoardLocation);

    handBtn.addButtonListener(this);
    addActor(handBtn, handBtnLocation);
    addActor(autoChk, autoChkLocation);
    autoChk.addCheckButtonListener(this);

    pipsField = new GGTextField(this, "", pipsLocation, false);
    pipsField.setFont(new Font("Arial", Font.PLAIN, 16));
    pipsField.setTextColor(YELLOW);
    pipsField.show();

    addActor(new Actor("sprites/linedisplay.gif"), statusDisplayLocation);
    statusField = new GGTextField(this, "Click the hand!", statusLocation, false);
    statusField.setFont(new Font("Arial", Font.PLAIN, 16));
    statusField.setTextColor(YELLOW);
    statusField.show();

    addActor(new Actor("sprites/linedisplay.gif"), scoreDisplayLocation);
    scoreField = new GGTextField(this, "# Rolls: 0", scoreLocation, false);
    scoreField.setFont(new Font("Arial", Font.PLAIN, 16));
    scoreField.setTextColor(YELLOW);
    scoreField.show();

    addActor(new Actor("sprites/linedisplay.gif"), resultDisplayLocation);
    resultField = new GGTextField(this, "Current pos: 0", resultLocation, false);
    resultField.setFont(new Font("Arial", Font.PLAIN, 16));
    resultField.setTextColor(YELLOW);
    resultField.show();
  }

  void showPips(String text)
  {
    pipsField.setText(text);
  }

  void showStatus(String text)
  {
    statusField.setText(text);
  }

  void showScore(String text)
  {
    scoreField.setText(text);
  }

  void showResult(String text)
  {
    resultField.setText(text);
  }

  void prepareRoll(int currentIndex)
  {
    if (currentIndex == 100)  // Game over
    {
      playSound(GGSound.FADE);
      showStatus("Click the hand!");
      showResult("Game over");
      isGameOver = true;
      handBtn.setEnabled(true);
    }
    else
    {
      playSound(GGSound.CLICK);
      showStatus("Done. Click the hand!");
      showResult("Current pos: " + currentIndex);
      if (isAuto)
        Monitor.wakeUp();
      else
        handBtn.setEnabled(true);
    }
  }

  void startMoving(int nb)
  {
    showStatus("Moving...");
    showPips("Pips: " + nb);
    showScore("# Rolls: " + (++nbRolls));
    gp.getPuppet().go(nb);
  }

  public void buttonClicked(GGButton btn)
  {
    handBtn.setEnabled(false);
    if (isGameOver)  // First click after game over
    {
      isGameOver = false;
      nbRolls = 0;
    }
    roll();
  }

  private void roll()
  {
    showStatus("Rolling...");
    showPips("");
    int nb = (int)(6 * Math.random() + 1);
    removeActors(Die.class);
    Die die = new Die(nb, this);
    addActor(die, dieBoardLocation);
  }

  public void buttonPressed(GGButton btn)
  {
  }

  public void buttonReleased(GGButton btn)
  {
  }

  public void buttonChecked(GGCheckButton button, boolean checked)
  {
    isAuto = checked;
    if (isAuto)
      Monitor.wakeUp();
  }

}
