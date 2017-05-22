package ui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;

import server.Client;
import server.ClientRunnable;
import server.MoveResult;
import server.Packet;
import server.Packet.PacketType;
import server.Protocol;
import ui.interfaces.EnemyPanelClickListener;
import ui.interfaces.GridClickListener;
import ui.interfaces.GridEnterListener;
import ui.interfaces.GridRightClickListener;
import ui.interfaces.ServerMoveListener;
import utils.Constants;
import utils.Enums.Direction;
import utils.Enums.GameMode;
import utils.Enums.HitType;
import utils.Point;
import core.Player;
import core.Ship;

import javax.swing.JLabel;

import java.awt.Font;

public class Gui extends JFrame implements GridClickListener, ActionListener, MouseMotionListener, GridRightClickListener, GridEnterListener, EnemyPanelClickListener, ServerMoveListener{

	private UserPanel userPanel;
	private JPanel contentPane;
	private ProfilePanel profilePanel;
	private PreferencesPanel preferencesPanel;
	private AboutPanel aboutPanel;
	private CardLayout cardLayout;
	private JMenuBar menuBar;
	private JMenuItem editProfile;
	private JMenuItem preferences;
	private JPanel gamePane;
	private BoardPanel playerBoardPanel;
	private BoardPanel enemyBoardPanel;
	private ShipZonePanel shipZonePanel;
	private JLabel yourTurn;
	private JLabel enemyTurn;
	private boolean inGame;
	private boolean playerTurn;
	
//	ClientRunnable clientRunnable;
	
	private boolean myTurn;
	private boolean hisTurn;
	
	int prefixedTurnTime;
	
	boolean infiniteTime; // true si es torn amb temps infinit, false si es torn amb temps prefixat
	
	GameMode gameMode;
	
	BufferedImage hitShape;
	
	Point lastClick;
	
	Gui self;
	
	private Player player;
	private Client client;
	
	Ship floatingShip = new Ship(0, 0, 0, Direction.HORIZONTAL);
	
	
	private static final int IMG_WIDTH = 50;
	private static final Color SHAPE_COLOR = Color.RED;
	private static final int GAP = 4;
	private JLabel usernameGameLabel;

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
	
	public BufferedImage getHitShape() {
		BufferedImage circleImg = new BufferedImage(IMG_WIDTH, IMG_WIDTH, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = circleImg.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(SHAPE_COLOR);
		int imgX = GAP;
		int imgY = GAP;
		int width = IMG_WIDTH - 2 * imgX;
		int height = IMG_WIDTH - 2 * imgY;
		g2.fillOval(imgX,imgY, width, height);
		g2.dispose();
		
		return circleImg;
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
		aboutPanel.setImageIcon(new ImageIcon(Gui.class.getResource("/img/icon.png")));
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
		playerBoardPanel.setGridRightClickListener(this);
		playerBoardPanel.setGridEnterListener(this);
		
		//enemyBoardPanel
		enemyBoardPanel = new BoardPanel();
		enemyBoardPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		enemyBoardPanel.setBounds(600, 53, 682, 562);
		enemyBoardPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		enemyBoardPanel.setPreferredSize(new Dimension(500, 500));
		enemyBoardPanel.setEnemyPanelClickPublisher(this);
		
		//profilePanel
		profilePanel = new ProfilePanel();
		profilePanel.getSaveBtn().addActionListener(this); // listener to return to main panel
		profilePanel.getCloseBtn().addActionListener(this);
		
		//profilePanel
		userPanel = new UserPanel();
		userPanel.getLblTitle().setBounds(138, 35, 599, 103);
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
		
		setHisTurn(false);
		setMyTurn(true);
		
		player = new Player();
		client = new Client();
//		clientRunnable = new ClientRunnable(client);
		
		
		
		hitShape = getHitShape();
		
		gameMode = GameMode.CLASSIC;
		
		addWindowListener(new MyWindowAdapter());
		
		setResizable(false);		
		setLocation(new java.awt.Point(50, 50));		
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
		
		JMenuItem startMenu = new JMenuItem("Start");
		startMenu.setMnemonic(startMenu.getText().charAt(0));
		startMenu.setActionCommand(startMenu.getText());
		startMenu.addActionListener(this);
		startMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		gameMenu.add(startMenu);
		
		JMenuItem loadMenu = new JMenuItem("Load");
		loadMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_MASK));
		gameMenu.add(loadMenu);
		
		JMenuItem saveMenu = new JMenuItem("Save");
		saveMenu.setIcon(new ImageIcon(Gui.class.getResource("/img/download-button.png")));
		saveMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_MASK));
		gameMenu.add(saveMenu);
		
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
		
		preferences = new JMenuItem("Preferences");
		preferences.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
		preferences.addActionListener(this);
		preferences.setActionCommand(preferences.getText());
		settingsMenu.add(preferences);
		
		JMenu helpMenu = new JMenu("Help");
		menuBar.add(helpMenu);
		
		JMenuItem onlineHelp = new JMenuItem("Online Help");
		onlineHelp.setIcon(new ImageIcon(Gui.class.getResource("/img/info.png")));
		onlineHelp.setActionCommand("OnlineHelp");
		onlineHelp.addActionListener(this);
		onlineHelp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		helpMenu.add(onlineHelp);
		
		JMenuItem about = new JMenuItem("About");
		about.setIcon(new ImageIcon(Gui.class.getResource("/img/quant.png")));
		about.setActionCommand(about.getText());
		about.addActionListener(this);
		about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
		helpMenu.add(about);
		
		yourTurn = new JLabel("Your Turn");
		yourTurn.setFont(new Font("Tahoma", Font.PLAIN, 30));
		yourTurn.setBounds(192, 24, 169, 29);
		yourTurn.setVisible(false);
		gamePane.add(yourTurn);
		
		enemyTurn = new JLabel("Enemy Turn");
		enemyTurn.setFont(new Font("Tahoma", Font.PLAIN, 30));
		enemyTurn.setBounds(870, 24, 192, 29);
		enemyTurn.setVisible(false);
		gamePane.add(enemyTurn);
		
		usernameGameLabel = new JLabel();
		usernameGameLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		usernameGameLabel.setBounds(10, 28, 133, 25);
		gamePane.add(usernameGameLabel);
		
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
		
		Ship clickedShip = shipZonePanel.getSelectedShip();
		if (clickedShip != null ) {
			
			Point currentClick = new Point(x, y);
	
			System.out.println("from " + lastClick + " to " + currentClick);
		
			Ship ship = new Ship(x, y, clickedShip.getLength(), floatingShip.getDirection(), clickedShip.getId());
			System.out.println(ship);
			
			playerBoardPanel.redrawBoard();
			if (playerBoardPanel.addShip(ship)) { // Si s'afegeix correctament (posicio correcta) borrem del panell
				System.out.println(shipZonePanel.removeShip(clickedShip));
				shipZonePanel.setSelectedShip(null);
				
				floatingShip.setDirection(Direction.HORIZONTAL);
			}
			else {
				JOptionPane.showMessageDialog(this, "Incorrect location", "Warning", JOptionPane.WARNING_MESSAGE);
			}
			repaint();
		}
	}
	
	public void toggleTurns () {
		setMyTurn(!myTurn);
		setHisTurn(!hisTurn);
		yourTurn.setVisible(isMyTurn());
		enemyTurn.setVisible(isHisTurn());
	}
	
	public boolean isMyTurn() {
		return myTurn;
	}

	public void setMyTurn(boolean myTurn) {
		this.myTurn = myTurn;
	}

	public boolean isHisTurn() {
		return hisTurn;
	}

	public void setHisTurn(boolean hisTurn) {
		this.hisTurn = hisTurn;
	}

	@Override
	public void onEnemyPanelClickListener(int x, int y) {
		System.out.println("Enemy Panel: Clicked on [" + x + ", " + y + "]");
		client.sendMove(x, y);
		
		enemyBoardPanel.setButtonsEnabled(false);
		
		toggleTurns();
	}
	
	@Override
	public void onServerMoveListener(String packetString) {
		
		Packet packet = Packet.fromString(packetString);
		
		if (packet.getType() == PacketType.MOVEMENT) {
			Point p = Protocol.parseMove(packet);
			
			System.out.println(p);
			
			toggleTurns();
			
			//player
		}
		
		if (packet.getType() == PacketType.MOVEMENT_RESULT) {
			MoveResult moveResult = Protocol.parseMoveResult(packet);
			System.out.println("Moveresult: " + moveResult);
			
			enemyBoardPanel.setButtonsEnabled(true);
			
			enemyBoardPanel.showMove(moveResult.getPoint(), moveResult.getHitType(), hitShape);
			
			if (moveResult.getHitType() == HitType.SUNK) {
				client.sendGetLastHitShip();
			}
			
		}
		
		if (packet.getType() == PacketType.GET_LAST_HIT_SHIP_RESULT) {
			Ship enemyShip = Protocol.parseGetLastShipResponse(packet);
			
			for (Point p : enemyShip.getSegmentsPositions())
				enemyBoardPanel.getButtonAt(p.getX(), p.getY()).setBackground(Color.RED);
			
			if (enemyShip.isLastShipStanding()) {
				JOptionPane.showMessageDialog(this, player.getName() + " Wins!", "", JOptionPane.INFORMATION_MESSAGE);
				client.close();
				this.dispose();
			}
		}
	}

	@Override
	public void onGridEnter(int x, int y) {
		floatingShip.setX(x);
		floatingShip.setY(y);
		
		int length = 0;
		if (shipZonePanel.getSelectedShip() != null)
			length = shipZonePanel.getSelectedShip().getLength();
		
		floatingShip.setLength(length);
		
		playerBoardPanel.redrawBoard();
		playerBoardPanel.drawShip(floatingShip);
		
		System.out.println(floatingShip);
	}

	@Override
	public void onGridRightClick(int x, int y) {
		floatingShip.setDirection((floatingShip.getDirection() == Direction.HORIZONTAL) ? Direction.VERTICAL : Direction.HORIZONTAL);
		
		playerBoardPanel.redrawBoard();
		playerBoardPanel.drawShip(floatingShip);
		
		System.out.println("New direction is " + floatingShip.getDirection());
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
			((ProfilePanel) profilePanel).setUsername(player.getName());
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
			usernameGameLabel.setText(userPanel.getUsername());
		}
		
		else if (e.getActionCommand().equals("Start")) {
			
			if (playerBoardPanel.getShipList().size() != 5) {
				JOptionPane.showMessageDialog(this, "Locate all your ships in the board before start the game", "", JOptionPane.INFORMATION_MESSAGE);
				
			} else if (!this.inGame) {
				self.setSize(Constants.inGameFrameSize);
				menuBar.setSize(Constants.inGameMenuBarSize);
				gamePane.remove(shipZonePanel);
				gamePane.add(enemyBoardPanel);
				yourTurn.setVisible(true);
				System.out.println(startGame());
				inGame = true;				
				repaint();
				
				preferences.setEnabled(false);
				
				
				client.setConected(true);
				client.setServerMoveListener(this);
				client.open();

				
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
			usernameGameLabel.setText(player.getName());
		}
		
		else if (e.getActionCommand().equals("resetBoard")) {
			playerBoardPanel.clearBoard();
			playerBoardPanel.resetShips();
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
			Object[] options = {
				"Accept",
				"Cancel"
			};
			
			int result = JOptionPane.showOptionDialog(self, "Are you sure about this?", "Confirm", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
			if (result == JOptionPane.YES_OPTION) {
				if (client.isConected())
					client.close(); // tanquem totes les conexions i Streams del client
				
				setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			}
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

