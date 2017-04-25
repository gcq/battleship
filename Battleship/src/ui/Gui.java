package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Component;

public class Gui extends JFrame {

	private JPanel contentPane;
	private BoardPanel boardPanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui frame = new Gui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Gui() {
		setResizable(false);		
		setLocation(new Point(50, 50));		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contentPane = new JPanel();
		contentPane.setPreferredSize(new Dimension(800, 700));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		boardPanel = new BoardPanel();
		boardPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		boardPanel.setBounds(50, 50, 682, 562);
		boardPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		boardPanel.setPreferredSize(new Dimension(500, 500));
		contentPane.add(boardPanel);
		setContentPane(contentPane);
		pack();
		
	}
}
