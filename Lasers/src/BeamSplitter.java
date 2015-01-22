/**
 * A rotatable object which both reflects and allows the laser through
 * @author Callum Moseley
 * @version January 2015
 */

public class BeamSplitter extends Mirror
{
	public BeamSplitter(int x, int y, int angle)
	{
		super(x, y, angle);
	}

	public boolean isTransparent()
	{
		return true;
	}
}