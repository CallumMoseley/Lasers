/**
 * Represents a collection of MenuItems
 * @author Callum Moseley
 * @version January 2015
 *
 */

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

public class Menu
{
	ArrayList<MenuItem> items;

	public Menu()
	{
		items = new ArrayList<MenuItem>();
	}

	/**
	 * Draws this Menu with the given graphics object
	 * @param g the graphics object to draw with
	 */
	public void draw(Graphics g)
	{
		// Draw each individual MenuItem
		for (int item = 0; item < items.size(); item++)
		{
			items.get(item).draw(g);
		}
	}

	public void click(Point point)
	{
		for (int item = 0; item < items.size(); item++)
		{
			try
			{
				if (items.get(item).intersects(point))
				{
					items.get(item).onClick();
				}
			}
			catch (Exception e)
			{
	
			}
		}
	}

	public void add(MenuItem menuItem)
	{
		items.add(menuItem);
	}
}