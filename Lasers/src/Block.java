public class Block extends Collidable
{
	public Block(int x, int y)
	{
		super(x, y, false, false);
	}

	public Block(int x, int y, boolean b, boolean r)
	{
		super(x, y, b, r);
	}
	
	public Vector2 intersects(Ray r)
	{
		Vector2[] intersections = new Vector2[4];
		intersections[0] = r.intersects(new Vector2(getX(), getY()),
				new Vector2(0, 32));
		intersections[1] = r.intersects(new Vector2(getX(), getY()),
				new Vector2(32, 0));
		intersections[2] = r.intersects(new Vector2(getX() + 32, getY()),
				new Vector2(0, 32));
		intersections[3] = r.intersects(new Vector2(getX(), getY() + 32),
				new Vector2(32, 0));
		Vector2 closest = new Vector2(Double.POSITIVE_INFINITY,
				Double.POSITIVE_INFINITY);
		for (int point = 0; point < 4; point++)
		{
			if (Vector2.subtract(intersections[point], r.getPosition()).getLength() < Vector2
					.subtract(closest, r.getPosition()).getLength())
			{
				closest = intersections[point];
			}
		}
		return closest;
	}
}