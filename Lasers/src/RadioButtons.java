import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;


public class RadioButtons implements MenuItem
{
	private ArrayList<MenuButton> buttons = new ArrayList<MenuButton>();
	private int selected;
	
	@Override
	public void draw(Graphics g)
	{
		
	}

	@Override
	public boolean intersects(Point point)
	{
		return false;
	}

	@Override
	public void onClick(Point point)
	{
		for (int button = 0; button < buttons.size(); button++)
		{
			if (buttons.get(button).intersects(point))
			{
				selected = button;
				buttons.get(button).highlight();
			}
		}
	}
	
	public int getSelected()
	{
		return selected;
	}
}