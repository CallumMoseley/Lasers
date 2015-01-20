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

	/**
	 * Invoked when the mouse is released
	 */
	public void onRelease();

	/**
	 * Sets the offset used for calculating clicks and drawing for this object,
	 * without actually moving it. Used for scrolling.
	 * @param x the amount to offset by in x
	 * @param y the amount to offset by in y
	 */
	public void setOffset(int x, int y);

	/**
	 * Gets the width of this item
	 * @return the width of this item
	 */
	public int getWidth();

	/**
	 * Gets the height of this item
	 * @return the height of this item
	 */
	public int getHeight();

	/**
	 * Gets the minimum x value of this item
	 * @return the minimum x value of this item
	 */
	public int minX();

	/**
	 * Gets the minimum y value of this item
	 * @return the minimum y value of this item
	 */
	public int minY();

	/**
	 * Gets the maximum x value of this item
	 * @return the maximum x value of this item
	 */
	public int maxX();

	/**
	 * Gets the maximum y value of this item
	 * @return the maximum y value of this item
	 */
	public int maxY();
}
