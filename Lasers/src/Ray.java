/**
 * Represents a ray, by two vectors, a position and a direction
 * 
 * @author Callum Moseley
 * @version January 2015
 */

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Ray
{
	private static Color laserColour;
	private static int laserThickness;
	private Vector2D pos;
	private Vector2D dir;

	/**
	 * Initialises a ray from a position vector and a direction vector
	 * @param p the position vector of the new ray
	 * @param d the direction vector of the new ray
	 */
	public Ray(Vector2D p, Vector2D d)
	{
		pos = p;
		dir = d;
	}

	/**
	 * Initialises a ray from values representing x and y parts of position and
	 * direction vectors
	 * @param x the x part of the position vector
	 * @param y the y part of the position vector
	 * @param dx the x part of the direction vector
	 * @param dy the y part of the direction vector
	 */
	public Ray(double x, double y, double dx, double dy)
	{
		pos = new Vector2D(x, y);
		dir = new Vector2D(dx, dy);
	}

	/**
	 * Determines whether this ray intersects the line segment from p1 to p1 +
	 * p2, and if it does, the point of intersection
	 * @param p1 the first point defining the line segment
	 * @param p2 the second point defining the line segment
	 * @return the point of intersection, or an infinite length vector if there
	 *         is no intersection
	 */
	public Vector2D intersects(Vector2D p1, Vector2D p2)
	{
		// For reference:
		// http://stackoverflow.com/questions/563198/how-do-you-detect-where-two-line-segments-intersect/565282#565282

		// Calculate the scalars required to have the ray and line segment
		// intersect
		double t = Vector2D.crossProduct(p1.subtract(pos),
				p2.multiply(1.0 / Vector2D.crossProduct(dir, p2)));
		double u = Vector2D.crossProduct(p1.subtract(pos),
				dir.multiply(1.0 / Vector2D.crossProduct(dir, p2)));

		// Check whether the point is in the line segment, and on the infinite
		// side of the ray
		if (Vector2D.crossProduct(dir, p2) != 0 && t >= 0 && u >= 0 && u <= 1)
		{
			return new Vector2D(pos.getX() + t * dir.getX(), pos.getY() + t
					* dir.getY());
		}
		return new Vector2D(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
	}

	/**
	 * Gets the position vector
	 * @return the position vector of this ray
	 */
	public Vector2D getPosition()
	{
		return pos;
	}

	/**
	 * Gets the direction vector
	 * @return the direction vector of this ray
	 */
	public Vector2D getDirection()
	{
		return dir;
	}

	/**
	 * Draws this ray on the given graphics object
	 * @param g the graphics object to draw with
	 */
	public void draw(Graphics g)
	{
		g.setColor(laserColour);
		((Graphics2D) g).setStroke(new BasicStroke(laserThickness));
		g.drawLine((int) pos.getX(), (int) pos.getY(),
				(int) (pos.getX() + dir.getX()),
				(int) (pos.getY() + dir.getY()));
	}
	
	public static void setLaserColour(Color colour)
	{
		laserColour = colour;
	}
	
	public static void setLaserThickness(int t)
	{
		laserThickness = t;
	}
}