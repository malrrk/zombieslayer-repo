# Ex05.py

from ch.aplu.jgamegrid import GameGrid, Actor, Location, GGActListener
from ch.aplu.util import X11Color

# --------------------- class CapturedActor ------------------
class CapturedActor(Actor):
   def __init__(self, isRotatable, imagePath, nbSprites):
      Actor.__init__(self, isRotatable, imagePath, nbSprites)
   
   def move(self, dist):
      pt = gg.toPoint(self.getNextMoveLocation())
      dir = self.getDirection()
      if pt.x < 0 or pt.x > gg.getPgWidth():
         self.setDirection(180 - dir)
         self.setHorzMirror(not self.isHorzMirror())
      if pt.y < 0 or pt.y > gg.getPgHeight():
         self.setDirection(360 - dir)
      Actor.move(self, dist)

# --------------------- class MyActListener ------------------
class MyActListener(GGActListener):
   def act(self):
      head.move(10)

# --------------------- main ----------------------------------
head = CapturedActor(False, "sprites/head_0.gif", 1)
gg = GameGrid(800, 600, 1)
gg.addActListener(MyActListener())
gg.setSimulationPeriod(40)
gg.setBgColor(X11Color("lightgray"))
gg.addActor(head, Location(400, 200), 66)
gg.show();


