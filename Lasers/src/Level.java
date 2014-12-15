import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Level
{
	private final int SQUARE_SIZE = 32;

	private ArrayList<Block> blocks;
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

		isSimulating = false;
	}

	public boolean runLaser()
	{
		isSimulating = true;

		// Get the first laser segment, from the source
		laser.add(new Ray(new Vector2(sources.get(0).getX() + 14, sources
				.get(0).getY() + 15), new Vector2(
				sources.get(0).getDirection() * 90)));
		laser.get(0).getDirection().multiply(500);

		Ray current = laser.get(0);
		boolean isMirror = true;

		// Simulate the laser bouncing while it hits mirrors
		while (isMirror)
		{
			isMirror = false;
			boolean isTarget = false;

			Vector2 closest = new Vector2(Double.POSITIVE_INFINITY,
					Double.POSITIVE_INFINITY);
			Vector2 closestDifference = Vector2.subtract(closest,
					current.getDirection());

			// Check for block collisions
			for (int block = 0; block < blocks.size(); block++)
			{
				Vector2 collision = current.intersects(blocks.get(block));
				if (!collision.equals(new Vector2(Double.POSITIVE_INFINITY,
						Double.POSITIVE_INFINITY)))
				{
					Vector2 difference = Vector2.subtract(collision,
							current.getPosition());
					if (difference.getLength() < Vector2.subtract(closest,
							current.getPosition()).getLength())
					{
						closest = collision;
						closestDifference = difference;
					}
				}
			}

			current.getDirection().normalize();
			current.getDirection().multiply(closestDifference.getLength());

			if (isMirror)
			{
				// Calculate reflection
				// Add reflected ray to laser
			}
		}

		return true;
	}

	public void loadLevel(String file)
	{
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = br.readLine();
			int lineNo = 0;
			while (line != null && !line.equals(""))
			{
				for (int pos = 0; pos < line.length(); pos++)
				{
					char nextCh = line.charAt(pos);
					if (nextCh == 'X')
					{
						blocks.add(new Block(pos * SQUARE_SIZE, lineNo
								* SQUARE_SIZE));
					}
					else if (nextCh == 'L')
					{
						sources.add(new LaserSource(pos * SQUARE_SIZE, lineNo
								* SQUARE_SIZE, 0));
					}
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
		runLaser();
	}

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
		for (int laserSeg = 0; laserSeg < laser.size(); laserSeg++)
		{
			laser.get(laserSeg).draw(g);
		}
	}
}