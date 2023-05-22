from gpanel import *

makeGPanel(0, 8, 0, 8)        
for i in range(8):
    for j in range(8):
        if (i + j) % 2 == 0:
          fillRectangle(i, j, i + 1, j + 1)
