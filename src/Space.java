
public class Space 
{
	private boolean wallUp;
	private boolean wallRight;
	private boolean wallDown;
	private boolean wallLeft;
	private boolean isOccupied;
	private int xCoord;
	private int yCoord;
	
	public Space()
	{
		wallUp = false;
		wallRight = false;
		wallDown = false;
		wallLeft = false;
		isOccupied = false;
		xCoord = 0;
		yCoord = 0;
	}
	
	public Space(int x, int y)
	{
		wallUp = false;
		wallRight = false;
		wallDown = false;
		wallLeft = false;
		isOccupied = false;
		xCoord = x;
		yCoord = y;
	}
	
	public void makeWall(Direction dir)
	{
		switch (dir)
		{
		case UP:
			wallUp = true;
			break;
		case RIGHT:
			wallRight = true;
			break;
		case DOWN:
			wallDown = true;
			break;
		case LEFT:
			wallLeft = true;
			break;
		}
	}
	
	public boolean isWall(Direction dir)
	{
		switch (dir)
		{
		case UP:
			return wallUp;
		case RIGHT:
			return wallRight;
		case DOWN:
			return wallDown;
		case LEFT:
			return wallLeft;
		default:
			return false;
		}
	}
	
	public boolean isOccupied()
	{
		return isOccupied;
	}
	
	public void occupy()
	{
		isOccupied = true;
	}
	
	public void empty()
	{
		isOccupied = false;
	}
	
	public int getXCoord()
	{
		return xCoord;
	}
	
	public int getYCoord()
	{
		return yCoord;
	}
	
	public String toString()
	{
		if (wallUp)
		{
			return "--";
		}
		else if (wallRight)
		{
			return " |";
		}
		else if (wallDown)
		{
			return "__";
		}
		else if (wallLeft)
		{
			return "| ";
		}
		else return ". ";
	}
}
