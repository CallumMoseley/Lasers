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
	
	/**
	 * Initializes the laser source with position and direction
	 * @param x the x coordinate of the laser source
	 * @param y the y coordinate of the laser source
	 * @param direction the direction of the laser source
	 */
	public LaserSource(int x, int y, int direction)
	{
		this.x = x;
		this.y = y;
		this.direction = direction;
	}
	
	/**
	 * Gets the direction of the laser source object
	 * @return the direction
	 */
	public int getDirection()
	{
		return direction;
	}
	
	/**
	 * Loads an image as the sprite for all laser source objects
	 * @param file the file name of the image to load 
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
	 * Draws the laser source
	 * @param g the graphics object to draw with
	 */
	public void draw(Graphics g)
	{
		g.drawImage(sprite, x, y, null);
	}

	/**
	 * Gets the x coordinate
	 * @return the x coordinate
	 */
	public int getX()
	{
		return x;
	}

	/**
	 * Gets the y coordinate
	 * @return the y coordinate
	 */
	public int getY()
	{
		return y;
	}
}