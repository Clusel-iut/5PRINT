package InterfacesClient;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;

import DB.GestionDB;

import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AppClientConnexion {

	private JFrame frame;
	private JTextField txtEmail;
	private JPasswordField pwdMotDePasse;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		GestionDB.configure("ouzzineo", "Oussama123");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AppClientConnexion window = new AppClientConnexion();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AppClientConnexion() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 720, 480);
		frame.getContentPane().setLayout(null);
		
		JButton btnConnexion = new JButton("Connexion");
		btnConnexion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean connectionClient = GestionDB.connectionClient(txtEmail.getText(), pwdMotDePasse.getSelectedText());
				
								
			}
		});
		btnConnexion.setBounds(296, 356, 117, 25);
		frame.getContentPane().add(btnConnexion);
		
		JButton btnInscription = new JButton("Inscription");
		btnInscription.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				new AppClientInscription().setVisible(true);
			}
		});
		btnInscription.setBounds(296, 399, 117, 25);
		frame.getContentPane().add(btnInscription);
		
		txtEmail = new JTextField();
		txtEmail.setBounds(296, 266, 114, 19);
		frame.getContentPane().add(txtEmail);
		txtEmail.setColumns(10);
		
		pwdMotDePasse = new JPasswordField();
		pwdMotDePasse.setBounds(296, 310, 117, 19);
		frame.getContentPane().add(pwdMotDePasse);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(222, 266, 70, 15);
		frame.getContentPane().add(lblEmail);
		
		JLabel lblMotDePasse = new JLabel("Mot de passe");
		lblMotDePasse.setBounds(164, 310, 114, 15);
		frame.getContentPane().add(lblMotDePasse);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(AppClientConnexion.class.getResource("/Ressources/logo.png")));
		lblNewLabel.setBounds(95, 0, 497, 254);
		frame.getContentPane().add(lblNewLabel);
	}
}
