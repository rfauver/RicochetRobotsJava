import java.awt.Color;

public class Robot
{
	public Color color;
	private Space curSpace;

	
	public Robot (Color c, Space startSpace)
	{
		color = c;
		curSpace = startSpace;
		startSpace.occupy();
	}
	
	public Robot(Space startSpace)
	{
		color = Color.BLACK;
		curSpace = startSpace;
		startSpace.occupy();
	}
	
	public Space getCurrentSpace()
	{
		return curSpace;
	}
	
	public void setCurrentSpace(Space s)
	{
		curSpace = s;
	}
	
}
