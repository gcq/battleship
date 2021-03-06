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


/**
 * 
 * @author Victor
 * JButton que identifica cada un dels vaixells a posicionar
 *
 */
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

/**
 * 
 * @author Victor
 * Panell on es troben els vaixells a posicionar en el taulell
 *
 */
public class ShipZonePanel extends JPanel{
	
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
	
	public void removeShip (Ship ship) {
		System.out.println("Removing ship with index index: " + this.shipArray.indexOf(ship));
		remove(this.btnArray.get(this.shipArray.indexOf(ship)));
	}
	
	public void reset () {
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
		
		button.addMouseListener(new MyMouseAdapter());
		
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
	}
	
	class MyMouseAdapter extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent e) {
			super.mouseClicked(e);
			System.out.println("component: " + e.getComponent());
			System.out.println("selected ship: " + shipArray.get(btnArray.indexOf(e.getComponent())));
			setSelectedShip(shipArray.get(btnArray.indexOf(e.getComponent())));
		}
	}

}
