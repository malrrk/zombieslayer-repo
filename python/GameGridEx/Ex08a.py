# Ex08a.py

from ch.aplu.jgamegrid import GameGrid, Actor, Location
from ch.aplu.jgamegrid import GGMouse, GGMouseListener, GGSound, GGActorCollisionListener
from java.awt import Point
from ch.aplu.util import Monitor
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

# --------------------- MyActorCollisionListener ------------
class MyActorCollisionListener(GGActorCollisionListener):
   def collide(self, actor1, actor2):
      gg.setMouseEnabled(False)  # Inhibit further mouse events
      Monitor.wakeUp()           # Resume processing
      return 0


# --------------------- main ---------------------------------
gg = GameGrid(400, 300, 1, False)
gg.setTitle("Move dart using mouse left button drag")
gg.setSimulationPeriod(50)
gg.setBgColor(X11Color("lightblue"))
gg.playSound(GGSound.DUMMY)

dart = Dart()
dart.setCollisionSpot(Point(30, 0))
dart.addActorCollisionListener(MyActorCollisionListener())
  
gg.addActor(dart, Location(50, 50))
gg.addMouseListener(dart, GGMouse.lDrag)

balloon = Actor("sprites/balloon.gif", 2)
gg.addActor(balloon, Location(300, 200))
balloon.setCollisionImage(0)  # Select IMAGE type detection
dart.addCollisionActor(balloon)
gg.show()
gg.doRun()

while True:
   Monitor.putSleep()
   if gg.isDisposed():
      break
   dart.hide()                     # Hide dart
   balloon.show(1)                 # Show exlode image
   gg.playSound(GGSound.PING);     # Play ping
   dart.setLocation(dart.getLocationStart())   # Init dart
   dart.setDirection(dart.getDirectionStart()) # ditto
   gg.delay(1000)                  # Wait a moment
   balloon.show(0)                 # Show heart image
   dart.show()                     # Show dart
   gg.setMouseEnabled(True)        # Enable mouse events

   

