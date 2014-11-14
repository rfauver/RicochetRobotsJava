import java.awt.*;
import java.util.Random;

import javax.swing.*;

public class Board 
{
	private Space board[][];
	private Space startSpaces[];
	private int width = 16;
	private int height = 16;
	
	private Robot roboArray[];
	private int numRobots = 5;
	private Robot activeRobot;
	
	private int timeInt;
	private int timeDigit;
	private int numMoves;
	private int totalMoves;
	private int totalTimeInt;
	private int totalTimeDig;
	
	private Goal goalArray[];
	private Goal completedGoals[];
	private Goal currentGoal;
	private int numGoals = 17;
	private Robot goalRobot = new Robot(new Space());
	
	private Color red = new Color(255,99,71);
	private Color green = new Color(50,205,50);
	private Color yellow = new Color(255,214,0);
	private Color blue = new Color(0,191,255);
	
	public Boolean go;
	public int state;
	
	public Board()
	{
		board = new Space[width][height];
		for (int i=0; i < width; i++)
		{
			for (int j=0; j < height ; j++)
			{
				board[i][j] = new Space(i,j);
			}
		}	   
		
		makeOuterWalls();
		makeMiddleBox();
		createBoard(0);
		

		makeRobots();
		setGoal();
		
		startSpaces = new Space[numRobots];
		setStartSpaces();
		numMoves = 0;
		state = 0;
		go = true;
	}

	public void makeOuterWalls()
	{
		for (int i = 0; i < width; i++)
		{
			board[i][0].makeWall(Direction.UP);
		}
		for (int i = 0; i < height; i++)
		{
			board[0][i].makeWall(Direction.LEFT);
		}
		for (int i = 0; i < width; i++)
		{
			board[i][height-1].makeWall(Direction.DOWN);
		}
		for (int i = 0; i < board[board.length-1].length; i++)
		{
			board[width-1][i].makeWall(Direction.RIGHT);
		}
	}

	public void makeWalls(Direction dir, int x, int y)
	{
		switch (dir)
		{
		case UP:
			board[x][y].makeWall(dir);
			board[x][y-1].makeWall(Direction.DOWN);
			break;
		case RIGHT:
			board[x][y].makeWall(dir);
			board[x+1][y].makeWall(Direction.LEFT);
			break;
		case DOWN:
			board[x][y].makeWall(dir);
			board[x][y+1].makeWall(Direction.UP);
			break;
		case LEFT:
			board[x][y].makeWall(dir);
			board[x-1][y].makeWall(Direction.RIGHT);
			break;
		}
	}

	public void createBoard(int x)
	{
		switch(x)
		{
		case 0:
			makeWalls(Direction.RIGHT, 5, 0);
			makeWalls(Direction.DOWN, 3, 1);
			makeWalls(Direction.RIGHT, 2, 2);
			makeWalls(Direction.DOWN, 0, 3);
			makeWalls(Direction.DOWN, 2, 3);
			makeWalls(Direction.RIGHT, 4, 3);
			makeWalls(Direction.DOWN, 5, 3);
			makeWalls(Direction.RIGHT, 2, 4);
			makeWalls(Direction.RIGHT, 4, 5);
			makeWalls(Direction.DOWN, 4, 5);
			makeWalls(Direction.RIGHT, 11, 0);
			makeWalls(Direction.DOWN, 9, 1);
			makeWalls(Direction.RIGHT, 9, 1);
			makeWalls(Direction.DOWN, 15, 2);
			makeWalls(Direction.RIGHT, 10, 3);
			makeWalls(Direction.DOWN, 11, 3);
			makeWalls(Direction.DOWN, 14, 4);
			makeWalls(Direction.DOWN, 10, 5);
			makeWalls(Direction.RIGHT, 13, 5);
			makeWalls(Direction.RIGHT, 10, 6);
			makeWalls(Direction.RIGHT, 12, 7);
			makeWalls(Direction.DOWN, 13, 7);
			makeWalls(Direction.UP, 3, 9);
			makeWalls(Direction.RIGHT, 3, 9);
			makeWalls(Direction.LEFT, 12, 9);
			makeWalls(Direction.UP, 12, 9);
			makeWalls(Direction.DOWN, 15, 9);
			makeWalls(Direction.RIGHT, 10, 10);
			makeWalls(Direction.DOWN, 10, 10);
			makeWalls(Direction.LEFT, 6, 11);
			makeWalls(Direction.UP, 6, 11);
			makeWalls(Direction.LEFT, 1, 12);
			makeWalls(Direction.DOWN, 1, 12);
			makeWalls(Direction.UP, 14, 12);
			makeWalls(Direction.RIGHT, 14, 12);
			makeWalls(Direction.DOWN, 0, 13);
			makeWalls(Direction.RIGHT, 4, 14);
			makeWalls(Direction.DOWN, 4, 14);
			makeWalls(Direction.LEFT, 11, 14);
			makeWalls(Direction.DOWN, 11, 14);
			makeWalls(Direction.RIGHT, 6, 15);
			makeWalls(Direction.RIGHT, 13, 15);

			goalArray = new Goal[numGoals];
			goalArray[0] = new Goal(yellow, Symbol.MOON, board[3][2]);
			goalArray[1] = new Goal(red, Symbol.PLANET, board[2][4]);
			goalArray[2] = new Goal(blue, Symbol.GEAR, board[5][3]);
			goalArray[3] = new Goal(green, Symbol.STAR, board[4][5]);
			goalArray[4] = new Goal(yellow, Symbol.GEAR, board[9][1]);
			goalArray[5] = new Goal(red, Symbol.STAR, board[11][3]);
			goalArray[6] = new Goal(green, Symbol.PLANET, board[10][6]);
			goalArray[7] = new Goal(blue, Symbol.MOON, board[14][5]);
			goalArray[8] = new Goal(yellow, Symbol.STAR, board[3][9]);
			goalArray[9] = new Goal(blue, Symbol.STAR, board[12][9]);
			goalArray[10] = new Goal(yellow, Symbol.PLANET, board[10][10]);
			goalArray[11] = new Goal(blue, Symbol.PLANET, board[6][11]);
			goalArray[12] = new Goal(green, Symbol.GEAR, board[1][12]);
			goalArray[13] = new Goal(red, Symbol.GEAR, board[14][12]);
			goalArray[14] = new Goal(red, Symbol.MOON, board[4][14]);
			goalArray[15] = new Goal(green, Symbol.MOON, board[11][14]);
			goalArray[16] = new Goal(Color.WHITE, Symbol.HOLE, board[13][7]);
			
			break;
		}
	}

	public void makeMiddleBox()
	{
		board[width/2][height/2].makeWall(Direction.RIGHT);
		board[width/2][height/2].makeWall(Direction.DOWN);
		board[width/2][height/2].occupy();
		board[width/2+1][height/2].makeWall(Direction.LEFT);
		board[width/2][height/2+1].makeWall(Direction.UP);

		board[width/2-1][height/2].makeWall(Direction.DOWN);
		board[width/2-1][height/2].makeWall(Direction.LEFT);
		board[width/2-1][height/2].occupy();
		board[width/2-1][height/2+1].makeWall(Direction.UP);
		board[width/2-2][height/2].makeWall(Direction.RIGHT);

		board[width/2][height/2-1].makeWall(Direction.UP);
		board[width/2][height/2-1].makeWall(Direction.RIGHT);
		board[width/2][height/2-1].occupy();
		board[width/2][height/2-2].makeWall(Direction.DOWN);
		board[width/2+1][height/2-1].makeWall(Direction.LEFT);

		board[width/2-1][height/2-1].makeWall(Direction.UP);
		board[width/2-1][height/2-1].makeWall(Direction.LEFT);
		board[width/2-1][height/2-1].occupy();
		board[width/2-2][height/2-1].makeWall(Direction.RIGHT);
		board[width/2-1][height/2-2].makeWall(Direction.DOWN);
	}

	public void makeRobots()
	{
		roboArray = new Robot[numRobots];

		Random rand = new Random();

		for (int i = 0; i < numRobots; i++)
		{
			int tempx = rand.nextInt(width);
			int tempy = rand.nextInt(height);
			if (board[tempx][tempy].isOccupied())
			{
				i--;
			}
			else
			{
				switch (i)
				{
				case 0:
					roboArray[i] = new Robot(red, board[tempx][tempy]);
					break;
				case 1:
					roboArray[i] = new Robot(blue, board[tempx][tempy]);
					break;
				case 2:
					roboArray[i] = new Robot(green, board[tempx][tempy]);
					break;
				case 3:
					roboArray[i] = new Robot(yellow, board[tempx][tempy]);
					break;	
				case 4:
					roboArray[i] = new Robot(Color.GRAY, board[tempx][tempy]);
					break;
				default:
					roboArray[i] = new Robot(board[tempx][tempy]);
				}
			}
		}
	}

	public void setGoal()
	{
		completedGoals = new Goal[numGoals];
		Random rand = new Random();
		int temp = rand.nextInt(numGoals);
		boolean madeGoal = false;
		boolean conflict = false;
		while (!madeGoal)
		{
			for (int i = 0; i < numGoals; i++)
			{
				if (goalArray[temp] == completedGoals[i])
				{
					conflict = true;
					break;
				}
			}
			if (conflict)
			{
				temp = rand.nextInt(numGoals);
			}
			else
			{
				currentGoal = goalArray[temp];
				for (int i = 0; i < roboArray.length; i++)
				{
					if (currentGoal.color == roboArray[i].color)
					{
						goalRobot = roboArray[i];
					}
				}
				madeGoal = true;
			}
		}
	}
	
	public Goal getCurrentGoal()
	{
		return currentGoal;
	}
	
	public Robot getGoalRobot()
	{
		return goalRobot;
	}

	public void moveRobot(Robot rob, Direction dir)
	{
		switch (dir)
		{
		case UP:
			rob.getCurrentSpace().empty();
			while (!(rob.getCurrentSpace().isWall(dir) || 
					board[rob.getCurrentSpace().getXCoord()][rob.getCurrentSpace().getYCoord()-1].isOccupied()))
			{		
				rob.setCurrentSpace(board[rob.getCurrentSpace().getXCoord()][rob.getCurrentSpace().getYCoord()-1]);	
			}
			rob.getCurrentSpace().occupy();
			break;
		case RIGHT:
			rob.getCurrentSpace().empty();
			while (!(rob.getCurrentSpace().isWall(dir) || 
					board[rob.getCurrentSpace().getXCoord()+1][rob.getCurrentSpace().getYCoord()].isOccupied()))
			{
				rob.setCurrentSpace(board[rob.getCurrentSpace().getXCoord()+1][rob.getCurrentSpace().getYCoord()]);
			}
			rob.getCurrentSpace().occupy();
			break;
		case DOWN:
			rob.getCurrentSpace().empty();
			while (!(rob.getCurrentSpace().isWall(dir) || 
					board[rob.getCurrentSpace().getXCoord()][rob.getCurrentSpace().getYCoord()+1].isOccupied()))
			{
				rob.setCurrentSpace(board[rob.getCurrentSpace().getXCoord()][rob.getCurrentSpace().getYCoord()+1]);
			}
			rob.getCurrentSpace().occupy();
			break;
		case LEFT:
			rob.getCurrentSpace().empty();
			while (!(rob.getCurrentSpace().isWall(dir) || 
					board[rob.getCurrentSpace().getXCoord()-1][rob.getCurrentSpace().getYCoord()].isOccupied()))
			{
				rob.setCurrentSpace(board[rob.getCurrentSpace().getXCoord()-1][rob.getCurrentSpace().getYCoord()]);
			}
			rob.getCurrentSpace().occupy();
			break;
		}
	}
	
	public void reset()
	{
		for (int i = 0; i < startSpaces.length; i++)
		{
			roboArray[i].getCurrentSpace().empty();
			roboArray[i].setCurrentSpace(startSpaces[i]);
			roboArray[i].getCurrentSpace().occupy();
			numMoves = 0;
		}
	}
	
	public void setStartSpaces()
	{
		for (int i = 0; i < roboArray.length; i++)
		{
			startSpaces[i] = roboArray[i].getCurrentSpace();
		}
	}
	
	public void newTurn()
	{
		totalMoves += numMoves;
		totalTimeDig += timeDigit;
		if (totalTimeDig > 9)
		{
			totalTimeDig -= 10;
			totalTimeInt += 1;
		}
		totalTimeInt += timeInt;
		
		setGoal();
		go = true;
		numMoves = 0;
		setStartSpaces();
	}

	public Space getSpace (int x, int y)
	{
		return board[x][y];
	}

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}

	public Robot[] getRobotArray()
	{
		return roboArray;
	}

	public void setActiveRobot(Robot rob)
	{
		activeRobot = rob;
	}

	public Robot getActiveRobot()
	{
		return activeRobot;
	}

	public void setTime(int tmi, int tmd)
	{
		timeInt = tmi;
		timeDigit = tmd;
	}

	public String getTimeString()
	{
		return "Time: " + timeInt + "." + timeDigit;
	}
	
	public String getTotalTimeString()
	{
		return totalTimeInt + "." + totalTimeDig;
	}

	public int getCurrentNumMoves()
	{
		return numMoves;
	}
	
	public int getTotalNumMoves()
	{
		return totalMoves;
	}

	public void incrementCurrentNumMoves()
	{
		numMoves++;
	}

	public Goal[] getGoalArray()
	{
		return goalArray;
	}

	public String toString()
	{
		String temp = "";
		for (int i = 0; i < width; i++)
		{
			String inner = "";
			for (int j = 0; j < height; j++)
			{
				inner += board[j][i].toString();
			}
			temp += inner + "\n";
		}
		return temp;
	}
}
