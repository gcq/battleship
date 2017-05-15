package ui;

import java.awt.CardLayout;
import java.awt.Component;
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
import core.Player;
import core.Ship;
import core.exceptions.InvalidPointsForShipException;

public class Gui extends JFrame implements GridClickListener, ActionListener{

	private UserPanel userPanel;
	private JPanel contentPane;
	private JPanel profilePanel;
	private CardLayout cardLayout;
	private JPanel gamePane;
	private BoardPanel boardPanel;
	private ShipZonePanel shipZonePanel;
	
	Point lastClick;
	
	private int portavionesLength = 5;
	private int acorazadoLength = 4;
	private int cruceroLength = 3;
	private int submarinoLength = 3;
	private int destructorLength = 2;
	
	Ship[] shipArray;
	int ships = 5;
	
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
		
		shipArray = new Ship[ships];
		
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
		menuBar.setBounds(0, 0, 800, 21);
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
		
		Point currentClick = new Point(x, y);
		
		if (lastClick == null) {
			lastClick = currentClick;
		
		} else {
			System.out.println("from " + lastClick + " to " + currentClick);
			
			Ship ship = null;
			try {
				ship = Ship.getShipFromPoints(lastClick, currentClick);
			} catch (InvalidPointsForShipException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			boardPanel.addShip(ship);
			
			System.out.println(ship);
			
			lastClick = null;
		}
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
	
	public void initShips () {
		Ship portaviones = new Ship(portavionesLength, 1);
		Ship acorazado = new Ship(acorazadoLength, 2);
		Ship crucero = new Ship(cruceroLength, 3);
		Ship submarino = new Ship(submarinoLength, 4);
		Ship destructor = new Ship(destructorLength, 5);
		this.shipArray[0] = portaviones;
		this.shipArray[1] = acorazado;
		this.shipArray[2] = crucero;
		this.shipArray[3] = submarino;
		this.shipArray[4] = destructor;
		
		for (int i = 0; i < ships; i++) {
			for (int j = 0; j < shipArray[i].getLength(); j++) {
				
			}
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

}

