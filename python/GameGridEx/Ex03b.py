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

# ----------------- main -----------------------------
nemo = Actor("sprites/nemo.gif")
gg = GameGrid(10, 10, 60, X11Color("red"), "sprites/reef.gif")
gg.addActor(nemo, Location(2, 4))
gg.addActListener(MyActListener())
gg.show()





