import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Block extends Collidable
{
	public Block(int x, int y)
	{
		super(x, y);
	}

	private static Image sprite;

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

	public Vector2D intersects(Ray r)
	{
		Vector2D[] intersections = new Vector2D[4];
		intersections[0] = r.intersects(new Vector2D(getX(), getY()),
				new Vector2D(0, 32));
		intersections[1] = r.intersects(new Vector2D(getX(), getY()),
				new Vector2D(32, 0));
		intersections[2] = r.intersects(new Vector2D(getX() + 32, getY()),
				new Vector2D(0, 32));
		intersections[3] = r.intersects(new Vector2D(getX(), getY() + 32),
				new Vector2D(32, 0));
		Vector2D closest = new Vector2D(Double.POSITIVE_INFINITY,
				Double.POSITIVE_INFINITY);
		for (int point = 0; point < 4; point++)
		{
			if (Vector2D.subtract(intersections[point], r.getPosition())
					.getLength() < Vector2D
					.subtract(closest, r.getPosition()).getLength())
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
}