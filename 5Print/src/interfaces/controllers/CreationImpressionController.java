package interfaces.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import DB.LocalDataClient;
import DTO.Adresse;
import DTO.Impression;
import DTO.TypeSupport;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;

public class CreationImpressionController implements Initializable{
	
	@FXML
	private ComboBox<ArrayList<TypeSupport>> listeImpressions;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ArrayList<TypeSupport> types = new ArrayList<TypeSupport>();
		types.add(TypeSupport.AGENDA);
		types.add(TypeSupport.ALBUM);
		types.add(TypeSupport.CADRE);
		types.add(TypeSupport.CALENDRIER);
		types.add(TypeSupport.TIRAGE);
		
		listeImpressions.setValue(types);		
	}
	
	@FXML
    void create(MouseEvent event) { 
		System.out.println(listeImpressions.getSelectionModel().getSelectedItem().toString());
	}
	
	@FXML
    void back(MouseEvent event) { 
		
	}

}
