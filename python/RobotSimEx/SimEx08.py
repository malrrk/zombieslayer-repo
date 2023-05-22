# SimEx08.py
# One light sensor, event driven
from simrobot import *                         

RobotContext.setStartDirection(10)
 
def init(gg):
  bg = gg.getBg()
  bg.setPaintColor(Color.black)
  bg.fillArc(Point(250, 150), 120, 0, 360)
  bg.setPaintColor(Color.white)
  bg.fillArc(Point(250, 150), 60, 0, 360)
  bg.setPaintColor(Color.black)
  bg.fillArc(Point(250, 350), 120, 0, 360)
  bg.setPaintColor(Color.white)
  bg.fillArc(Point(250, 350), 60, 0, 360)

def onBright(port, level):
  gear.rightArc(0.15)

def onDark(port, level):
  gear.leftArc(0.15)

robot = LegoRobot()
init(robot.getGameGrid())
gear = Gear()
robot.addPart(gear)
ls1 = LightSensor(SensorPort.S1)
ls2 = LightSensor(SensorPort.S2)
robot.addPart(ls1)
robot.addPart(ls2)
gear.forward()

v = 500
r = 0.3
while not robot.isEscapeHit():
  v1 = ls1.getValue()
  v2 = ls2.getValue()
  if (v1 < v and v2 < v):
    gear.forward()
  if (v1 < v and v2 > v):
    gear.rightArc(r)
  if (v1 > v and v2 < v):
    gear.leftArc(r)
  if (v1 > v and v2 > v):
    gear.backward()
robot.exit()