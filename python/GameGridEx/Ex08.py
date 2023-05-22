# Ex08.py

from ch.aplu.jgamegrid import GameGrid, Actor, Location, GGMouse, GGMouseListener
from ch.aplu.util import X11Color
import math

# --------------------- class Dart --------------------------
class Dart(Actor, GGMouseListener):
   def __init__(self):
      Actor.__init__(self, True, "sprites/dart.gif") # Rotatable
      self.oldLocation = Location()
   
   def mouseEvent(self, mouse):
      location = gg.toLocationInGrid(mouse.getX(), mouse.getY())
      self.setLocation(location)
      dx = location.x - self.oldLocation.x;
      dy = location.y - self.oldLocation.y;
      if (dx * dx + dy * dy < 25):
         return True
      phi = math.atan2(dy, dx)
      self.setDirection(math.degrees(phi))
      self.oldLocation.x = location.x
      self.oldLocation.y = location.y
      return True

# --------------------- main ---------------------------------
gg = GameGrid(400, 300, 1, False)
gg.setTitle("Move dart using mouse left button drag")
gg.setSimulationPeriod(50)
gg.setBgColor(X11Color("lightblue"))
dart = Dart()
gg.addActor(dart, Location(50, 50))
gg.addMouseListener(dart, GGMouse.lDrag)
gg.show()
gg.doRun()

