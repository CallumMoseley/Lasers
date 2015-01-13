/**
 * Contains all game logic, graphics, and input handling
 * @author Callum Moseley
 * @version January 2015
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JPanel;

public class LaserPanel extends JPanel implements MouseListener,
		MouseMotionListener, KeyListener
{
	private static final int WIDTH = 1024;
	private static final int HEIGHT = 768;

	private Level currentLevel;
	private Menu currentMenu;
	private boolean inGame;

	private final Menu mainMenu;
	private final Menu levelSelect;
	private final Menu optionsMenu;
	private final Menu inGameMenu;
	private final Menu inGameRunningMenu;

	private final RadioButtons levelButtons;

	private Placeable selectedObject;
	private boolean isHeld;
	private boolean isHeldNew;

	private ArrayList<Level> levels;

	/**
	 * Loads all levels, static resources, menus, and starts the main menu
	 */
	public LaserPanel()
	{
		setPreferredSize(new Dimension(WIDTH, HEIGHT));

		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
		setFocusable(true);

		// Load static resources
		Block.loadSprite("gfx/block.png");
		Mirror.loadSprite("gfx/mirror.png");
		LaserSource.loadSprite("gfx/laser_source.png");
		Target.loadSprite("gfx/target.png");
		Level.loadBackground("gfx/background.png");

		// Load levels
		// TODO load all levels from the folder "levels"
		// TODO stand-alone level editor
		// TODO make more levels
		levels = new ArrayList<Level>();
		levels.add(new Level("levels/test1.lvl"));
		levels.add(new Level("levels/test2.lvl"));

		// Initialize menus
		mainMenu = new Menu();
		levelSelect = new Menu();
		optionsMenu = new Menu();
		inGameMenu = new Menu();
		inGameRunningMenu = new Menu();

		// Add labels and buttons to main menu
		// TODO add other menu items (instructions, about, exit)
		mainMenu.add(new MenuLabel(512, 200, 0, 0, "Lasers", new Color(0, 0, 0,
				0), Color.WHITE, new Font("Consolas", 0, 60)));
		mainMenu.add(new MenuButton(212, 300, 600, 50, "Play",
				Color.DARK_GRAY, Color.WHITE, new Font("Consolas", 0, 40)) {
			@Override
			public void onClick(Point point)
			{
				currentMenu = levelSelect;
				repaint();
			}
		});
		mainMenu.add(new MenuButton(212, 360, 600, 50, "Options",
				Color.DARK_GRAY, Color.WHITE, new Font("Consolas", 0, 40)) {
			@Override
			public void onClick(Point point)
			{
				currentMenu = optionsMenu;
				repaint();
			}
		});

		// Add labels and buttons to options menu
		// TODO actually add some options
		optionsMenu.add(new MenuButton(870, 670, 50, 50, "Back",
				Color.DARK_GRAY, Color.WHITE, new Font("Consolas", 0, 20)) {
			@Override
			public void onClick(Point point)
			{
				currentMenu = mainMenu;
				repaint();
			}
		});

		// Add labels and buttons to level select menu
		levelSelect.add(new MenuLabel(200, 50, 0, 0, "Level Select", new Color(
				0, 0, 0, 0), Color.WHITE, new Font("Consolas", 0, 50)));
		// TODO have a scroll bar
		levelButtons = new RadioButtons();
		for (int level = 0; level < levels.size(); level++)
		{
			levelButtons.add(new MenuButton(30, 100 + 60 * level, 500, 50,
					levels.get(level).getName(), Color.DARK_GRAY, Color.WHITE,
					new Font("Consolas", 0, 40)) {
				@Override
				public void onClick(Point point)
				{
					repaint();
				}
			});
		}
		levelSelect.add(levelButtons);
		levelSelect.add(new MenuLabel(800, 180, 0, 0, "Preview", new Color(0,
				0, 0, 0), Color.WHITE, new Font("Consolas", 0, 40)));
		levelSelect.add(new MenuButton(50, 670, 160, 70, "Play!",
				Color.DARK_GRAY, Color.WHITE, new Font("Consolas", 0, 50)) {
			@Override
			public void onClick(Point point)
			{
				int selectedLevel = levelButtons.getSelected();
				if (selectedLevel >= 0 && selectedLevel < levels.size())
				{
					currentMenu = inGameMenu;
					currentLevel = levels.get(selectedLevel);
					inGame = true;
					repaint();
				}
			}
		});
		levelSelect.add(new MenuButton(870, 670, 50, 50, "Back",
				Color.DARK_GRAY, Color.WHITE, new Font("Consolas", 0, 20)) {
			@Override
			public void onClick(Point point)
			{
				currentMenu = mainMenu;
				repaint();
			}
		});

		// Add buttons and labels to in game menus
		// TODO clear button
		inGameMenu.add(new MenuButton(800, 670, 200, 50, "Run!",
				Color.DARK_GRAY, Color.WHITE, new Font("Consolas", 0, 20)) {
			@Override
			public void onClick(Point point)
			{
				currentLevel.runLaser();
				currentMenu = inGameRunningMenu;
				repaint();
			}
		});
		inGameMenu.add(new MenuButton(800, 40, 200, 50, "Level select",
				Color.DARK_GRAY, Color.WHITE, new Font("Consolas", 0, 20)) {
			@Override
			public void onClick(Point point)
			{
				currentMenu = levelSelect;
				inGame = false;
				repaint();
			}
		});
		// TODO add beam splitters too
		inGameMenu.add(new ImageButton(800, 300, "gfx/mirror.png") {
			@Override
			public void onClick(Point point)
			{
				selectedObject = new Mirror(800, 300, 0);
				isHeldNew = true;
				isHeld = true;
				repaint();
			}
		});

		inGameRunningMenu.add(new MenuButton(800, 670, 200, 50, "Stop!",
				Color.DARK_GRAY, Color.WHITE, new Font("Consolas", 0, 20)) {
			@Override
			public void onClick(Point point)
			{
				currentLevel.stopLaser();
				currentMenu = inGameMenu;
				repaint();
			}
		});
		inGameRunningMenu.add(new MenuButton(800, 40, 200, 50, "Level select",
				Color.DARK_GRAY, Color.WHITE, new Font("Consolas", 0, 20)) {
			@Override
			public void onClick(Point point)
			{
				currentLevel.stopLaser();
				currentMenu = levelSelect;
				inGame = false;
				repaint();
			}
		});

		// Start main menu
		currentMenu = mainMenu;
	}

	/**
	 * Draws the game with the given graphics context
	 * @param g the graphics context to draw with
	 */
	public void paintComponent(Graphics g)
	{
		// Clear the screen
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		// Draw the game, if a level is loaded
		if (inGame)
		{
			currentLevel.draw(g);
		}

		// Draw a level preview if the player is in the level select menu and a
		// level is selected
		if (currentMenu == levelSelect && levelButtons.getSelected() != -1)
		{
			levels.get(levelButtons.getSelected()).drawPreview(g, 1200, 450,
					0.5);
		}
		
		if (selectedObject != null)
		{
			selectedObject.draw(g);
		}

		// Draw the menu if one is loaded
		currentMenu.draw(g);
	}

	@Override
	public void mousePressed(MouseEvent arg0)
	{
		// Pass the click to the current menu
		currentMenu.click(arg0.getPoint());
		
		if (inGame && arg0.getX() < 32 * 24)
		{
			selectedObject = currentLevel.getClicked(arg0.getPoint());
			if (selectedObject != null)
			{
				isHeld = true;
				isHeldNew = false;
			}
		}
	}

	@Override
	public void mouseDragged(MouseEvent arg0)
	{
		if (inGame && selectedObject != null && isHeld)
		{
			selectedObject.moveTo(arg0.getX(), arg0.getY());
		}
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent arg0)
	{
		if (selectedObject != null)
		{
			if (isHeld)
			{
				if (arg0.getX() < 32 * 24)
				{
					if (isHeldNew)
					{
						currentLevel.addPlaceable(selectedObject);
					}
				}
				else
				{
					currentLevel.removePlaceable(selectedObject);
					selectedObject = null;
				}
			}

			isHeldNew = false;
			isHeld = false;
		}
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent arg0)
	{
		if (inGame)
		{
			// TODO show degrees while rotating
			// TODO make only work while not simulating
			if (arg0.getKeyCode() == KeyEvent.VK_LEFT)
			{
				if (selectedObject != null)
				{
					selectedObject.rotateCCW(1);
					repaint();
				}
			}
			else if (arg0.getKeyCode() == KeyEvent.VK_RIGHT)
			{
				if (selectedObject != null)
				{
					selectedObject.rotateCW(1);
					repaint();
				}
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0)
	{
	}

	@Override
	public void keyTyped(KeyEvent arg0)
	{
	}

	@Override
	public void mouseClicked(MouseEvent arg0)
	{
	}

	@Override
	public void mouseEntered(MouseEvent arg0)
	{
	}

	@Override
	public void mouseExited(MouseEvent arg0)
	{
	}

	@Override
	public void mouseMoved(MouseEvent arg0)
	{
	}
}