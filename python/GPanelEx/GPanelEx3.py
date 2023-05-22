# GPanelEx3.py

from gpanel import *
import math

def transform():
# Transform horizontal grid lines
   setColor("green")
   z = complex(min, min)
   while z.imag < max:
      z = complex(min, z.imag)
      cmove(f(z))
      while z.real < max:
         cdraw(f(z))
         z = z + reStep
      z = z + imStep
      repaint()

# Transform vertical grid lines
   setColor("red")
   z = complex(min, min)
   while z.real < max:
      z = complex(z.real, min)
      cmove(f(z))
      while z.imag < max:
         cdraw(f(z))
         z = z + imStep
      z = z + reStep
      repaint()

# function f(z) = 1/z
def f(z):
   if abs(z) != 0:
      return 1 / z
   else:
      return complex(0, 0)

def cmove(z):
   move(z.real, z.imag)

def cdraw(z):
   draw(z.real, z.imag)


min = -5.0
max = 5.0
step = (max - min) / 400.0
reStep = complex(step, 0)
imStep = complex(0, step)

makeGPanel(min, max, min, max)
line(min, 0, max, 0)  # Real axis
line(0, min, 0, max) # Imaginary axis
enableRepaint(False)
transform()


