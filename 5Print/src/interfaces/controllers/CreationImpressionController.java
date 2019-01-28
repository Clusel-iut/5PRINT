package interfaces.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import DB.GestionDB;
import DB.LocalDataClient;
import DTO.Adresse;
import DTO.Impression;
import DTO.Stock;
import DTO.TypeSupport;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class CreationImpressionController implements Initializable{
	
	@FXML
	private ComboBox<Stock> listeStock;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		listeStock.getItems().setAll(GestionDB.getAllStock());		
	}
	
	@FXML
    void create(MouseEvent event) { 
		System.out.println(listeStock.getSelectionModel().getSelectedItem().toString());
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
