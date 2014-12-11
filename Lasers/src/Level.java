import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Level
{
	private ArrayList<Block> blocks;
	private ArrayList<Mirror> mirrors;
	private ArrayList<LaserSource> sources;
	
	public Level()
	{
		 blocks = new ArrayList<Block>();
		 mirrors = new ArrayList<Mirror>();
		 sources = new ArrayList<LaserSource>();
	}
	
	public void loadLevel(String file)
	{
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = br.readLine();
			int lineNo = 0;
			while (line != null && !line.equals(""))
			{
				for (int pos = 0; pos < line.length(); pos++)
				{
					char nextCh = line.charAt(pos);
					if (nextCh == 'X')
					{
						blocks.add(new Block(pos, lineNo));
					}
					else if (nextCh == 'L')
					{
						sources.add(new LaserSource(pos, lineNo, 0));
					}
				}
				line = br.readLine();
				lineNo++;
			}
			br.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void draw(Graphics g)
	{
		for (int block = 0; block < blocks.size(); block++)
		{
			blocks.get(block).draw(g);
		}
		for (int mirror = 0; mirror < mirrors.size(); mirror++)
		{
			mirrors.get(mirror).draw(g);
		}
		for (int source = 0; source < sources.size(); source++)
		{
			sources.get(source).draw(g);
		}
	}
}