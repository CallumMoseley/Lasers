import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Mirror extends Collidable
{
	private static Image sprite;
	private int angle;
	private Vector2D normal;

	public Mirror(int x, int y, int angle)
	{
		super(x, y);
		this.angle = angle;
		normal = new Vector2D(angle + 135).getNormalized();
	}
	
	public boolean isReflective()
	{
		return true;
	}
	
	public boolean isTarget()
	{
		return false;
	}

	public Vector2D intersects(Ray r)
	{
		Vector2D p1 = new Vector2D(getX(), getY());
		Vector2D p2 = Vector2D.multiply(new Vector2D(getAngle() + 45),
				Math.sqrt(32 * 32 + 32 * 32));
		return r.intersects(p1, p2);
	}

	public Vector2D reflect(Ray incident)
	{
		return Vector2D.reflect(incident.getDirection(), normal);
	}

	public int getAngle()
	{
		return angle;
	}

	public void draw(Graphics g)
	{
		AffineTransform af = AffineTransform.getTranslateInstance(getX(),
				getY());
		af.rotate(Math.toRadians(angle));
		((Graphics2D) g).drawImage(sprite, af, null);
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