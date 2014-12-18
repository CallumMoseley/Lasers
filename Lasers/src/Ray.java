import java.awt.Color;
import java.awt.Graphics;

public class Ray
{
	private Vector2 pos;
	private Vector2 dir;

	public Ray(Vector2 p, Vector2 d)
	{
		pos = p;
		dir = d;
	}

	public Ray(double x, double y, double dx, double dy)
	{
		pos = new Vector2(x, y);
		dir = new Vector2(dx, dy);
	}

	/**
	 * Finds whether this ray intersects with the given block, and the point
	 * where it intersects
	 * @param b the block to check intersection with
	 * @return The point where this ray intersects the block, or a Vector2 of
	 *         infinite length if there is no intersection
	 */
	public Vector2 intersects(Block b)
	{
		Vector2[] edges = new Vector2[4];
		return new Vector2(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
	}

	public Vector2 intersects(Mirror mirror)
	{
		return new Vector2(dir.getX(), dir.getY());
	}

	public Vector2 getPosition()
	{
		return pos;
	}
	public Vector2 getDirection()
	{
		return dir;
	}

	public void draw(Graphics g)
	{
		g.setColor(Color.RED);
		g.drawLine((int) pos.getX(), (int) pos.getY(), (int) (pos.getX() + dir.getX()),
				(int) (pos.getY() + dir.getY()));
	}
}