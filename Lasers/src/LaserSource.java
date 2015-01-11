/**
 * An object in a level that, when simulated, creates a laser
 * @author Callum Moseley
 * @version January 2015
 */

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class LaserSource
{
	private static Image sprites[];
	private static Vector2D[] offsets = new Vector2D[] { new Vector2D(14, 16),
			new Vector2D(16, 14),
			new Vector2D(18, 16),
			new Vector2D(16, 18) };
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
	 * Gets the offset between the top left corner of the laser source and
	 * the point where the laser comes out
	 * @return the offset for this laser source
	 */
	public Vector2D getOffset()
	{
		return offsets[direction];
	}

	/**
	 * Loads an image as the sprite for all laser source objects
	 * @param file the file name of the image to load
	 */
	public static void loadSprite(String file)
	{
		try
		{
			BufferedImage temp = ImageIO.read(new File(file));
			sprites = new Image[4];
			
			// Get each individual sprite
			sprites[0] = temp.getSubimage(0, 0, 32, 32);
			sprites[1] = temp.getSubimage(32, 0, 32, 32);
			sprites[2] = temp.getSubimage(64, 0, 32, 32);
			sprites[3] = temp.getSubimage(96, 0, 32, 32);
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
		g.drawImage(sprites[direction], x, y, null);
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