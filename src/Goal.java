import java.awt.Color;


public class Goal 
{
	public Color color;
	public Symbol symb;
	private Space space;
	
	public Goal (Color c, Symbol s, Space sp)
	{
		color = c;
		symb = s;
		space = sp;
	}
	
	public Goal (Space sp)
	{
		space = sp;
		color = Color.RED;
		symb = Symbol.GEAR;
	}
	
	public Space getSpace()
	{
		return space;
	}
}
