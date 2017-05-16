package ui;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;

import ui.interfaces.GridClickListener;
import utils.Constants;
import utils.Enums.Direction;
import core.Player;
import core.Ship;

import java.awt.event.KeyEvent;
import java.awt.Color;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import core.Player;
import core.Ship;
import core.exceptions.InvalidPointsForShipException;

import javax.swing.JButton;
import javax.swing.ImageIcon;

public class Gui extends JFrame implements GridClickListener, ActionListener, MouseMotionListener{

	private UserPanel userPanel;
	private JPanel contentPane;
	private JPanel profilePanel;
	private AboutPanel aboutPanel;
	private CardLayout cardLayout;
	private JPanel gamePane;
	private BoardPanel boardPanel;
	private ShipZonePanel shipZonePanel;
	
	Point lastClick;
	
	Gui self;
	
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
		setPreferredSize(Constants.frameSize);
		
		self = this;
		
		player = new Player();
		
		addWindowListener(new MyWindowAdapter());
		
		setResizable(false);		
		setLocation(new Point(50, 50));		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contentPane = new JPanel();
		
		gamePane = new JPanel();
		gamePane.setPreferredSize(new Dimension(850, 700));
		gamePane.setBorder(new EmptyBorder(5, 5, 5, 5));
		gamePane.setLayout(null);
		
		shipZonePanel = new ShipZonePanel();
		shipZonePanel.setBorder(new EmptyBorder(2, 2, 2, 2));
		shipZonePanel.setBounds(564,107,274, 256);
		shipZonePanel.setAlignmentX(RIGHT_ALIGNMENT);

		
		
		MouseListener mouseListener = new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				System.out.println("Component dropped on Position: " + "X[" + e.getX() + "]" + "Y[" + e.getY() + "]");
			}
		};
		
		cardLayout = new CardLayout();
		contentPane.setLayout(cardLayout);
		
		
		aboutPanel = new AboutPanel();
		aboutPanel.setCredits("PuiVicCruGuiA14Credits.html");
		aboutPanel.setLicence("gpl3.html");
		aboutPanel.setImageIcon(new ImageIcon("D:\\DAM\\WorkspaceBattleship\\battleship\\Battleship\\img\\icon.png"));
		aboutPanel.setDescriptionName("BattleShip");
		aboutPanel.setDescriptionText("Victor Puigcerver i Guillem Cruz");
		aboutPanel.setVersio("1.0");
		aboutPanel.setTitleText("Battleship");
		
		((AboutPanel) aboutPanel).getCloseButton().addActionListener(this); // listener to return to main panel
		
		contentPane.add(aboutPanel, "about");
		
		gamePane.add(shipZonePanel);
		
		boardPanel = new BoardPanel();
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
		menuBar.setBounds(0, 0, 850, 21);
		gamePane.add(menuBar);
		
		JMenu gameMenu = new JMenu("Game");
		menuBar.add(gameMenu);
		
		JMenuItem startItem = new JMenuItem("start");
		startItem.setMnemonic(startItem.getText().charAt(0));
		startItem.setActionCommand(startItem.getText());
		startItem.addActionListener(this);
		startItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		gameMenu.add(startItem);
		
		JMenu profileMenu = new JMenu("Profile");
		menuBar.add(profileMenu);
		
		JMenuItem editProfile = new JMenuItem("edit");
		editProfile.setMnemonic(editProfile.getText().charAt(0));
		editProfile.setActionCommand(editProfile.getText());
		editProfile.addActionListener(this);
		editProfile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK));
		profileMenu.add(editProfile);
		
		JMenu settingsMenu = new JMenu("Settings");
		menuBar.add(settingsMenu);
		
		JMenuItem turnMode = new JMenuItem("turn Mode");
		turnMode.setActionCommand(turnMode.getText());
		turnMode.addActionListener(this);
		turnMode.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, InputEvent.CTRL_MASK));
		settingsMenu.add(turnMode);
		
		JMenuItem turnTime = new JMenuItem("turn Time");
		turnTime.setActionCommand(turnTime.getText());
		turnTime.addActionListener(this);
		turnTime.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_MASK));
		settingsMenu.add(turnTime);
		
		JMenu helpMenu = new JMenu("Help");
		menuBar.add(helpMenu);
		
		JMenuItem onlineHelp = new JMenuItem("Online Help");
		onlineHelp.setActionCommand("OnlineHelp");
		onlineHelp.addActionListener(this);
		onlineHelp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		helpMenu.add(onlineHelp);
		
		JMenuItem about = new JMenuItem("About");
		about.setActionCommand(about.getText());
		about.addActionListener(this);
		about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
		helpMenu.add(about);
		
		JButton resetBoard = new JButton("Reset Board");
		resetBoard.setActionCommand("resetBoard");
		resetBoard.addActionListener(this);
		resetBoard.setBounds(647, 402, 109, 23);
		gamePane.add(resetBoard);
		
		userPanel = new UserPanel();
		
		userPanel.getBtnGo().addActionListener(this);
		
		contentPane.add(userPanel, "intro");
		
		profilePanel = new ProfilePanel();
		profileMenu.setSize(Constants.profilePanelSize);
		((ProfilePanel) profilePanel).getSaveBtn().addActionListener(this); // listener to return to main panel
		((ProfilePanel) profilePanel).getCloseBtn().addActionListener(this);
		
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
		
		Point currentClick = new Point(x, y);
		
		Ship clickedShip = shipZonePanel.getSelectedShip();
		
		System.out.println("from " + lastClick + " to " + currentClick);
		
		if (clickedShip != null ) {
			Ship ship = new Ship(x, y, clickedShip.getLength(), clickedShip.getDirection(), clickedShip.getId());
			System.out.println(ship);
			if (boardPanel.addShip(ship)) { // Si s'afegeix correctament (posicio correcta) borrem del panell
				System.out.println(shipZonePanel.removeShip(clickedShip));
				shipZonePanel.setSelectedShip(null);
			}
			else {
				System.out.println("Posicio incorrecta");
			}
			repaint();
		}
		
		
	}
	
	public void resetFrameSize () {
		self.setSize(Constants.frameSize);
		repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("edit")) {
			self.setSize(Constants.profilePanelSize);
			repaint();
			changePanel("profile");
			((ProfilePanel) profilePanel).setUsername(userPanel.getUsername());
		}
		else if (e.getActionCommand().equals("StartGame")) {
			changePanel("game");
			player.setName(userPanel.getUsername());
			((ProfilePanel) profilePanel).setUsername(userPanel.getUsername());
		}
		
		else if (e.getActionCommand().equals("CloseProfile")) {
			resetFrameSize();
			changePanel("game");
		}
		
		else if (e.getActionCommand().equals("SaveProfile")) {
			changePanel("game");
			resetFrameSize();
			player.setName(((ProfilePanel) profilePanel).getUsername());
		}
		
		else if (e.getActionCommand().equals("resetBoard")) {
			System.out.println(boardPanel.resetBoard());
			System.out.println(shipZonePanel.reset());
		}
		
		else if (e.getActionCommand().equals("About")) {
			self.setSize(Constants.aboutPanelSize);
			repaint();
			changePanel("about");
		}
		
		else if (e.getActionCommand().equals("CloseAbout")) {
			resetFrameSize();
			changePanel("game");
		}
		else if (e.getActionCommand().equals("OnlineHelp")) {
			try {
				openWebpage(new URL("http://portaljuegos.wikidot.com/hundirlaflota"));
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public static void openWebpage(URI uri) {
	    Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
	    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
	        try {
	            desktop.browse(uri);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}

	public static void openWebpage(URL url) {
	    try {
	        openWebpage(url.toURI());
	    } catch (URISyntaxException e) {
	        e.printStackTrace();
	    }
	}

	
	class MyWindowAdapter extends WindowAdapter {
		@Override
		public void windowClosing(WindowEvent e) {
			
			int result = JOptionPane.showConfirmDialog(null, "Are you sure about this?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (result == JOptionPane.YES_OPTION)
				setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			else
				setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		}
	}


	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseMoved(MouseEvent e) {
		System.out.println(e.getComponent());
	}
}

