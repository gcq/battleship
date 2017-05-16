package ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

import java.awt.Color;

public class UserPanel extends JPanel {
	private JTextField textField;
	private JLabel lblTitle;
	private JButton btnGo;

	/**
	 * Create the panel.
	 */
	public UserPanel() {
		
		setLayout(null);
		setPreferredSize(new Dimension(800, 700));
		setBorder(new EmptyBorder(5, 5, 5, 5));
		
		
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(287, 290, 92, 15);
		add(lblUsername);
		
		textField = new JTextField();
		textField.setBounds(397, 288, 114, 19);
		textField.setColumns(10);
		add(textField);
		
		btnGo = new JButton("go");
		btnGo.setBounds(336, 380, 117, 25);
		add(btnGo);
		
		lblTitle = new JLabel("TITLE");
		lblTitle.setBounds(359, 205, 70, 15);
		add(lblTitle);
		
		JLabel lblNewLabel = new JLabel(new ImageIcon("img/warship3.jpg"));
		lblNewLabel.setBackground(new Color(240, 240, 240));
		lblNewLabel.setBounds(0, 0, 858, 700);
		add(lblNewLabel);

	}
	
	public JLabel getLblTitle() {
		return lblTitle;
	}
	public JTextField getTextField() {
		return textField;
	}
	public JButton getBtnGo() {
		return btnGo;
	}
	
	public void setTitle(String title) {
		getLblTitle().setText(title);
	}
	
	public String getUsername() {
		return getTextField().getText();
	}
}
