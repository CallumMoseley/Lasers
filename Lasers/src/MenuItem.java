/**
 * A interface for any item on a menu
 * Examples: Buttons, check boxes, pictures, labels
 * @author Callum Moseley
 * @version January 2015
 *
 */

import java.awt.Graphics;
import java.awt.Point;

public interface MenuItem
{
	public void draw(Graphics g);
	
	public boolean intersects(Point point);
	
	public void onClick(Point point);
}
