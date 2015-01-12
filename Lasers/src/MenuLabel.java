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

	/**
	 * Initialises this label with position, size, text, colour, and font
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @param w the width of the label
	 * @param h the height of the label
	 * @param t the text on the label
	 * @param b the colour of the label's background
	 * @param fc the colour of the label's text
	 * @param f the font of the text on the label
	 */
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
		// Draw background, with colour either brighter or not based on whether
		// this object is highlighted
		g.setColor(highlighted ? backgroundColour.brighter() : backgroundColour);
		g.fillRect(x, y, width, height);
		
		// Calculate the centre of the label, and draw the text there
		g.setColor(fontColour);
		g.setFont(font);
		int strWidth = g.getFontMetrics().stringWidth(text);
		int strHeight = g.getFontMetrics().getHeight();
		g.drawString(text, x + width / 2 - strWidth / 2, y + height / 2
				+ strHeight / 4);
	}

	/**
	 * Highlights this label
	 */
	public void highlight()
	{
		highlighted = true;
	}

	/**
	 * Un-highlights this label
	 */
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