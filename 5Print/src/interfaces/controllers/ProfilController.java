package interfaces.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.DefaultComboBoxModel;

import DB.GestionDB;
import DB.LocalDataClient;
import DTO.Adresse;
import DTO.Client;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ProfilController implements Initializable {
	
	@FXML
	private Label userEmail;
	@FXML 
	private TextField userPrenom;
	@FXML 
	private TextField userNom;
	@FXML 
	private TextField userPassword;
	@FXML 
	private ComboBox<Adresse> listAddress;
	
	
	@FXML
    void addAddress(MouseEvent event) {
		Parent home_page_parent;
		try {
			home_page_parent = new FXMLLoader(getClass().getResource("/interfaces/views/AjoutAdresse.fxml")).load();
			Scene home_page_scene = new Scene(home_page_parent);
			Stage app_stage = (Stage) ((Node) event.getSource()).getScene()
				.getWindow();
			app_stage.setScene(home_page_scene);
			app_stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
    void deleteAddress(MouseEvent event) {
		if(this.listAddress.getSelectionModel() != null) {
			if(GestionDB.deleteAdresseById(listAddress.getSelectionModel().getSelectedItem().getId_adresse())) {
				this.popup("Adresse", "Adresse supprimée !", "Fermer");
			}
			else {
				this.popup("Adresse", "Suppression échoué", "Fermer");
			}
		}
	}
	
	@FXML
    void save(MouseEvent event) {
		if(GestionDB.updateClient(new Client(userEmail.getText(), userNom.getText(), userPrenom.getText(), userPassword.getText()))) {
			LocalDataClient.refresh();
			this.popup("Mise à jour", "Votre profil a été mise à jour !", "Fermer");
		}
		else {
			this.popup("Mise à jour", "Erreur dans la mise à jour de votre profil !", "Fermer");
		}	
	}
	
	@FXML
    void back(MouseEvent event) {
		Parent home_page_parent;
		try {
			home_page_parent = new FXMLLoader(getClass().getResource("/interfaces/views/PageRecap.fxml")).load();
			Scene home_page_scene = new Scene(home_page_parent);
			Stage app_stage = (Stage) ((Node) event.getSource()).getScene()
				.getWindow();
			app_stage.setScene(home_page_scene);
			app_stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		userEmail.setText(LocalDataClient.client.getEmail());
		userPrenom.setText(LocalDataClient.client.getPrenom());
		userNom.setText(LocalDataClient.client.getNom());
		userPassword.setText(LocalDataClient.client.getMotDePasse());
		System.out.println("fefse");
		listAddress.getItems().setAll(LocalDataClient.client.getAdresse());
	}
	
	void popup(String title, String label, String buttonText) {
		Stage popupwindow=new Stage();	      
		popupwindow.initModality(Modality.APPLICATION_MODAL);
		popupwindow.setTitle(title);
		Label texte = new Label(label);
		Button button= new Button(buttonText);
		button.setOnAction(e -> popupwindow.close());
		VBox layout= new VBox(10);
		layout.getChildren().addAll(texte, button);
		layout.setAlignment(Pos.CENTER);
		Scene scene= new Scene(layout, 300, 250);
		popupwindow.setScene(scene);
		popupwindow.showAndWait();
	}
}
