/**
 * Represents a collection of MenuItems which are displayed at the same time
 * @author Callum Moseley
 * @version January 2015
 */

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

public class Menu
{
	ArrayList<MenuItem> items;

	/**
	 * Initialise a blank menu
	 */
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

	/**
	 * Passes the click to each menu item
	 * @param point the point that was clicked
	 */
	public void click(Point point)
	{
		// For every item, see whether the point intersects with it, and if so,
		// run it's onClick method
		for (int item = 0; item < items.size(); item++)
		{
			if (items.get(item).intersects(point))
			{
				items.get(item).onClick(point);
			}
		}
	}

	/**
	 * Passes the mouse release to each item on this menu
	 */
	public void release()
	{
		// For every item, call onRelease()
		for (int item = 0; item < items.size(); item++)
		{
			items.get(item).onRelease();
		}
	}

	/**
	 * Adds a new menu item to this menu
	 * @param menuItem the menu item to add to this menu
	 */
	public void add(MenuItem menuItem)
	{
		items.add(menuItem);
	}
}