import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Target extends Block
{
	private static Image sprite;

	public Target(int x, int y, boolean r)
	{
		super(x, y, true, r);
	}

	public Vector2 reflect(Ray incident)
	{
		Vector2[] intersections = new Vector2[4];
		intersections[0] = incident.intersects(new Vector2(getX(), getY()),
				new Vector2(0, 32));
		intersections[1] = incident.intersects(new Vector2(getX(), getY()),
				new Vector2(32, 0));
		intersections[2] = incident.intersects(
				new Vector2(getX() + 32, getY()), new Vector2(0, 32));
		intersections[3] = incident.intersects(
				new Vector2(getX(), getY() + 32), new Vector2(32, 0));
		Vector2 closest = new Vector2(Double.POSITIVE_INFINITY,
				Double.POSITIVE_INFINITY);
		int closestIndex = 0; 
		for (int point = 0; point < 4; point++)
		{
			if (Vector2.subtract(intersections[point], incident.getPosition())
					.getLength() < Vector2
					.subtract(closest, incident.getPosition()).getLength())
			{
				closest = intersections[point];
				closestIndex = point;
			}
		}
		
		Vector2 normal;
		if (closestIndex == 0)
		{
			normal = new Vector2(-1, 0);
		}
		else if (closestIndex == 1)
		{
			normal = new Vector2(0, -1);
		}
		else if (closestIndex == 2)
		{
			normal = new Vector2(1, 0);
		}
		else
		{
			normal = new Vector2(0, 1);
		}
		
		return Vector2.reflect(incident.getDirection(), normal);
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

	/**
	 * Draws the object
	 * @param g the graphics object to draw with
	 */
	public void draw(Graphics g)
	{
		g.drawImage(sprite, getX(), getY(), null);
	}
}
