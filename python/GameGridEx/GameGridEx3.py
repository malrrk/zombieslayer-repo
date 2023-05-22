# GameGridEx3.py
from gamegrid import *                         

# ---------------- class Fish ----------------
class Fish(Actor):
   def __init__(self):
      Actor.__init__(self, "sprites/nemo.gif");
    
   def act(self):
      self.move()
      if not self.isMoveValid():
         self.turn(180)
         self.setHorzMirror(not self.isHorzMirror())
         
# ---------------- main ----------------------
def onMousePressed(mouse):
   loc = toLocationInGrid(mouse.getX(), mouse.getY())
   addActorNoRefresh(Fish(), loc)
makeGameGrid(10, 10, 60, makeColor("red"), 
   "sprites/reef.gif", False, 
    mousePressed = onMousePressed)
show()
doRun()

