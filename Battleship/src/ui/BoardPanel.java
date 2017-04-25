package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class BoardPanel extends JPanel implements ActionListener {
	
	Color buttonColor; 
	String[] topBtnsText = {"1","2","3","4","5","6","7","8","9","10"};
	String[] leftBtnsText = {"A","B","C","D","E","F","G","H","I","J"};
	public BoardPanel () {
		buttonColor = Color.BLUE;
		setAlignmentY(Component.TOP_ALIGNMENT);
		GridBagLayout gbl_btnPanel = new GridBagLayout();
		gbl_btnPanel.columnWidths = new int[] {50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50};
		gbl_btnPanel.rowHeights = new int[] {50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50};
		gbl_btnPanel.columnWeights = new double[]{0.0,0,0,0,0,0,0,0,0,0};
		gbl_btnPanel.rowWeights = new double[]{0.0,0,0,0,0,0,0,0,0,0};
		setLayout(gbl_btnPanel);
		
		List<JButton>btnArray = new ArrayList<JButton>();
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
				button.setActionCommand(button.getText());
				button.addActionListener(this);
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

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

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
