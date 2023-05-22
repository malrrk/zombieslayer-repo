# TuEx2.py		

from gturtle import *

makeTurtle()

def tree(s):
   if s < 8:
     return
   fd(s)
   lt(45)
   tree(s/2)
   rt(90)
   tree(s/2)
   lt(45)
   back(s)

setY(-100)
tree(128)

