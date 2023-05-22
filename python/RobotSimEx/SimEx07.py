# SimEx07.py
# One light sensor, event driven
from simrobot import *                         

def init(gg):
  bg = gg.getBg()
  bg.setPaintColor(makeColor("black"))
  bg.fillArc(Point(250, 250), 50, 0, 360)
  bg.fillArc(Point(250, 350), 100, 0, 360)

def onBright(port, level):
  gear.rightArc(0.15)

def onDark(port, level):
  gear.leftArc(0.15)

robot = NxtRobot()
init(robot.getGameGrid())
gear = Gear()
robot.addPart(gear)
ls = LightSensor(SensorPort.S3, bright = onBright, 
                                dark = onDark)
robot.addPart(ls)
gear.setSpeed(30)
gear.forward()
while not robot.isEscapeHit():
   pass
robot.exit()

