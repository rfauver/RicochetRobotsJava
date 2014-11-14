import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonPanel extends JPanel
{
	private JButton upButton;
	private JButton rightButton;
	private JButton downButton;
	private JButton leftButton;
	private JButton resetButton;
	private JPanel pan;
	private Board board;

	public ButtonPanel(Board b)
	{
		board = b;
		setLayout(new BorderLayout());
		pan = new JPanel();

		upButton = new JButton("Up");
		rightButton = new JButton("Right");
		downButton = new JButton("Down");
		leftButton = new JButton("Left");
		resetButton = new JButton("Reset");

		upButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent ae) 
			{
				board.moveRobot(board.getActiveRobot(), Direction.UP);
				board.incrementCurrentNumMoves();
			}
		});

		downButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent ae) 
			{
				board.moveRobot(board.getActiveRobot(), Direction.DOWN);
				board.incrementCurrentNumMoves();
			}
		});

		rightButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent ae) 
			{
				board.moveRobot(board.getActiveRobot(), Direction.RIGHT);
				board.incrementCurrentNumMoves();
			}
		});

		leftButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent ae) 
			{
				board.moveRobot(board.getActiveRobot(), Direction.LEFT);
				board.incrementCurrentNumMoves();
			}
		});
		resetButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent ae) 
			{
				board.reset();
			}
		});


		JPanel upBPanel = new JPanel();
		upBPanel.add(upButton);
		JPanel rightBPanel = new JPanel();
		rightBPanel.add(rightButton);
		JPanel downBPanel = new JPanel();
		downBPanel.add(downButton);
		JPanel leftBPanel = new JPanel();
		leftBPanel.add(leftButton);

		pan.setSize(100, 100);


		pan.removeAll();
		pan.add(upBPanel, BorderLayout.NORTH);
		pan.add(rightBPanel, BorderLayout.EAST);
		pan.add(downBPanel, BorderLayout.SOUTH);
		pan.add(leftBPanel, BorderLayout.WEST);
		pan.add(resetButton);

		//if (b.go)
		//{
		//	nextTurnButton.setVisible(false);
		//}
		//else
		//{
		//	pan.setVisible(false);
		//}



		add(pan);
	}


}
