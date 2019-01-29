package interfaces.controllers;

import java.io.IOException;

import DB.GestionDB;
import DB.LocalDataClient;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AjoutFichierPhotoController {
	
	@FXML
	private TextField chemin;
	@FXML
	private TextField resolution;
	@FXML
	private TextField infovue;
	@FXML
	private CheckBox partage;
	
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
			System.err.println("Impossible d'afficher la page 'PageRecap'");
		}
	}
	
	
	@FXML
	void save(MouseEvent event) {
		
		System.out.println(partage.isSelected());
		//GestionDB.createFichierPhoto(chemin.getText(), LocalDataClient.client.getEmail(), resolution.getText(), infovue.getText(), partage.isSelected());
		LocalDataClient.refresh();
		
		Parent home_page_parent;
		try {
			home_page_parent = new FXMLLoader(getClass().getResource("/interfaces/views/PageRecap.fxml")).load();
			Scene home_page_scene = new Scene(home_page_parent);
			Stage app_stage = (Stage) ((Node) event.getSource()).getScene()
				.getWindow();
			app_stage.setScene(home_page_scene);
			app_stage.show();
		} catch (IOException e) {
			System.err.println("Impossible d'afficher la page 'PageRecap'");
		}
	}
	

}
