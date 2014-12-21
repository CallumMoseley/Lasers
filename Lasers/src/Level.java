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
	private final int NONE = 0;
	private final int BLOCK = 1;
	private final int MIRROR = 2;
	private final int TARGET = 3;
	private final int SQUARE_SIZE = 32;

	private ArrayList<Block> blocks;
	private ArrayList<Target> targets;
	private ArrayList<Mirror> mirrors;
	private ArrayList<LaserSource> sources;
	private ArrayList<Ray> laser;
	private boolean isSimulating;

	public Level()
	{
		blocks = new ArrayList<Block>();
		mirrors = new ArrayList<Mirror>();
		sources = new ArrayList<LaserSource>();
		laser = new ArrayList<Ray>();
		targets = new ArrayList<Target>();

		mirrors.add(new Mirror(500, 250, 30));
		mirrors.add(new Mirror(250, 410, 90));
		mirrors.add(new Mirror(120, 585, 10));

		isSimulating = false;
	}

	/**
	 * Simulates all lasers being emitted and reflecting off mirrors, until the
	 * laser hits an opaque object. Also finds whether the laser hit all
	 * targets.
	 * @return whether all targets were hit or not
	 */
	public boolean[] runLaser()
	{
		isSimulating = true;

		// Get the first laser segment, from the source
		laser.add(new Ray(new Vector2(sources.get(0).getX() + 14, sources
				.get(0).getY() + 15), new Vector2(
				sources.get(0).getDirection() * 90)));
		laser.get(0).getDirection().multiply(500);

		int objectHit = NONE;

		// Simulate the laser bouncing while it hits mirrors. Finds the point of
		// intersection first with all blocks, then all mirrors, then all
		// targets. The interaction that is handled will be the closest one to
		// the current laser segment's point.
		do
		{
			Ray current = laser.get(laser.size() - 1);
			Vector2 closest = new Vector2(Double.POSITIVE_INFINITY,
					Double.POSITIVE_INFINITY);
			Vector2 closestDifference = Vector2.subtract(closest,
					current.getDirection());

			objectHit = NONE;
			int mirrorHit = -1;

			// Check for block collisions
			for (int block = 0; block < blocks.size(); block++)
			{
				Vector2 collision = current.intersects(blocks.get(block));
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
						objectHit = BLOCK;
					}
				}
			}
			
			// Check for target collisions
			for (int target = 0; target < targets.size(); target++)
			{
				Vector2 collision = current.intersects(targets.get(target));
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
						objectHit = BLOCK;
					}
				}
			}

			// Check for mirror collisions
			for (int mirror = 0; mirror < mirrors.size(); mirror++)
			{
				Vector2 collision = current.intersects(mirrors.get(mirror));
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
						objectHit = MIRROR;
						mirrorHit = mirror;
					}
				}
			}

			current.getDirection().normalize();
			if (closestDifference.getLength() != Double.POSITIVE_INFINITY)
			{
				current.getDirection().multiply(closestDifference.getLength());
			}
			else
			{
				current.getDirection().multiply(1000);
			}

			if (objectHit == MIRROR)
			{
				// Calculate reflected ray
				Vector2 newDir = mirrors.get(mirrorHit).reflect(
						current.getDirection());
				Vector2 newPos = Vector2.add(current.getPosition(),
						current.getDirection());

				Ray newRay = new Ray(newPos, newDir);

				// Append the new ray to the current laser
				laser.add(newRay);
			}
		}
		while (objectHit == MIRROR);

		return new boolean[] { true };
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
						blocks.add(new Block(pos * SQUARE_SIZE, lineNo
								* SQUARE_SIZE));
					}
					// L's are laser sources
					else if (nextCh == 'L')
					{
						sources.add(new LaserSource(pos * SQUARE_SIZE, lineNo
								* SQUARE_SIZE, 0));
					}
					// T's are non-reflective targets
					else if (nextCh == 'T')
					{
						targets.add(new Target(pos * SQUARE_SIZE, lineNo
								* SQUARE_SIZE, false));
					}
					// R's are reflective targets
					else if (nextCh == 'R')
					{
						targets.add(new Target(pos * SQUARE_SIZE, lineNo
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

		// Uhh... don't mind this
		runLaser();
	}

	/**
	 * Draws the level and all of it's objects
	 * @param g the graphics object to draw with
	 */
	public void draw(Graphics g)
	{
		for (int block = 0; block < blocks.size(); block++)
		{
			blocks.get(block).draw(g);
		}
		for (int mirror = 0; mirror < mirrors.size(); mirror++)
		{
			mirrors.get(mirror).draw(g);
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