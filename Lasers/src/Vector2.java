/**
 * Represents a two dimensional vector.
 * 
 * @author Callum Moseley
 * @version December 2014
 */

public class Vector2
{
	private double x;
	private double y;

	/**
	 * Initialises a vector to [0,0]
	 */
	public Vector2()
	{
		x = 0;
		y = 0;
	}

	/**
	 * Initialises a vector to the given x and y
	 * @param x the x part of the vector
	 * @param y the y part of the vector
	 */
	public Vector2(double x, double y)
	{
		this.x = x;
		this.y = y;
	}

	/**
	 * Initialises a normalized vector along the given angle
	 * @param angle the angle of the vector
	 */
	public Vector2(double angle)
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
	public void add(Vector2 v)
	{
		x += v.x;
		y += v.y;
	}

	/**
	 * Subtracts a vector from this vector
	 * @param v the vector to subtract from this vector
	 */
	public void subtract(Vector2 v)
	{
		x -= v.x;
		y -= v.y;
	}

	/**
	 * Multiplies this vector by a scalar value
	 * @param scalar the scalar to multiply this vector by
	 */
	public void multiply(double scalar)
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
	public Vector2 getNormalized()
	{
		double length = getLength();

		return Vector2.multiply(this, 1 / length);
	}

	/**
	 * Gets the angle of this vector
	 * @return the angle of this vector
	 */
	public double getAngle()
	{
		return Math.toDegrees(Math.atan(y / x));
	}

	public Vector2 clone()
	{
		return new Vector2(x, y);
	}

	// Static methods

	/**
	 * Finds the normal of the given vector
	 * @param v the given vector
	 * @return the normal of the given vector
	 */
	public static Vector2 getNormalized(Vector2 v)
	{
		return v.getNormalized();
	}

	/**
	 * Finds the sum of two given vectors
	 * @param a the first vector
	 * @param b the second vector
	 * @return the sum of the two vectors
	 */
	public static Vector2 add(Vector2 a, Vector2 b)
	{
		return new Vector2(a.x + b.x, a.y + b.y);
	}

	/**
	 * Finds the difference of two given vectors
	 * @param a the first vector
	 * @param b the second vector
	 * @return the difference of the two vectors
	 */
	public static Vector2 subtract(Vector2 a, Vector2 b)
	{
		return new Vector2(a.x - b.x, a.y - b.y);
	}

	/**
	 * Finds the vector which is the given vector multiplied by a scalar
	 * @param v the given vector
	 * @param scalar the number to multiply the vector by
	 * @return the given vector multiylied by the scalar
	 */
	public static Vector2 multiply(Vector2 v, double scalar)
	{
		return new Vector2(v.x * scalar, v.y * scalar);
	}

	/**
	 * Finds the dot product of two given vectors
	 * @param a the first vector
	 * @param b the second vector
	 * @return the dot product of the two vectors
	 */
	public static double dotProduct(Vector2 a, Vector2 b)
	{
		return a.x * b.x + a.y * b.y;
	}

	/**
	 * Finds the cross product of two given vectors
	 * @param a the first vector
	 * @param b the second vector
	 * @return the cross product of the two vectors
	 */
	public static double crossProduct(Vector2 a, Vector2 b)
	{
		return a.x * b.y - a.y * b.x;
	}

	/**
	 * Finds the vector equal to the incident vector reflected over the normal
	 * @param incident the incident vector to reflect
	 * @param normal the normal vector to reflect the incident vector over
	 * @return the incident vector reflected over the normal
	 */
	public static Vector2 reflect(Vector2 incident, Vector2 normal)
	{
		return subtract(
				incident,
				multiply(normal.getNormalized(),
						2 * dotProduct(incident, normal.getNormalized())));
	}
}