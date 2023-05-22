# SimEx06.py
# Two touch sensors, complex track
from simrobot import *                         

RobotContext.setLocation(10, 10)
RobotContext.setStartDirection(5)
RobotContext.setStartPosition(100, 240)
RobotContext.useObstacle(NxtContext.channel)
   
robot = LegoRobot()
gear = Gear()
robot.addPart(gear)
ts1 = TouchSensor(SensorPort.S1) # right sensor
ts2 = TouchSensor(SensorPort.S2) # left sensor
robot.addPart(ts1)
robot.addPart(ts2)
gear.forward()
while not robot.isEscapeHit():
  t1 = ts1.isPressed()
  t2 = ts2.isPressed()
  if (t1 and t2):
    gear.backward(500)
    gear.left(400)
    gear.forward()
  else:
    if t1:
      gear.backward(500)
      gear.left(400)
      gear.forward()
    else:
      if t2:
        gear.backward(500)
        gear.right(100)
        gear.forward()
robot.exit()
