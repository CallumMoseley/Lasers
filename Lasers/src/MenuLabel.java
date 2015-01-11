import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;

public class MenuLabel implements MenuItem
{
	private int x;
	private int y;

	private int width;
	private int height;

	private String text;
	private Color backgroundColour;
	private Color fontColour;
	private Font font;

	private boolean highlighted;

	public MenuLabel(int x, int y, int w, int h, String t, Color b, Color fc,
			Font f)
	{
		this.x = x;
		this.y = y;

		width = w;
		height = h;

		text = t;
		backgroundColour = b;
		fontColour = fc;

		font = f;
	}

	@Override
	public void draw(Graphics g)
	{
		g.setColor(highlighted ? backgroundColour.brighter() : backgroundColour);
		g.fillRect(x, y, width, height);
		g.setColor(fontColour);
		g.setFont(font);
		int strWidth = g.getFontMetrics().stringWidth(text);
		int strHeight = g.getFontMetrics().getHeight();
		g.drawString(text, x + width / 2 - strWidth / 2, y + height / 2
				+ strHeight / 4);
	}

	public void highlight()
	{
		highlighted = true;
	}

	public void unHighlight()
	{
		highlighted = false;
	}

	@Override
	public boolean intersects(Point click)
	{
		return click.getX() >= x && click.getX() < x + width
				&& click.getY() >= y && click.getY() < y + height;
	}

	@Override
	public void onClick(Point point)
	{
	}
}