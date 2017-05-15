package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

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
	public ShipPanelButton () {
		setBackground(Color.black);
	}
}

public class ShipZonePanel extends JPanel implements MouseListener{
	
	BoardPanel boardPanel;
	
	int ships = 5;
	
	Point originPoint = null;
	
	Ship[] shipArray;
	private List<JButton>btnArray;
	
	private int portavionesLength = 5;
	private int acorazadoLength = 4;
	private int cruceroLength = 3;
	private int submarinoLength = 3;
	private int destructorLength = 2;
	
	JPanel[] panelArray;
	
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
	}
	
	public JPanel[] getPanelArray () {
		return this.panelArray;
	}
	
	
	
	
	public ShipZonePanel() {
		shipArray = new Ship[ships];
		panelArray = new JPanel[ships];
		
		initShips();
		setPreferredSize(new Dimension(250, 250));
		//setAlignmentY(Component.TOP_ALIGNMENT);
		GridBagLayout gbl_btnPanel = new GridBagLayout();
		gbl_btnPanel.columnWidths = new int[] {50, 50, 50, 50, 50};
		gbl_btnPanel.rowHeights = new int[] {50, 50, 50, 50, 50};
		gbl_btnPanel.columnWeights = new double[]{0,0,0,0,0};
		gbl_btnPanel.rowWeights = new double[]{0,0,0,0,0};
		setLayout(gbl_btnPanel);
		
		int gridX = 0;
		int gridY = 0;
		
		btnArray = new ArrayList<JButton>();
		
		for (int i = 0; i < shipArray.length; i++) {
			JButton button = new ShipPanelButton();			
			GridBagConstraints gbc_button = new GridBagConstraints();				
			gbc_button.gridwidth = shipArray[i].getLength(); 
			gbc_button.insets = new Insets(0, 0, 5, 0);
			gbc_button.gridx = gridX;
			gbc_button.gridy = gridY;
			gbc_button.fill=GridBagConstraints.BOTH;
			button.addMouseListener(new MyMouseAdapter());
//			button.addActionListener(this);
			button.setActionCommand(gridX + "," + gridY);
			button.addMouseListener((MouseListener) this);
			add(button, gbc_button);
			gridY++;
			btnArray.add(button);
			
			System.out.println("gridX: " + gridX + " | " + "gridY: " + gridY);
			System.out.println("Button Width: " + gbc_button.gridwidth);	
		}

	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
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
