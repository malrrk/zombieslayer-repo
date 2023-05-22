# Ex05a.py

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

# --------------------- class Player -------------------------
class Player(CapturedActor):
   def __init__(self):
      CapturedActor.__init__(self, False, "sprites/head.gif", 3)

   def act(self): 
      if self.getNbCycles() % 5 == 0:
         self.showNextSprite()
      CapturedActor.move(self, 10)

# --------------------- main ----------------------------------
gg = GameGrid(800, 600, 1, False)
gg.setSimulationPeriod(40)
gg.setBgColor(X11Color("lightgray"))
for i in range(3):
   player = Player()
   startLocation = Location(200 + 100 * i, 125)
   startDirection = 15 * i
   gg.addActor(player, startLocation, startDirection)
gg.show()
gg.doRun()
