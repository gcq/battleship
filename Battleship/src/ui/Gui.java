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
import javax.swing.JOptionPane;
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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JLayeredPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import java.awt.FlowLayout;

import javax.swing.BoxLayout;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JTextField;

import core.Player;

public class Gui extends JFrame implements GridClickListener, ActionListener{

	private UserPanel userPanel;
	private JPanel contentPane;
	private JPanel profilePanel;
	private CardLayout cardLayout;
	private JPanel gamePane;
	private BoardPanel boardPanel;
	private ShipZonePanel shipZonePanel;
	
	private Player player;

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
		
		player = new Player();
		
		addWindowListener(new MyWindowAdapter());
		
		setResizable(false);		
		setLocation(new Point(50, 50));		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contentPane = new JPanel();
		
		gamePane = new JPanel();
		gamePane.setPreferredSize(new Dimension(800, 700));
		gamePane.setBorder(new EmptyBorder(5, 5, 5, 5));
		gamePane.setLayout(null);
		
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
		
		cardLayout = new CardLayout();
		contentPane.setLayout(cardLayout);
		
		shipZonePanel.setLayout(new BoxLayout(shipZonePanel, BoxLayout.Y_AXIS));
		
		getLayeredPane().moveToFront(shipZonePanel);

		gamePane.add(shipZonePanel);
		
		boardPanel = new BoardPanel(shipZonePanel.getPanelArray());
		boardPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		boardPanel.setBounds(-55, 53, 682, 562);
		boardPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		boardPanel.setPreferredSize(new Dimension(500, 500));
		boardPanel.setGridClickListener(this);
		gamePane.add(boardPanel);
		
		getLayeredPane().moveToBack(boardPanel);
		contentPane.add(gamePane, "game");
		
		changePanel("user");
		
		setContentPane(contentPane);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 800, 21);
		gamePane.add(menuBar);
		
		JMenu gameMenu = new JMenu("Game");
		menuBar.add(gameMenu);
		
		JMenuItem startItem = new JMenuItem("start");
		startItem.setMnemonic(startItem.getText().charAt(0));
		startItem.setActionCommand(startItem.getText());
		startItem.addActionListener(this);
		gameMenu.add(startItem);
		
		JMenu profileMenu = new JMenu("Profile");
		menuBar.add(profileMenu);
		
		JMenuItem editProfile = new JMenuItem("edit");
		editProfile.setMnemonic(editProfile.getText().charAt(0));
		editProfile.setActionCommand(editProfile.getText());
		editProfile.addActionListener(this);
		profileMenu.add(editProfile);
		
		JMenu settingsMenu = new JMenu("Settings");
		menuBar.add(settingsMenu);
		
		JMenuItem turnMode = new JMenuItem("turn Mode");
		turnMode.setActionCommand(turnMode.getText());
		turnMode.addActionListener(this);
		settingsMenu.add(turnMode);
		
		JMenuItem turnTime = new JMenuItem("turn Time");
		turnTime.setActionCommand(turnTime.getText());
		turnTime.addActionListener(this);
		settingsMenu.add(turnTime);
		
		userPanel = new UserPanel();
		
		userPanel.getBtnGo().addActionListener(this);
		userPanel.getBtnGo().setActionCommand(userPanel.getBtnGo().getText());
		
		contentPane.add(userPanel, "intro");
		
		profilePanel = new ProfilePanel();
		((ProfilePanel) profilePanel).getSaveBtn().addActionListener(this);
		((ProfilePanel) profilePanel).getSaveBtn().setActionCommand(((ProfilePanel) profilePanel).getSaveBtnText());
		
		((ProfilePanel) profilePanel).getCloseBtn().addActionListener(this);
		((ProfilePanel) profilePanel).getCloseBtn().setActionCommand(((ProfilePanel) profilePanel).getCloseBtnText());
		
		contentPane.add(profilePanel, "profile");
		cardLayout.show(contentPane, "intro");
		pack();
		
	}
	
	public void changePanel(String name) {
		cardLayout.show(contentPane, name);
	}

	@Override
	public void onGridClick(int x, int y) {
		System.out.println("Clicked on [" + x + ", " + y + "]");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("edit")) {
			cardLayout.show(contentPane, "profile");
		}
		else if (e.getActionCommand().equals("go")) {
			changePanel("game");
			player.setName(userPanel.getUsername());
			((ProfilePanel) profilePanel).setUsername(userPanel.getUsername());
		}
		
		else if (e.getActionCommand().equals("CloseProfile")) {
			changePanel("game");
		}
		
		else if (e.getActionCommand().equals("SaveProfile")) {
			changePanel("game");
			player.setName(((ProfilePanel) profilePanel).getUsername());
		}
	}

	
	class MyWindowAdapter extends WindowAdapter {
		@Override
		public void windowClosing(WindowEvent e) {
			int result = JOptionPane.showConfirmDialog(null, "Are you sure about this?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (result == JOptionPane.YES_OPTION)
				dispose();
		}
	}

}

