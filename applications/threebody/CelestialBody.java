// CelestialBody.java
// Used for class ThreeBody

import ch.aplu.jgamegrid.*;
import java.awt.Color;
import java.util.*;

public class CelestialBody extends Actor
{
  private final double scaleFactor = 1E6;
  private final double G = 6.67E-11; // Gravitational constant
  private final double mass;
  private double timeFactor = 500;
  private Location oldLocation = new Location(-1, -1);
  private GGVector startVelocity;
  private GGVector position;
  private GGVector velocity;
  private GGVector acceleration;
  private boolean drawTrace = true;
  private int id;

  public CelestialBody(int id, double mass, GGVector startVelocity)
  {
    super("sprites/celestialbody" + id + ".gif");
    this.id = id;
    this.mass = mass;
    this.startVelocity = startVelocity;
  }

  public void reset()
  {
    position = toPosition(getLocationStart());
    velocity = startVelocity.clone();
    oldLocation.x = -1;
    oldLocation.y = -1;
  }

  public GGVector getPosition()
  {
    return position.clone();
  }

  public GGVector getVelocity()
  {
    return velocity.clone();
  }

  public double getMass()
  {
    return mass;
  }

  private GGVector toPosition(Location location)
  {
    return new GGVector(location.x * scaleFactor, location.y * scaleFactor);
  }

  private Location toLocation(GGVector position)
  {
    return new Location((int)(position.x / scaleFactor), (int)(position.y / scaleFactor));
  }
  
  public void act()
  {
    ArrayList<Actor> neighbours = gameGrid.getActors(CelestialBody.class);
    neighbours.remove(this);  // Remove self
    GGVector totalForce = new GGVector();
    for (Actor neighbour : neighbours)
    {
      CelestialBody body = (CelestialBody)neighbour;
      GGVector r = body.getPosition().sub(position);
      double rmag = r.magnitude();
      GGVector force = r.mult(G * mass * body.getMass() / (rmag * rmag * rmag));
      totalForce = totalForce.add(force);
    }
    acceleration = totalForce.mult(1 / mass);
    velocity = velocity.add(acceleration.mult(timeFactor));
    position = position.add(velocity.mult(timeFactor));
    Location location = toLocation(position);
    setLocation(location);

    if (drawTrace)
    {
      switch (id)
      {
        case 1:
          getBackground().setPaintColor(Color.yellow);
          break;
        case 2:
          getBackground().setPaintColor(Color.red);
          break;
        case 3:
          getBackground().setPaintColor(Color.green);
          break;
      }
      if (oldLocation.x != -1)
        getBackground().drawLine(oldLocation.x, oldLocation.y, location.x, location.y);
      oldLocation.x = location.x;
      oldLocation.y = location.y;
    }
  }
}
