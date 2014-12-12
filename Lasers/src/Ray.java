import java.awt.Color;
import java.awt.Graphics;

public class Ray
{
	private double x;
	private double y;
	private Vector2 vector;
	
	public Ray(double x, double y, Vector2 v)
	{
		this.x = x;
		this.y = y;
		vector = v;
	}
	
	public Ray(double x, double y, double vx, double vy)
	{
		this.x = x;
		this.y = y;
		
		vector = new Vector2(vx, vy);
	}
	
	public double getX()
	{
		return x;
	}
	
	public double getY()
	{
		return y;
	}
	
	public Vector2 getVector()
	{
		return vector;
	}

	public void draw(Graphics g)
	{
		g.setColor(Color.RED);
		g.drawLine((int)x, (int)y, (int)(x + vector.getX()), (int)(y + vector.getY()));
	}
}