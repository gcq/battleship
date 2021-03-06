
package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.dnd.DropTarget;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import core.Board;
import core.Player;
import core.Ship;
import core.exceptions.InvalidShipPlacementException;
import ui.interfaces.EnemyPanelClickListener;
import ui.interfaces.EnemyPanelClickPublisher;
import ui.interfaces.GridClickListener;
import ui.interfaces.GridClickPublisher;
import ui.interfaces.GridEnterListener;
import ui.interfaces.GridRightClickListener;
import utils.Enums.Direction;
import utils.Enums.HitType;
import utils.Point;

/**
 * 
 * @author Victor
 * Panell utilitzat per el taulell tant del jugador com del adversari
 * Omplert de JButtons que ser�n pintats posteriorment segons els colors de cada vaixell posicionat
 *
 */
public class BoardPanel extends JPanel implements GridClickPublisher, EnemyPanelClickPublisher {
	
	DropTarget dropTarget;
	
	Color buttonColor; 
	Color shipColor;
	
	JPanel[] shipPanels;
	String[] topBtnsText = {"1","2","3","4","5","6","7","8","9","10"};
	String[] leftBtnsText = {"A","B","C","D","E","F","G","H","I","J"};
	private List<JButton> btnArray;
	
	Player player;
	
	GridClickListener clickListener;
	GridRightClickListener rightClickListener;
	GridEnterListener enterListener;
	
	EnemyPanelClickListener enemyPanelListener = new EnemyPanelClickListener() {
		
		@Override
		public void onEnemyPanelClickListener(int x, int y) {
			System.out.println("Listener per defecte. Crida setEnemyPanelClickPublisher. (" + x + ", " + y + ")");
		}
	};
	
	
	public BoardPanel () {
		
		player = new Player(new Board(10, 10)); 
		
		buttonColor = new Color(40, 122, 255);
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
				button.setForeground(Color.WHITE);
				button.setBackground(buttonColor);
				button.setPreferredSize(new Dimension(50, 50));
				button.setAlignmentX(Component.CENTER_ALIGNMENT);
				GridBagConstraints gbc_button = new GridBagConstraints();
				gbc_button.insets = new Insets(0, 0, 0, 0);
				gbc_button.gridx = gridX+1;
				gbc_button.gridy = gridY+1;
				button.setActionCommand(gridX + "," + gridY);
				
				button.addMouseListener(new MyMouseAdapter());
				
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
	
	public void setGridClickListener(GridClickListener l) {
		clickListener = l;
	}
	
	public void removeGridClickListener() {
		clickListener = null;
	}
	
	public void setGridEnterListener(GridEnterListener l) {
		enterListener = l;
	}
	
	public void removeGridEnterListener() {
		enterListener = null;
	}
	
	public void setGridRightClickListener(GridRightClickListener l) {
		rightClickListener = l;
	}
	
	public void removeGridRightClickListener() {
		rightClickListener = null;
	}
	
	@Override
	public void setEnemyPanelClickPublisher(EnemyPanelClickListener l) {
		enemyPanelListener = l;
	}
	
	public void setButtonsEnabled(boolean enabled) {
		for (JButton jButton : btnArray) {
			jButton.setEnabled(enabled);
		}
	}
	
	public void showMove(Point p, HitType h, BufferedImage hitShape) {
		if (h == HitType.HIT) {
			getButtonAt(p.getX(), p.getY()).setIcon(new ImageIcon(hitShape));
		}
		
		else if (h == HitType.WATER) {
			getButtonAt(p.getX(), p.getY()).setText("X");
		}
	}
	
	public void resetShips () {
		player.clear();
	}
	
	public void clearBoard () {
		for (JButton jButton : btnArray) {
			jButton.setBackground(buttonColor);
			jButton.setText("");
		}
	}
	
	public void redrawBoard() {
		clearBoard();
		
		for (Ship s : player.getShips()) {
			drawShip(s);
		}
		
		//Dibuixar cada cela amb el valor tocado o agua
	}
	
	public List<Ship> getShipList() {
		return player.getShips();
	}

	/**
	 * 
	 * @param ship
	 * @return Retorna true si el vaixell es pot posicionar
	 */
	public boolean isValidPosition (Ship ship) {
		return player.checkShipPlacement(ship);
	}
	
	public void drawShip(Ship ship) {
		
		Color shipColor = Color.getHSBColor((ship.getId() / 5f) - 0.07f, 1f, 1f);
		
		if (ship.getDirection() == Direction.HORIZONTAL) {
			for (int x = ship.getX(); x < ship.getX() + ship.getLength() && x < 10; x++) {
				getButtonAt(x, ship.getY()).setBackground(shipColor);
			}
			
		} else if (ship.getDirection() == Direction.VERTICAL) {
			for (int y = ship.getY(); y < ship.getY() + ship.getLength() && y < 10; y++) {
				getButtonAt(ship.getX(), y).setBackground(shipColor);
			}
		}
	}
	
	public boolean addShip(Ship ship) {
		try {
			player.addShip(ship);
		} catch (InvalidShipPlacementException e) {
			return false;
		}
		
		return true;
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
	
	public Player getPlayer() {
		return player;
	}

	class MyMouseAdapter extends MouseAdapter{
		@Override
		public void mouseEntered(MouseEvent e) {
			super.mouseEntered(e);
			Point p = getCoordsFromJButton((JButton) e.getSource());
			
			if (enterListener != null)
				enterListener.onGridEnter(p.getX(), p.getY());
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			super.mouseClicked(e);
			if (e.getButton() == MouseEvent.BUTTON1) {
				Point p = getCoordsFromJButton((JButton) e.getSource());
				
				if (clickListener != null)
					clickListener.onGridClick(p.getX(), p.getY());
				
				if (enemyPanelListener != null)
					enemyPanelListener.onEnemyPanelClickListener(p.getX(), p.getY());
				
			} else if (e.getButton() == MouseEvent.BUTTON3) {
				Point p = getCoordsFromJButton((JButton) e.getSource());
				
				if (rightClickListener != null)
					rightClickListener.onGridRightClick(p.getX(), p.getY());
	        }
		}
	}
}

