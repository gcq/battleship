package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
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
import java.awt.event.MouseMotionAdapter;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import core.Ship;



class ShipPanel extends JPanel implements DragGestureListener, DragSourceListener{
	
	private DragSource ds = DragSource.getDefaultDragSource();
	 
	public ShipPanel() {
		setBounds(0, 0, 100, 100);
		setMinimumSize(new Dimension(150, 50));
		setMaximumSize(new Dimension(160, 50));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		int action = DnDConstants.ACTION_COPY_OR_MOVE;
	    ds.createDefaultDragGestureRecognizer(this, action, this);
	}

	@Override
	public void dragDropEnd(DragSourceDropEvent dsde) {
		System.out.println("Drag and drop end");

	    if (dsde.getDropSuccess() == false) {
	      System.out.println("unsuccessful");
	      return;
	    }
	}

	@Override
	public void dragEnter(DragSourceDragEvent dsde) {
		System.out.println("Entering drop target #2");

	    DragSourceContext ctx = dsde.getDragSourceContext();

	    int action = dsde.getDropAction();
	    if ((action & DnDConstants.ACTION_COPY) != 0)
	      ctx.setCursor(DragSource.DefaultCopyDrop);
	    else
	      ctx.setCursor(DragSource.DefaultCopyNoDrop);
	}

	@Override
	public void dragExit(DragSourceEvent dse) {
		System.out.println("Exiting drop target #2");
	}

	@Override
	public void dragOver(DragSourceDragEvent dsde) {
	    System.out.println("Dragging over drop target #2");
	}

	@Override
	public void dropActionChanged(DragSourceDragEvent dsde) {
	    System.out.println("Drop action changed #2");
	}

	@Override
	public void dragGestureRecognized(DragGestureEvent e) {
		try {
			JPanel panel = ((JPanel)e.getComponent());
			String transferData = String.valueOf(panel.getComponents().length);
			System.out.println("Transfer data (ShipLength): " + transferData);
			Transferable t = new StringSelection(transferData);
			e.startDrag(DragSource.DefaultCopyNoDrop, t, this);
	    } catch (InvalidDnDOperationException e2) {
	      System.out.println(e2);
	    }
	}
}

class ShipPanelButton extends JButton {
	public ShipPanelButton () {
		setBackground(Color.black);
		setMinimumSize(new Dimension(20, 30));
		setMaximumSize(new Dimension(30, 30));
	}
}

public class ShipZonePanel extends JPanel{
	
	public static final int X_ORIGIN = 75;
	public static final int Y_ORIGIN = 117;
	public static final int TILE_SIZE = 47;
	public static final int BORDER_SIZE = 5;
	
	BoardPanel boardPanel;

	int ships = 5;
	
	Ship[] shipArray;
	
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
		
		JLabel title = new JLabel("Drag and drop your ships");
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(title);
		
		setLayout(null);
		
		int shipNum;
		
		for (int i = 0; i < panelArray.length; i++) {
			shipNum = i;
			JPanel panel = new ShipPanel();
			for (int j = 0; j < shipArray[i].getLength(); j++) {
				JButton btn = new ShipPanelButton();
				btn.setName("mec");
				panel.add(btn);
//				System.out.println((JButton)panel.getComponents()[j]);
			}
			add(panel);
			this.panelArray[i] = panel;
		}
		
		

	}
	
	private boolean rotatePanel (JPanel panel) {
		
		//if panel has X Axis layout
		if (((BoxLayout) panel.getLayout()).getAxis() == BoxLayout.X_AXIS) {
			// set the layout to y axis
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			
			// swap the width and height
			int temp = panel.getWidth();
			int temp2 = panel.getHeight();
			panel.setSize(temp2, temp);
			
			// revalidates the panel, forcing the layout to update
			panel.validate();
			
			// sets the panel location
			panel.setLocation(panel.getX(), panel.getY());
			
			// gets the length of the ship
			int counter = 0;
			while (Y_ORIGIN + ((TILE_SIZE + BORDER_SIZE) * counter) < panel.getY() + panel.getWidth()) {
				counter++;
			}
			counter--;

			// if the panel is intersecting another ship panel or is partially
			// off the grid
			if (!(counter <= 10 - panel.getHeight() / TILE_SIZE && counter >= 0)
					|| isIntersection(panel)) {
				// return that the rotation was a failure
				return false;
			}
		}
		
		//if panel has Y Axis layout
		else if (((BoxLayout) panel.getLayout()).getAxis() == BoxLayout.Y_AXIS) {
			// set the layout to y axis
			panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
			// swap the width and height
			int temp = panel.getWidth();
			int temp2 = panel.getHeight();
			panel.setSize(temp2, temp);
			
			// revalidates the panel, forcing the layout to update
			panel.validate();

			// sets the panel location
			panel.setLocation(panel.getX(), panel.getY());

			// gets the length of the ship
			int counter = 0;
			while (X_ORIGIN + ((TILE_SIZE + BORDER_SIZE) * counter) < panel.getX() + panel.getHeight()) {
				counter++;
			}
			counter--;

			// if the panel is intersecting another ship panel or is partially
			// off the grid
			if (!(counter <= 10 - panel.getWidth() / TILE_SIZE && counter >= 0)
					|| isIntersection(panel)) {
				// set the location to the starting location
				// panel.setLocation(shipArray[shipNum].getStartingOffGridPosition());
				// return that the rotation was a failure
				return false;
			}
		}
		return true;
	}
	
	private boolean isIntersection (JPanel panel) {
		for (int i = 0; i < panelArray.length; i++) {
			// checks if p intersects with a panel in the array other than
			// itself
			if (panel.getBounds().intersects(panelArray[i].getBounds()) && !panel.equals(panelArray[i])) {
				return true;
			}
		}
		return false;
	}
}
