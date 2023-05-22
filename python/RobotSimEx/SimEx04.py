# SimEx04.py
# One touch sensor, polling
from simrobot import *                         

RobotContext.showNavigationBar();
RobotContext.useObstacle("sprites/bar0.gif", 250, 200);
RobotContext.useObstacle("sprites/bar1.gif", 400, 250);
RobotContext.useObstacle("sprites/bar2.gif", 250, 400);
RobotContext.useObstacle("sprites/bar3.gif", 100, 250);

robot = LegoRobot()
gear = Gear()
ts = TouchSensor(SensorPort.S3)
robot.addPart(gear)
robot.addPart(ts)
gear.setSpeed(30)
gear.forward()
while not robot.isEscapeHit():
  if ts.isPressed():
    gear.backward(1200);
    gear.left(750);
    gear.forward();
robot.exit()
