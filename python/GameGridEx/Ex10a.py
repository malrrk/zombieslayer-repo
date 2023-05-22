# Ex10a.py

from ch.aplu.jgamegrid import GameGrid, Actor, Location
from ch.aplu.util import X11Color


# --------------------- class Earth ---------------------
class Earth(Actor):
   def __init__(self):
      nbSprites = 18
      Actor.__init__(self, "sprites/earth.gif", nbSprites)
      self.setSlowDown(6)
  
   def act(self):
      self.showNextSprite()

# --------------------- class Rocket ---------------------
class Rocket(Actor):
   def __init__(self):
      Actor.__init__(self, "sprites/rocket.gif")
  
   def act(self): 
      self.move()
      if self.getX() < -100 or self.getX() > 700:
         self.turn(180)
         if self.isHorzMirror():
            self.setHorzMirror(False)
            gg.setPaintOrder(Rocket)  # Rocket in foreground
         else:
           self.setHorzMirror(True)
           gg.setPaintOrder(Earth)  # Earth in foreground

# --------------------- main ---------------------
gg = GameGrid(600, 200, 1, None, False)
gg.setSimulationPeriod(50)
earth = Earth()
gg.addActor(earth, Location(300, 100))
# Rocket added last->default paint order: in front of earth
rocket = Rocket()
gg.addActor(rocket, Location(200, 100))

gg.show()
gg.doRun()


