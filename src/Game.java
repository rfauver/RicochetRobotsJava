import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Game {

	private static Board b;
	private static int curTimeInt = 0;
	private static int curTimeDig = 0;
	private static Timer t;


	public static void main(String[] args)
	{
		b = new Board();
		b.setActiveRobot(b.getRobotArray()[0]);
		b.setTime(0,0);

		DrawPanel dPanel = new DrawPanel(b);  // window for drawing

		final ButtonPanel bPanel = new ButtonPanel(b);

		final JButton nextButton = new JButton("Next Turn");

		final JPanel welcomeButtons = new JPanel();
		JButton beginB = new JButton ("Begin game");
		JButton tutB = new JButton ("Learn how to play");
		welcomeButtons.add(beginB);
		welcomeButtons.add(tutB);
		
		final JPanel tutorialButtons = new JPanel();
		JButton backB = new JButton ("Back");
		final JButton nextB = new JButton ("Next");
		tutorialButtons.add(backB);
		tutorialButtons.add(nextB);
		
		final JMenuBar mBar = new JMenuBar();
		final JMenu menu = new JMenu("Menu");
		final JMenuItem quit = new JMenuItem("Quit");
		final JMenuItem home = new JMenuItem("Back to Main Menu");
		
		mBar.add(menu);
		menu.add(home);
		menu.add(quit);

		final JPanel container = new JPanel();
		container.setLayout(new BorderLayout());

		container.add(dPanel);
		container.add(mBar, BorderLayout.NORTH);
		//container.add(welcomeButtons, BorderLayout.CENTER);
		//container.add(bPanel, BorderLayout.SOUTH);
		//container.add(nextButton, BorderLayout.EAST);

		final JFrame application = new JFrame("RICOCHET ROBOTS 1.0 by Ryan Fauver");                            // the program itself

		application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   // set frame to exit
		// when it is closed

		

		application.add(container);  

		application.setSize(695, 680);    
		application.setVisible(true); 
		application.setResizable(false);
		
		application.setLocation(application.getGraphicsConfiguration().getBounds().width/2-application.getWidth()/2, 
				application.getGraphicsConfiguration().getBounds().height/2-application.getHeight()/2);
		
		int delay = 100;
		
		quit.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent ae) 
			{
				System.exit(0);
			}
		});
		
		home.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent ae) 
			{
				b.state = 0;
				container.remove(tutorialButtons);
				container.remove(bPanel);
				container.add(welcomeButtons, BorderLayout.SOUTH);
				container.validate();
			}
		});

		nextButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent ae) 
			{
				b.newTurn();
				curTimeInt = 0;
				curTimeDig = 0;
			}
		});
		
		beginB.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				container.remove(welcomeButtons);
				container.validate();
				b.state = 4;
			}
		});
		
		tutB.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				container.remove(welcomeButtons);
				container.validate();
				b.state = 1;
			}
		});
		
		backB.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				b.state--;
			}
		});
		
		nextB.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				b.state++;
			}
		});

		ActionListener al = new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{

				switch(b.state)
				{
				case 0:
					container.remove(tutorialButtons);
					container.add(welcomeButtons, BorderLayout.SOUTH);
					container.validate();
					break;
				case 1:
					container.add(tutorialButtons, BorderLayout.SOUTH);
					container.validate();
					break;
				case 2:
					nextB.setText("Next");
					break;
				case 3:
					nextB.setText("Begin");
					break;
				case 4:				
					container.remove(tutorialButtons);
					container.add(bPanel, BorderLayout.SOUTH);
					container.add(nextButton, BorderLayout.EAST);
					container.validate();
					
					if (b.getCurrentGoal().getSpace() == b.getGoalRobot().getCurrentSpace() || 
							(b.getCurrentGoal().symb == Symbol.HOLE && b.getCurrentGoal().getSpace().isOccupied()))
					{
						b.go = false;
					}
					if (b.go)
					{
						nextButton.setVisible(false);
						clock();	
						b.setTime(curTimeInt, curTimeDig);
					}
					else 
					{
						nextButton.setVisible(true);
					}
					
					break;
				}
				application.repaint();
			}
		};

		t = new Timer(delay, al);
		t.start();


		application.repaint();

	}




	public static void clock()
	{
		if (curTimeDig == 9)
		{
			curTimeDig = 0;
			curTimeInt++;
		}
		else curTimeDig++;
	}


}
