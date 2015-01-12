/**
 * An object which can be placed in a level, and a laser can collide with
 * @author Callum Moseley
 * @version January 2015
 */

import java.awt.Graphics;

public abstract class Collidable
{
	private int x;
	private int y;
	private boolean wasHit;

	/**
	 * Initialises the object to a given position
	 * @param x the x coordinate of the object
	 * @param y the y coordinate of the object
	 */
	public Collidable(int x, int y)
	{
		this.x = x;
		this.y = y;
		wasHit = false;
	}

	/**
	 * Gets the x coordinate of this object
	 * @return the x coordinate of this object
	 */
	public int getX()
	{
		return x;
	}

	/**
	 * Gets the y coordinate of this object
	 * @return the y coordinate of this object
	 */
	public int getY()
	{
		return y;
	}

	/**
	 * Gets whether this object is a target
	 * @return whether this object is a target
	 */
	public boolean isTarget()
	{
		return false;
	}

	/**
	 * Gets whether this object reflects lasers
	 * @return whether this object reflects lasers
	 */
	public boolean isReflective()
	{
		return false;
	}

	/**
	 * Flags this object as hit for the current simulation
	 */
	public void hit()
	{
		wasHit = true;
	}

	/**
	 * Clears the hit flag
	 */
	public void unHit()
	{
		wasHit = false;
	}

	/**
	 * Gets whether this object has been hit in the current simulation
	 * @return whether this object was hit
	 */
	public boolean getHit()
	{
		return wasHit;
	}
	
	public void moveTo(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	/**
	 * Determines the point at which the given ray intersects with this object
	 * @param r the ray to check intersection with
	 * @return the point at which the ray intersects this object, or an infinite
	 *         length vector if there is no intersection
	 */
	public Vector2D intersects(Ray r)
	{
		return new Vector2D(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
	}

	/**
	 * Finds the direction of the given ray if it were reflected by this object
	 * @param incident the ray to reflect over this object
	 * @return the direction vector of the reflected ray
	 */
	public Vector2D reflect(Ray incident)
	{
		return incident.getDirection();
	}

	/**
	 * Draws the object in the given graphics context
	 * @param g the graphics object to draw with
	 */
	public void draw(Graphics g)
	{
		return;
	}
}
