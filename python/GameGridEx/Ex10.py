# Ex10.py

from ch.aplu.jgamegrid import GameGrid, Actor, Location
from ch.aplu.util import X11Color

# --------------------- class Animal ---------------------
class Animal(Actor):
   def __init__(self, sprite, clazz):
      Actor.__init__(self, sprite)
      self.clazz = clazz
  
   def act(self):
      if self.isMoveValid():
         self.move()
      else:
         if self.isHorzMirror():
            self.setHorzMirror(False)
            self.turn(270)
            self.move()
            self.turn(270)
         else:
            self.setHorzMirror(True)
            self.turn(90)
            self.move()
            self.turn(90)
      self.tryToEat()
      
   def tryToEat(self):
      actor = gg.getOneActorAt(self.getLocation(), self.clazz)
      if actor != None:
         actor.hide()

# --------------------- class Dog ---------------------
class Dog(Animal):
   def _init__(self, clazz):
      Animal._init(self, "sprites/dog.gif", clazz)

# --------------------- class Bear ---------------------
class Bear(Animal):
   def _init__(self, clazz):
      Animal._init(self, "sprites/bear.gif", clazz)

# --------------------- main ---------------------
gg = GameGrid(10, 10, 60, X11Color("red"), False)
bear = Bear("sprites/bear.gif", Dog)
dog = Dog("sprites/dog.gif", Bear)
gg.addActor(bear, Location(0, 5))
gg.addActor(dog, Location(9, 5), 180)
# gg.setActOrder(Bear)
gg.show()
gg.doRun()


