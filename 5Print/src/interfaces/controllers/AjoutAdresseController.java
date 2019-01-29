package interfaces.controllers;

import java.io.IOException;

import DB.GestionDB;
import DB.LocalDataClient;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AjoutAdresseController {
	
	@FXML
	private TextField num;
	@FXML
	private TextField rue;
	@FXML
	private TextField cp;
	@FXML
	private TextField ville;
	@FXML
	private TextField pays;
	
	@FXML
    void save(MouseEvent event) {
		if(GestionDB.createAdresse(pays.getText(), LocalDataClient.client.getEmail(), ville.getText(), cp.getText(),  rue.getText(), num.getText())) {
			Parent home_page_parent;
			try {
				LocalDataClient.refresh();
				home_page_parent = new FXMLLoader(getClass().getResource("/interfaces/views/Profil.fxml")).load();
				Scene home_page_scene = new Scene(home_page_parent);
				Stage app_stage = (Stage) ((Node) event.getSource()).getScene()
					.getWindow();
				app_stage.setScene(home_page_scene);
				app_stage.show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.popup("Création d'adresse", "Votre adresse a été ajoutée", "Fermer");
		}
		else {
			this.popup("Création d'adresse", "Erreur lors de l'ajout de l'adresse", "Fermer");
		}
	}
	
	@FXML
    void back(MouseEvent event) {
		Parent home_page_parent;
		try {
			home_page_parent = new FXMLLoader(getClass().getResource("/interfaces/views/Profil.fxml")).load();
			Scene home_page_scene = new Scene(home_page_parent);
			Stage app_stage = (Stage) ((Node) event.getSource()).getScene()
				.getWindow();
			app_stage.setScene(home_page_scene);
			app_stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
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
