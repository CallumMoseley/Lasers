/**
 * Contains all of the game logic and graphics
 * @author Callum Moseley
 * @version January 2015
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class LaserPanel extends JPanel implements MouseListener, KeyListener
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

	public LaserPanel()
	{
		setPreferredSize(new Dimension(WIDTH, HEIGHT));

		addMouseListener(this);

		// Load static resources
		Block.loadSprite("gfx/block.png");
		Mirror.loadSprite("gfx/mirror.png");
		LaserSource.loadSprite("gfx/laser_source.png");
		Target.loadSprite("gfx/target.png");
		Level.loadSprite("gfx/background.png");

		// Initialise menus
		mainMenu = new Menu();
		levelSelect = new Menu();
		optionsMenu = new Menu();
		inGameMenu = new Menu();
		inGameRunningMenu = new Menu();

		mainMenu.add(new MenuButton(212, 300, 600, 50, "Play",
				Color.DARK_GRAY, new Font("Consolas", 0, 40)) {
			@Override
			public void onClick()
			{
				currentMenu = levelSelect;
				repaint();
			}
		});
		mainMenu.add(new MenuButton(212, 360, 600, 50, "Options",
				Color.DARK_GRAY, new Font("Consolas", 0, 40)) {
			@Override
			public void onClick()
			{
				currentMenu = optionsMenu;
				repaint();
			}
		});
		optionsMenu.add(new MenuButton(870, 670, 50, 50, "Back",
				Color.DARK_GRAY, new Font("Consolas", 0, 20)) {
			@Override
			public void onClick()
			{
				currentMenu = mainMenu;
				repaint();
			}
		});
		levelSelect.add(new MenuButton(30, 30, 500, 50, "Level 1",
				Color.DARK_GRAY, new Font("Consolas", 0, 40)) {
			@Override
			public void onClick()
			{
				inGame = true;
				currentLevel = new Level();
				currentLevel.loadLevel("levels/test1.lvl");
				currentMenu = inGameMenu;
				repaint();

			}
		});
		levelSelect.add(new MenuButton(870, 670, 50, 50, "Back",
				Color.DARK_GRAY, new Font("Consolas", 0, 20)) {
			@Override
			public void onClick()
			{
				currentMenu = mainMenu;
				repaint();
			}
		});
		levelSelect.add(new MenuButton(50, 670, 160, 70, "Play!",
				Color.DARK_GRAY, new Font("Consolas", 0, 50)) {
			@Override
			public void onClick()
			{

			}
		});
		inGameMenu.add(new MenuButton(800, 670, 200, 50, "Run!",
				Color.DARK_GRAY, new Font("Consolas", 0, 20)) {
			@Override
			public void onClick()
			{
				currentLevel.runLaser();
				currentMenu = inGameRunningMenu;
				repaint();
			}
		});
		inGameMenu.add(new MenuButton(800, 40, 200, 50, "Level select",
				Color.DARK_GRAY, new Font("Consolas", 0, 20)) {
			@Override
			public void onClick()
			{
				currentMenu = levelSelect;
				inGame = false;
				repaint();
			}
		});
		inGameRunningMenu.add(new MenuButton(800, 670, 200, 50, "Stop!",
				Color.DARK_GRAY, new Font("Consolas", 0, 20)) {
			@Override
			public void onClick()
			{
				currentLevel.stopLaser();
				currentMenu = inGameMenu;
				repaint();
			}
		});
		inGameRunningMenu.add(new MenuButton(800, 40, 200, 50, "Level select",
				Color.DARK_GRAY, new Font("Consolas", 0, 20)) {
			@Override
			public void onClick()
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

	public void paintComponent(Graphics g)
	{
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		if (inGame)
		{
			currentLevel.draw(g);
		}
		currentMenu.draw(g);
	}

	@Override
	public void mousePressed(MouseEvent arg0)
	{
		currentMenu.click(arg0.getPoint());
	}

	@Override
	public void keyPressed(KeyEvent arg0)
	{
		if (inGame)
		{
			if (arg0.getKeyCode() == KeyEvent.VK_LEFT)
			{

			}
			else if (arg0.getKeyCode() == KeyEvent.VK_RIGHT)
			{

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
	public void mouseReleased(MouseEvent arg0)
	{
	}
}