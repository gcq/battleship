package ui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;

import core.Board;
import core.Player;
import core.Ship;
import server.Client;
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
import utils.NoSeLaVeritat;
import utils.Point;

public class Gui extends JFrame implements GridClickListener, ActionListener, GridRightClickListener, GridEnterListener, EnemyPanelClickListener, ServerMoveListener{



	private InitialPanel userPanel;
	private JPanel contentPane;
	private ProfilePanel profilePanel;
	private PreferencesPanel preferencesPanel;
	private AboutPanel aboutPanel;
	private CardLayout cardLayout;
	private JMenuBar menuBar;
	private JMenuItem editProfile;

	private JMenuItem preferences;

	private JMenuItem exitItem;

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
	
	Gui self;
	
	private Player player;
	private Client client;
	
	Ship floatingShip = new Ship(0, 0, 0, Direction.HORIZONTAL);
	
	
	
	private JLabel usernameGameLabel;
	private JSeparator separator;
	private JMenuItem restartItem;

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
		userPanel = new InitialPanel();
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
		
		player = new Player(new Board(10, 10));
		client = new Client();
//		clientRunnable = new ClientRunnable(client);
		
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
		
		JMenuItem loadMenu = new JMenuItem("Load");
		loadMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_MASK));
		gameMenu.add(loadMenu);
		
		JMenuItem saveMenu = new JMenuItem("Save");
		saveMenu.setIcon(new ImageIcon(Gui.class.getResource("/img/download-button.png")));
		saveMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_MASK));
		gameMenu.add(saveMenu);
		
		separator = new JSeparator();
		gameMenu.add(separator);
		
		JMenuItem startMenu = new JMenuItem("Start");
		startMenu.setMnemonic(startMenu.getText().charAt(0));
		startMenu.setActionCommand(startMenu.getText());
		startMenu.addActionListener(this);
		startMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		gameMenu.add(startMenu);
		
		restartItem = new JMenuItem("Restart");
		restartItem.addActionListener(this);
		restartItem.setActionCommand(restartItem.getText());
		gameMenu.add(restartItem);
		
		exitItem = new JMenuItem("Exit");
		exitItem.addActionListener(this);
		exitItem.setActionCommand(exitItem.getText());
		gameMenu.add(exitItem);
		
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
		yourTurn.setBounds(212, 24, 169, 29);
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
		
			Ship ship = new Ship(x, y, clickedShip.getLength(), floatingShip.getDirection(), clickedShip.getId());
			System.out.println(ship);
			
			if (playerBoardPanel.addShip(ship)) { // Si s'afegeix correctament (posicio correcta) borrem del panell
				shipZonePanel.removeShip(clickedShip);
				shipZonePanel.setSelectedShip(null);
				
				floatingShip.setDirection(Direction.HORIZONTAL);
			}
			else {
				JOptionPane.showMessageDialog(this, "Incorrect location", "Warning", JOptionPane.WARNING_MESSAGE);
			}
			repaint();
		}
		
		playerBoardPanel.redrawBoard();
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
			
			HitType hp = playerBoardPanel.getPlayer().hit(p.getX(), p.getY());
			
			if (hp == HitType.SUNK) {
				for (Point pt : playerBoardPanel.getPlayer().getLastHitShip().getSegmentsPositions())
					playerBoardPanel.getButtonAt(pt.getX(), pt.getY()).setBackground(Color.RED);
			} else 
				playerBoardPanel.showMove(p, hp, NoSeLaVeritat.getHitShape());
			
			toggleTurns();
		}
		
		if (packet.getType() == PacketType.MOVEMENT_RESULT) {
			MoveResult moveResult = Protocol.parseMoveResult(packet);
			System.out.println("Moveresult: " + moveResult);
			
			enemyBoardPanel.setButtonsEnabled(true);
			
			enemyBoardPanel.showMove(moveResult.getPoint(), moveResult.getHitType(), NoSeLaVeritat.getHitShape());
			
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
		
		if (!playerBoardPanel.isValidPosition(floatingShip))
			System.out.println("Posicio invalida. Mostrar algo en la GUI");
			
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
		
		else if (e.getActionCommand().equals("Exit")) {
			this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		}
		else if (e.getActionCommand().equals("Restart")) {
			if (!this.inGame) {
				playerBoardPanel.clearBoard();
				playerBoardPanel.resetShips();
				shipZonePanel.reset();
			}
			else
				JOptionPane.showMessageDialog(this, "You can't restart the board while you're playing!", "", JOptionPane.WARNING_MESSAGE);
		}
		
		else if (e.getActionCommand().equals("Start")) {
			
			if (playerBoardPanel.getShipList().size() != 5) {
				JOptionPane.showMessageDialog(this, "Locate all your ships in the board before start the game", "", JOptionPane.INFORMATION_MESSAGE);
				
			} else if (!this.inGame) {
				client.setConected(client.open());
				if (client.isConected()) {
					self.setSize(Constants.inGameFrameSize);
					menuBar.setSize(Constants.inGameMenuBarSize);
					gamePane.remove(shipZonePanel);
					gamePane.add(enemyBoardPanel);
					yourTurn.setVisible(true);
					System.out.println(startGame());
					inGame = true;
					
					playerBoardPanel.removeGridClickListener();
					playerBoardPanel.removeGridEnterListener();
					playerBoardPanel.removeGridRightClickListener();
					
					repaint();
				}
				else
					JOptionPane.showMessageDialog(this, "Connection refused", "", JOptionPane.WARNING_MESSAGE);
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
			shipZonePanel.reset();
			player.clearBoard();
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
			File htmlFile = new File("src/ui/help.html");
			openWebpage(htmlFile.toURI());
		}
	}
	
	public String startGame () {
		return "Game started";
	}
	
	public void closeWindow () {
		Object[] options = {
				"Accept",
				"Cancel"
			};
		
		int result = JOptionPane.showOptionDialog(self, "Are you sure about this?", "Leaving...", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
		if (result == JOptionPane.YES_OPTION) {
			if (client.isConected())
				client.close(); // tanquem totes les conexions i Streams del client
			
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		}
		else
			setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
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
			closeWindow();

		}
	}

}

