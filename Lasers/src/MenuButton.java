/**
 * A button with text, which can be put on a menu, and can be extended to change behaviour on click
 * @author Callum Moseley
 * @version January 2015
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;

public abstract class MenuButton extends MenuLabel
{
	public MenuButton(int x, int y, int w, int h, String t, Color b, Color fc,
			Font f)
	{
		super(x, y, w, h, t, b, fc, f);
	}

	/**
	 * Executed when the button is clicked on
	 */
	@Override
	public abstract void onClick(Point point);

	@Override
	public abstract void onRelease();
}