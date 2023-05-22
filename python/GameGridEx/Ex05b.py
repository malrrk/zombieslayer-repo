# Ex05b.py

from ch.aplu.jgamegrid import GameGrid, Actor, Location, X11Color

# --------------------- class Ghost --------------------------
class Ghost(Actor):
   def __init__(self):
      Actor.__init__(self, "sprites/ghost.gif", 4)
   
   def act(self):
      if self.getNbCycles() % 5 == 0:
         self.showNextSprite()

      pt = gg.toPoint(self.getNextMoveLocation())
      dir = self.getDirection()

      if pt.x < 0 or pt.x > gg.getPgWidth():
        self.setDirection(180 - dir)

      if pt.y < 0 or pt.y > gg.getPgHeight():
        self.setDirection(360 - dir)

      self.move();
 
# --------------------- main ---------------------------------
gg = GameGrid(800, 600, 1, None, True)
gg.setSimulationPeriod(20)
gg.setBgColor(X11Color("darkgray"))
for i in range(7):
   ghost = Ghost()
   startLocation = Location(310 + 30 * i, 100)
   startDirection = 66 + 66 * i
   gg.addActor(ghost, startLocation, startDirection)
gg.show();


