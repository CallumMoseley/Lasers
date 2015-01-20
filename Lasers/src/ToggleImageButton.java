/**
 * An image button, that when clicked, changes to another image
 * @author Callum Moseley
 * @version January 2015
 */

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.File;

import javax.imageio.ImageIO;

public class ToggleImageButton implements MenuItem
{
	private Image[] images;
	private int x;
	private int y;
	private int xOffset;
	private int yOffset;
	private int currentState;

	/**
	 * Initialise this button with its two images and position
	 * @param image1
	 * @param image2
	 * @param x
	 * @param y
	 */
	public ToggleImageButton(String image1, String image2, int x, int y)
	{
		images = new Image[2];
		try
		{
			images[0] = ImageIO.read(new File(image1));
			images[1] = ImageIO.read(new File(image2));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		this.x = x;
		this.y = y;
		xOffset = 0;
		yOffset = 0;
		currentState = 0;
	}
	
	public int getState()
	{
		return currentState;
	}

	@Override
	public void draw(Graphics g)
	{
		g.drawImage(images[currentState], x + xOffset, y + yOffset, null);
	}

	@Override
	public boolean intersects(Point point)
	{
		// Check intersection with the current image
		return point.getX() >= x + xOffset
				&& point.getX() < x + xOffset
						+ images[currentState].getWidth(null)
				&& point.getY() >= y + yOffset
				&& point.getY() < y + yOffset
						+ images[currentState].getHeight(null);
	}

	@Override
	public void onClick(Point point)
	{
		// Toggle state
		currentState = 1 - currentState;
	}

	@Override
	public void onRelease()
	{
	}

	@Override
	public void setOffset(int x, int y)
	{
		xOffset = x;
		yOffset = y;
	}

	@Override
	public int getWidth()
	{
		return images[currentState].getWidth(null);
	}

	@Override
	public int getHeight()
	{
		return images[currentState].getHeight(null);
	}

	@Override
	public int minX()
	{
		return x + xOffset;
	}

	@Override
	public int minY()
	{
		return y + yOffset;
	}

	@Override
	public int maxX()
	{
		return x + xOffset + images[currentState].getWidth(null);
	}

	@Override
	public int maxY()
	{
		return y + yOffset + images[currentState].getHeight(null);
	}
}