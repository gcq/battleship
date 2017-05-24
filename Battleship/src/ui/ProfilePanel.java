package ui;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 
 * @author Victor
 * Panell que permet al usuari modificar les seves dades
 *
 */
public class ProfilePanel extends JPanel{
	
	private JTextField userNameTextField;
	private JPanel innerPanel;
	private JButton closeBtn;
	private JButton saveBtn;
	
	public ProfilePanel() {
		setLayout(null);
		
		innerPanel = new JPanel();
		innerPanel.setBounds(108, 29, 165, 173);
		add(innerPanel);
		innerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel userLbl = new JLabel("Username");
		innerPanel.add(userLbl);
		
		userNameTextField = new JTextField();
		innerPanel.add(userNameTextField);
		userNameTextField.setColumns(10);
		
		JPanel panel = new JPanel();
		innerPanel.add(panel);
		
		closeBtn = new JButton("Close");
		closeBtn.setActionCommand("CloseProfile");
		panel.add(closeBtn);
		
		saveBtn = new JButton("Save");
		saveBtn.setActionCommand("SaveProfile");
		panel.add(saveBtn);
	}

	public JTextField getUserNameTextField() {
		return userNameTextField;
	}

	public void setUserNameTextField(JTextField userNameTextField) {
		this.userNameTextField = userNameTextField;
	}

	public JPanel getInnerPanel() {
		return innerPanel;
	}

	public void setUsername (String name) {
		this.userNameTextField.setText(name);
	}
	
	public String getUsername () {
		return this.userNameTextField.getText();
	}

	public JButton getCloseBtn() {
		return closeBtn;
	}

	public JButton getSaveBtn() {
		return saveBtn;
	}
	
}

