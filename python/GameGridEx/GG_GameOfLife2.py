# GG_GameOfLife2.py
# not finished

from gamegrid import *
 
def showPopulation():
    for x in range(s):
        for y in range(s):
            loc = Location(x, y)
            if a[x][y]:
                getBg().fillCell(loc, Color.green, False)
            else:
                getBg().fillCell(loc, Color.black, False)
      
def getNumberOfNeighbours(x, y):
    nb = 0
    for i in range(max(0, x - 1), min(s, x + 2)):
         for (k in range (max(0, y - 1), min(s, y + 2))
             if not(i == x and k == y)
                if a[i][k]:
                    nb += 1
    return nb
  
# ----------------- class MyActListener --------------
class MyActListener(GGActListener):
    def act(self):
        for creature in creatures:
            # Get number of (living) neighbours
            neighbours = creature.getNeighbours(1)
            nbNeighbours = 0
            for neighbour in neighbours:
                if neighbour.isVisible():
                    nbNeighbours += 1
            # Generation rule:
            if creature.isVisible():  # alive
                if not (nbNeighbours == 2 or nbNeighbours == 3):
                    creature.isAlive = False # dying
            else: # dead
                if nbNeighbours == 3:
                    creature.isAlive = True  # become alive
        
        showPopulation()
 
nb = 20 # Number of cells in each direction
nbCreatures = nb * nb
popSize = 200 # size of populatin (at start)
creatures = [0] * nbCreatures

makeGameGrid(nb, nb, 25, makeColor("red"), False)
addActListener(MyActListener())
k = 0
# Create hidden creature in every cell
for x in range(nb):
    for y in range(nb):
        creatures[k] = Creature()
        loc = Location(x, y)
        addActor(creatures[k], loc)
        creatures[k].hide()
        k += 1

for i in range(popSize):
    creature = getOneActorAt(getRandomLocation())
    creature.isAlive = True
    showPopulation()
show()
doRun()
    



