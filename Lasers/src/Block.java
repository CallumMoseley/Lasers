/**
 * A simple square block in a level
 * @author Callum Moseley
 * @version January 2015
 */

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Block extends Collidable
{
	private static Image sprite;

	/**
	 * Initializes the block to a given position
	 * @param x the x coordinate of the block
	 * @param y the y coordinate of the block
	 */
	public Block(int x, int y)
	{
		super(x, y);
	}

	public Vector2D intersects(Ray r)
	{
		// Find the point of intersection of the ray for each side
		Vector2D[] intersections = new Vector2D[4];
		intersections[0] = r.intersects(new Vector2D(getX(), getY()),
				new Vector2D(0, 32));
		intersections[1] = r.intersects(new Vector2D(getX(), getY()),
				new Vector2D(32, 0));
		intersections[2] = r.intersects(new Vector2D(getX() + 32, getY()),
				new Vector2D(0, 32));
		intersections[3] = r.intersects(new Vector2D(getX(), getY() + 32),
				new Vector2D(32, 0));

		// Find the point which is closest to the rays position, as it would be
		// hit first, and return it
		Vector2D closest = new Vector2D(Double.POSITIVE_INFINITY,
				Double.POSITIVE_INFINITY);
		for (int point = 0; point < 4; point++)
		{
			if (intersections[point].subtract(r.getPosition()).getLength() < closest
					.subtract(r.getPosition()).getLength())
			{
				closest = intersections[point];
			}
		}
		return closest;
	}

	public void draw(Graphics g)
	{
		g.drawImage(sprite, getX(), getY(), null);
	}

	/**
	 * Loads a static block sprite from an image, to draw all blocks with
	 * @param filename the path to the file to be loaded
	 */
	public static void loadSprite(String filename)
	{
		try
		{
			sprite = ImageIO.read(new File(filename));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}