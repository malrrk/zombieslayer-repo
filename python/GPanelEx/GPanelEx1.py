# GPanelEx1.py

from gpanel import *

makeGPanel(0, 10, 0, 10)

for i in range(0, 11):
   for k in range (0, 11):
      line(i, 0, k, 10)
      delay(100)
    
for i in range(0, 11):
   for k in range (0, 11):
      line(0, i, 10, k)
      delay(100)
