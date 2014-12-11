public class Vector2
{
	private double x;
	private double y;
	
	public Vector2()
	{
		x = 0;
		y = 0;
	}
	
	public Vector2(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public double getX()
	{
		return x;
	}
	
	public double getY()
	{
		return y;
	}
	
	public Vector2(double angle)
	{
		y = Math.sin(angle);
		x = Math.cos(angle);
	}
	
	public void add(Vector2 v)
	{
		x += v.x;
		y += v.y;
	}
	
	public void subtract(Vector2 v)
	{
		x -= v.x;
		y -= v.y;
	}
	
	public void multiply(double scalar)
	{
		x *= scalar;
		y *= scalar;
	}
	
	public double dotProduct(Vector2 operand)
	{
		return x * operand.x + y * operand.y;
	}
	
	public double getLength()
	{
		return Math.sqrt(x * x + y * y);
	}
	
	public void normalize()
	{
		double length = getLength();
		
		x /= length;
		y /= length;
	}
	
	public Vector2 getNormalized()
	{
		double length = getLength();
		
		return new Vector2(x / length, y / length);
	}
	
	public double getAngle()
	{
		return Math.toDegrees(Math.atan(y / x));
	}
	
	// Static methods
	
	public static Vector2 getNormalized(Vector2 v)
	{
		return v.getNormalized();
	}
	
	public static Vector2 add(Vector2 a, Vector2 b)
	{
		return new Vector2(a.x + b.x, a.y + b.y);
	}
	
	public static Vector2 subtract(Vector2 a, Vector2 b)
	{
		return new Vector2(a.x - b.x, a.y - b.y);
	}
	
	public static Vector2 multiply(Vector2 v, double scalar)
	{
		return new Vector2(v.x * scalar, v.y * scalar);
	}
	
	public static double dotProduct(Vector2 a, Vector2 b)
	{
		return a.dotProduct(b);
	}
	
	public static Vector2 reflect(Vector2 incident, Vector2 normal)
	{
		return subtract(incident, multiply(normal.getNormalized(), 2 * dotProduct(incident, normal.getNormalized())));
	}
}