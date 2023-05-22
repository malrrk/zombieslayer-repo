# SimEx02.py
# Gear
from simrobot import *                         
#from simrobot import *                         
  
robot = NxtRobot()
gear = Gear()
robot.addPart(gear)

gear.forward()
Tools.delay(2000)
gear.left(2000)
gear.forward(2000)
gear.leftArc(0.2, 2000)
gear.forward(2000)
gear.leftArc(-0.2, 2000)
robot.exit()

