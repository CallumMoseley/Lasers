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
	private int width;
	private int height;
	
	private Image img;
	
	public ImageButton(int x, int y, String filename)
	{
		this.x = x;
		this.y = y;
		
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
		g.drawImage(img, x, y, null);
	}

	@Override
	public boolean intersects(Point click)
	{
		return click.getX() >= x && click.getX() < x + width
				&& click.getY() >= y && click.getY() < y + height;
	}

	@Override
	public abstract void onClick(Point point);
}