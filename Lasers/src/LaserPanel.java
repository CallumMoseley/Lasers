/**
 * Contains all of the game logic and graphics
 * @author Callum Moseley
 * @version January 2015
 */

import java.awt.Color;
import java.awt.Dimension;
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

	public LaserPanel()
	{
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		addMouseListener(this);

		// Load static resources
		Block.loadSprite("gfx/block.png");
		Mirror.loadSprite("gfx/mirror.png");
		LaserSource.loadSprite("gfx/laser_source.png");
		Target.loadSprite("gfx/target.png");
		
		// Initialize menus
		mainMenu = new Menu();
		levelSelect = new Menu();
		
		mainMenu.add(new MenuButton(800, 670, 50, 50, "Play") {
			@Override
			public void onClick()
			{
				currentMenu = levelSelect;
				repaint();
			}
		});
		levelSelect.add(new MenuButton(870, 670, 50, 50, "Back") {
			@Override
			public void onClick()
			{
				currentMenu = mainMenu;
				repaint();
			}
		});

//		// Start debug level
//		inGame = true;
//		currentLevel = new Level();
//		currentLevel.loadLevel("levels/test1.lvl");
//		repaint();
		
		// Start main menu
		currentMenu = mainMenu;
	}

	public void paintComponent(Graphics g)
	{
		g.setColor(Color.DARK_GRAY);
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
	public void keyPressed(KeyEvent arg0){}
	@Override
	public void keyReleased(KeyEvent arg0){}
	@Override
	public void keyTyped(KeyEvent arg0){}
	@Override
	public void mouseClicked(MouseEvent arg0){}
	@Override
	public void mouseEntered(MouseEvent arg0){}
	@Override
	public void mouseExited(MouseEvent arg0){}
	@Override
	public void mouseReleased(MouseEvent arg0){}
}