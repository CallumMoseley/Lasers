/**
 * A interface for any item on a menu
 * Examples: Buttons, check boxes, pictures, labels
 * @author Callum Moseley
 * @version January 2015
 *
 */

import java.awt.Graphics;
import java.awt.Point;

public interface MenuItem
{
	/**
	 * Draws this menu item in the given graphics context
	 * @param g the graphics context to draw with
	 */
	public void draw(Graphics g);
	
	/**
	 * Finds whether the given point intersects with this menu item
	 * @param point the point to check intersection with
	 * @return whether the point intersects with this item
	 */
	public boolean intersects(Point point);
	
	/**
	 * Invoked when this item is clicked on
	 * @param point the point at which this item was clicked on
	 */
	public void onClick(Point point);
}
