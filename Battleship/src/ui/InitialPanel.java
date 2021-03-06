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
import java.awt.Font;

/**
 * 
 * @author Victor
 * Panell inicial amb el titol del joc que permet al usuari introduir el seu username i comen�ar el joc
 *
 */
public class InitialPanel extends JPanel {
	private JTextField textField;
	private JLabel lblTitle;
	private JButton btnGo;

	/**
	 * Create the panel.
	 */
	public InitialPanel() {
		
		setLayout(null);
		setPreferredSize(new Dimension(800, 700));
		setBorder(new EmptyBorder(5, 5, 5, 5));
		
		
		
		JLabel lblUsername = new JLabel("Enter your username");
		lblUsername.setBounds(310, 250, 161, 18);
		add(lblUsername);
		
		textField = new JTextField();
		textField.setBounds(481, 249, 117, 19);
		textField.setColumns(10);
		add(textField);
		
		btnGo = new JButton("Start Game");
		btnGo.setActionCommand("StartGame");
		btnGo.setBounds(481, 299, 117, 25);
		add(btnGo);
		
		lblTitle = new JLabel("ULTIMATE BATTLESHIP GAME");
		lblTitle.setFont(new Font("Carlito", Font.PLAIN, 40));
		lblTitle.setBounds(173, 34, 486, 103);
		add(lblTitle);
		
		JLabel background = new JLabel(new ImageIcon(InitialPanel.class.getResource("/img/warship.jpg")));
		background.setBackground(new Color(240, 240, 240));
		background.setBounds(0, 0, 858, 700);
		add(background);

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
