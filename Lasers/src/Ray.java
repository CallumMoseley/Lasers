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

	/**
	 * 
	 * @param mirror
	 * @return
	 */
	public Vector2 intersects(Mirror mirror)
	{
		// For reference: http://stackoverflow.com/questions/563198/how-do-you-detect-where-two-line-segments-intersect/565282#565282
		
		Vector2 p = pos;
		Vector2 r = dir;
		
		Vector2 q = new Vector2(mirror.getX(), mirror.getY());
		Vector2 s = Vector2.multiply(new Vector2(mirror.getAngle() + 45), Math.sqrt(32 * 32 + 32 * 32));
		
		double t = Vector2.crossProduct(Vector2.subtract(q, p), Vector2.multiply(s, 1.0 / Vector2.crossProduct(r, s)));
		double u = Vector2.crossProduct(Vector2.subtract(q, p), Vector2.multiply(r, 1.0 / Vector2.crossProduct(r, s)));
		
		if (Vector2.crossProduct(r, s) != 0 && u <= 1 && u >= 0)
		{
			return new Vector2(pos.getX() + t * r.getX(), pos.getY() + t * r.getY());
		}
		
		return new Vector2(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
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