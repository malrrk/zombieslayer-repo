# SimEx15.py
# Shadow

from simrobot import *

RobotContext.useTorch(1, 250, 250, 100)
RobotContext.useShadow(50, 150, 450, 200)
RobotContext.useShadow(100, 300, 350, 450)
  
robot = LegoRobot()
gear = Gear()
robot.addPart(gear)
ls = LightSensor(SensorPort.S1, True)
robot.addPart(ls)
gear.leftArc(0.5)
isLightOn = False

while not robot.isEscapeHit():
    v = ls.getValue()
    if not isLightOn and v == 0:
        isLightOn = True
        robot.playTone(2000, 200)
    if isLightOn and v > 0:
        isLightOn = False
        robot.playTone(1000, 200)
    Tools.delay(100)
robot.exit()   