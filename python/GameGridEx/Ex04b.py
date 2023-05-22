# Ex04a.py

from ch.aplu.jgamegrid import GameGrid, Actor, Location, GGButton, GGButtonListener
from ch.aplu.util import X11Color
import random

# --------------------- class Hamster ---------------------
class Hamster(Actor):
   def __init__(self):
      Actor.__init__(self, "sprites/hamster.gif");
  
   def act(self):
      hazelnut = gg.getOneActorAt(self.getLocation(), Hazelnut)
      if hazelnut != None:
         hazelnut.removeSelf()

      # Try to turn +-90 degrees each 5 periods
      if self.nbCycles % 5 == 0:
         if random.random() < 0.5:
            self.turn(90)
         else:
            self.turn(-90)

      # If new location is valid, move to it
      if self.canMove():
         self.move()
      # if not, turn 90, 180 or 270 degrees until a valid location is found
      else:
         for i in range(1, 5):
            self.turn(i * 90)
            if self.canMove():
               break
   
   def canMove(self):
      if self.isMoveValid() and gg.getOneActorAt(self.getNextMoveLocation(), Rock) == None:
         return True  # Inside grid and no rock
      return False
    
# --------------------- class Rock ---------------------------
class Rock(Actor):
   def __init__(self):
      Actor.__init__(self, "sprites/rock.gif")

# --------------------- class Hazelnut -----------------------
class Hazelnut(Actor):
   def __init__(self):
      Actor.__init__(self, "sprites/hazelnut.gif")

# --------------------- class MyButtonListener ----------------
class MyButtonListener(GGButtonListener):
   def buttonClicked(self, button):
      gg.dispose()

# ----------------- main -----------------------------
exitButton = GGButton("sprites/ggExitButtonA.gif")
gg = GameGrid(10, 10, 50, X11Color("green"), None, False, True)  # undecorated
gg.setBgColor(X11Color("darkGray"))
gg.addActor(exitButton, Location(9, 9))
exitButton.addButtonListener(MyButtonListener());
for i in range(10):
  gg.addActor(Rock(), gg.getRandomEmptyLocation())
for i in range(20):
  gg.addActor(Hazelnut(), gg.getRandomEmptyLocation())
gg.addActor(Hamster(), gg.getRandomEmptyLocation())
gg.show()
gg.doRun()


