# SimEx05.py
# One touch sensor, event driven
from simrobot import *                         

NxtContext.showNavigationBar();
NxtContext.useObstacle("sprites/bar0.gif", 250, 200);
NxtContext.useObstacle("sprites/bar1.gif", 400, 250);
NxtContext.useObstacle("sprites/bar2.gif", 250, 400);
NxtContext.useObstacle("sprites/bar3.gif", 100, 250);

def pressed(port):
  gear.backward(1200)
  gear.left(750)
  gear.forward()

robot = NxtRobot()
gear = Gear()
ts = TouchSensor(SensorPort.S3, pressed = pressed)
robot.addPart(gear)
robot.addPart(ts)
gear.setSpeed(30)
gear.forward()

