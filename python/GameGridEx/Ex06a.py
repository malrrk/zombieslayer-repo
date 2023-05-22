# Ex06a.py

from ch.aplu.jgamegrid import GameGrid, Actor, Location, GGMouse, GGMouseListener
from ch.aplu.util import X11Color


# --------------------- class Honey --------------------------
class Honey(Actor, GGMouseListener):
   def __init__(self):
      Actor.__init__(self, "sprites/honey.gif")
      self.isDragging = False
   
   def mouseEvent(self, mouse):
      location = gg.toLocationInGrid(mouse.getX(), mouse.getY())
      event = mouse.getEvent()
      if event == GGMouse.lPress:
         self.lastLocation = location.clone()
         if gg.getOneActorAt(location) == self:
            self.isDragging = True
      elif event == GGMouse.lDrag:
         if self.isDragging and gg.isEmpty(location):
            self.setLocation(location)
            self.lastLocation = location.clone()
      elif event == GGMouse.lRelease:
         if self.isDragging: 
            self.setLocation(self.lastLocation)
            self.isDragging = False
      return False # Don't consume the event, other listeners must be notified

# --------------------- class Bear --------------------------
class Bear(Actor):
   def __init__(self):
      Actor.__init__(self, "sprites/bear.gif")
 
   def act(self):
      if self.isMoveValid():
         self.move()
      else:
         if self.isHorzMirror():  # at left border
            self.setHorzMirror(False)
            self.turn(270)
            self.move()
            self.turn(270)
         else:  # at right border
            self.setHorzMirror(True)
            self.turn(90)
            self.move()
            self.turn(90)
      self.tryToEat()

   def tryToEat(self):
      actor = gg.getOneActorAt(self.getLocation(), Honey)
      if actor != None:
         actor.hide()

# --------------------- class MyMouseListener --------------------------
class MyMouseListener(GGMouseListener):
   def mouseEvent(self, mouse):
      location = gg.toLocationInGrid(mouse.getX(), mouse.getY())
      if gg.isEmpty(location):  # Do not create an actor if cell is occupied
         honey = Honey()
         gg.addActor(honey, location);
         gg.addMouseListener(honey, GGMouse.lPress | GGMouse.lDrag | GGMouse.lRelease)
      return False  # Don't consume the event, other listeners must be notified
 
# --------------------- main ---------------------------------
gg = GameGrid(10, 10, 60, X11Color("green"), False)
gg.setTitle("Click to create a honey pot, press and drag to move it")
gg.addActor(Bear(), Location(0, 0))
gg.addMouseListener(MyMouseListener(), GGMouse.lPress)
gg.show()
gg.doRun()

