# SimEx10.py
# One light sensor, one touchsensor, road follower
from simrobot import *                         

RobotContext.setStartPosition(40, 460)
RobotContext.setStartDirection(-90)
RobotContext.useBackground("sprites/roboroad.gif")
RobotContext.useObstacle("sprites/chocolate.gif", 400, 50)
 
robot = LegoRobot()
gear = Gear()
ls = LightSensor(SensorPort.S3)
ts = TouchSensor(SensorPort.S1)
robot.addPart(gear)
robot.addPart(ls)
robot.addPart(ts)
ls.activate(True)

while not robot.isEscapeHit():
   v = ls.getValue()
   if v < 100:  # black
      gear.forward()
   if v > 600 and v < 700:  # green
      gear.leftArc(0.1)
   if v > 800:  # yellow
     gear.rightArc(0.1)
   if ts.isPressed():
      gear.stop()
      break
robot.exit()
