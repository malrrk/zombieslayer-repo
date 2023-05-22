// Julia.java
// Julia

import ch.aplu.jgamegrid.*;
import java.awt.*;
import ch.aplu.util.Complex;

public class Julia extends GameGrid
{
  private GGPanel p;

  public Julia()
  {
    super(400, 600);
    p = getPanel();
    show();
    setTitle("Working. Please wait...");
    p.setRefreshEnabled(false);
    drawJulia();
    setTitle("Working. Please wait...done.");
  }

  private void drawJulia()
  {
    double range = 2;
    double maxNorm = 50;
    int maxIterations = 100;
    Complex c = new Complex(-0.5, -0.5);
    Complex z0 = new Complex(-range, -range);
    double step = 0.001;
    int it;

    p.window(-range, range, -range, range);
    while (z0.getImg() < range) // outer loop in imag direction
    {
      z0 = new Complex(-range, z0.getImg() + step);
      while (z0.getReal() < range) // inner loop in real direction
      {
        z0 = Complex.add(z0, new Complex(step, 0));

        // Julia iteration
        Complex z = z0;
        it = 0;
        while (z.modulus() < maxNorm && it < maxIterations)
        {
          // z = z^2 + c
          z = Complex.add(Complex.multiply(z, z), c);
          it++;
        }
        if (it < 4)
          putPixel(z0, Color.GRAY);
        else if (it < 5)
          putPixel(z0, Color.GREEN);
        else if (it < 8)
          putPixel(z0, Color.RED);
        else if (it < 12)
          putPixel(z0, Color.BLUE);
        else if (it < 100)
          putPixel(z0, Color.YELLOW);
        else
          putPixel(z0, Color.BLACK);

      }
      refresh();
    }
  }

  private void putPixel(Complex z, Color color)
  {
    p.color(color);
    p.point(z.getReal(), z.getImg());
  }

  public static void main(String[] args)
  {
    new Julia();
  }
}
