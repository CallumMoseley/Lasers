import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class LaserPanel extends JPanel
{
	private static final int WIDTH = 1024;
	private static final int HEIGHT = 768;

	private Level currentLevel;
	private boolean inGame;

	public LaserPanel()
	{
		setPreferredSize(new Dimension(WIDTH, HEIGHT));

		// Load static resources
		Block.loadSprite("gfx/block.png");
		Mirror.loadSprite("gfx/mirror.png");
		LaserSource.loadSprite("gfx/laser_source.png");
		Target.loadSprite("gfx/target.png");

		// Start debug level
		inGame = true;
		currentLevel = new Level();
		currentLevel.loadLevel("levels/test1.lvl");
		repaint();
	}

	public void paintComponent(Graphics g)
	{
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		if (inGame)
		{
			currentLevel.draw(g); 
		}
	}
}