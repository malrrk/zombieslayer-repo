# Ex07c.py

from ch.aplu.jgamegrid import GameGrid, Actor, Location, GGKeyListener
from ch.aplu.util import X11Color
from java.awt.event import KeyEvent

# ---------------- class Angelfish ----------------
class Angelfish(Actor, GGKeyListener):
   def __init__(self):
      Actor.__init__(self, "sprites/angelfish.gif")
   
   def act(self):
      self.move()
      if self.getX() < 0 or self.getX() > 599:
         self.turn(180)
         self.setHorzMirror(not self.isHorzMirror())

   def keyPressed(self, evt):
      keyCode = evt.getKeyCode()
      if keyCode == KeyEvent.VK_UP and self.getY() > 0:
            self.setY(self.getY() - 1)
      if keyCode == KeyEvent.VK_DOWN and self.getY() < 599:
         self.setY(self.getY() + 1)

# ----------------- class MyKeyListener --------------
class MyKeyListener(GGKeyListener):
   def keyPressed(self, evt):
      if evt.getKeyCode() == KeyEvent.VK_SPACE:
         fish = Angelfish()
         gg.addActor(fish, Location(300, 300))
         gg.addKeyListener(fish)
         return False  # Don't consume

      
# --------------------- main ---------------------------------
gg = GameGrid(600, 600, 1, None, "sprites/reef.gif", False)
gg.setSimulationPeriod(50)
gg.setTitle("Space bar for new object, cursor to move up/down")
gg.addKeyListener(MyKeyListener())
gg.show()
gg.doRun()

 
