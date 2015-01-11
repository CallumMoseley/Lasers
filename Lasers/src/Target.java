/**
 * A block which lights up when hit with a laser, and must be hit to win
 * @author Callum Moseley
 * @version January 2015
 */

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Target extends Block
{
	private static Image[] sprites;
	boolean reflective;

	/**
	 * Initialises this target to a position, and reflectivity
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @param r whether this target is reflective
	 */
	public Target(int x, int y, boolean r)
	{
		super(x, y);
		reflective = r;
	}
	
	public boolean isReflective()
	{
		return reflective;
	}
	
	public boolean isTarget()
	{
		return true;
	}

	public Vector2D reflect(Ray incident)
	{
		// Finds which side the laser hits, and the point where it hits.  See Block.intersects(Ray r)
		Vector2D[] intersections = new Vector2D[4];
		intersections[0] = incident.intersects(new Vector2D(getX(), getY()),
				new Vector2D(0, 32));
		intersections[1] = incident.intersects(new Vector2D(getX(), getY()),
				new Vector2D(32, 0));
		intersections[2] = incident.intersects(
				new Vector2D(getX() + 32, getY()), new Vector2D(0, 32));
		intersections[3] = incident.intersects(
				new Vector2D(getX(), getY() + 32), new Vector2D(32, 0));
		Vector2D closest = new Vector2D(Double.POSITIVE_INFINITY,
				Double.POSITIVE_INFINITY);
		int closestIndex = 0;
		for (int point = 0; point < 4; point++)
		{
			if (Vector2D.subtract(intersections[point], incident.getPosition())
					.getLength() < Vector2D
					.subtract(closest, incident.getPosition()).getLength())
			{
				closest = intersections[point];
				closestIndex = point;
			}
		}

		// Based on which side is hit, find the normal
		Vector2D normal;
		if (closestIndex == 0)
		{
			normal = new Vector2D(-1, 0);
		}
		else if (closestIndex == 1)
		{
			normal = new Vector2D(0, -1);
		}
		else if (closestIndex == 2)
		{
			normal = new Vector2D(1, 0);
		}
		else
		{
			normal = new Vector2D(0, 1);
		}

		// Reflect the ray
		return Vector2D.reflect(incident.getDirection(), normal);
	}

	/**
	 * Loads an image file for all of this object
	 * @param file the filename of the image
	 */
	public static void loadSprite(String file)
	{
		try
		{
			BufferedImage spriteSheet = ImageIO.read(new File(file));
			sprites = new Image[4];
			
			// Get sprites from sprite sheet
			sprites[0] = spriteSheet.getSubimage(0, 0, 32, 32);
			sprites[1] = spriteSheet.getSubimage(32, 0, 32, 32);
			sprites[2] = spriteSheet.getSubimage(64, 0, 32, 32);
			sprites[3] = spriteSheet.getSubimage(96, 0, 32, 32);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Draws the object
	 * @param g the graphics object to draw with
	 */
	public void draw(Graphics g)
	{
		g.drawImage(sprites[(getHit() ? 1 : 0) + (isReflective() ? 2 : 0)],
				getX(), getY(), null);
	}
}
