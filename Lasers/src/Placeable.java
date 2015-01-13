import java.awt.Graphics;
import java.awt.Point;

public interface Placeable
{
	public void	moveTo(int x, int y);
	public void rotateCCW(int degrees);
	public void rotateCW(int degrees);
	public boolean intersects(Point click);
	public int getAngle();
	public void draw(Graphics g);
}