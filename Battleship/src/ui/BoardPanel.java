package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.TooManyListenersException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ui.interfaces.GridClickListener;
import ui.interfaces.GridClickPublisher;
import ui.interfaces.GridEnterListener;
import ui.interfaces.GridEnterPublisher;
import ui.interfaces.GridRightClickListener;
import utils.Enums.Direction;
import utils.Point;
import core.Ship;


public class BoardPanel extends JPanel implements MouseListener, GridClickPublisher {
	
	DropTarget dropTarget;
	
	Color buttonColor; 
	Color shipColor;
	
	JPanel[] shipPanels;
	String[] topBtnsText = {"1","2","3","4","5","6","7","8","9","10"};
	String[] leftBtnsText = {"A","B","C","D","E","F","G","H","I","J"};
	private List<JButton> btnArray;
	
	List<Ship> shipList;
	
	GridClickListener clickListener = new GridClickListener() {
		
		@Override
		public void onGridClick(int x, int y) {
			System.out.println("Listener per defecte. Crida setGridClickListener. (" + x + ", " + y + ")");
			
		}
	};
	
	GridRightClickListener rightClickListener = new GridRightClickListener() {
		
		@Override
		public void onGridRightClick(int x, int y) {
			System.out.println("Listener per defecte. Crida setRightGridClickListener. (" + x + ", " + y + ")");
			
		}
	};
	
	GridEnterListener enterListener = new GridEnterListener() {
		
		@Override
		public void onGridEnter(int x, int y) {
			System.out.println("Listener per defecte. Crida setGridEnterListener. (" + x + ", " + y + ")");
			
		}
	};
	
	
	public BoardPanel () {
		
		shipList = new ArrayList<>();
		
		buttonColor = Color.BLUE;
		shipColor = Color.black;
		
		setAlignmentY(Component.TOP_ALIGNMENT);
		GridBagLayout gbl_btnPanel = new GridBagLayout();
		gbl_btnPanel.columnWidths = new int[] {50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50};
		gbl_btnPanel.rowHeights = new int[] {50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50};
		gbl_btnPanel.columnWeights = new double[]{0.0,0,0,0,0,0,0,0,0,0};
		gbl_btnPanel.rowWeights = new double[]{0.0,0,0,0,0,0,0,0,0,0};
		setLayout(gbl_btnPanel);
		
		btnArray = new ArrayList<JButton>();
		int gridX = 0;
		int gridY = 0;
		
		for (int i = 1; i<=10; i++) {
			for (int j = 1; j <= 10 ; j++) {
				JButton button = new JButton();
				button.addMouseListener(new MyMouseAdapter());
				button.setForeground(Color.WHITE);
				button.setBackground(buttonColor);
				button.setPreferredSize(new Dimension(50, 50));
				button.setAlignmentX(Component.CENTER_ALIGNMENT);
				GridBagConstraints gbc_button = new GridBagConstraints();
				gbc_button.insets = new Insets(0, 0, 0, 0);
				gbc_button.gridx = gridX+1;
				gbc_button.gridy = gridY+1;
				button.setActionCommand(gridX + "," + gridY);
				button.addMouseListener(this);
				add(button, gbc_button);
				gridY++;
				btnArray.add(button);
			}
			gridY=0;
			gridX++;	
		}
		
		gridY=1;
		gridX=0;
		
		for (int i = 0; i<10; i++) {
			JLabel jlabel = new JLabel(leftBtnsText[i]);
			jlabel.setForeground(Color.BLACK);
			jlabel.setPreferredSize(new Dimension(50, 50));
			jlabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			GridBagConstraints gbc_label = new GridBagConstraints();
			gbc_label.insets = new Insets(0, 0, 0, 0);
			gbc_label.gridx = gridX;
			gbc_label.gridy = gridY;
			add(jlabel, gbc_label);
			gridY++;
		}
		
		gridY = 0;
		gridX = 1;
		
		for (int i = 0; i<10; i++) {
			JLabel jlabel = new JLabel(String.valueOf(i+1));
			jlabel.setForeground(Color.BLACK);
			jlabel.setPreferredSize(new Dimension(50, 50));
			jlabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			GridBagConstraints gbc_label = new GridBagConstraints();
			gbc_label.insets = new Insets(0, 0, 0, 0);
			gbc_label.gridx = gridX;
			gbc_label.gridy = gridY;
			add(jlabel, gbc_label);
			gridX++;
		}
		
		
		
	}
	
	Point getCoordsFromJButton(JButton b) {
		String[] coords = b.getActionCommand().split(",");
		
		int x = Integer.parseInt(coords[0]);
		int y = Integer.parseInt(coords[1]);
		
		return new Point(x, y);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
		if (e.getButton() == MouseEvent.BUTTON1) {
			Point p = getCoordsFromJButton((JButton) e.getSource());
			
			clickListener.onGridClick(p.getX(), p.getY());
			
		} else if (e.getButton() == MouseEvent.BUTTON3) {
			Point p = getCoordsFromJButton((JButton) e.getSource());
			
			rightClickListener.onGridRightClick(p.getX(), p.getY());
        }
		
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
		Point p = getCoordsFromJButton((JButton) e.getSource());
		
		enterListener.onGridEnter(p.getX(), p.getY());
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void setGridClickListener(GridClickListener l) {
		clickListener = l;
	}
	
	public void setGridEnterListener(GridEnterListener l) {
		enterListener = l;
	}
	
	public void setGridRightClickListener(GridRightClickListener l) {
		rightClickListener = l;
	}
	
	public String resetBoard () {
		for (JButton jButton : btnArray) {
			jButton.setBackground(buttonColor);
		}
		return "Board resetejat";
	}
	
	public List<Ship> getShipList() {
		return shipList;
	}

	/**
	 * 
	 * @param ship
	 * @return Retorna true si el vaixell es pot posicionar
	 */
	public boolean isValidPosition (Ship ship) {
		if (ship.getDirection() == Direction.HORIZONTAL) {	
			for (int x = ship.getX(); x < ship.getX() + ship.getLength(); x++) {
				if (getButtonAt(x, ship.getY()).getBackground() == shipColor)
					return false;
			}
		} else if (ship.getDirection() == Direction.VERTICAL) {
			for (int y = ship.getY(); y < ship.getY() + ship.getLength(); y++) {
				if (getButtonAt(y, ship.getX()).getBackground() == shipColor)
					return false;
			}
		}
		return true;
	}
	
	public boolean addShip(Ship ship) {
		
		if (ship.getDirection() == Direction.HORIZONTAL) {
			if (isValidPosition(ship)) {
				for (int x = ship.getX(); x < ship.getX() + ship.getLength(); x++) {
					getButtonAt(x, ship.getY()).setBackground(shipColor);
				}
			}
			else
				return false;
			
		} else if (ship.getDirection() == Direction.VERTICAL) {
			if (isValidPosition(ship)) {
				for (int y = ship.getY(); y < ship.getY() + ship.getLength(); y++) {
					getButtonAt(ship.getX(), y).setBackground(shipColor);
				}
			}
			else 
				return false;
		}
		
		shipList.add(ship);
		
		return true;
	}
	
	public void displayShip(int x, int y, Direction direction, int length) {
		
	}

	public List<JButton> getBtnArray() {
		return btnArray;
	}

	public void setBtnArray(List<JButton> btnArray) {
		this.btnArray = btnArray;
	}
	
	public JButton getButtonAt(int x, int y) {
		return btnArray.get(y + x * 10);
	}
//
//	@Override
//	public void dragEnter(DropTargetDragEvent arg0) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void dragExit(DropTargetEvent arg0) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void dragOver(DropTargetDragEvent arg0) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void drop(DropTargetDropEvent e) {
//		System.out.println("Dropping");
//
//	    try {
//	      Transferable t = e.getTransferable();
//
//	      if (e.isDataFlavorSupported(DataFlavor.stringFlavor)) {
//	        e.acceptDrop(e.getDropAction());
//
//	        String s;
//	        s = (String) t.getTransferData(DataFlavor.stringFlavor);
//	        
//	        //target.setText(s);
//	        System.out.println("Data droped: " + s);
//
//	        e.dropComplete(true);
//	      } else
//	        e.rejectDrop();
//	    } catch (java.io.IOException e2) {
//	    	
//	    } catch (UnsupportedFlavorException e2) {
//	    }
//	}
//
//	@Override
//	public void dropActionChanged(DropTargetDragEvent arg0) {
//		// TODO Auto-generated method stub
//		
//	}	
	
}


class MyMouseAdapter extends MouseAdapter {
	@Override
	public void mouseEntered(MouseEvent e) {
		super.mouseEntered(e);
//		buttonAfterColor = e.getComponent().getBackground();
//		e.getComponent().setBackground(new Color(255,255,0));
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		super.mouseExited(e);
		
//		e.getComponent().setBackground(buttonAfterColor);
	}
}
