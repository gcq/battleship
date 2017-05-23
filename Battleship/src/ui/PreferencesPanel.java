package ui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.BoxLayout;
import javax.swing.JTextField;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class PreferencesPanel extends JPanel{
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();
	private JRadioButton infiniteBtn;
	private JRadioButton prefixedBtn;
	private JRadioButton classicBtn;
	private JRadioButton alternativeBtn;
	private JButton closePrefBtn;
	private JButton savePrefBtn;
	private JComboBox prefixedComboBox;
	/**
	 * Create the panel.
	 */
	public PreferencesPanel() {
		setLayout(null);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBounds(10, 211, 201, 38);
		add(buttonPanel);
		
		closePrefBtn = new JButton("Close");
		buttonPanel.add(closePrefBtn);
		closePrefBtn.setActionCommand("ClosePrefBtn");
		
		savePrefBtn = new JButton("Save");
		savePrefBtn.setActionCommand("SavePrefBtn");
		buttonPanel.add(savePrefBtn);
		
		JLabel timeLabel = new JLabel("Time per turn");
		timeLabel.setBounds(53, 76, 103, 15);
		add(timeLabel);
		timeLabel.setVerticalAlignment(SwingConstants.TOP);
		
		JLabel modeLabel = new JLabel("Turn mode");
		modeLabel.setBounds(53, 169, 103, 15);
		add(modeLabel);
		
		infiniteBtn = new JRadioButton("Infinite");
		infiniteBtn.setBounds(156, 51, 75, 23);
		add(infiniteBtn);
		infiniteBtn.setSelected(true);
		infiniteBtn.setActionCommand(infiniteBtn.getText());
		buttonGroup.add(infiniteBtn);
		
		prefixedBtn = new JRadioButton("Prefixed");
		prefixedBtn.setBounds(156, 72, 83, 23);
		add(prefixedBtn);
		prefixedBtn.setActionCommand(prefixedBtn.getText());
		buttonGroup.add(prefixedBtn);
		
		prefixedComboBox = new JComboBox();
		prefixedComboBox.setBounds(160, 99, 65, 23);
		add(prefixedComboBox);
		prefixedComboBox.setEnabled(false);
		prefixedComboBox.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"}));
		
		classicBtn = new JRadioButton("Classic");
		classicBtn.setBounds(157, 148, 74, 23);
		add(classicBtn);
		classicBtn.setSelected(true);
		buttonGroup_1.add(classicBtn);
		
		alternativeBtn = new JRadioButton("Alternative");
		alternativeBtn.setBounds(156, 173, 103, 15);
		add(alternativeBtn);
		buttonGroup_1.add(alternativeBtn);

	}
	
	public JButton getSavePrefBtn() {
		return savePrefBtn;
	}
	public JComboBox getPrefixedComboBox() {
		return prefixedComboBox;
	}
	public JRadioButton getInfiniteBtn() {
		return infiniteBtn;
	}
	public JRadioButton getPrefixedBtn() {
		return prefixedBtn;
	}
	public JRadioButton getClassicBtn() {
		return classicBtn;
	}
	public JRadioButton getAlternativeBtn() {
		return alternativeBtn;
	}
	public JButton getClosePrefBtn() {
		return closePrefBtn;
	}
	
	
}
