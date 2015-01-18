import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class LevelCompletionIndicator implements MenuItem
{
	private int x;
	private int y;
	private int xOffset;
	private int yOffset;
	private int width;
	private int height;
	private Level level;

	public LevelCompletionIndicator(int x, int y, int width, int height,
			Level level)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.level = level;
	}

	@Override
	public void draw(Graphics g)
	{
		Color drawColor = Color.RED;

		if (level.isComplete())
		{
			drawColor = Color.YELLOW;
			if (level.getTopScore() <= level.getOptimal())
			{
				drawColor = Color.GREEN;
			}
		}

		g.setColor(drawColor);
		g.fillRect(x + xOffset, y + yOffset, width, height);
	}

	@Override
	public boolean intersects(Point point)
	{
		return point.getX() >= x + xOffset && point.getX() < x + xOffset + width
				&& point.getY() >= y + yOffset && point.getY() < y + yOffset + height;
	}

	@Override
	public void onClick(Point point)
	{
	}

	@Override
	public void onRelease()
	{
	}

	@Override
	public void setOffset(int x, int y)
	{
		xOffset = x;
		yOffset = y;
	}

	@Override
	public int getWidth()
	{
		return width;
	}

	@Override
	public int getHeight()
	{
		return height;
	}

	@Override
	public int minX()
	{
		return x + xOffset;
	}

	@Override
	public int minY()
	{
		return y + yOffset;
	}

	@Override
	public int maxX()
	{
		return x + xOffset + width;
	}

	@Override
	public int maxY()
	{
		return y + yOffset + height;
	}
}