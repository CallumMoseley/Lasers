/**
 * A collection of buttons, where only one can be selected at a time
 * @author Callum Moseley
 * @version January 2015
 */

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

public class RadioButtons implements MenuItem
{
	private ArrayList<MenuButton> buttons;
	private int selected;

	/**
	 * Initialises the radio buttons
	 */
	public RadioButtons()
	{
		buttons = new ArrayList<MenuButton>();
		selected = -1;
	}

	@Override
	public void draw(Graphics g)
	{
		for (int button = 0; button < buttons.size(); button++)
		{
			buttons.get(button).draw(g);
		}
	}

	@Override
	public boolean intersects(Point point)
	{
		for (int button = 0; button < buttons.size(); button++)
		{
			if (buttons.get(button).intersects(point))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public void onClick(Point point)
	{
		// Loop through each button, and check whether it was clicked
		for (int button = 0; button < buttons.size(); button++)
		{
			if (buttons.get(button).intersects(point))
			{
				// Click the button, un-highlight the previous selected button,
				// and highlight the new one
				buttons.get(button).onClick(point);
				if (selected != -1)
				{
					buttons.get(selected).unHighlight();
				}
				selected = button;
				buttons.get(button).highlight();
			}
		}
	}

	/**
	 * Gets which button is currently selected
	 * @return the index of the selected button, or -1 if no button is selected
	 */
	public int getSelected()
	{
		return selected;
	}

	/**
	 * Adds a new button to these radio buttons
	 * @param button the button to add
	 */
	public void add(MenuButton button)
	{
		buttons.add(button);
	}
}