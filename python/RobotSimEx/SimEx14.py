# SimEx14.py
# Poll ultrasonic sensor
from simrobot import *                         

mesh_hbar = [[200, 10], [-200, 10], 
   [-200, -10], [200, -10]]
mesh_vbar = [[10, 200], [-10, 200], 
   [-10, -200], [10, -200]]
RobotContext.useTarget("sprites/bar0.gif", 
                     mesh_hbar, 250, 100)
RobotContext.useTarget("sprites/bar0.gif", 
                     mesh_hbar, 250, 400)
RobotContext.useTarget("sprites/bar1.gif", 
                     mesh_vbar, 100, 250)
RobotContext.useTarget("sprites/bar1.gif", 
                     mesh_vbar, 400, 250)

robot = LegoRobot()
gear = Gear()
robot.addPart(gear)
us = UltrasonicSensor(SensorPort.S1)
robot.addPart(us)
us.setBeamAreaColor(Color.green)
us.setProximityCircleColor(Color.lightGray)
  
gear.setSpeed(15)
gear.forward()
    
while not robot.isEscapeHit():
  distance = us.getDistance()
  if distance > 0 and distance < 50:
     gear.backward(2000)
     gear.left(1000)
     gear.forward()
robot.exit()

