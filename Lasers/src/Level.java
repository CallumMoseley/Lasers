/**
 * Contains all the data for a the current level loaded in the game, as well as handling running logic
 * 
 * @author Callum Moseley
 * @version January 2015
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import javax.imageio.ImageIO;

public class Level
{
	private final int ITERATION_LIMIT = 10000;
	private static Image background;

	private String name;
	private ArrayList<Collidable> collidable;
	private ArrayList<Placeable> placeable;
	private ArrayList<LaserSource> sources;
	private ArrayList<Ray> laser;
	private int topScore;
	private boolean completed;
	private boolean isSimulating;
	private int optimal;

	/**
	 * Initialises the level to be blank
	 */
	public Level()
	{
		collidable = new ArrayList<Collidable>();
		placeable = new ArrayList<Placeable>();
		sources = new ArrayList<LaserSource>();
		laser = new ArrayList<Ray>();

		topScore = -1;
		completed = false;

		isSimulating = false;
	}

	/**
	 * Initialises the level, and loads a level from the given file
	 * @param file the path to the file to load the level from
	 */
	public Level(File file)
	{
		collidable = new ArrayList<Collidable>();
		placeable = new ArrayList<Placeable>();
		sources = new ArrayList<LaserSource>();
		laser = new ArrayList<Ray>();

		topScore = -1;
		completed = false;

		this.loadLevel(file);

		isSimulating = false;
	}

	/**
	 * Simulates all lasers being emitted and reflecting off mirrors, until the
	 * laser hits an opaque object. Also finds whether the laser hit all
	 * targets.
	 */
	public boolean runLaser()
	{
		AudioHandler.startLaser();
		isSimulating = true;

		Queue<Ray> unprocessedLasers = new LinkedList<Ray>();

		// Put all of the initial lasers in the queue to be processed
		for (int source = 0; source < sources.size(); source++)
		{
			// Get the first laser segment, from the source
			Ray newLaser = new Ray(new Vector2D(sources.get(source).getX(),
					sources.get(source).getY()), new Vector2D(sources.get(
					source).getDirection() * 90));

			// Add the offset, so the laser is coming out of the right part of
			// the laser source
			newLaser.getPosition().addToThis(sources.get(source).getOffset());

			unprocessedLasers.add(newLaser);
		}

		// Used to avoid crashes from beam splitter loops
		int iterationCount = 0;

		// Simulate the laser bouncing while it hits reflective objects.
		// Finds the point of intersection with all collidable objects. The
		// interaction that is handled will be the closest one to the
		// current laser segment's position.
		while (!unprocessedLasers.isEmpty() && iterationCount < ITERATION_LIMIT)
		{
			int objectHit;

			Ray current = unprocessedLasers.poll();
			Vector2D closest = new Vector2D(Double.POSITIVE_INFINITY,
					Double.POSITIVE_INFINITY);
			Vector2D closestDifference = closest.subtract(current
					.getDirection());

			objectHit = -1;

			// Check for collisions with each object, updating the closest
			// so far along the way
			for (int object = 0; object < collidable.size(); object++)
			{
				Vector2D collision = collidable.get(object).intersects(
						current);
				if (collision.getLength() != Double.POSITIVE_INFINITY)
				{
					Vector2D difference = collision.subtract(current
							.getPosition());
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

			if (objectHit >= 0)
			{
				collidable.get(objectHit).hit();

				// Set the current laser segment to be the correct length
				current.getDirection().normalize();
				if (closestDifference.getLength() != Double.POSITIVE_INFINITY)
				{
					current.getDirection().multiplyBy(
							closestDifference.getLength());
				}

				// Add the processed laser to the current laser
				laser.add(current);

				// Reflect the laser if necessary
				if (collidable.get(objectHit).isReflective())
				{
					// Calculate reflected ray
					Vector2D newDir = collidable.get(objectHit)
							.reflect(current).getNormalized();
					Vector2D newPos = closest;

					Ray newRay = new Ray(newPos, newDir);

					// Queue the new laser to be processed
					unprocessedLasers.add(newRay);
				}
				if (collidable.get(objectHit).isTransparent())
				{
					// Calculate new ray
					Vector2D newDir = current.getDirection().getNormalized();
					Vector2D newPos = closest;

					Ray newRay = new Ray(newPos, newDir);

					// Queue the new laser to be processed
					unprocessedLasers.add(newRay);
				}
			}
			else
			{
				current.getDirection().multiplyBy(10000);
				laser.add(current);
			}
			iterationCount++;
		}

		// Finds whether all targets were hit
		boolean allHit = true;
		for (int object = 0; object < collidable.size(); object++)
		{
			if (collidable.get(object).isTarget()
					&& !collidable.get(object).getHit())
			{
				allHit = false;
			}
		}
		completed = false;
		if (allHit)
		{
			completed = true;
			if (placeable.size() < topScore || topScore == -1)
			{
				topScore = placeable.size();
			}
		}

		return completed;
	}

	/**
	 * Stops the laser simulation
	 */
	public void stopLaser()
	{
		AudioHandler.stopLaser();
		laser.clear();
		isSimulating = false;
		for (int object = 0; object < collidable.size(); object++)
		{
			collidable.get(object).unHit();
		}
	}

	public boolean isSimulating()
	{
		return isSimulating;
	}

	public boolean isComplete()
	{
		return completed;
	}

	/**
	 * Adds the given mirror to this level
	 * @param mirror the mirror to add
	 */
	public void addPlaceable(Placeable object)
	{
		collidable.add((Collidable) object);
		placeable.add(object);
	}

	public void removePlaceable(Placeable object)
	{
		collidable.remove(object);
		placeable.remove(object);
	}

	public int getTopScore()
	{
		return topScore;
	}

	public Placeable getClicked(Point click)
	{
		for (int object = 0; object < placeable.size(); object++)
		{
			if (placeable.get(object).intersects(click))
			{
				return placeable.get(object);
			}
		}
		return null;
	}

	public void clearPlaced()
	{
		for (Placeable p : placeable)
		{
			collidable.remove(p);
		}
		placeable.clear();
	}

	/**
	 * Gets the name of the level
	 * @return the name of the level
	 */
	public String getName()
	{
		return name;
	}

	public int getOptimal()
	{
		return optimal;
	}

	public int getObjectsUsed()
	{
		return placeable.size();
	}

	public boolean loadLevel(File file)
	{
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(file));
			name = br.readLine();
			optimal = Integer.parseInt(br.readLine());
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
						collidable.add(new Block(pos * 32, lineNo
								* 32));
					}
					// >, V, <, and ^ are laser sources in different directions
					else if (">V<^".indexOf(nextCh) >= 0)
					{
						sources.add(new LaserSource(pos * 32, lineNo
								* 32, ">V<^".indexOf(nextCh)));
					}
					// T's are non-reflective targets
					else if (nextCh == 'T')
					{
						collidable.add(new Target(pos * 32, lineNo
								* 32, false));
					}
					// R's are reflective targets
					else if (nextCh == 'R')
					{
						collidable.add(new Target(pos * 32, lineNo
								* 32, true));
					}
					// Anything else is blank
				}
				line = br.readLine();
				lineNo++;
			}
			br.close();
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Loads a level from a file
	 * @param file the filename of the file containing the level
	 */
	public boolean loadLevel(String file)
	{
		return loadLevel(new File(file));
	}

	/**
	 * Draws the level and all of it's objects
	 * @param g the graphics object to draw with
	 */
	public void draw(Graphics g)
	{
		// Draw the background
		for (int row = 0; row < 24; row++)
		{
			for (int col = 0; col < 24; col++)
			{
				g.drawImage(background, row * 32, col * 32,
						null);
			}
		}

		// If the level is currently simulating, draw the laser
		if (isSimulating)
		{
			for (int laserSeg = 0; laserSeg < laser.size(); laserSeg++)
			{
				laser.get(laserSeg).draw(g);
			}
		}

		// Draw all collidable objects
		for (int object = 0; object < collidable.size(); object++)
		{
			collidable.get(object).draw(g);
		}

		// Draw all laser sources
		for (int source = 0; source < sources.size(); source++)
		{
			sources.get(source).draw(g);
		}
		
		// Draw title
		g.setFont(new Font("Consolas", 0, 25));
		g.setColor(new Color(0, 0, 0, 90));
		g.fillRect(0, 0, g.getFontMetrics().stringWidth(name) + 20, g.getFontMetrics().getHeight() + 15);
		
		g.setColor(Color.WHITE);
		g.drawString(name, 10, g.getFontMetrics().getHeight());
	}

	/**
	 * Draws a smaller version of the level at the given position and scale
	 * @param g the graphics context to draw with
	 * @param x the x position of the preview
	 * @param y the y position of the preview
	 * @param scale the scale of the preview, 1 is full size
	 */
	public void drawPreview(Graphics g, int x, int y, double scale)
	{
		// This image is used to store a picture of the level
		BufferedImage level = new BufferedImage(24 * 32, 24 * 32,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D imageGraphics = (Graphics2D) level.getGraphics();

		// Add objects to the level picture
		for (int row = 0; row < 24; row++)
		{
			for (int col = 0; col < 24; col++)
			{
				imageGraphics.drawImage(background, row * 32, col
						* 32, null);
			}
		}
		for (int object = 0; object < collidable.size(); object++)
		{
			collidable.get(object).draw(imageGraphics);
		}
		for (int source = 0; source < sources.size(); source++)
		{
			sources.get(source).draw(imageGraphics);
		}

		// Scale and position the preview
		AffineTransform scaleDown = new AffineTransform();
		scaleDown.scale(scale, scale);
		scaleDown.translate(x, y);

		((Graphics2D) g).drawImage(level, scaleDown, null);
	}

	public void updatePlacementValid()
	{
		for (Placeable p : placeable)
		{
			p.setValid(true);
			for (Collidable c : collidable)
			{
				if (p != c && p.intersects(c))
				{
					p.setValid(false);
				}
			}
		}
	}

	public boolean allPlacementsValid()
	{
		for (Placeable p : placeable)
		{
			if (!p.isValid())
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * Loads the static background tile for all levels
	 * @param file the path to the image file with the background
	 */
	public static void loadBackground(String file)
	{
		try
		{
			background = ImageIO.read(new File(file));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}