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
	
	private int minX;
	private int minY;
	private int maxX;
	private int maxY;

	/**
	 * Initialises the radio buttons
	 */
	public RadioButtons()
	{
		buttons = new ArrayList<MenuButton>();
		selected = -1;
		
		minX = -1;
		minY = -1;
		maxX = -1;
		maxY = -1;
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
	public void drawOffset(Graphics g, int x, int y)
	{
		for (int button = 0; button < buttons.size(); button++)
		{
			buttons.get(button).drawOffset(g, x, y);
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
		
		// Update min and max coordinate values
		if (minX == -1 || button.minX() < minX)
		{
			minX = button.minX();
		}
		if (minY == -1 || button.minY() < minY)
		{
			minY = button.minY();
		}
		if (maxX == -1 || button.maxX() > maxX)
		{
			maxX = button.maxX();
		}
		if (maxY == -1 || button.maxY() > maxY)
		{
			maxY = button.maxY();
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