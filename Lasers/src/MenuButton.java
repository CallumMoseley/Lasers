/**
 * A button, which can be put on a menu, and can be extended to change behaviour on click
 * @author Callum Moseley
 * @version January 2015
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;

public abstract class MenuButton implements MenuItem
{
	private int x;
	private int y;

	private int width;
	private int height;

	private String text;
	private Color background;
	private Font font;

	public MenuButton(int x, int y, int w, int h, String t, Color b, Font f)
	{
		this.x = x;
		this.y = y;

		width = w;
		height = h;

		text = t;
		background = b;
		font = f;
	}

	/**
	 * Finds whether the point clicked is inside this button
	 * @param click the point to check intersection with
	 * @return Whether the point clicked is inside this button
	 */
	public boolean intersects(Point click)
	{
		return click.getX() >= x && click.getX() < x + width
				&& click.getY() >= y && click.getY() < y + height;
	}

	/**
	 * Draws this button with the given graphics object
	 * @param g the graphics object to draw with
	 */
	public void draw(Graphics g)
	{
		g.setColor(background);
		g.fillRect(x, y, width, height);
		// TextHelper.drawString(x + 3, y + 3, text, Color.WHITE, g);
		g.setColor(Color.WHITE);
		g.setFont(font);
		int strWidth = g.getFontMetrics().stringWidth(text);
		int strHeight = g.getFontMetrics().getHeight();
		g.drawString(text, x + width / 2 - strWidth / 2, y + height / 2
				+ strHeight / 4);
	}

	/**
	 * Executed when the button is clicked on
	 */
	public abstract void onClick();
}