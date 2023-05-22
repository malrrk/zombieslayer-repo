# TuEx4.py

from gturtle import *
from thread import start_new_thread

def go(t):
  start_new_thread(draw, (t, ))

def draw(t):
   for i in range(9):
      drawLine(t)

def drawLine(t):
   t.forward(100)
   t.right(160)

frame = TurtleFrame()
joe = Turtle(frame)
lea = Turtle(frame, "red")
sara = Turtle(frame, "green")

joe.setPos(-90, -50)
lea.setPos(0, -50)
sara.setPos(90, -50)
sara.setPenColor("green")
lea.setPenColor("red")
go(joe)
go(lea)
go(sara)


