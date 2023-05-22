# Line1.py

from gpanel import *

def gline(*args):
   if len(args) == 1 and type(args[0]) in (list, tuple):
       print "ok"
       for i in range(len(args[0])):
           if i == 0:
               pos(args[0][i])
           else:
               draw(args[0][i])
   else:
       line(*args)


makeGPanel(0, 10, 0, 10)

line([(5, 5), (7, 7), (8, 7)])
line(((5, 4), (7, 6), (8, 6)))
line([complex(5, 3), complex(7, 5), complex(8, 5)])
