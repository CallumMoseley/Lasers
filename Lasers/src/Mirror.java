import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Mirror
{
	private static Image sprite;
	private int x;
	private int y;
	private int angle;
	private Vector2 normal;
	
	public Mirror(int x, int y, int angle)
	{
		this.x = x;
		this.y = y;
		this.angle = angle;
		normal = new Vector2(angle + 135).getNormalized();
	}
	
	public Vector2 reflect(Vector2 incident)
	{
		return Vector2.reflect(incident, normal);
	}
	
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
	
	public void draw(Graphics g)
	{
		AffineTransform af = AffineTransform.getTranslateInstance(x, y);
		af.rotate(Math.toRadians(angle));
		((Graphics2D)g).drawImage(sprite, af, null);
	}
}