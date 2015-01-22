/**
 * A rotatable mirror which reflects lasers and can be manipulated by the player
 * @author Callum Moseley
 * @version January 2015
 */

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

public class Mirror extends Collidable implements Placeable
{
	private final int WIDTH = 3;
	private final int LENGTH = 32;
	private int angle;
	private Vector2D normal;
	private boolean valid;

	/**
	 * Initialises as new mirror with the given position and angle
	 * @param x the x coordinate of the mirror
	 * @param y the y coordinate of the mirror
	 * @param angle the angle of the mirror
	 */
	public Mirror(int x, int y, int angle)
	{
		super(x, y);
		this.angle = angle;
		normal = new Vector2D(angle).getNormalized();
		valid = true;
	}

	public boolean isReflective()
	{
		return true;
	}

	public boolean isTarget()
	{
		return false;
	}

	public Vector2D intersects(Ray r)
	{
		// Calculates the end points of this vector
		Vector2D end1 = new Vector2D(angle + 90);
		end1 = end1.multiply(LENGTH / 2);
		Vector2D end2 = end1.multiply(-2);
		end1.addToThis(new Vector2D(getX(), getY()));

		// Find intersection
		return r.intersects(end1, end2);
	}

	public Vector2D reflect(Ray incident)
	{
		return Vector2D.reflect(incident.getDirection(), normal);
	}

	/**
	 * Gets the angle of this mirror
	 * @return the angle of this mirror
	 */
	public int getAngle()
	{
		return angle;
	}

	@Override
	public void draw(Graphics g)
	{
		// Calculates the two end points of this mirror
		Vector2D end1 = new Vector2D(angle + 90);
		end1 = end1.multiply(LENGTH / 2);
		Vector2D end2 = end1.multiply(-1);
		end1 = end1.add(new Vector2D(getX(), getY()));
		end2 = end2.add(new Vector2D(getX(), getY()));

		// Draws a thick line for the mirror
		((Graphics2D) g).setStroke(new BasicStroke(WIDTH));
		if (valid)
		{
			g.setColor(Color.WHITE);
		}
		else
		{
			g.setColor(Color.RED);
		}
		g.drawLine((int) end1.getX(), (int) end1.getY(), (int) end2.getX(),
				(int) end2.getY());
	}

	@Override
	public void draw(Graphics g, boolean drawAngle)
	{
		draw(g);
		if (drawAngle)
		{
			drawAngle(g);
		}
	}

	@Override
	public void drawAngle(Graphics g)
	{
		g.setFont(new Font("Consolas", 0, 15));
		
		int stringWidth = g.getFontMetrics().stringWidth("" + angle);
		int stringHeight = g.getFontMetrics().getHeight();
		
		// Calculate box for background
		int x = getX() - stringWidth / 2 - 5;
		int y = getY() - (LENGTH / 2) - stringHeight - 10;
		int w = stringWidth + 10;
		int h = stringHeight + 10;
		
		// Draw Background
		g.setColor(new Color(0, 0, 0, 90));
		g.fillRect(x, y, w, h);
		
		// Draw angle text
		g.setColor(Color.WHITE);
		g.drawString("" + angle, getX() - stringWidth / 2, getY()
				- (LENGTH / 2 + 10));
	}

	@Override
	public void rotate(int degrees)
	{
		angle += degrees;
		normal = new Vector2D(angle).getNormalized();
		angle = (angle + 180) % 180;
	}
	
	@Override
	public void moveRelative(int x, int y)
	{
		moveTo(getX() + x, getY() + y);
	}

	@Override
	public boolean intersects(Point click)
	{
		// Checks whether the click is within a LENGTH by LENGTH box centred on
		// the mirror
		return click.getX() >= getX() - LENGTH / 2
				&& click.getX() < getX() + LENGTH / 2
				&& click.getY() >= getY() - LENGTH / 2
				&& click.getY() < getY() + LENGTH / 2;
	}

	@Override
	public Vector2D intersects(Vector2D a, Vector2D b)
	{
		// Calculate the end points of this mirror
		Vector2D end1 = new Vector2D(angle + 90);
		end1 = end1.multiply(LENGTH / 2);
		Vector2D end2 = end1.multiply(-2);
		end1.addToThis(new Vector2D(getX(), getY()));

		// Check collision
		return Vector2D.intersects(end1, end2, a, b);
	}

	@Override
	public boolean intersects(Collidable c)
	{
		// Calculate the end points of this mirror
		Vector2D end1 = new Vector2D(angle + 90);
		end1 = end1.multiply(LENGTH / 2);
		Vector2D end2 = end1.multiply(-2);
		end1.addToThis(new Vector2D(getX(), getY()));

		// Check whether the collision point was finite, thus the objects
		// intersect
		Vector2D intersection = c.intersects(end1, end2);
		if (!(intersection.getX() == Double.POSITIVE_INFINITY)
				&& !(intersection.getY() == Double.POSITIVE_INFINITY))
		{
			return true;
		}
		return false;
	}

	@Override
	public void setValid(boolean b)
	{
		valid = b;
	}

	@Override
	public boolean isValid()
	{
		return valid;
	}
}