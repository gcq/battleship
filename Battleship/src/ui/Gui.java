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
import utils.Enums;
import utils.Enums.Direction;
import utils.Enums.GameMode;
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

import java.awt.GridBagLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class Gui extends JFrame implements GridClickListener, ActionListener, MouseMotionListener{

	private UserPanel userPanel;
	private JPanel contentPane;
	private ProfilePanel profilePanel;
	private PreferencesPanel preferencesPanel;
	private AboutPanel aboutPanel;
	private CardLayout cardLayout;
	private JMenuBar menuBar;
	private JMenuItem editProfile;
	private JPanel gamePane;
	private BoardPanel playerBoardPanel;
	private BoardPanel enemyBoardPanel;
	private ShipZonePanel shipZonePanel;
	
	private boolean inGame;
	
	int prefixedTurnTime;
	
	boolean infiniteTime; // true si es torn amb temps infinit, false si es torn amb temps prefixat
	
	GameMode gameMode;
	
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
	
	public void initPanels() {
		//shipZonePanel
		shipZonePanel = new ShipZonePanel();
		shipZonePanel.setBorder(new EmptyBorder(2, 2, 2, 2));
		shipZonePanel.setBounds(570,107,274, 304);
		shipZonePanel.setAlignmentX(RIGHT_ALIGNMENT);
		shipZonePanel.getResetBoardBtn().addActionListener(this);
		
		//aboutPanel
		aboutPanel = new AboutPanel();
		aboutPanel.setCredits("PuiVicCruGuiA14Credits.html");
		aboutPanel.setLicence("gpl3.html");
		aboutPanel.setImageIcon(new ImageIcon("D:\\DAM\\WorkspaceBattleship\\battleship\\Battleship\\img\\icon.png"));
		aboutPanel.setDescriptionName("BattleShip");
		aboutPanel.setDescriptionText("Victor Puigcerver i Guillem Cruz");
		aboutPanel.setVersio("1.0");
		aboutPanel.setTitleText("Battleship");
		aboutPanel.getCloseButton().addActionListener(this); // listener to return to main panel
		
		//playerBoardPanel
		playerBoardPanel = new BoardPanel();
		playerBoardPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		playerBoardPanel.setBounds(-55, 53, 682, 562);
		playerBoardPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		playerBoardPanel.setPreferredSize(new Dimension(500, 500));
		playerBoardPanel.setGridClickListener(this);
		
		//enemyBoardPanel
		enemyBoardPanel = new BoardPanel();
		enemyBoardPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		enemyBoardPanel.setBounds(600, 53, 682, 562);
		enemyBoardPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		enemyBoardPanel.setPreferredSize(new Dimension(500, 500));
		enemyBoardPanel.setGridClickListener(this);
		
		//profilePanel
		profilePanel = new ProfilePanel();
		profilePanel.getSaveBtn().addActionListener(this); // listener to return to main panel
		profilePanel.getCloseBtn().addActionListener(this);
		
		//profilePanel
		userPanel = new UserPanel();
		userPanel.getBtnGo().addActionListener(this);
		
		//preferencesPanel
		preferencesPanel = new PreferencesPanel();
		preferencesPanel.getPrefixedBtn().addActionListener(this);
		preferencesPanel.getInfiniteBtn().addActionListener(this);
		preferencesPanel.getClosePrefBtn().addActionListener(this);
		preferencesPanel.getSavePrefBtn().addActionListener(this);
	}
	

	/**
	 * Create the frame.
	 */
	public Gui() {
		setPreferredSize(Constants.initFrameSize);
		
		inGame = false; 
		
		self = this;
		
		player = new Player();
		
		gameMode = GameMode.CLASSIC;
		
		addWindowListener(new MyWindowAdapter());
		
		setResizable(false);		
		setLocation(new Point(50, 50));		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contentPane = new JPanel();
		
		gamePane = new JPanel();
		gamePane.setPreferredSize(new Dimension(850, 700));
		gamePane.setBorder(new EmptyBorder(5, 5, 5, 5));
		gamePane.setLayout(null);
		
		initPanels();
		
		cardLayout = new CardLayout();
		contentPane.setLayout(cardLayout);

		changePanel("user");
		
		contentPane.add(aboutPanel, "about");
		gamePane.add(shipZonePanel);
		gamePane.add(playerBoardPanel);
		
//		getLayeredPane().moveToBack(playerBoardPanel);
		
		contentPane.add(gamePane, "game");
		
		setContentPane(contentPane);
		
		menuBar = new JMenuBar();
		menuBar.setSize(Constants.initMenuBarSize);
		gamePane.add(menuBar);
		
		JMenu gameMenu = new JMenu("Game");
		menuBar.add(gameMenu);
		
		JMenuItem startItem = new JMenuItem("Start");
		startItem.setMnemonic(startItem.getText().charAt(0));
		startItem.setActionCommand(startItem.getText());
		startItem.addActionListener(this);
		startItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		gameMenu.add(startItem);
		
		JMenu profileMenu = new JMenu("Profile");
		profileMenu.setSize(Constants.profilePanelSize);
		menuBar.add(profileMenu);
		
		editProfile = new JMenuItem("Edit");
		editProfile.setMnemonic(editProfile.getText().charAt(0));
		editProfile.setActionCommand(editProfile.getText());
		editProfile.addActionListener(this);
		editProfile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK));
		profileMenu.add(editProfile);
		
		JMenu settingsMenu = new JMenu("Settings");
		menuBar.add(settingsMenu);
		
		JMenuItem preferences = new JMenuItem("Preferences");
		preferences.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
		preferences.addActionListener(this);
		preferences.setActionCommand(preferences.getText());
		settingsMenu.add(preferences);
		
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
		
		contentPane.add(userPanel, "intro");
		contentPane.add(profilePanel, "profile");
		contentPane.add(preferencesPanel, "preferences");
		
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
			if (playerBoardPanel.addShip(ship)) { // Si s'afegeix correctament (posicio correcta) borrem del panell
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
		if (!this.inGame)
			self.setSize(Constants.initFrameSize);
		else
			self.setSize(Constants.inGameFrameSize);
		repaint();
	}
	
	public void setTurnTime () {
		if (preferencesPanel.getPrefixedBtn().isSelected()) {
			prefixedTurnTime = Integer.parseInt((String) preferencesPanel.getPrefixedComboBox().getSelectedItem());
			infiniteTime = false;
		}
		else {
			prefixedTurnTime = 0; // 0 = Temps infinit
			infiniteTime = true;
		}
	}
	
	public void setTurnMode () {
		gameMode = preferencesPanel.getAlternativeBtn().isSelected() ? GameMode.ALTERNATIVE : GameMode.CLASSIC;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Edit")) {
			self.setSize(Constants.profilePanelSize);
			repaint();
			changePanel("profile");
			((ProfilePanel) profilePanel).setUsername(userPanel.getUsername());
		}
		
		else if (e.getActionCommand().equals("Preferences")) {
			self.setSize(Constants.preferencesPanelSize);
			changePanel("preferences");
		}
		
		else if (e.getActionCommand().equals("Infinite")) {
			preferencesPanel.getPrefixedComboBox().setEnabled(false);
		}
		else if (e.getActionCommand().equals("Prefixed")) {
			preferencesPanel.getPrefixedComboBox().setEnabled(true);	
		}
		
		else if (e.getActionCommand().equals("ClosePrefBtn")) {
			changePanel("game");
			resetFrameSize();
		}
		
		else if (e.getActionCommand().equals("SavePrefBtn")) {
			setTurnMode();
			setTurnTime();
			changePanel("game");
			resetFrameSize();
		}
		
		else if (e.getActionCommand().equals("StartGame")) {
			changePanel("game");
			player.setName(userPanel.getUsername());
			profilePanel.setUsername(userPanel.getUsername());
		}
		
		else if (e.getActionCommand().equals("Start")) {
			if (!this.inGame) {
				self.setSize(Constants.inGameFrameSize);
				menuBar.setSize(Constants.inGameMenuBarSize);
				gamePane.remove(shipZonePanel);
				gamePane.add(enemyBoardPanel);
				System.out.println(startGame());
				inGame = true;
				repaint();
			}
		}
		
		else if (e.getActionCommand().equals("CloseProfile")) {
			resetFrameSize();
			changePanel("game");
		}
		
		else if (e.getActionCommand().equals("SaveProfile")) {
			resetFrameSize();
			changePanel("game");
			player.setName(((ProfilePanel) profilePanel).getUsername());
		}
		
		else if (e.getActionCommand().equals("resetBoard")) {
			System.out.println(playerBoardPanel.resetBoard());
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
	
	public String startGame () {
		return "Game started";
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

