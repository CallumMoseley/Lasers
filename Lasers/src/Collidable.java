import java.awt.Graphics;

public abstract class Collidable
{
	private int x;
	private int y;
	private boolean wasHit;

	/**
	 * Initialises the object to an x-y position
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

	public boolean isTarget()
	{
		return false;
	}

	public boolean isReflective()
	{
		return false;
	}

	public void hit()
	{
		wasHit = true;
	}

	public void unHit()
	{
		wasHit = false;
	}

	public boolean getHit()
	{
		return wasHit;
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

	public Vector2D reflect(Ray incident)
	{
		return incident.getDirection();
	}

	/**
	 * Draws the object
	 * @param g the graphics object to draw with
	 */
	public void draw(Graphics g)
	{
		return;
	}
}
