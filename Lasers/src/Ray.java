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
	private Vector2 pos;
	private Vector2 dir;

	/**
	 * Initialises a ray from a position vector and a direction vector
	 * @param p the position vector of the new ray
	 * @param d the direction vector of the new ray
	 */
	public Ray(Vector2 p, Vector2 d)
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
		pos = new Vector2(x, y);
		dir = new Vector2(dx, dy);
	}

	/**
	 * Finds whether this ray intersects with the given block, and the point
	 * where it intersects
	 * @param b the block to check intersection with
	 * @return The point where this ray intersects the block, or a vector of
	 *         infinite length if there is no intersection
	 */
	public Vector2 intersects(Block b)
	{
		Vector2[] edges = new Vector2[4];
		return new Vector2(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
	}

	/**
	 * Determines whether this ray intersects the given mirror, and finds the
	 * point of intersection
	 * @param mirror the mirror to check intersection with
	 * @return the point of intersection, or an infinite length vector if there
	 *         is no intersection
	 */
	public Vector2 intersects(Mirror mirror)
	{
		Vector2 p1 = new Vector2(mirror.getX(), mirror.getY());
		Vector2 p2 = Vector2.multiply(new Vector2(mirror.getAngle() + 45),
				Math.sqrt(32 * 32 + 32 * 32));

		return intersects(p1, p2);
	}

	/**
	 * Determines whether this ray intersects the line segment from p1 to p2,
	 * and if it does, the point of intersection
	 * @param p1 the first point defining the line segment
	 * @param p2 the second point defining the line segment
	 * @return the point of intersection, or an infinite length vector if there
	 *         is no intersection
	 */
	public Vector2 intersects(Vector2 p1, Vector2 p2)
	{
		// For reference:
		// http://stackoverflow.com/questions/563198/how-do-you-detect-where-two-line-segments-intersect/565282#565282

		double t = Vector2.crossProduct(Vector2.subtract(p1, pos),
				Vector2.multiply(p2, 1.0 / Vector2.crossProduct(dir, p2)));
		double u = Vector2.crossProduct(Vector2.subtract(p1, pos),
				Vector2.multiply(dir, 1.0 / Vector2.crossProduct(dir, p2)));

		if (Vector2.crossProduct(dir, p2) != 0 && u <= 1 && u >= 0)
		{
			return new Vector2(pos.getX() + t * dir.getX(), pos.getY() + t
					* dir.getY());
		}

		return new Vector2(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
	}

	/**
	 * Gets the position vector
	 * @return the position vector of this ray
	 */
	public Vector2 getPosition()
	{
		return pos;
	}

	/**
	 * Gets the direction vector
	 * @return the direction vector of this ray
	 */
	public Vector2 getDirection()
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