# Ex07.py

from ch.aplu.jgamegrid import GameGrid, Actor, Location, GGActListener
from ch.aplu.util import X11Color
from java.awt.event import KeyEvent

# ---------------- class Clownfish ----------------
class Clownfish(Actor):
   def __init__(self):
      Actor.__init__(self, "sprites/nemo.gif")
   
   def act(self):
      self.move()
      if self.getX() == 0 or self.getX() == 9:
         self.turn(180)
         self.setHorzMirror(not self.isHorzMirror())

# ---------------- class MyActListener ----------------
class MyActListener(GGActListener):
   def act(self):
      global fish
      if gg.isKeyPressed(KeyEvent.VK_SPACE):
         fish = Clownfish()
         gg.addActor(fish, Location(0, 0))
      if fish == None: # no fish yet
         return;
      if gg.isKeyPressed(KeyEvent.VK_UP) and fish.getY() > 0:
         fish.setY(fish.getY() - 1)
      if gg.isKeyPressed(KeyEvent.VK_DOWN) and fish.getY() < 9:
         fish.setY(fish.getY() + 1)
      if gg.isKeyPressed(KeyEvent.VK_SPACE):
         fish = Clownfish()
         gg.addActor(fish, Location(0, 0))
 
# --------------------- main ---------------------------------
gg = GameGrid(10, 10, 60, X11Color("red"), False)
gg.setTitle("Space bar for new object, cursor to move up/down")
gg.addActListener(MyActListener())
fish = None
gg.show()
gg.doRun()


