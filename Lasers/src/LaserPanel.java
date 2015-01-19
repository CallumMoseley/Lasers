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
import java.io.File;
import java.util.ArrayList;

import javax.swing.JOptionPane;
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
	private final Menu instructionsMenu;
	private final Menu aboutMenu;
	private final Menu inGameMenu;
	private final Menu inGameRunningMenu;

	// MenuItems that need to be modified by the main program
	private final ScrollContainer levelScroll;
	private final RadioButtons levelButtons;
	private final MenuLabel scoreLabel;
	private final MenuLabel targetScore;
	private final MenuLabel inGameTargetScore;
	private final MenuSlider thicknessSlider;
	private final ToggleImageButton muteButton;
	private final MenuLabel currentMoves;

	private Placeable selectedObject;
	private boolean isHeld;
	private boolean isHeldNew;
	private boolean controlHeld;

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

		controlHeld = false;

		// Load static resources
		Block.loadSprite("gfx/block.png");
		LaserSource.loadSprite("gfx/laser_source.png");
		Target.loadSprite("gfx/target.png");
		Level.loadBackground("gfx/background.png");
		AudioHandler.loadAudio();

		// Start music
		AudioHandler.startMusic();

		// Load levels
		levels = new ArrayList<Level>();
		File[] levelFiles = new File("levels").listFiles();
		for (int file = 0; file < levelFiles.length; file++)
		{
			Level newLevel = new Level();
			if (newLevel.loadLevel(levelFiles[file]))
				levels.add(newLevel);
		}

		// Initialize menus
		mainMenu = new Menu();
		levelSelect = new Menu();
		optionsMenu = new Menu();
		instructionsMenu = new Menu();
		aboutMenu = new Menu();
		inGameMenu = new Menu();
		inGameRunningMenu = new Menu();

		// Add labels and buttons to main menu
		mainMenu.add(new ImageButton(0, 0, "gfx/title.png") {
			@Override
			public void onClick(Point point)
			{
			}

			@Override
			public void onRelease()
			{
			}
		});
		mainMenu.add(new MenuButton(212, 300, 600, 50, "Play",
				Color.DARK_GRAY, Color.WHITE, new Font("Consolas", 0, 40)) {
			@Override
			public void onClick(Point point)
			{
				currentMenu = levelSelect;
				repaint();
			}

			@Override
			public void onRelease()
			{
			}
		});
		mainMenu.add(new MenuButton(212, 360, 600, 50, "Instructions",
				Color.DARK_GRAY, Color.WHITE, new Font("Consolas", 0, 40)) {
			@Override
			public void onClick(Point point)
			{
				currentMenu = instructionsMenu;
				repaint();
			}

			@Override
			public void onRelease()
			{
			}
		});
		mainMenu.add(new MenuButton(212, 420, 295, 50, "Options",
				Color.DARK_GRAY, Color.WHITE, new Font("Consolas", 0, 40)) {
			@Override
			public void onClick(Point point)
			{
				currentMenu = optionsMenu;
				repaint();
			}

			@Override
			public void onRelease()
			{
			}
		});
		mainMenu.add(new MenuButton(517, 420, 295, 50, "About",
				Color.DARK_GRAY, Color.WHITE, new Font("Consolas", 0, 40)) {
			@Override
			public void onClick(Point point)
			{
				currentMenu = aboutMenu;
				repaint();
			}

			@Override
			public void onRelease()
			{
			}
		});
		mainMenu.add(new MenuButton(212, 480, 600, 50, "Exit",
				Color.DARK_GRAY, Color.WHITE, new Font("Consolas", 0, 40)) {
			@Override
			public void onClick(Point point)
			{
				System.exit(0);
			}

			@Override
			public void onRelease()
			{
			}
		});

		// Add labels and buttons to options menu
		optionsMenu.add(new MenuLabel(200, 50, 0, 0, "Options", new Color(
				0, 0, 0, 0), Color.WHITE, new Font("Consolas", 0, 50)));
		RadioButtons colourButtons = new RadioButtons();
		colourButtons.add(new MenuButton(87, 150, 200, 50, "Red",
				Color.DARK_GRAY, Color.WHITE, new Font("Consolas", 0, 40)) {
			@Override
			public void onClick(Point point)
			{
				Ray.setLaserColour(Color.RED);
				repaint();
			}

			@Override
			public void onRelease()
			{
			}
		});
		colourButtons.add(new MenuButton(337, 150, 200, 50, "Blue",
				Color.DARK_GRAY, Color.WHITE, new Font("Consolas", 0, 40)) {
			@Override
			public void onClick(Point point)
			{
				Ray.setLaserColour(Color.BLUE);
				repaint();
			}

			@Override
			public void onRelease()
			{
			}
		});
		colourButtons.add(new MenuButton(87, 220, 200, 50, "Green",
				Color.DARK_GRAY, Color.WHITE, new Font("Consolas", 0, 40)) {
			@Override
			public void onClick(Point point)
			{
				Ray.setLaserColour(Color.GREEN);
				repaint();
			}

			@Override
			public void onRelease()
			{
			}
		});
		colourButtons.add(new MenuButton(337, 220, 200, 50, "Yellow",
				Color.DARK_GRAY, Color.WHITE, new Font("Consolas", 0, 40)) {
			@Override
			public void onClick(Point point)
			{
				Ray.setLaserColour(Color.YELLOW);
				repaint();
			}

			@Override
			public void onRelease()
			{
			}
		});
		optionsMenu.add(colourButtons);

		// Select red by default
		colourButtons.onClick(new Point(87, 150));
		optionsMenu.add(new MenuLabel(200, 400, 0, 0, "Laser Thickness",
				Color.DARK_GRAY, Color.WHITE, new Font("Consolas", 0, 35)));
		thicknessSlider = new MenuSlider(100, 500, 600, 4, Color.LIGHT_GRAY,
				Color.BLACK);

		optionsMenu.add(thicknessSlider);
		optionsMenu.add(new MenuLabel(750, 190, 0, 0, "Preview:",
				Color.DARK_GRAY, Color.WHITE, new Font("Consolas", 0, 35)));
		optionsMenu.add(new MenuButton(870, 670, 50, 50, "Back",
				Color.DARK_GRAY, Color.WHITE, new Font("Consolas", 0, 20)) {
			@Override
			public void onClick(Point point)
			{
				currentMenu = mainMenu;
				repaint();
			}

			@Override
			public void onRelease()
			{
			}
		});

		instructionsMenu.add(new ImageButton(0, 0, "gfx/instructions.png") {
			@Override
			public void onClick(Point point)
			{
			}

			@Override
			public void onRelease()
			{
			}
		});
		instructionsMenu.add(new MenuButton(890, 670, 50, 50, "Back",
				Color.DARK_GRAY, Color.WHITE, new Font("Consolas", 0, 20)) {
			@Override
			public void onClick(Point point)
			{
				currentMenu = mainMenu;
				repaint();
			}

			@Override
			public void onRelease()
			{
			}
		});

		aboutMenu.add(new ImageButton(0, 0, "gfx/about.png") {
			@Override
			public void onClick(Point point)
			{
			}

			@Override
			public void onRelease()
			{
			}
		});
		aboutMenu.add(new MenuButton(870, 670, 50, 50, "Back",
				Color.DARK_GRAY, Color.WHITE, new Font("Consolas", 0, 20)) {
			@Override
			public void onClick(Point point)
			{
				currentMenu = mainMenu;
				repaint();
			}

			@Override
			public void onRelease()
			{
			}
		});

		// Add labels and buttons to level select menu
		levelSelect.add(new MenuLabel(200, 50, 0, 0, "Level Select", new Color(
				0, 0, 0, 0), Color.WHITE, new Font("Consolas", 0, 50)));
		Container buttonsAndIndicators = new Container();
		levelButtons = new RadioButtons();
		for (int level = 0; level < levels.size(); level++)
		{
			levelButtons.add(new MenuButton(30, 100 + 100 * level, 500, 80,
					levels.get(level).getName(), new Color(30, 30, 30),
					Color.WHITE,
					new Font("Consolas", 0, 40)) {
				@Override
				public void onClick(Point point)
				{
					repaint();
				}

				@Override
				public void onRelease()
				{
				}
			});
			buttonsAndIndicators.add(new LevelCompletionIndicator(450,
					130 + 100 * level, 30, 30, levels.get(level)));
		}
		buttonsAndIndicators.add(levelButtons, 0);
		levelScroll = new ScrollContainer(20, 90, 550, 550, Color.DARK_GRAY,
				Color.GRAY.brighter());
		levelScroll.setItem(buttonsAndIndicators);
		levelSelect.add(levelScroll);

		levelSelect.add(new MenuLabel(800, 530, 0, 0, "Best score:",
				Color.DARK_GRAY, Color.WHITE, new Font("Consolas", 0, 30)));
		scoreLabel = new MenuLabel(800, 570, 0, 0, "", Color.DARK_GRAY,
				Color.WHITE, new Font("Consolas", 0, 30));
		levelSelect.add(scoreLabel);

		levelSelect.add(new MenuLabel(800, 620, 0, 0, "Target score:",
				Color.DARK_GRAY, Color.WHITE, new Font("Consolas", 0, 30)));
		targetScore = new MenuLabel(800, 660, 0, 0, "", Color.DARK_GRAY,
				Color.WHITE, new Font("Consolas", 0, 30));
		levelSelect.add(targetScore);

		levelSelect.add(new MenuLabel(800, 80, 0, 0, "Preview", new Color(0,
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
					currentMoves.setText("" + currentLevel.getObjectsUsed());
					inGameTargetScore.setText("" + currentLevel.getOptimal());
					repaint();
				}
			}

			@Override
			public void onRelease()
			{
			}
		});

		levelSelect.add(new MenuButton(950, 670, 50, 50, "Back",
				Color.DARK_GRAY, Color.WHITE, new Font("Consolas", 0, 20)) {
			@Override
			public void onClick(Point point)
			{
				currentMenu = mainMenu;
				repaint();
			}

			@Override
			public void onRelease()
			{
			}
		});

		// Add buttons and labels to in game menus
		inGameMenu.add(new MenuButton(800, 670, 200, 50, "Run!",
				Color.DARK_GRAY, Color.WHITE, new Font("Consolas", 0, 20)) {
			@Override
			public void onClick(Point point)
			{
				if (currentLevel.allPlacementsValid())
				{
					boolean completed = currentLevel.runLaser();
					currentMenu = inGameRunningMenu;
					repaint();
					if (completed)
					{
						JOptionPane.showMessageDialog(null,
								"You beat the level!");
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null,
							"Some object positions are not valid!");
				}
				repaint();
			}

			@Override
			public void onRelease()
			{
			}
		});
		inGameMenu.add(new MenuButton(800, 40, 200, 50, "Level select",
				Color.DARK_GRAY, Color.WHITE, new Font("Consolas", 0, 20)) {
			@Override
			public void onClick(Point point)
			{
				currentMenu = levelSelect;
				inGame = false;
				selectedObject = null;
				repaint();
			}

			@Override
			public void onRelease()
			{
			}
		});
		inGameMenu.add(new MenuButton(800, 100, 200, 50, "Clear",
				Color.DARK_GRAY, Color.WHITE, new Font("Consolas", 0, 20))
		{
			@Override
			public void onClick(Point point)
			{
				currentLevel.clearPlaced();
				selectedObject = null;
				currentMoves.setText("" + currentLevel.getObjectsUsed());
				repaint();
			}

			@Override
			public void onRelease()
			{
			}
		});
		inGameMenu.add(new ImageButton(800, 400, "gfx/mirror.png") {
			@Override
			public void onClick(Point point)
			{
				selectedObject = new Mirror(800, 400, 0);
				isHeldNew = true;
				isHeld = true;
				repaint();
			}

			@Override
			public void onRelease()
			{
			}
		});
		// Beam splitters disabled due to not being fun
		// inGameMenu.add(new ImageButton(850, 400, "gfx/splitter.png") {
		// @Override
		// public void onClick(Point point)
		// {
		// selectedObject = new BeamSplitter(850, 400, 0);
		// isHeldNew = true;
		// isHeld = true;
		// repaint();
		// }
		//
		// @Override
		// public void onRelease()
		// {
		// }
		// });
		muteButton = new ToggleImageButton("gfx/sound.png", "gfx/mute.png",
				950, 600) {
			@Override
			public void onClick(Point click)
			{
				super.onClick(click);
				AudioHandler.toggleMute();
			}
		};
		inGameMenu.add(muteButton);

		// Current used objects labels
		inGameMenu.add(new MenuLabel(890, 200, 0, 0, "Objects used",
				Color.DARK_GRAY, Color.WHITE, new Font("Consolas", 0, 30)));
		currentMoves = new MenuLabel(890, 250, 0, 0, "0", Color.DARK_GRAY,
				Color.WHITE, new Font("Consolas", 0, 30));
		inGameMenu.add(currentMoves);

		// Target score labels
		inGameMenu.add(new MenuLabel(890, 300, 0, 0, "Target objects",
				Color.DARK_GRAY, Color.WHITE, new Font("Consolas", 0, 30)));
		inGameTargetScore = new MenuLabel(890, 350, 0, 0, "0", Color.DARK_GRAY,
				Color.WHITE, new Font("Consolas", 0, 30));
		inGameMenu.add(inGameTargetScore);

		inGameRunningMenu.add(new MenuButton(800, 670, 200, 50, "Stop!",
				Color.DARK_GRAY, Color.WHITE, new Font("Consolas", 0, 20)) {
			@Override
			public void onClick(Point point)
			{
				currentLevel.stopLaser();
				currentMenu = inGameMenu;
				repaint();
			}

			@Override
			public void onRelease()
			{
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
				selectedObject = null;
				repaint();
			}

			@Override
			public void onRelease()
			{
			}
		});
		inGameRunningMenu.add(muteButton);
		inGameRunningMenu.add(new MenuLabel(890, 200, 0, 0, "Objects used",
				Color.DARK_GRAY, Color.WHITE, new Font("Consolas", 0, 30)));
		inGameRunningMenu.add(currentMoves);

		// Target score labels
		inGameRunningMenu.add(new MenuLabel(890, 300, 0, 0, "Target objects",
				Color.DARK_GRAY, Color.WHITE, new Font("Consolas", 0, 30)));
		inGameRunningMenu.add(inGameTargetScore);

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
			levels.get(levelButtons.getSelected()).drawPreview(g, 1200, 220,
					0.5);
		}

		// If the player is in the options screen, show a preview for laser
		// colour and laser width
		if (currentMenu == optionsMenu)
		{
			Ray preview = new Ray(900, 50, 0, 500);
			preview.draw(g);
		}

		// Draw the held object
		if (selectedObject != null)
		{
			selectedObject.draw(g, true);
		}

		// Update score label and best score for the currently selected level
		if (levelButtons.getSelected() != -1)
		{
			if (levels.get(levelButtons.getSelected()).isComplete())
			{
				scoreLabel.setText(""
						+ levels.get(levelButtons.getSelected()).getTopScore());
			}
			else
			{
				scoreLabel.setText("Incomplete");
			}
			targetScore.setText(""
					+ levels.get(levelButtons.getSelected()).getOptimal());
		}
		else
		{
			scoreLabel.setText("");
		}

		// Draw the menu if one is loaded
		currentMenu.draw(g);
	}

	@Override
	public void mousePressed(MouseEvent arg0)
	{
		// Pass the click to the current menu
		currentMenu.click(arg0.getPoint());

		if (inGame && arg0.getX() < 32 * 24 && !currentLevel.isSimulating())
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
		if (currentMenu == levelSelect)
		{
			levelScroll.scrollTo(arg0.getY());
		}
		if (currentMenu == optionsMenu)
		{
			thicknessSlider.slideTo(arg0.getX());
			Ray.setLaserThickness(thicknessSlider.getPosition() + 1);
		}
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent arg0)
	{
		currentMenu.release();
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
			currentLevel.updatePlacementValid();
			currentMoves.setText("" + currentLevel.getObjectsUsed());
		}
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent arg0)
	{
		if (inGame && !currentLevel.isSimulating())
		{
			if (arg0.getKeyCode() == KeyEvent.VK_LEFT)
			{
				if (selectedObject != null)
				{
					int amountToRotate = 5;
					if (controlHeld)
					{
						amountToRotate = 1;
					}
					selectedObject.rotateCCW(amountToRotate);
				}
			}
			else if (arg0.getKeyCode() == KeyEvent.VK_RIGHT)
			{
				if (selectedObject != null)
				{
					int amountToRotate = 5;
					if (controlHeld)
					{
						amountToRotate = 1;
					}
					selectedObject.rotateCW(amountToRotate);
				}
			}
			currentLevel.updatePlacementValid();
		}
		if (arg0.getKeyCode() == KeyEvent.VK_CONTROL)
		{
			controlHeld = true;
		}
		if (arg0.getKeyCode() == KeyEvent.VK_DELETE)
		{
			currentLevel.removePlaceable(selectedObject);
			selectedObject = null;
		}

		repaint();
	}

	@Override
	public void keyReleased(KeyEvent arg0)
	{
		if (arg0.getKeyCode() == KeyEvent.VK_CONTROL)
		{
			controlHeld = false;
		}
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