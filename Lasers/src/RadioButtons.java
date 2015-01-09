import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;


public class RadioButtons implements MenuItem
{
	private ArrayList<MenuButton> buttons = new ArrayList<MenuButton>();
	private int selected;
	
	public RadioButtons()
	{
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
		for (int button = 0; button < buttons.size(); button++)
		{
			if (buttons.get(button).intersects(point))
			{
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
	
	public int getSelected()
	{
		return selected;
	}

	public void add(MenuButton button)
	{
		buttons.add(button);
	}
}