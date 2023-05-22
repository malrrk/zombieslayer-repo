# Ex03a.py

from ch.aplu.jgamegrid import GameGrid, Actor, Location, GGActListener
from ch.aplu.util import X11Color


# ----------------- class MyActListener --------------
class MyActListener(GGActListener):
   def act(self):
      nemo.move()
      if not nemo.isMoveValid():
         nemo.turn(180)
         nemo.setHorzMirror(not nemo.isHorzMirror())

# ----------------- class MyGame ---------------------
class MyGame(GameGrid):
   def __init__(self):
      GameGrid.__init__(self, 10, 10, 60, X11Color("red"), "sprites/reef.gif")
      self.addActor(nemo, Location(2, 4))
      self.addActListener(MyActListener())
      self.show()

# ----------------- main -----------------------------
nemo = Actor("sprites/nemo.gif")
MyGame()




