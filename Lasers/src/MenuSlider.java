import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

public class MenuSlider implements MenuItem
{
	private final int SLIDER_HEIGHT = 20;
	private final int SLIDER_WIDTH = 10;
	private int x;
	private int y;

	private int length;
	private int distinctPositions;
	private int sliderPos;

	private Color sliderColour;
	private Color lineColour;

	private boolean clicked;

	public MenuSlider(int x, int y, int length, int noPositions, Color slider, Color line)
	{
		this.x = x;
		this.y = y;
		this.length = length;
		distinctPositions = noPositions;
		sliderPos = 0;
		clicked = false;
		sliderColour = slider;
		lineColour = line;
	}

	public int getPosition()
	{
		return (int) (distinctPositions * ((double) sliderPos / (length - SLIDER_WIDTH)));
	}

	@Override
	public void draw(Graphics g)
	{
		// Draw background line
		g.setColor(lineColour);
		((Graphics2D) g).setStroke(new BasicStroke(2));
		g.drawLine(x, y, x + length, y);
		
		// Draw slider
		g.setColor(sliderColour);
		g.fillRect(x + sliderPos - SLIDER_WIDTH / 2, y - SLIDER_HEIGHT / 2,
				SLIDER_WIDTH, SLIDER_HEIGHT);
	}

	@Override
	public boolean intersects(Point point)
	{
		return point.getX() >= x + sliderPos - SLIDER_WIDTH / 2
				&& point.getX() < x + sliderPos + SLIDER_WIDTH / 2
				&& point.getY() >= y - SLIDER_HEIGHT / 2
				&& point.getY() < y + SLIDER_HEIGHT / 2;
	}

	@Override
	public void onClick(Point point)
	{
		clicked = true;
	}

	@Override
	public void onRelease()
	{
		clicked = false;
	}

	public void slideTo(int clickX)
	{
		if (clicked)
		{
			sliderPos = Math.max(0, Math.min(clickX - SLIDER_WIDTH / 2 - x, length));
		}
	}

	@Override
	public void setOffset(int x, int y)
	{
		
	}

	@Override
	public int getWidth()
	{
		return length;
	}

	@Override
	public int getHeight()
	{
		return SLIDER_HEIGHT;
	}

	@Override
	public int minX()
	{
		return x;
	}

	@Override
	public int minY()
	{
		return 0;
	}

	@Override
	public int maxX()
	{
		return x + length;
	}

	@Override
	public int maxY()
	{
		return y + SLIDER_HEIGHT;
	}
}