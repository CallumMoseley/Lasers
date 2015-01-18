/**
 * A generic container.  This can be use simply to reduce many MenuItems into a single item.
 * @author Callum Moseley
 * @version January 2015
 */

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

public class Container implements MenuItem
{
	private ArrayList<MenuItem> items = new ArrayList<MenuItem>();
	
	private int minX;
	private int minY;
	private int maxX;
	private int maxY;
	
	public Container()
	{
		items = new ArrayList<MenuItem>();

		minX = -1;
		minY = -1;
		maxX = -1;
		maxY = -1;
	}
	
	public void add(MenuItem item)
	{
		items.add(item);
		if (minX == -1 || item.minX() < minX)
		{
			minX = item.minX();
		}

		if (minY == -1 || item.minY() < minY)
		{
			minY = item.minY();
		}

		if (maxX == -1 || item.maxX() < maxX)
		{
			maxX = item.maxX();
		}

		if (maxY == -1 || item.maxY() < maxY)
		{
			maxY = item.maxY();
		}
	}

	public void add(MenuItem item, int index)
	{
		items.add(0, item);
		if (minX == -1 || item.minX() < minX)
		{
			minX = item.minX();
		}

		if (minY == -1 || item.minY() < minY)
		{
			minY = item.minY();
		}

		if (maxX == -1 || item.maxX() > maxX)
		{
			maxX = item.maxX();
		}

		if (maxY == -1 || item.maxY() > maxY)
		{
			maxY = item.maxY();
		}
	}

	@Override
	public void draw(Graphics g)
	{
		for (MenuItem item : items)
		{
			item.draw(g);
		}
	}

	@Override
	public boolean intersects(Point point)
	{
		return true;
	}

	@Override
	public void onClick(Point point)
	{
		for (MenuItem item : items)
		{
			if (item.intersects(point))
			{
				item.onClick(point);
			}
		}
	}

	@Override
	public void onRelease()
	{
		for (MenuItem item : items)
		{
			item.onRelease();
		}
	}

	@Override
	public void setOffset(int x, int y)
	{
		for (MenuItem item : items)
		{
			item.setOffset(x, y);
		}
	}

	@Override
	public int getWidth()
	{
		return maxX - minX;
	}

	@Override
	public int getHeight()
	{
		return maxY - minY;
	}

	@Override
	public int minX()
	{
		return minX;
	}

	@Override
	public int minY()
	{
		return minY;
	}

	@Override
	public int maxX()
	{
		return maxX;
	}

	@Override
	public int maxY()
	{
		return maxY;
	}
}