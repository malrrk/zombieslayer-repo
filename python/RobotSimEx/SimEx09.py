# SimEx09.py
# One sound sensor, one touch sensor, event driven
from simrobot import *                         

RobotContext.useObstacle("sprites/bar0.gif", 250, 50);

def onPressed(port):
  gear.backward()

def onLoud(port, level):
  if gear.isMoving():
      gear.stop()
  else:
      gear.forward()

robot = LegoRobot()
gear = Gear()
robot.addPart(gear)
ss = SoundSensor(SensorPort.S1, loud = onLoud)
ts = TouchSensor(SensorPort.S3, pressed = onPressed) 
robot.addPart(ss)
robot.addPart(ts)

