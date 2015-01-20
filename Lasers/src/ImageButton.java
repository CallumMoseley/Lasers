import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class ImageButton implements MenuItem
{
	private int x;
	private int y;
	private int xOffset;
	private int yOffset;
	private int width;
	private int height;
	
	private Image img;
	
	/**
	 * Initialises this menu button to a position and an image
	 * @param x the x coordinate of the button
	 * @param y the y coordinate of the button
	 * @param filename the path to the image for this button
	 */
	public ImageButton(int x, int y, String filename)
	{
		this.x = x;
		this.y = y;
		xOffset = 0;
		yOffset = 0;
		try
		{
			img = ImageIO.read(new File(filename));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		width = img.getWidth(null);
		height = img.getHeight(null);
	}
	
	@Override
	public void draw(Graphics g)
	{
		g.drawImage(img, x + xOffset, y + yOffset, null);
	}

	@Override
	public boolean intersects(Point click)
	{
		return click.getX() >= x + xOffset && click.getX() < x + width + xOffset
				&& click.getY() >= y + yOffset && click.getY() < y + height + yOffset;
	}

	@Override
	public abstract void onClick(Point point);

	@Override
	public abstract void onRelease();
	
	@Override
	public void setOffset(int x, int y)
	{
		xOffset = x;
		yOffset = y;
	}
	
	@Override
	public int getWidth()
	{
		return width;
	}

	@Override
	public int getHeight()
	{
		return height;
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
		return x + width + xOffset;
	}
	
	@Override
	public int maxY()
	{
		return y + height + yOffset;
	}
}