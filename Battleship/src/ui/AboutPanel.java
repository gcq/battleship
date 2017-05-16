package ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import javax.swing.Box;


class CreditPanel extends JEditorPane {
	
	private URL htmlUrl;
	
	public CreditPanel() {
		setPreferredSize(new Dimension(200, 200));
		setEditable(false);
	}
	
	public void setHtmlPage (String htmlUrl) {
		URL url = CreditPanel.class.getResource(htmlUrl);
		if (htmlUrl != null) {
			try {
				setPage(url);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {
			 System.err.println("Couldn't find file: " + url.getPath());
		}
	}

	public void setHtmlUrl(URL htmlUrl) {
		this.htmlUrl = htmlUrl;
	}
}

class DescriptionPanel extends JPanel {
	private JLabel description;
	private JLabel nom;
	private Component verticalStrut;
	
	public DescriptionPanel() {
		setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 450, 300);
		add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		nom = new JLabel();
		nom.setText("Introdueix titol");
		nom.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(nom);
		nom.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		verticalStrut = Box.createVerticalStrut(100);
		panel.add(verticalStrut);
		
		description = new JLabel();
		description.setText("Introdueix copyright");
		description.setHorizontalAlignment(SwingConstants.CENTER);
		description.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(description);

	}
	
	public void setDescription(String description) {
		this.description.setText(description);
	}

	public void setNom(String name) {
		this.nom.setText(name);
	}
}

class Licencia extends JScrollPane {
	public Licencia() {}
	
	public void setHtmlPage (String htmlUrl) {
		URL url = getClass().getResource(htmlUrl);
		JTextPane textPane = new JTextPane();
		setViewportView(textPane);
		
		if (htmlUrl != null) {
			try {
				textPane.setPage(url);
			} catch (IOException e) {
				e.printStackTrace();
			};
		}
		else {
			 System.err.println("Couldn't find file: " + url.getPath());
		}
	}
}

public class AboutPanel extends JPanel {

	private CardLayout PuiVicCL;
	private JPanel PuiVicCard;
	private JLabel imageIcon;
	private JLabel titleText;
	private JLabel versio;
	JButton closeButton;
	private CreditPanel credits;
	private Licencia licence;
	private DescriptionPanel description;
	private JPanel panel_1;
	private Component horizontalStrut;
	private Component verticalStrut_1;

	public AboutPanel() {
		
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);
		{
			PuiVicCard = new JPanel();
			add(PuiVicCard);
			PuiVicCL = new CardLayout(0, 0);
			PuiVicCard.setBounds(0, 94, 450, 136);
			PuiVicCard.setLayout(PuiVicCL);
			
			{
				credits = new CreditPanel();
				
				PuiVicCard.add(credits, "credits");
			}
			{
				licence = new Licencia();
				PuiVicCard.add(licence, "licence");
			}
			{
				description = new DescriptionPanel();
				PuiVicCard.add(description, "description");
			}
			
			PuiVicCL.show(PuiVicCard, "description");
		}
		{
			
			JPanel panel = new JPanel();
			panel.setBounds(0, 0, 450, 95);
			
			add(panel);
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			{
				verticalStrut_1 = Box.createVerticalStrut(20);
				panel.add(verticalStrut_1);
			}
			
			imageIcon = new JLabel();
			imageIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
			imageIcon.setText("Enter image");
			panel.add(imageIcon);
			{
				panel_1 = new JPanel();
				panel_1.setAlignmentY(Component.TOP_ALIGNMENT);
				panel.add(panel_1);
				panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
				{
					titleText = new JLabel();
					panel_1.add(titleText);
					titleText.setAlignmentX(Component.CENTER_ALIGNMENT);
				}
				{
					horizontalStrut = Box.createHorizontalStrut(20);
					panel_1.add(horizontalStrut);
				}
				
				versio = new JLabel();
				panel_1.add(versio);
				versio.setAlignmentX(Component.CENTER_ALIGNMENT);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 230, 450, 70);
			FlowLayout fl_buttonPane = new FlowLayout(FlowLayout.LEFT);
			fl_buttonPane.setHgap(10);
			buttonPane.setLayout(fl_buttonPane);
			add(buttonPane, BorderLayout.SOUTH);
			{
				JButton licenceButton = new JButton("Licence");
				licenceButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						PuiVicCL.show(PuiVicCard, "licence");
					}
				});
				buttonPane.add(licenceButton);
			}
			{
				JButton creditsButton = new JButton("Credits");
				creditsButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						PuiVicCL.show(PuiVicCard, "credits");
					}
				});
				buttonPane.add(creditsButton);
			}
			{
				JPanel panel = new JPanel();
				panel.setBorder(new EmptyBorder(0, 135, 0, 0));
				FlowLayout flowLayout = (FlowLayout) panel.getLayout();
				flowLayout.setAlignment(FlowLayout.RIGHT);
				buttonPane.add(panel);
				{
					closeButton = new JButton("Close");
					panel.add(closeButton);
					closeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
					closeButton.setActionCommand("CloseAbout");
				}
			}
		}
		
	}
	
	

	public JButton getCloseButton() {
		return closeButton;
	}

	public void setImageIcon(Icon icon) {
		this.imageIcon.setIcon(icon);
		this.imageIcon.setText(null);
	}
	
//	public void setImageIcon(String imageRoute) {
//		this.imageIcon.setIcon(new ImageIcon(PuiVicCruGuiA14AboutPanel.class.getResource(imageRoute)));
//		this.imageIcon.setText(null);
//	}

	public void setTitleText(String titleText) {
		this.titleText.setText(titleText);
	}

	public void setVersio(String numVersio) {
		this.versio.setText(numVersio);
	}

	public void setCredits(String htmlRoute) {
		this.credits.setHtmlPage(htmlRoute);
	}

	public void setDescriptionName(String nom) {
		this.description.setNom(nom);
	}
	
	public void setDescriptionText(String description) {
		this.description.setDescription(description);
	}
	
	public void setLicence(String htmlRoute) {
		this.licence.setHtmlPage(htmlRoute);
	}
}
