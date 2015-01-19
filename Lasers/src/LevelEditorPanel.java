import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
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
		MouseMotionListener
{
	private char[][] level;
	private char[][] lastLevel;
	private String name;
	private int optimal;
	private Tile[] tiles;
	private int selectedTile;

	public LevelEditorPanel()
	{
		setPreferredSize(new Dimension(1024, 768));

		level = new char[24][24];
		lastLevel = new char[24][24];

		tiles = new Tile[8];
		try
		{
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
		
		newLevel();

		repaint();
	}
	
	public void undo()
	{
		for (int row = 0; row < 24; row++)
		{
			level[row] = lastLevel[row].clone();
		}
		repaint();
	}

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

	public void saveLevel(File saveTo)
	{
		PrintWriter pw;
		try
		{
			pw = new PrintWriter(new FileWriter(saveTo));
			pw.println(name);
			pw.println(optimal);
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

	public void loadLevel(File file)
	{
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(file));
			name = br.readLine();
			optimal = Integer.parseInt(br.readLine());
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
		for (int row = 0; row < 24; row++)
		{
			for (int col = 0; col < 24; col++)
			{
				for (int tile = 0; tile < tiles.length; tile++)
				{
					if (tiles[tile].getChar() == level[row][col])
					{
						if (">V<^".indexOf(level[row][col]) >= 0)
						{
							g.drawImage(tiles[0].getImage(), col * 32, row * 32, null);
						}
						g.drawImage(tiles[tile].getImage(), col * 32, row * 32, null);
					}
				}
			}
		}
		g.setColor(Color.red);
		g.fillRect(tiles[selectedTile].box.x - 10, tiles[selectedTile].box.y - 10, 52, 52);
		for (int tile = 0; tile < tiles.length; tile++)
		{
			tiles[tile].draw(g);
		}
		g.setColor(Color.DARK_GRAY);
		g.fillRect(800, 100, 200, 50);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Consolas", 0, 30));
		g.drawString("Rename", 850, 135);
		g.drawString(name, 780, 50);
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		for (int tile = 0; tile < tiles.length; tile++)
		{
			if (tiles[tile].intersects(e.getPoint()))
			{
				selectedTile = tile;
			}
		}
		if (new Rectangle(0, 0, 768, 768).contains(e.getPoint()))
		{
			for (int row = 0; row < 24; row++)
			{
				lastLevel[row] = level[row].clone();
			}
			int xSquare = e.getX() / 32;
			int ySquare = e.getY() / 32;

			level[ySquare][xSquare] = tiles[selectedTile].getChar();
		}
		if (new Rectangle(800, 100, 200, 50).contains(e.getPoint()))
		{
			name = JOptionPane.showInputDialog("New name:");
		}
		repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e)
	{
		if (new Rectangle(0, 0, 768, 768).contains(e.getPoint()))
		{
			int xSquare = e.getX() / 32;
			int ySquare = e.getY() / 32;

			level[ySquare][xSquare] = tiles[selectedTile].getChar();
		}
		repaint();
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

	class Tile
	{
		private Image sprite;
		private Rectangle box;
		private char tile;

		public Tile(Image i, Rectangle r, char c)
		{
			sprite = i;
			box = r;
			tile = c;
		}

		public void draw(Graphics g)
		{
			g.drawImage(sprite, (int) box.getX(), (int) box.getY(), null);
		}

		public boolean intersects(Point p)
		{
			return box.contains(p);
		}

		public char getChar()
		{
			return tile;
		}
		
		public Image getImage()
		{
			return sprite;
		}
	}
}