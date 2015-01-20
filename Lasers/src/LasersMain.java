/**
 * A puzzle game made for ICS3U as a final project
 * The goal of the game is to place mirrors in a level to reflect a laser to a target
 * @author Callum Moseley
 * @version January 2015
 */

import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class LasersMain extends JFrame
{
	private LaserPanel game;

	public LasersMain()
	{
		// Initialise this frame
		super("Laser Puzzle Game");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try
		{
			this.setIconImage(ImageIO.read(new File("gfx/icon.png")));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		// Load the game, and put it in this frame
		game = new LaserPanel();
		setContentPane(game);
	}

	public static void main(String[] args)
	{
		// Create and show the frame
		LasersMain lasers = new LasersMain();
		lasers.pack();
		lasers.setVisible(true);
	}
}