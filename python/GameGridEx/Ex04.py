# Ex04.py

from ch.aplu.jgamegrid import GameGrid, Actor, Location
from ch.aplu.util import X11Color


# --------------------- class Bear ---------------------
class Bear(Actor):
   def __init__(self):
      Actor.__init__(self, "sprites/bear.gif");
  
   def act(self):
      if self.isMoveValid():
         self.move()
      else:
         if self.isHorzMirror():
            self.setHorzMirror(False)
            self.turn(270)
            self.move()
            self.turn(270)
         else:
            self.setHorzMirror(True)
            self.turn(90)
            self.move()
            self.turn(90)
      self.tryToEat()
      
   def tryToEat(self):
      actor = gg.getOneActorAt(self.getLocation(), Leaf)
      if actor != None:
         actor.hide()
    
# --------------------- class Leaf ---------------------------
class Leaf(Actor):
   def __init__(self):
      Actor.__init__(self, "sprites/leaf.gif")

# ----------------- main -----------------------------
gg = GameGrid(10, 10, 60, X11Color("red"))
gg.addActor(Bear(), Location(0, 0))
for i in range(10):
   gg.addActor(Leaf(), gg.getRandomEmptyLocation())
gg.show()


