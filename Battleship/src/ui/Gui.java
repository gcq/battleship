package ui;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ui.interfaces.GridClickListener;

public class Gui extends JFrame implements GridClickListener, ActionListener{

	JPanel contentPane;
	private JPanel gamePane;
	private BoardPanel boardPanel;
	private ShipZonePanel shipZonePanel;

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
		contentPane.setLayout(new CardLayout());
		
		gamePane = new JPanel();
		gamePane.setPreferredSize(new Dimension(800, 700));
		gamePane.setBorder(new EmptyBorder(5, 5, 5, 5));
		gamePane.setLayout(null);
		
		shipZonePanel = new ShipZonePanel();
		shipZonePanel.setBorder(new EmptyBorder(2, 2, 2, 2));
		shipZonePanel.setBounds(604, 103, 150, 500);
		shipZonePanel.setAlignmentX(RIGHT_ALIGNMENT);
		shipZonePanel.setLayout(new BoxLayout(shipZonePanel, BoxLayout.Y_AXIS));
		gamePane.add(shipZonePanel);
		
		boardPanel = new BoardPanel(shipZonePanel.getPanelArray());
		boardPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		boardPanel.setBounds(-55, 53, 682, 562);
		boardPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		boardPanel.setPreferredSize(new Dimension(500, 500));
		boardPanel.setGridClickListener(this);
		gamePane.add(boardPanel);
		
		contentPane.add(gamePane, "game");
		
		UserPanel userPanel = new UserPanel();
		
		userPanel.getBtnGo().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				changePanel("game");
				
			}
		});
		
		contentPane.add(userPanel, "user");
		
		changePanel("user");
		
		setContentPane(contentPane);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 369, 21);
		gamePane.add(menuBar);
		
		JMenu gameMenu = new JMenu("Game");
		menuBar.add(gameMenu);
		
		JMenuItem menuItem = new JMenuItem("Start");
		gameMenu.add(menuItem);
		
		JMenu profileMenu = new JMenu("Profile");
		menuBar.add(profileMenu);
		
		JMenuItem editProfile = new JMenuItem("Edit");
		profileMenu.add(editProfile);
		
		JMenuItem showProfile = new JMenuItem("Show");
		profileMenu.add(showProfile);
		
		JMenu settingsMenu = new JMenu("Settings");
		menuBar.add(settingsMenu);
		
		JMenuItem turnMode = new JMenuItem("Turn Mode");
		settingsMenu.add(turnMode);
		
		JMenuItem turnTime = new JMenuItem("Turn Time");
		settingsMenu.add(turnTime);
//		boardPanel.setVisible(false);
		pack();
		
	}
	
	public void changePanel(String name) {
		LayoutManager layout = (CardLayout) contentPane.getLayout();
		((CardLayout) layout).show(contentPane, name);
	}

	@Override
	public void onGridClick(int x, int y) {
		System.out.println("Clicked on [" + x + ", " + y + "]");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	}
}
