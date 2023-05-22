# GameGridEx1.py
from gamegrid import *                         

# ---------------- class Fish ----------------
class Fish(Actor):
   def __init__(self):
      Actor.__init__(self, "sprites/nemo.gif")
   
   def act(self):
      self.move()
      if not self.isMoveValid():
         self.turn(180)
 
# ---------------- main ----------------------
makeGameGrid(10, 10, 60, makeColor("red"), "sprites/reef.gif")
nemo = Fish()
addActor(nemo, Location(2, 4))
show()

