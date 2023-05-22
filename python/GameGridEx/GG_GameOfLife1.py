# GG_GameOfLife1.py

'''
 Population rules:
 For a cell that is 'populated':
 - each cell with one or no neighbours dies, as if by loneliness.
 - each cell with four or more neighbours dies, as if by overpopulation.
 - each cell with two or three neighbours survives

 For a cell that is 'unpopulated':
 - each cell with three neighbours becomes populated
'''

'''
 Implementation:
 All cell contains actors of the same class Creature. For a populated cell
 the actor is visible, for an unpopulated cell, the actor is invisible
'''

from gamegrid import *

# ---------------- class Creature ----------------
class Creature(Actor):
   def __init__(self):
      Actor.__init__(self, "sprites/creature_1.gif")
      self.isAlive = False
 
def showPopulation():
    for creature in creatures:
        if creature.isAlive:
            creature.show()
        else:
            creature.hide()

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
    



