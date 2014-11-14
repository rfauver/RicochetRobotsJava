import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;

public class DrawPanel extends JPanel
{
	private Board board;
	private int offset = 35;
	private int borderThickness = 2;
	private int clickX;
	private int clickY;
	private Image gear,
	moon,
	star,
	planet,
	hole;

	public DrawPanel(Board b)
	{
		super();
		setBackground(Color.WHITE);
		board = b;
		addMouseListener(ml);
		try 
		{
			/*
			URL gURL = getClass().getResource("Gear.png");
			gear = ImageIO.read(gURL);
			URL mURL = getClass().getResource("Moon.png");
			moon = ImageIO.read(mURL);
			URL sURL = getClass().getResource("Star.png");
			star = ImageIO.read(sURL);
			URL pURL = getClass().getResource("Planet.png");
			planet = ImageIO.read(pURL);
			URL hURL = getClass().getResource("Hole.png");
			hole = ImageIO.read(hURL);
			 */ 

			gear = ImageIO.read(new File("Gear.png"));
			moon = ImageIO.read(new File("Moon.png"));
			star = ImageIO.read(new File("Star.png"));
			planet = ImageIO.read(new File("Planet.png"));
			hole = ImageIO.read(new File("Hole.png"));

		} 
		catch (IOException e) 
		{System.out.println("Didn't work");}

	}

	public void paintComponent(Graphics g)  // draw graphics in the panel
	{

		super.paintComponent(g);            // call superclass to make panel display correctly

		g.setFont(new Font("Monospaced", Font.PLAIN, 14));

		switch (board.state)
		{
		case 0:
			g.drawRect(20, 20, getWidth()-40, getHeight()-40);
			g.setFont(new Font("Monospaced", Font.ITALIC, 100));
			g.drawString("RICOCHET", 105, 110);
			g.drawString("ROBOTS", 153, 200);

			g.setFont(new Font("Monospaced", Font.PLAIN, 20));
			g.drawString("A boardgame designed by Alex Randolph", 125, 250);
			g.drawString("and published by Rio Grande Games", 145, 275);
			g.drawString("Recreated in Java by Ryan Fauver", 150, 560);
			g.drawString("Version 1.0", 275, 585);

			drawRobot(g, new Color(255,99,71), 120, 375, 60);
			drawRobot(g, Color.GRAY, 215, 375, 60);
			drawRobot(g, new Color(50,205,50), 310, 375, 60);
			drawRobot(g, new Color(255,214,0), 405, 375, 60);
			drawRobot(g, new Color(0,191,255), 500, 375, 60);
			break;

		case 1:
			g.setFont(new Font("Monospaced", Font.PLAIN, 20));
			g.drawRect(20, 20, getWidth()-40, getHeight()-40);

			drawRobot(g, new Color(50,205,50), 150, 70, 60);
			g.drawString("<-- This is a Robot", 250, 105);

			g.setColor(new Color(0,191,255));
			g.fillRect(150, 170, 60, 60);
			g.drawImage(planet, 150, 170, 60, 60, null);
			g.setColor(Color.BLACK);
			g.drawString("<-- This is a Symbol", 250, 205);

			g.drawString("The big Symbol in the center of the board", 80, 300);
			g.drawString("is the current goal. The objective of the", 80, 325);
			g.drawString("game is to move the Robot whose color", 80, 350);
			g.drawString("matches to the current goal. However...", 80, 375);

			g.drawImage(hole, 150, 420, 60, 60, null);
			g.drawString("<-- this is a Black Hole", 250, 455);

			g.drawString("If a Black Hole is the current goal, any", 80, 535);
			g.drawString("Robot can be moved to the Black Hole space.", 80, 560);
			break;

		case 2:
			g.setFont(new Font("Monospaced", Font.PLAIN, 20));
			g.drawRect(20, 20, getWidth()-40, getHeight()-40);

			g.drawString("To move a Robot:", 60, 65);
			g.drawString("First click on the Robot.", 80, 90);
			g.drawString("Then click the Up, Down, Left, or Right", 80, 115);
			g.drawString("button to move the Robot in that direction.", 80, 140);

			g.drawString("The Robot will move in the selected", 80, 275);
			g.drawString("direction until it hits a wall or another", 80, 300);
			g.drawString("Robot.", 80, 325);

			g.drawString("Try to move the correct Robot to the", 80, 500);
			g.drawString("current goal in as few moves as possible", 80, 525);
			g.drawString("and as fast as you can.", 80, 550);
			break;

		case 3:
			g.setFont(new Font("Monospaced", Font.PLAIN, 20));
			g.drawRect(20, 20, getWidth()-40, getHeight()-40);

			g.drawString("You may be able to move a Robot to a symbol", 80, 85);
			g.drawString("on its own, but you will probably need to", 80, 110);
			g.drawString("use the other Robots to get there.", 80, 135);

			g.drawString("If you want to start a round over, just", 80, 475);
			g.drawString("click the Reset button. The number of moves", 80, 500);
			g.drawString("will go back to zero but the clock will ", 80, 525);
			g.drawString("keep ticking.", 80, 550);
			break;

		case 4:
			g.drawRect(20, 20, board.getWidth()*offset, board.getHeight()*offset);

			g.drawString(board.getTimeString(), 20, 14);
			g.drawString("Moves: " + board.getCurrentNumMoves(), board.getWidth()*offset-50, 14);

			for (int i = 0; i < board.getWidth(); i++)
			{
				g.drawLine(i*offset+20, 20, i*offset+20, board.getHeight()*offset+20);
			}
			for (int i = 0; i < board.getHeight(); i++)
			{
				g.drawLine(20, i*offset+20, board.getWidth()*offset+20, i*offset+20);
			}
			for (int i = 0; i < board.getGoalArray().length; i++)
			{
				g.setColor(board.getGoalArray()[i].color);
				g.fillRect(board.getGoalArray()[i].getSpace().getXCoord()*offset+1+20, 
						board.getGoalArray()[i].getSpace().getYCoord()*offset+1+20, 
						offset-1, offset-1);
				g.setColor(Color.BLACK);
				switch(board.getGoalArray()[i].symb)
				{
				case GEAR:
					g.drawImage(gear, board.getGoalArray()[i].getSpace().getXCoord()*offset+1+20, 
							board.getGoalArray()[i].getSpace().getYCoord()*offset+1+20, 
							offset-1, offset-1, null);
					break;
				case HOLE:
					g.drawImage(hole, board.getGoalArray()[i].getSpace().getXCoord()*offset+1+20, 
							board.getGoalArray()[i].getSpace().getYCoord()*offset+1+20, 
							offset-1, offset-1, null);
					break;
				case MOON:
					g.drawImage(moon, board.getGoalArray()[i].getSpace().getXCoord()*offset+1+20, 
							board.getGoalArray()[i].getSpace().getYCoord()*offset+1+20, 
							offset-1, offset-1, null);
					break;
				case PLANET:
					g.drawImage(planet, board.getGoalArray()[i].getSpace().getXCoord()*offset+1+20, 
							board.getGoalArray()[i].getSpace().getYCoord()*offset+1+20, 
							offset-1, offset-1, null);
					break;
				case STAR:
					g.drawImage(star, board.getGoalArray()[i].getSpace().getXCoord()*offset+1+20, 
							board.getGoalArray()[i].getSpace().getYCoord()*offset+1+20, 
							offset-1, offset-1, null);
					break;
				default:
					break;
				}
			}

			g.setColor(board.getCurrentGoal().color);
			g.fillRect(board.getWidth()*offset/2 - offset + 20, board.getHeight()*offset/2 - offset + 20, offset*2, offset*2);
			switch(board.getCurrentGoal().symb)
			{
			case GEAR:
				g.drawImage(gear, board.getWidth()*offset/2 - offset + 21, board.getHeight()*offset/2 - offset + 21, (offset-1)*2, (offset-1)*2, null);
				break;
			case HOLE:
				g.drawImage(hole, board.getWidth()*offset/2 - offset + 20, board.getHeight()*offset/2 - offset + 20, offset*2, offset*2, null);
				break;
			case MOON:
				g.drawImage(moon, board.getWidth()*offset/2 - offset + 22, board.getHeight()*offset/2 - offset + 22, (offset-1)*2, (offset-1)*2, null);
				break;
			case PLANET:
				g.drawImage(planet, board.getWidth()*offset/2 - offset + 22, board.getHeight()*offset/2 - offset + 22, (offset-1)*2, (offset-1)*2, null);
				break;
			case STAR:
				g.drawImage(star, board.getWidth()*offset/2 - offset + 22, board.getHeight()*offset/2 - offset + 20, (offset-1)*2, (offset-1)*2, null);
				break;
			default:
				break;
			}


			g.setColor(Color.BLACK);
			g.drawString("Total Time:", board.getWidth()*offset+30, 140);
			g.drawString(board.getTotalTimeString(), board.getWidth()*offset+30, 155);
			g.drawString("Total Moves: ", board.getWidth()*offset+30, 185);
			g.drawString(board.getTotalNumMoves()+"", board.getWidth()*offset+30, 200);

			for (int i = 0; i < board.getWidth(); i++)
			{
				for (int j = 0; j < board.getHeight(); j++)
				{
					if (board.getSpace(i, j).isWall(Direction.UP))
					{
						g.fillRect(i*offset+20, j*offset+20, offset, borderThickness);
					}
					if (board.getSpace(i, j).isWall(Direction.RIGHT))
					{
						g.fillRect((i+1)*offset+20-borderThickness, j*offset+20, borderThickness, offset);
					}
					if (board.getSpace(i, j).isWall(Direction.DOWN))
					{
						g.fillRect(i*offset+20, (j+1)*offset+20-borderThickness, offset, borderThickness);
					}
					if (board.getSpace(i, j).isWall(Direction.LEFT))
					{
						g.fillRect(i*offset+20, j*offset+20, borderThickness, offset);
					}
				}
			}

			g.drawString("Active ", board.getWidth()*offset+30, 45);
			g.drawString("Robot:", board.getWidth()*offset+30, 60);
			drawRobot(g, board.getActiveRobot().color, board.getWidth()*offset+30, 70, 45);

			for (int i = 0; i < board.getRobotArray().length; i++)
			{
				Robot tempRobo = board.getRobotArray()[i];
				drawRobot(g, tempRobo.color, tempRobo.getCurrentSpace().getXCoord()*offset+20, tempRobo.getCurrentSpace().getYCoord()*offset+20);

			}
			break;
		}

	}

	public void drawRobot (Graphics g, Color c, int x, int y)
	{
		g.setColor(c);
		g.fillOval(x+offset/8, y+offset/8, 3*offset/4, 3*offset/4);
		g.setColor(Color.BLACK);
		g.fillOval(x+3*offset/8, y+3*offset/8, offset/4, offset/4);
		g.drawOval(x+offset/8, y+offset/8, 3*offset/4, 3*offset/4);
	}

	public void drawRobot (Graphics g, Color c, int x, int y, int diameter)
	{
		g.setColor(c);
		g.fillOval(x+diameter/8, y+diameter/8, 3*diameter/4, 3*diameter/4);
		g.setColor(Color.BLACK);
		g.fillOval(x+3*diameter/8, y+3*diameter/8, diameter/4, diameter/4);
		g.drawOval(x+diameter/8, y+diameter/8, 3*diameter/4, 3*diameter/4);
	}


	private MouseListener ml = new MouseListener()
	{
		public void mouseClicked(MouseEvent me)
		{
			if (board.state == 4)
			{
				clickX = me.getX();
				clickY = me.getY();
				if (clickX > 20 && clickX < board.getWidth()*offset+20 && 
						clickY > 20 && clickY < board.getHeight()*offset+20)
				{
					if (board.getSpace((clickX-20)/offset, (clickY-20)/offset).isOccupied())
					{
						for (int i = 0; i < board.getRobotArray().length; i++)
						{
							if (board.getRobotArray()[i].getCurrentSpace() == 
									board.getSpace((clickX-20)/offset, (clickY-20)/offset))
							{
								board.setActiveRobot(board.getRobotArray()[i]);
								return;
							}
						}
					}
				}
			}
		}
		public void mouseEntered(MouseEvent arg0) {}
		public void mouseExited(MouseEvent arg0) {}
		public void mousePressed(MouseEvent arg0) {}
		public void mouseReleased(MouseEvent arg0) {}
	};

}
