package interfaces.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import DB.GestionDB;
import DTO.Impression;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Spinner;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class GestionImpressionController implements Initializable {
	
	@FXML
	private Label impressionID;
	@FXML
	private Label impressionDate;
	@FXML
	private Label montantTotal;
	@FXML
	private Label etatImpression;
	@FXML
	private Label qualite;
	@FXML
	private Label format;
	@FXML
	private TextField modeleCadre;
	@FXML
	private Spinner<Integer> nbImpression;
	@FXML
	private Tab cadre;
	@FXML
	private Tab album;
	@FXML
	private Tab agenda;
	@FXML
	private Tab tirage;
	@FXML
	private Tab calendrier;
	@FXML
	private TabPane tabType;
	
	private int idImpression;
	
	private Impression impression;

	public void setObjects(int idImpress) {
		this.idImpression = idImpress;
		this.impressionID.setText(Integer.toString(idImpression));
		
		impression = GestionDB.getImpressionById(idImpression);
		if(impression.getDate_impression() != null) {
			impressionDate.setText(impression.getDate_impression().toString());
		}
		montantTotal.setText(Float.toString(impression.getMontant_total()));
		etatImpression.setText(Boolean.toString(impression.getEtat_impression()));
		qualite.setText(impression.getStock().getQualite());
		format.setText(impression.getStock().getFormat());
		//nbImpression.getValueFactory().setValue(impression.getNb_impression());
		System.out.println(impression.getStock().getType_support().toString().toLowerCase());
		for(int index=0; index < tabType.getTabs().size(); index++) {
			if(!tabType.getTabs().get(index).getText().toLowerCase().equals(impression.getStock().getType_support().toString().toLowerCase()))
			{
				tabType.getTabs().get(index).setDisable(true);
			}
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
	
	@FXML
    void add(MouseEvent event) { 
		FXMLLoader Loader = new FXMLLoader();
	    Loader.setLocation(getClass().getResource("/interfaces/views/AjoutPhoto.fxml"));
	    try {
			Loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    AjoutPhotoController controller = Loader.getController();
	    controller.setObjects(idImpression);

	    Parent home_page_parent = Loader.getRoot();
	    Scene home_page_scene = new Scene(home_page_parent);
	    Stage app_stage = (Stage) ((Node) event.getSource()).getScene()
		    .getWindow();
	    app_stage.setScene(home_page_scene);
	    app_stage.show();
	}
	
	
	
	

}
