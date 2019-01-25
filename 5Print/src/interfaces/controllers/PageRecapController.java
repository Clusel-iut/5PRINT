package interfaces.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import DB.GestionDB;
import DB.LocalDataClient;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class PageRecapController implements Initializable {
	
	@FXML
	private Label prenomNomClient;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		prenomNomClient.setText(LocalDataClient.client.getPrenom() + " " + LocalDataClient.client.getNom());
	}
	
	 @FXML
	void profil(MouseEvent event) {
		Parent home_page_parent;
		try {
			home_page_parent = new FXMLLoader(getClass().getResource("/interfaces/views/Profil.fxml")).load();
			Scene home_page_scene = new Scene(home_page_parent);
			Stage app_stage = (Stage) ((Node) event.getSource()).getScene()
				.getWindow();
			app_stage.setScene(home_page_scene);
			app_stage.show();
		} catch (IOException e) {
			System.err.println("Impossible d'afficher la page 'Profil'");
		}
	}
	 
	 @FXML
    void deconnect(MouseEvent event) {
		LocalDataClient.client = null;
    	Parent home_page_parent;
		try {
			home_page_parent = new FXMLLoader(getClass().getResource("/interfaces/views/Connexion.fxml")).load();
			Scene home_page_scene = new Scene(home_page_parent);
			Stage app_stage = (Stage) ((Node) event.getSource()).getScene()
				.getWindow();
			app_stage.setScene(home_page_scene);
			app_stage.show();
		} catch (IOException e) {
			System.err.println("Impossible d'afficher la page 'Connexion'");
		}
		
    }
	 
	 @FXML
	 void create(MouseEvent event) {
		 Parent home_page_parent;
			try {
				home_page_parent = new FXMLLoader(getClass().getResource("/interfaces/views/Connexion.fxml")).load();
				Scene home_page_scene = new Scene(home_page_parent);
				Stage app_stage = (Stage) ((Node) event.getSource()).getScene()
					.getWindow();
				app_stage.setScene(home_page_scene);
				app_stage.show();
			} catch (IOException e) {
				System.err.println("Impossible d'afficher la page 'Connexion'");
			}
	 }
}
