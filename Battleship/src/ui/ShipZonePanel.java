package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragGestureRecognizer;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceContext;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.awt.dnd.DropTarget;
import java.awt.dnd.InvalidDnDOperationException;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import utils.Enums.Direction;
import utils.Point;
import core.Ship;



class ShipPanel extends JPanel {
	public ShipPanel() {
		setBounds(0, 0, 100, 100);
		setMinimumSize(new Dimension(150, 50));
		setMaximumSize(new Dimension(160, 50));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		int action = DnDConstants.ACTION_COPY_OR_MOVE;
	}
}

class ShipPanelButton extends JButton {
	boolean clicked;
	
	public ShipPanelButton () {
		setBackground(Color.black);
		this.clicked = false;
	}
	
	public boolean isClicked () {
		return this.clicked;
	}
	
	public void setClicked (boolean click) {
		this.clicked = click;
	}
}

public class ShipZonePanel extends JPanel implements  MouseListener, MouseMotionListener, FocusListener{
	
	private BoardPanel boardPanel;
	
	private JButton resetBoardBtn;
	
	private Ship selectedShip;
	
	private int ships = 5;
	
	private Point originPoint = null;
	
	private ShipZonePanel self;
	
	private List<Ship> shipArray;
	private List<JButton>btnArray;
	
	private int portavionesLength = 5;
	private int acorazadoLength = 4;
	private int cruceroLength = 3;
	private int submarinoLength = 3;
	private int destructorLength = 2;
	
	public void initShips () {
		Ship portaviones = new Ship(portavionesLength, Direction.HORIZONTAL);
		portaviones.setId(0);
		Ship acorazado = new Ship(acorazadoLength, Direction.HORIZONTAL);
		acorazado.setId(1);
		Ship crucero = new Ship(cruceroLength, Direction.HORIZONTAL);
		crucero.setId(2);
		Ship submarino = new Ship(submarinoLength, Direction.HORIZONTAL);
		submarino.setId(3);
		Ship destructor = new Ship(destructorLength, Direction.HORIZONTAL);
		destructor.setId(4);
		this.shipArray.add(portaviones);
		this.shipArray.add(acorazado);
		this.shipArray.add(crucero);
		this.shipArray.add(submarino);
		this.shipArray.add(destructor);
	}
	
	
	public Ship getSelectedShip() {
		return selectedShip;
	}

	public JButton getResetBoardBtn() {
		return resetBoardBtn;
	}

	public void setSelectedShip(Ship selectedShip) {
		this.selectedShip = selectedShip;
	}
	
	public String removeShip (Ship ship) {
		System.out.println("index: " + this.shipArray.indexOf(ship));
		remove(this.btnArray.get(this.shipArray.indexOf(ship)));
		return "Vaixell " + ship + " esborrat del panell";
	}
	
	public String reset () {
		int gridX = 0;
		int gridY = 0;
		
		for (int i = 0; i < shipArray.size(); i++) {
			btnArray.get(i).setVisible(false);
			setButtonPropertiesAndAdd(btnArray.get(i), shipArray.get(i).getLength(), gridX, gridY);
			btnArray.get(i).setVisible(true);
			repaint();
			gridY++;
		}
		System.out.println("Erased shipsArray: " + shipArray);
		
		System.out.println("Init shipsArray: " + shipArray);
		
		System.out.println("btnArray: " + btnArray);
		return "ShipZonePanel reset";
	}
	
	public void removeAllShips () {
		for (int i = 0; i < shipArray.size(); i++) {
			System.out.println(i);
			remove(this.btnArray.get(i));
			this.btnArray.remove(i);
			this.shipArray.remove(i);
		}
		repaint();
	}
	
	public void setButtonPropertiesAndAdd (JButton button, int width, int gridX, int gridY) {
		GridBagConstraints gbc_button = new GridBagConstraints();				
		gbc_button.gridwidth = width; 
		gbc_button.insets = new Insets(0, 0, 5, 0);
		gbc_button.gridx = gridX;
		gbc_button.gridy = gridY;
		gbc_button.fill=GridBagConstraints.BOTH;
		button.addMouseListener(this);
		button.setActionCommand(gridX + "," + gridY);
		add(button, gbc_button);
		
	}
	
	public void initShipsBtnArray () {
		int gridX = 0;
		int gridY = 0;
		for (int i = 0; i < ships; i++) {
			JButton button = new ShipPanelButton();			
			GridBagConstraints gbc_button = new GridBagConstraints();
			
			setButtonPropertiesAndAdd(button, shipArray.get(i).getLength(), gridX, gridY);
			
			gridY++;
			btnArray.add(button);
			System.out.println("gridX: " + gridX + " | " + "gridY: " + gridY);
			System.out.println("Button Width: " + gbc_button.gridwidth);
		}
		repaint();
	}

	public ShipZonePanel() {
		self = this;
		
		shipArray = new ArrayList<Ship>();
		
		JPanel panel = new JPanel();
		
		initShips();
		
		panel.setPreferredSize(new Dimension(250, 250));
		GridBagLayout gbl_btnPanel = new GridBagLayout();
		gbl_btnPanel.columnWidths = new int[] {50, 50, 50, 50, 50};
		gbl_btnPanel.rowHeights = new int[] {50, 50, 50, 50, 50, 50};
		gbl_btnPanel.columnWeights = new double[]{0,0,0,0,0};
		gbl_btnPanel.rowWeights = new double[]{0,0,0,0,0, 0};
		setLayout(gbl_btnPanel);
		
		resetBoardBtn = new JButton("Reset Board");
		resetBoardBtn.setActionCommand("resetBoard");
		GridBagConstraints gbc_resetBoard = new GridBagConstraints();
		gbc_resetBoard.insets = new Insets(0, 0, 0, 5);
		gbc_resetBoard.gridx = 2;
		gbc_resetBoard.gridy = 5;
		add(resetBoardBtn, gbc_resetBoard);
		
		btnArray = new ArrayList<JButton>();
		
		initShipsBtnArray();

//		addFocusListener(this);
//		addMouseMotionListener(this);
//		addMouseListener(this);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
	}

	@Override
	public void focusGained(FocusEvent e) {
	}

	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("component: " + e.getComponent());
		System.out.println("selected ship: " + shipArray.get(btnArray.indexOf(e.getComponent())));
		setSelectedShip(shipArray.get(btnArray.indexOf(e.getComponent())));
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
//	private boolean rotatePanel (JPanel panel) {
//		
//		//if panel has X Axis layout
//		if (((BoxLayout) panel.getLayout()).getAxis() == BoxLayout.X_AXIS) {
//			// set the layout to y axis
//			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
//			
//			// swap the width and height
//			int temp = panel.getWidth();
//			int temp2 = panel.getHeight();
//			panel.setSize(temp2, temp);
//			
//			// revalidates the panel, forcing the layout to update
//			panel.validate();
//			
//			// sets the panel location
//			panel.setLocation(panel.getX(), panel.getY());
//			
//			// gets the length of the ship
//			int counter = 0;
//			while (Y_ORIGIN + ((TILE_SIZE + BORDER_SIZE) * counter) < panel.getY() + panel.getWidth()) {
//				counter++;
//			}
//			counter--;
//
//			// if the panel is intersecting another ship panel or is partially
//			// off the grid
//			if (!(counter <= 10 - panel.getHeight() / TILE_SIZE && counter >= 0)
//					|| isIntersection(panel)) {
//				// return that the rotation was a failure
//				return false;
//			}
//		}
//		
//		//if panel has Y Axis layout
//		else if (((BoxLayout) panel.getLayout()).getAxis() == BoxLayout.Y_AXIS) {
//			// set the layout to y axis
//			panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
//			// swap the width and height
//			int temp = panel.getWidth();
//			int temp2 = panel.getHeight();
//			panel.setSize(temp2, temp);
//			
//			// revalidates the panel, forcing the layout to update
//			panel.validate();
//
//			// sets the panel location
//			panel.setLocation(panel.getX(), panel.getY());
//
//			// gets the length of the ship
//			int counter = 0;
//			while (X_ORIGIN + ((TILE_SIZE + BORDER_SIZE) * counter) < panel.getX() + panel.getHeight()) {
//				counter++;
//			}
//			counter--;
//
//			// if the panel is intersecting another ship panel or is partially
//			// off the grid
//			if (!(counter <= 10 - panel.getWidth() / TILE_SIZE && counter >= 0)
//					|| isIntersection(panel)) {
//				// set the location to the starting location
//				// panel.setLocation(shipArray[shipNum].getStartingOffGridPosition());
//				// return that the rotation was a failure
//				return false;
//			}
//		}
//		return true;
//	}
//	
//	private boolean isIntersection (JPanel panel) {
//		for (int i = 0; i < panelArray.length; i++) {
//			// checks if p intersects with a panel in the array other than
//			// itself
//			if (panel.getBounds().intersects(panelArray[i].getBounds()) && !panel.equals(panelArray[i])) {
//				return true;
//			}
//		}
//		return false;
//	}
}
