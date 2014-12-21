import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class Collidable
{
	private static Image sprite;
	private int x;
	private int y;
	private boolean target;
	private boolean reflective;

	/**
	 * Initialises the object to an x-y position
	 * @param x the x coordinate of the object
	 * @param y the y coordinate of the object
	 */
	public Collidable(int x, int y, boolean t, boolean r)
	{
		this.x = x;
		this.y = y;
		target = t;
		reflective = r;
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
		return target;
	}
	
	public boolean isReflective()
	{
		return reflective;
	}

	/**
	 * Determines the point at which the given ray intersects with this object
	 * @param r the ray to check intersection with
	 * @return the point at which the ray intersects this object, or an infinite
	 *         length vector if there is no intersection
	 */
	public Vector2 intersects(Ray r)
	{
		return new Vector2(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
	}
	
	public Vector2 reflect(Vector2 incident)
	{
		return incident;
	}

	/**
	 * Draws the object
	 * @param g the graphics object to draw with
	 */
	public void draw(Graphics g)
	{
		g.drawImage(sprite, x, y, null);
	}

	/**
	 * Loads an image file for all of this object
	 * @param file the filename of the image
	 */
	public static void loadSprite(String file)
	{
		try
		{
			sprite = ImageIO.read(new File(file));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
