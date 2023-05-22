# ButtonEx1.py

from simrobot import *

robot = LegoRobot()
gear = Gear()
robot.addPart(gear)
isRunning = True
while isRunning:
    if robot.isDownHit():
        gear.backward()
    elif robot.isUpHit():
        gear.forward()
    elif robot.isLeftHit():
        gear.left()
    elif robot.isRightHit():
        gear.right()
    elif robot.isEnterHit():
        gear.stop()
    elif robot.isEscapeHit():
        isRunning = False
robot.exit()
print "all done"
    
