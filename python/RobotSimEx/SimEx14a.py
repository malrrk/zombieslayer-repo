# SimEx14a.py
# Two ultrasonic sensors
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
us1 = UltrasonicSensor(SensorPort.S1)
robot.addPart(us1)
us1.setBeamAreaColor(Color.green)
us1.setProximityCircleColor(Color.lightGray)
us2 = UltrasonicSensor(SensorPort.S2)
robot.addPart(us2)
gear.setSpeed(15)
gear.forward()
    
while not robot.isEscapeHit():
  distance1 = us1.getDistance()
  distance2 = us2.getDistance()
  print distance2
  if distance1 > 0 and distance1 < 50:
     gear.backward(2000)
     gear.left(1000)
     gear.forward()
robot.exit()
