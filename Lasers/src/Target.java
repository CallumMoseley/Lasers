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
	
	public Vector2 reflect(Vector2 incident)
	{
		return incident;
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
