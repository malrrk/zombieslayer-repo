# TuEx3.py

from gturtle import *

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
for i in range(9):
   drawLine(joe)
   drawLine(lea)
   drawLine(sara)


