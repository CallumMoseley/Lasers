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
	private int xOffset;
	private int yOffset;
	private int width;
	private int height;
	private Color backgroundColor;
	private Color scrollBarColor;

	private int scrollBarPos;
	private boolean scrolling;

	private MenuItem contained;

	public ScrollContainer(int x, int y, int w, int h, Color bg, Color sb)
	{
		this.x = x;
		this.y = y;
		xOffset = 0;
		yOffset = 0;
		width = w;
		height = h;
		backgroundColor = bg;
		scrollBarColor = sb;
		scrolling = false;
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
		g.fillRect(x + xOffset, y + yOffset, width, height);

		// Draw the contained item, clipped to fit within this object
		BufferedImage clippedItem = new BufferedImage(1024, 768,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = clippedItem.createGraphics();
		g2.setClip(x + xOffset, y + yOffset, width, height);
		contained.draw(g2);

		g.drawImage(clippedItem, 0, 0, null);

		// Draw scroll bar
		if (contained.getHeight() > height)
		{
			g.setColor(scrollBarColor);
			g.fillRect(x + xOffset + width - SCROLL_BAR_WIDTH, y + yOffset + scrollBarPos,
					SCROLL_BAR_WIDTH, SCROLL_BAR_HEIGHT);
		}
	}

	@Override
	public boolean intersects(Point point)
	{
		return point.getX() >= x + xOffset && point.getX() < x + xOffset + width
				&& point.getY() >= y + yOffset && point.getY() < y + yOffset + height;
	}

	@Override
	public void onClick(Point point)
	{
		contained.onClick(point);
		if (point.getX() >= x + xOffset + width - SCROLL_BAR_WIDTH
				&& point.getX() < x + xOffset + width && point.getY() >= y + yOffset + scrollBarPos
				&& point.getY() < y + yOffset + scrollBarPos + SCROLL_BAR_HEIGHT
				&& contained.getHeight() > height)
		{
			scrolling = true;
		}
	}

	@Override
	public void onRelease()
	{
		scrolling = false;
	}

	@Override
	public void setOffset(int x, int y)
	{
		xOffset = x;
		yOffset = y;
		contained.setOffset(x, y);
	}

	public void scrollTo(int sy)
	{
		if (scrolling)
		{
			scrollBarPos = Math.min(Math.max(0, sy - (y + yOffset)), height
					- SCROLL_BAR_HEIGHT);
			contained
					.setOffset(
							0,
							(int) -((contained.getHeight() - height + 20) * ((double)scrollBarPos / (height - SCROLL_BAR_HEIGHT))));
		}
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
		return x + xOffset + width;
	}

	@Override
	public int maxY()
	{
		return y + yOffset + height;
	}
}