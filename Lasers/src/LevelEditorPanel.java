/**
 * A level editor, for graphically creating levels for Lasers
 * @author Callum Moseley
 * @version January 2015
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class LevelEditorPanel extends JPanel implements MouseListener,
		MouseMotionListener, KeyListener
{
	private char[][] level;
	private char[][] lastLevel;
	private String name;
	private int optimal;
	private Tile[] tiles;
	private int selectedTile;

	/**
	 * Initialises the tiles and panel
	 */
	public LevelEditorPanel()
	{
		setPreferredSize(new Dimension(1024, 768));

		level = new char[24][24];
		lastLevel = new char[24][24];
		name = "Untitled";
		
		tiles = new Tile[8];
		try
		{
			// Load all tiles that can be placed in a level
			tiles[0] = new Tile(ImageIO.read(new File("gfx/background.png")),
					new Rectangle(840, 200, 32, 32), '.');
			tiles[1] = new Tile(ImageIO.read(new File("gfx/block.png")),
					new Rectangle(940, 200, 32, 32), 'X');
			tiles[2] = new Tile(ImageIO.read(new File("gfx/target.png"))
					.getSubimage(0, 0, 32, 32),
					new Rectangle(840, 300, 32, 32), 'T');
			tiles[3] = new Tile(ImageIO.read(new File("gfx/target.png"))
					.getSubimage(64, 0, 32, 32),
					new Rectangle(940, 300, 32, 32), 'R');
			tiles[4] = new Tile(ImageIO.read(new File("gfx/laser_source.png"))
					.getSubimage(0, 0, 32, 32),
					new Rectangle(840, 400, 32, 32), '>');
			tiles[5] = new Tile(ImageIO.read(new File("gfx/laser_source.png"))
					.getSubimage(32, 0, 32, 32),
					new Rectangle(940, 400, 32, 32), 'V');
			tiles[6] = new Tile(ImageIO.read(new File("gfx/laser_source.png"))
					.getSubimage(64, 0, 32, 32),
					new Rectangle(840, 500, 32, 32), '<');
			tiles[7] = new Tile(ImageIO.read(new File("gfx/laser_source.png"))
					.getSubimage(96, 0, 32, 32),
					new Rectangle(940, 500, 32, 32), '^');
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
		setFocusable(true);
		
		newLevel();

		repaint();
	}
	
	/**
	 * Undoes the most recent action done by the user
	 */
	public void undo()
	{
		for (int row = 0; row < 24; row++)
		{
			level[row] = lastLevel[row].clone();
		}
		repaint();
	}

	/**
	 * Clears the level
	 */
	public void newLevel()
	{
		for (int row = 0; row < 24; row++)
		{
			Arrays.fill(level[row], '.');
		}
		for (int row = 0; row < 24; row++)
		{
			Arrays.fill(lastLevel[row], '.');
		}
		name = "Untitled";
		optimal = 0;
		selectedTile = 0;
		repaint();
	}

	/**
	 * Writes the current level to the given file
	 * @param saveTo the file to write to
	 */
	public void saveLevel(File saveTo)
	{
		PrintWriter pw;
		try
		{
			// Print out metadata
			pw = new PrintWriter(new FileWriter(saveTo));
			pw.println(name);
			pw.println(optimal);
			
			// Print out level data
			for (int row = 0; row < 24; row++)
			{
				for (int col = 0; col < 24; col++)
				{
					pw.print(level[row][col]);
				}
				pw.println();
			}
			pw.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		repaint();
	}

	/**
	 * Loads a level from the given file
	 * @param file the file to load from
	 */
	public void loadLevel(File file)
	{
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(file));
			
			// Read metadata
			name = br.readLine();
			optimal = Integer.parseInt(br.readLine());
			
			// Read level data
			for (int i = 0; i < 24; i++)
			{
				String line = br.readLine();
				lastLevel[i] = line.toCharArray();
				level[i] = line.toCharArray();
			}
			br.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		repaint();
	}

	@Override
	public void paintComponent(Graphics g)
	{
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, 1024, 768);
		// For every square, find it's sprite and draw
		for (int row = 0; row < 24; row++)
		{
			for (int col = 0; col < 24; col++)
			{
				// Find the sprite of this char
				for (int tile = 0; tile < tiles.length; tile++)
				{
					if (tiles[tile].getChar() == level[row][col])
					{
						// If it is a laser source, draw a background tile behind it
						if (">V<^".indexOf(level[row][col]) >= 0)
						{
							g.drawImage(tiles[0].getImage(), col * 32, row * 32, null);
						}
						g.drawImage(tiles[tile].getImage(), col * 32, row * 32, null);
					}
				}
			}
		}
		
		// Highlight the currently selected tile
		g.setColor(Color.red);
		g.fillRect(tiles[selectedTile].box.x - 10, tiles[selectedTile].box.y - 10, 52, 52);
		
		// Drraw tiles
		for (int tile = 0; tile < tiles.length; tile++)
		{
			tiles[tile].draw(g);
		}
		
		// Draw buttons and labels
		g.setColor(Color.DARK_GRAY);
		g.fillRect(800, 100, 200, 50);
		g.fillRect(780, 600, 240, 50);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Consolas", 0, 30));
		g.drawString("Rename", 850, 135);
		g.drawString(name, 780, 50);
		g.drawString("Change target", 790, 635);
		g.drawString("Target:", 810, 720);
		g.drawString("" + optimal, 940, 720);
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		// Find which tile is being selected
		for (int tile = 0; tile < tiles.length; tile++)
		{
			if (tiles[tile].intersects(e.getPoint()))
			{
				selectedTile = tile;
			}
		}
		
		// If the player clicks the level, add the current tile at the selected square
		if (new Rectangle(0, 0, 768, 768).contains(e.getPoint()))
		{
			// Store current state for undoing
			for (int row = 0; row < 24; row++)
			{
				lastLevel[row] = level[row].clone();
			}
			int xSquare = e.getX() / 32;
			int ySquare = e.getY() / 32;

			level[ySquare][xSquare] = tiles[selectedTile].getChar();
		}
		
		// Rename button
		if (new Rectangle(800, 100, 200, 50).contains(e.getPoint()))
		{
			name = JOptionPane.showInputDialog("New name:");
		}
		
		// Change target button
		if (new Rectangle(780, 600, 240, 50).contains(e.getPoint()))
		{
			try
			{
				optimal = Integer.parseInt(JOptionPane.showInputDialog("New target:"));
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(this, "That is not a valid number", "Invalid number", JOptionPane.ERROR_MESSAGE);
			}
		}
		repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e)
	{
		// Dragging the mouse over the level draws
		if (new Rectangle(0, 0, 768, 768).contains(e.getPoint()))
		{
			int xSquare = e.getX() / 32;
			int ySquare = e.getY() / 32;

			level[ySquare][xSquare] = tiles[selectedTile].getChar();
		}
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		if (e.getKeyCode() == KeyEvent.VK_Z)
		{
			if ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)
			{
				undo();
			}
		}
	}

	/**
	 * A tile is a button which is used to select what to draw on the level
	 * @author Callum Moseley
	 */
	class Tile
	{
		private Image sprite;
		private Rectangle box;
		private char tile;

		/**
		 * Initialises this tile to have an image, bounding box, and char to draw on the level
		 * @param i the image of this button
		 * @param r the bounding box of this button
		 * @param c the character of this tile
		 */
		public Tile(Image i, Rectangle r, char c)
		{
			sprite = i;
			box = r;
			tile = c;
		}

		/**
		 * Draws this tile
		 * @param g the graphics context to draw with
		 */
		public void draw(Graphics g)
		{
			g.drawImage(sprite, (int) box.getX(), (int) box.getY(), null);
		}

		/**
		 * Finds whether the given point is in this tile's bounding box
		 * @param p the point to check 
		 * @return whether the given point is in this tile's bounding box
		 */
		public boolean intersects(Point p)
		{
			return box.contains(p);
		}

		/**
		 * Gets the char of this tile
		 * @return the char of this tile
		 */
		public char getChar()
		{
			return tile;
		}
		
		/**
		 * Gets the image of this tile
		 * @return the image of this tile
		 */
		public Image getImage()
		{
			return sprite;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
	}

	@Override
	public void mouseMoved(MouseEvent e)
	{
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
	}
}