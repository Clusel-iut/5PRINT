package interfaces.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import DB.LocalDataClient;
import DTO.Adresse;
import DTO.Impression;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;

public class CreationImpressionController implements Initializable{
	
	@FXML
	private ComboBox<ArrayList<TypeSupport>> listeImpressions;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		listeImpressions.setValue(GestionDB.get);
		
	}
	
	@FXML
    void create(MouseEvent event) { 
		
	}
	
	@FXML
    void back(MouseEvent event) { 
		
	}

}
