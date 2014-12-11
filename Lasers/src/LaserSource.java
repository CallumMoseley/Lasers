import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class LaserSource
{
	private static Image sprite;
	private int x;
	private int y;
	private int direction;
	
	public LaserSource(int x, int y, int direction)
	{
		this.x = x;
		this.y = y;
		this.direction = direction;
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
		g.drawImage(sprite, x * 32, y * 32, null);
	}
}