import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Block
{
	private static Image sprite;
	private int x;
	private int y;
	
	public Block(int x, int y)
	{
		this.x = x;
		this.y = y;
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
		g.drawImage(sprite, 32 * x, 32 * y, null);
	}
}