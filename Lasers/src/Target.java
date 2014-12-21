public class Target extends Block
{
	private boolean reflective;
	
	public Target(int x, int y, boolean r)
	{
		super(x, y);
		reflective = r;
	}
	
	public boolean isReflective()
	{
		return reflective;
	}
}
