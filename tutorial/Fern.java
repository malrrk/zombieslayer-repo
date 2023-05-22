// Fern.java

import ch.aplu.jgamegrid.*;
import java.awt.*;
import java.util.*;
import ch.aplu.util.Complex;

public class Fern extends GameGrid
{
  private GGPanel p;

  public Fern()
  {
    super(400, 600);
    p = getPanel();
    show();
    fern(20000);
  }

  private void fern(int nbIterations)
  {
    setTitle("Creating Fern...");
    p.window(-3.5, 3.5, 0, 10);
    Complex z = new Complex(0, 0);
    int it = 0;
    Random rnd = new Random();

    while (it <= nbIterations)
    {
      delay(5);
      if (it % 1000 == 0)
        p.color(Color.WHITE);
      double r = rnd.nextDouble();
      Color color = Color.BLACK;

      if (r < 0.01)
      {
        color = Color.ORANGE;
        z = f(z, 0, 0, 0, 0.16, 0, 0); // Stem
      }
      else
      {
        if (r < 0.86)
        {
          color = Color.GREEN;
          z = f(z, 0.85, 0.04, -0.04, 0.85, 0, 1.60); // symmetry
        }
        else
        {
          if (r >= 0.86 && r < 0.93)
          {
            color = Color.RED;
            z = f(z, 0.20, -0.26, 0.23, 0.22, 0, 1.60); // left leaves
          }
          else
          {
            if (r >= 0.93)
            {
              color = Color.BLUE;
              z = f(z, -0.15, 0.28, 0.26, 0.24, 0, 1.44); // right leaves
            }
          }
        }
      }
      p.color(color);
      p.point(z.real, z.img);
      it++;
    }
    setTitle("Creating Fern...Done.");
  }

  private Complex f(Complex z,
    double a, double b, double c, double d, double e, double f)
  {
    double re = a * z.real + b * z.img + e;
    double im = c * z.real + d * z.img + f;
    return new Complex(re, im);
  }
  
  public static void main(String[] args)
  {
    new Fern();
  }
}
