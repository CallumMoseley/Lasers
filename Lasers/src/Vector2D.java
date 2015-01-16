/**
 * Represents a two dimensional vector.
 * 
 * @author Callum Moseley
 * @version December 2014
 */

public class Vector2D implements Comparable<Vector2D>
{
	private double x;
	private double y;

	/**
	 * Initialises a vector to [0,0]
	 */
	public Vector2D()
	{
		x = 0;
		y = 0;
	}

	/**
	 * Initialises a vector to the given x and y
	 * @param x the x part of the vector
	 * @param y the y part of the vector
	 */
	public Vector2D(double x, double y)
	{
		this.x = x;
		this.y = y;
	}

	/**
	 * Initialises a normalized vector along the given angle
	 * @param angle the angle of the vector
	 */
	public Vector2D(double angle)
	{
		y = Math.sin(Math.toRadians(angle));
		x = Math.cos(Math.toRadians(angle));
	}

	/**
	 * Gets the x part of this vector
	 * @return the x part of this vector
	 */
	public double getX()
	{
		return x;
	}

	/**
	 * Gets the y part of this vector
	 * @return the y part of this vector
	 */
	public double getY()
	{
		return y;
	}

	/**
	 * Adds a vector to this vector
	 * @param v the vector to add to this vector
	 */
	public Vector2D add(Vector2D v)
	{
		return new Vector2D(x + v.x, y + v.y);
	}
	
	public void addToThis(Vector2D v)
	{
		x += v.x;
		y += v.y;
	}

	/**
	 * @param v the vector to subtract from this vector
	 */
	public Vector2D subtract(Vector2D v)
	{
		return new Vector2D(x - v.x, y - v.y);
	}

	/**
	 * Multiplies this vector by a scalar value
	 * @param scalar the scalar to multiply this vector by
	 */
	public Vector2D multiply(double scalar)
	{
		return new Vector2D(x * scalar, y * scalar);
	}

	public void multiplyBy(double scalar)
	{
		x *= scalar;
		y *= scalar;
	}

	/**
	 * Gets the length of this vector
	 * @return the length of this vector
	 */
	public double getLength()
	{
		return Math.sqrt(x * x + y * y);
	}

	/**
	 * Normalizes this vector (sets length to 1)
	 */
	public void normalize()
	{
		double length = getLength();
		this.multiply(1 / length);
	}

	/**
	 * Gets the normal of this vector
	 * @return the normal of this vector
	 */
	public Vector2D getNormalized()
	{
		double length = getLength();

		return multiply(1 / length);
	}

	/**
	 * Gets the angle of this vector
	 * @return the angle of this vector
	 */
	public double getAngle()
	{
		return Math.toDegrees(Math.atan(y / x));
	}

	public Vector2D clone()
	{
		return new Vector2D(x, y);
	}

	@Override
	public int compareTo(Vector2D o)
	{
		return ((Double) getLength()).compareTo(o.getLength());
	}

	/**
	 * Finds the dot product of two given vectors
	 * @param a the first vector
	 * @param b the second vector
	 * @return the dot product of the two vectors
	 */
	public static double dotProduct(Vector2D a, Vector2D b)
	{
		return a.x * b.x + a.y * b.y;
	}

	/**
	 * Finds the cross product of two given vectors
	 * @param a the first vector
	 * @param b the second vector
	 * @return the cross product of the two vectors
	 */
	public static double crossProduct(Vector2D a, Vector2D b)
	{
		return a.x * b.y - a.y * b.x;
	}

	/**
	 * Finds the vector equal to the incident vector reflected over the normal
	 * @param incident the incident vector to reflect
	 * @param normal the normal vector to reflect the incident vector over
	 * @return the incident vector reflected over the normal
	 */
	public static Vector2D reflect(Vector2D incident, Vector2D normal)
	{
		return incident.subtract(normal.getNormalized().multiply(
				2 * dotProduct(incident, normal.getNormalized())));
	}
}