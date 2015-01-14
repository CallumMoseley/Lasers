/**
 * A container for a single MenuItem, which allows a large item to fit in a small box, by being scrolled vertically
 * @author Callum Moseley
 * @version January 2015
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

public class ScrollContainer implements MenuItem
{
	private final int SCROLL_BAR_HEIGHT = 70;
	private final int SCROLL_BAR_WIDTH = 30;
	
	private int x;
	private int y;

	private int width;
	private int height;

	private Color backgroundColor;
	private Color scrollBarColor;
	
	private int scrollBarPos;

	private MenuItem contained;

	public ScrollContainer(int x, int y, int w, int h, Color bg, Color sb)
	{
		this.x = x;
		this.y = y;
		width = w;
		height = h;
		backgroundColor = bg;
		scrollBarColor = sb;
	}
	
	public void setItem(MenuItem i)
	{
		contained = i;
	}

	@Override
	public void draw(Graphics g)
	{
		// Draw solid background
		g.setColor(backgroundColor);
		g.fillRect(x, y, width, height);
		
		// Draw the contained item, clipped to fit within this object
		BufferedImage clippedItem = new BufferedImage(1024, 768, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = clippedItem.createGraphics();
		g2.setClip(x, y, width, height);
		contained.drawOffset(g2, 0, (int)Math.max(0, (height - contained.getHeight()) * ((double)scrollBarPos / (height - SCROLL_BAR_HEIGHT))));
		
		g.drawImage(clippedItem, 0, 0, null);
		
		// Draw scroll bar
		if (contained.getHeight() > height)
		{
			g.setColor(scrollBarColor);
			g.fillRect(x + width - SCROLL_BAR_WIDTH, y + scrollBarPos, SCROLL_BAR_WIDTH, SCROLL_BAR_HEIGHT);
		}
	}
	
	@Override
	public void drawOffset(Graphics g, int offx, int offy)
	{
		x += offx;
		y += offy;
		// Draw solid background
		g.setColor(backgroundColor);
		g.fillRect(x, y, width, height);
		
		// Draw the contained item, clipped to fit within this object
		BufferedImage clippedItem = new BufferedImage(1024, 768, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = clippedItem.createGraphics();
		g2.setClip(x, y, width, height);
		contained.drawOffset(g2, offx, offy + 0);
		
		g.drawImage(clippedItem, 0, 0, null);
		
		// Draw scroll bar
		if (contained.getHeight() > height)
		{
			g.setColor(scrollBarColor);
			g.fillRect(x + width - SCROLL_BAR_WIDTH, y + scrollBarPos, SCROLL_BAR_WIDTH, SCROLL_BAR_HEIGHT);
		}
		
		this.x += x;
		this.y += y;
	}

	@Override
	public boolean intersects(Point point)
	{
		return point.getX() >= x && point.getX() < x + width
				&& point.getY() >= y && point.getY() < y + height;
	}

	@Override
	public void onClick(Point point)
	{
		contained.onClick(point);
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
		return x;
	}

	@Override
	public int minY()
	{
		return y;
	}

	@Override
	public int maxX()
	{
		return x + width;
	}

	@Override
	public int maxY()
	{
		return y + height;
	}
}