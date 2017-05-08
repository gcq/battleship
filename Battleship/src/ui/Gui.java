package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ui.interfaces.GridClickListener;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JLayeredPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import java.awt.FlowLayout;

import javax.swing.BoxLayout;

public class Gui extends JFrame implements GridClickListener, ActionListener, MouseMotionListener{

	private JPanel contentPane;
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
		contentPane.setPreferredSize(new Dimension(800, 700));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		
		shipZonePanel = new ShipZonePanel();
		shipZonePanel.setBorder(new EmptyBorder(2, 2, 2, 2));
		shipZonePanel.setBounds(604, 103, 150, 500);
		shipZonePanel.setAlignmentX(RIGHT_ALIGNMENT);
		
		
		MouseListener mouseListener = new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				System.out.println("Component dropped on Position: " + "X[" + e.getX() + "]" + "Y[" + e.getY() + "]");
			}
		};
		
//		shipZonePanel.addMouseListener(mouseListener);
//		shipZonePanel.addMouseMotionListener(this);
		
		contentPane.add(shipZonePanel);
		shipZonePanel.setLayout(new BoxLayout(shipZonePanel, BoxLayout.Y_AXIS));
		
		getLayeredPane().moveToFront(shipZonePanel);
		
		
		boardPanel = new BoardPanel(shipZonePanel.getPanelArray());
		boardPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		boardPanel.setBounds(-55, 53, 682, 562);
		boardPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		boardPanel.setPreferredSize(new Dimension(500, 500));
		boardPanel.setGridClickListener(this);
		
		getLayeredPane().moveToBack(boardPanel);
		
		contentPane.add(boardPanel);
		setContentPane(contentPane);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 369, 21);
		contentPane.add(menuBar);
		
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

	@Override
	public void onGridClick(int x, int y) {
		System.out.println("Clicked on [" + x + ", " + y + "]");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
//		System.out.println("Component dragged: " + e.getComponent());
//		System.out.println("Position: X[" + e.getX() + "]" + "Y[" + e.getY() + "]" );
//		Component shipPanel = ((Container) e.getComponent()).getComponents()[1];
//		Component ship = ((JLayeredPane)shipPanel).getComponents()[0];
//		
////		((JLayeredPane)shipPanel).remove(ship);
//		System.out.println(ship);
//		ship.setLocation(e.getX(), e.getY());
//		contentPane.add(ship);
//		System.out.println(shipPanel);
//		System.out.println("Ship position: " + SwingUtilities.convertPoint(ship, ship.getX(), ship.getY(), this));
//		System.out.println("Ship position: " + ship.getParent().getParent().getLocation());
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		//System.out.println("Component moved: " + e.getComponent());
		
	}
}
