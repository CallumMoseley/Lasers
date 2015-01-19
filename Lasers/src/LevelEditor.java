/**
 * @author Callum Moseley
 * @version January 2015
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class LevelEditor extends JFrame implements ActionListener
{
	private LevelEditorPanel editor;
	final JFileChooser fc;

	public LevelEditor()
	{
		super("Lasers Level Editor");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		JMenu file = new JMenu("File");

		JMenuItem newq = new JMenuItem("New");
		newq.addActionListener(this);
		file.add(newq);

		JMenuItem save = new JMenuItem("Save");
		save.addActionListener(this);
		file.add(save);

		JMenuItem open = new JMenuItem("Open");
		open.addActionListener(this);
		file.add(open);
		
		JMenu edit = new JMenu("Edit");
		
		JMenuItem undo = new JMenuItem("Undo");
		undo.addActionListener(this);
		edit.add(undo);

		menuBar.add(file);
		menuBar.add(edit);
		setJMenuBar(menuBar);

		fc = new JFileChooser();
		fc.setCurrentDirectory(new File(System.getProperty("java.class.path")
				+ "\\.."));

		editor = new LevelEditorPanel();
		setContentPane(editor);
	}

	public static void main(String[] args)
	{
		// Create and show the frame
		LevelEditor lasers = new LevelEditor();
		lasers.pack();
		lasers.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getActionCommand().equals("New"))
		{
			if (JOptionPane.showOptionDialog(this,
					"The current level will be lost. Continue?", "New Level",
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE,
					null, null, 0) == JOptionPane.OK_OPTION)
			{
				editor.newLevel();
			}
		}
		if (e.getActionCommand().equals("Save"))
		{
			if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION)
			{
				File saveTo = fc.getSelectedFile();
				editor.saveLevel(saveTo);
			}
		}
		if (e.getActionCommand().equals("Open"))
		{
			if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
			{
				File file = fc.getSelectedFile();
				editor.loadLevel(file);
			}
		}
		if (e.getActionCommand().equals("Undo"))
		{
			editor.undo();
		}
	}
}