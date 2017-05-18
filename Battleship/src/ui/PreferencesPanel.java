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
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 47, 384, 142);
		add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		FlowLayout fl_panel_1 = new FlowLayout(FlowLayout.CENTER, 5, 10);
		panel_1.setLayout(fl_panel_1);
		
		JLabel timeLabel = new JLabel("Time per turn");
		timeLabel.setVerticalAlignment(SwingConstants.TOP);
		panel_1.add(timeLabel);
		
		JPanel subPanel_1 = new JPanel();
		panel_1.add(subPanel_1);
		subPanel_1.setLayout(new BoxLayout(subPanel_1, BoxLayout.Y_AXIS));
		
		infiniteBtn = new JRadioButton("Infinite");
		infiniteBtn.setSelected(true);
		infiniteBtn.setActionCommand(infiniteBtn.getText());
		buttonGroup.add(infiniteBtn);
		subPanel_1.add(infiniteBtn);
		
		prefixedBtn = new JRadioButton("Prefixed");
		prefixedBtn.setActionCommand(prefixedBtn.getText());
		buttonGroup.add(prefixedBtn);
		subPanel_1.add(prefixedBtn);
		
		prefixedComboBox = new JComboBox();
		prefixedComboBox.setEnabled(false);
		prefixedComboBox.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"}));
		subPanel_1.add(prefixedComboBox);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		
		JLabel modeLabel = new JLabel("Turn mode");
		panel_2.add(modeLabel);
		
		JPanel subPanel_2 = new JPanel();
		panel_2.add(subPanel_2);
		subPanel_2.setLayout(new BoxLayout(subPanel_2, BoxLayout.Y_AXIS));
		
		classicBtn = new JRadioButton("Classic");
		classicBtn.setSelected(true);
		buttonGroup_1.add(classicBtn);
		subPanel_2.add(classicBtn);
		
		alternativeBtn = new JRadioButton("Alternative");
		buttonGroup_1.add(alternativeBtn);
		subPanel_2.add(alternativeBtn);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBounds(88, 200, 201, 38);
		add(buttonPanel);
		
		closePrefBtn = new JButton("Close");
		buttonPanel.add(closePrefBtn);
		closePrefBtn.setActionCommand("ClosePrefBtn");
		
		savePrefBtn = new JButton("Save");
		savePrefBtn.setActionCommand("SavePrefBtn");
		buttonPanel.add(savePrefBtn);

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
