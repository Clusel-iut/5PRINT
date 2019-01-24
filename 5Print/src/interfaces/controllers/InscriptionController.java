package interfaces.controllers;


import java.io.IOException;

import DB.GestionDB;
import DTO.Adresse;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class InscriptionController {

	@FXML
    private TextField nom;
    @FXML
    private TextField prenom;
    @FXML
    private TextField email;
    @FXML
    private TextField rue;
    @FXML
    private TextField numero;
    @FXML
    private TextField codepostal;
    @FXML
    private TextField ville;
    @FXML
    private TextField pays;
    @FXML
    private PasswordField motdepasse;
	
    @FXML
    private ImageView btn_close;
	
    
    /**
     * Permet à l'utilisateur de revenir en arrière.
     * 
     * @param event
     */
    @FXML
    void back(MouseEvent event) {
    	this.changeView((Stage) ((Node) event.getSource()).getScene()
				.getWindow(), "/interfaces/views/Connexion.fxml");
    }

    
    /**
     * Permet de changer de vue.
     * 
     * @param app_stage
     * @param path
     * @throws IOException
     */
    private void changeView(Stage app_stage, String path) {
	Parent home_page_parent;
	try {
		home_page_parent = FXMLLoader.load(this.getClass().getResource(
			path));
		Scene home_page_scene = new Scene(home_page_parent);
		app_stage.setScene(home_page_scene);
		app_stage.show();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    }
    
    /**
     * Permet de créer un client.
     * 
     * @param event
     */
    @FXML
    void create(MouseEvent event) throws IOException {

	System.out.println(nom.getText());
	System.out.println(prenom.getText());
	System.out.println(email.getText());
	System.out.println(numero.getText());
	System.out.println(rue.getText());
	System.out.println(ville.getText());
	System.out.println(codepostal.getText());
	System.out.println(pays.getText());
	System.out.println(motdepasse.getText());

	this.changeView((Stage) ((Node) event.getSource()).getScene()
		.getWindow(), "/interfaces/views/Connexion.fxml");
    }
    
    /**
    * Permet de nettoyer les champs.
    * 
    * @param event
    * @throws IOException
    * @throws SQLException
    */
   @FXML
   void clear(MouseEvent event) throws IOException {

	System.out.println("A nettoyer");
   }

	/**
	 * Create the frame.
	 
	public AppClientInscription() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(100, 100, 698, 483);
		this.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Email");
		lblNewLabel.setBounds(177, 74, 70, 15);
		this.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Nom");
		lblNewLabel_1.setBounds(177, 98, 70, 15);
		this.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Prenom");
		lblNewLabel_2.setBounds(177, 125, 70, 15);
		this.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Adresse :");
		lblNewLabel_3.setBounds(177, 166, 70, 15);
		this.getContentPane().add(lblNewLabel_3);
		
		email = new JTextField();
		email.setBounds(255, 72, 219, 19);
		this.getContentPane().add(email);
		email.setColumns(10);
		
		nom = new JTextField();
		nom.setBounds(255, 96, 219, 19);
		this.getContentPane().add(nom);
		nom.setColumns(10);
		
		prenom = new JTextField();
		prenom.setColumns(10);
		prenom.setBounds(255, 123, 219, 19);
		this.getContentPane().add(prenom);
		
		JLabel lblRue = new JLabel("Rue");
		lblRue.setBounds(207, 217, 101, 15);
		this.getContentPane().add(lblRue);
		
		JLabel lblCodePostal = new JLabel("Code postal");
		lblCodePostal.setBounds(207, 244, 101, 15);
		this.getContentPane().add(lblCodePostal);
		
		JLabel lblVille = new JLabel("Ville");
		lblVille.setBounds(207, 271, 70, 15);
		this.getContentPane().add(lblVille);
		
		JLabel lblNumero = new JLabel("Numero");
		lblNumero.setBounds(207, 193, 70, 15);
		this.getContentPane().add(lblNumero);
		
		JLabel lblPays = new JLabel("Pays");
		lblPays.setBounds(207, 298, 70, 15);
		this.getContentPane().add(lblPays);
		
		numero = new JTextField();
		numero.setBounds(326, 191, 148, 19);
		this.getContentPane().add(numero);
		numero.setColumns(10);
		
		rue = new JTextField();
		rue.setColumns(10);
		rue.setBounds(326, 215, 148, 19);
		this.getContentPane().add(rue);
		
		codepostal = new JTextField();
		codepostal.setColumns(10);
		codepostal.setBounds(326, 242, 148, 19);
		this.getContentPane().add(codepostal);
		
		ville = new JTextField();
		ville.setColumns(10);
		ville.setBounds(326, 269, 148, 19);
		this.getContentPane().add(ville);
		
		pays = new JTextField();
		pays.setColumns(10);
		pays.setBounds(326, 296, 148, 19);
		this.getContentPane().add(pays);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(297, 339, 177, 19);
		this.getContentPane().add(passwordField);
		
		JLabel lblMotDePasse = new JLabel("Mot de passe");
		lblMotDePasse.setBounds(177, 341, 119, 15);
		this.getContentPane().add(lblMotDePasse);
		
		JButton btnInscription = new JButton("Valider");
		btnInscription.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if( email.getText().isEmpty() || nom.getText().isEmpty() || prenom.getText().isEmpty() || numero.getText().isEmpty() || rue.getText().isEmpty()
						||  codepostal.getText().isEmpty() || ville.getText().isEmpty() || pays.getText().isEmpty() || passwordField.getSelectedText().isEmpty())
				{
					JOptionPane jop = new JOptionPane();
					jop.showMessageDialog(null,"Un ou plusieurs champs sont manquants!");
				}
				else {
					GestionDB.createClient(email.getText(), nom.getText(), prenom.getText(), passwordField.getName(), new Adresse(pays.getText(), ville.getText(), codepostal.getText(), rue.getText(), numero.getText()));
				}
			}
		});
		btnInscription.setBounds(276, 388, 117, 25);
		this.getContentPane().add(btnInscription);
		
		JLabel lblIncription = new JLabel("Inscription");
		lblIncription.setFont(new Font("Dialog", Font.BOLD, 22));
		lblIncription.setBounds(255, 12, 148, 25);
		this.getContentPane().add(lblIncription);
	} */
}
