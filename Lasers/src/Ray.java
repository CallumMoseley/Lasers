/**
 * Represents a ray, by two vectors, a position and a direction
 * 
 * @author Callum Moseley
 * @version December 2014
 */

import java.awt.Color;
import java.awt.Graphics;

public class Ray
{
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

		double t = Vector2D.crossProduct(Vector2D.subtract(p1, pos),
				Vector2D.multiply(p2, 1.0 / Vector2D.crossProduct(dir, p2)));
		double u = Vector2D.crossProduct(Vector2D.subtract(p1, pos),
				Vector2D.multiply(dir, 1.0 / Vector2D.crossProduct(dir, p2)));
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
		g.setColor(Color.RED);
		g.drawLine((int) pos.getX(), (int) pos.getY(),
				(int) (pos.getX() + dir.getX()),
				(int) (pos.getY() + dir.getY()));
	}
}