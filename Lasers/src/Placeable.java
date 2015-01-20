import java.awt.Graphics;
import java.awt.Point;

public interface Placeable
{
	/**
	 * Moves this object to the given position
	 * @param x the new x coordinate
	 * @param y the new y coordinate
	 */
	public void moveTo(int x, int y);

	/**
	 * Rotates this object the given number of degrees. Positive numbers are
	 * clockwise, negative numbers are counter-clockwise
	 * @param degrees the number of degrees to rotate this
	 */
	public void rotate(int degrees);

	/**
	 * Finds whether a click at the given point is on this object
	 * @param click the point at which the player clicked
	 * @return whether the click was on this object
	 */
	public boolean intersects(Point click);

	/**
	 * Finds whether this object intersecting a Collidable object
	 * @param c the object to check intersection with
	 * @return whether this object intersects the given object
	 */
	public boolean intersects(Collidable c);

	/**
	 * Sets this object to be validly placed or not
	 * @param b the new validity
	 */
	public void setValid(boolean b);

	/**
	 * Finds whether this object is validly placed
	 * @return whether this object is validly placed
	 */
	public boolean isValid();

	/**
	 * Gets the current angle of this object
	 * @return the current angle of this object
	 */
	public int getAngle();

	/**
	 * Draws this object with the given graphics context
	 * @param g the graphics context to draw with
	 */
	public void draw(Graphics g);

	/**
	 * Draws this object with the given graphics context, and the angle of this
	 * object if specified
	 * @param g the graphics context to draw with
	 * @param angle whether or not to draw the angle of this object above it
	 */
	public void draw(Graphics g, boolean angle);

	/**
	 * Draws the angle of this object above
	 * @param g the graphics context to draw with
	 */
	public void drawAngle(Graphics g);
}