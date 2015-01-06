/**
 * Contains all the data for a the current level loaded in the game, as well as handling running logic
 * 
 * @author Callum Moseley
 * @version December 2014
 */

import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Level
{
	private final int SQUARE_SIZE = 32;

	private ArrayList<Collidable> collidable;
	private ArrayList<LaserSource> sources;
	private ArrayList<Ray> laser;
	private boolean isSimulating;
	private Mirror selected;

	public Level()
	{
		collidable = new ArrayList<Collidable>();
		sources = new ArrayList<LaserSource>();
		laser = new ArrayList<Ray>();
		
		collidable.add(new Mirror(100, 65, -7));
		collidable.add(new Mirror(300, 700, 132));
		collidable.add(new Mirror(490, 700, 143));

		isSimulating = false;
	}

	/**
	 * Simulates all lasers being emitted and reflecting off mirrors, until the
	 * laser hits an opaque object. Also finds whether the laser hit all
	 * targets.
	 * @return whether all targets were hit or not
	 */
	public boolean runLaser()
	{
		isSimulating = true;
		laser = new ArrayList<Ray>();

		for (int source = 0; source < sources.size(); source++)
		{
			// Get the first laser segment, from the source
			laser.add(new Ray(new Vector2(sources.get(source).getX(), sources
					.get(source).getY()), new Vector2(sources.get(source)
					.getDirection() * 90)));

			// Add the offset to the end of the laser source
			laser.get(laser.size() - 1).getPosition()
					.add(sources.get(source).getOffset());

			int objectHit;

			// Simulate the laser bouncing while it hits mirrors. Finds the
			// point of intersection with all collidable objects. The
			// interaction that is handled will be the closest one to the
			// current laser segment's point.
			do
			{
				Ray current = laser.get(laser.size() - 1);
				Vector2 closest = new Vector2(Double.POSITIVE_INFINITY,
						Double.POSITIVE_INFINITY);
				Vector2 closestDifference = Vector2.subtract(closest,
						current.getDirection());

				objectHit = -1;

				// Check for collisions with each object
				for (int object = 0; object < collidable.size(); object++)
				{
					Vector2 collision = collidable.get(object).intersects(
							current);
					if (collision.getLength() != Double.POSITIVE_INFINITY)
					{
						Vector2 difference = Vector2.subtract(collision,
								current.getPosition());
						if (difference.getLength() > 1
								&& difference.getLength() < closestDifference
										.getLength())
						{
							closest = collision;
							closestDifference = difference;
							objectHit = object;
						}
					}
				}

				collidable.get(objectHit).hit();

				current.getDirection().normalize();
				if (closestDifference.getLength() != Double.POSITIVE_INFINITY)
				{
					current.getDirection().multiply(
							closestDifference.getLength());
				}
				else
				{
					current.getDirection().multiply(1000);
				}

				if (objectHit >= 0 && collidable.get(objectHit).isReflective())
				{
					// Calculate reflected ray
					Vector2 newDir = collidable.get(objectHit).reflect(current);
					Vector2 newPos = Vector2.add(current.getPosition(),
							current.getDirection());

					Ray newRay = new Ray(newPos, newDir);

					// Append the new ray to the current laser
					laser.add(newRay);
				}
			}
			while (objectHit >= 0 && collidable.get(objectHit).isReflective());
		}

		for (int object = 0; object < collidable.size(); object++)
		{
			if (collidable.get(object).isTarget()
					&& !collidable.get(object).getHit())
			{
				return false;
			}
		}
		return true;
	}
	
	public void stopLaser()
	{
		isSimulating = false;
		for (int object = 0; object < collidable.size(); object++)
		{
			collidable.get(object).unHit();
		}
	}

	/**
	 * Loads a level from a file
	 * @param file the filename of the file containing the level
	 */
	public void loadLevel(String file)
	{
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = br.readLine();
			int lineNo = 0;
			// Read every line and process its characters
			while (line != null && !line.equals(""))
			{
				// Read every character, and add the object
				for (int pos = 0; pos < line.length(); pos++)
				{
					char nextCh = line.charAt(pos);
					// X's are solid blocks
					if (nextCh == 'X')
					{
						collidable.add(new Block(pos * SQUARE_SIZE, lineNo
								* SQUARE_SIZE));
					}
					// >, V, <, and ^ are laser sources in different directions
					else if (">V<^".indexOf(nextCh) >= 0)
					{
						sources.add(new LaserSource(pos * SQUARE_SIZE, lineNo
								* SQUARE_SIZE, ">V<^".indexOf(nextCh)));
					}
					// T's are non-reflective targets
					else if (nextCh == 'T')
					{
						collidable.add(new Target(pos * SQUARE_SIZE, lineNo
								* SQUARE_SIZE, false));
					}
					// R's are reflective targets
					else if (nextCh == 'R')
					{
						collidable.add(new Target(pos * SQUARE_SIZE, lineNo
								* SQUARE_SIZE, true));
					}
					// Anything else is blank
				}
				line = br.readLine();
				lineNo++;
			}
			br.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Draws the level and all of it's objects
	 * @param g the graphics object to draw with
	 */
	public void draw(Graphics g)
	{
		for (int object = 0; object < collidable.size(); object++)
		{
			collidable.get(object).draw(g);
		}
		for (int source = 0; source < sources.size(); source++)
		{
			sources.get(source).draw(g);
		}
		if (isSimulating)
		{
			for (int laserSeg = 0; laserSeg < laser.size(); laserSeg++)
			{
				laser.get(laserSeg).draw(g);
			}
		}
	}
}