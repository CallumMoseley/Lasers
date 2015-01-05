/**
 * A puzzle game made for ICS3U as a final project
 * The goal of the game is to place mirrors in a level to reflect a laser to a target
 * @author Callum Moseley
 * @version December 2014
 */

import javax.swing.JFrame;

public class LasersMain extends JFrame
{
	private LaserPanel game;

	public LasersMain()
	{
		super("Laser Puzzle Game");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		game = new LaserPanel();
		setContentPane(game);
	}

	public static void main(String[] args)
	{
		LasersMain lasers = new LasersMain();
		lasers.pack();
		lasers.setVisible(true);
	}
}