package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


class ShipPanel extends JPanel{
	public ShipPanel() {
		setBounds(0, 0, 100, 100);
		setMinimumSize(new Dimension(150, 50));
		setMaximumSize(new Dimension(160, 50));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
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
	
	private int portavionesLength = 5;
	private int acorazadoLength = 4;
	private int cruceroLength = 3;
	private int submarinoLength = 3;
	private int destructorLength = 2;
	
	public ShipZonePanel() {
		
		JLabel title = new JLabel("Drag and drop your ships");
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(title);
		
		setLayout(null);
		
		JPanel portavionesPanel = new ShipPanel();
		for (int i = 0; i < portavionesLength ; i++) {
			JButton btn = new ShipPanelButton();
			portavionesPanel.add(btn);
		}
		
		add(portavionesPanel);
		
		JPanel acorazadoPanel = new ShipPanel();
		for (int i = 0; i < acorazadoLength ; i++) {
			JButton btn = new ShipPanelButton();
			acorazadoPanel.add(btn);
		}
		add(acorazadoPanel);
		
		JPanel cruceroPanel = new ShipPanel();
		for (int i = 0; i < cruceroLength ; i++) {
			JButton btn = new ShipPanelButton();
			cruceroPanel.add(btn);
		}
		add(cruceroPanel);
		
		JPanel submarinoPanel = new ShipPanel();
		for (int i = 0; i < submarinoLength ; i++) {
			JButton btn = new ShipPanelButton();
			submarinoPanel.add(btn);
		}
		add(submarinoPanel);
		
		JPanel destructorPanel = new ShipPanel();
		for (int i = 0; i < destructorLength ; i++) {
			JButton btn = new ShipPanelButton();
			destructorPanel.add(btn);
		}
		add(destructorPanel);
	}

}
