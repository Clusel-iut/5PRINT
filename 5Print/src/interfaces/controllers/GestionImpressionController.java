package interfaces.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import DB.GestionDB;
import DTO.Client;
import DTO.Commande;
import DTO.FichierPhoto;
import DTO.Impression;
import DTO.Photo;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
	@FXML
	private TableView<Photo> cadreView;
	@FXML
	private TableView<Photo> albumView;
	@FXML
	private TableView<Photo> agendaView;
	@FXML
	private TableView<Photo> tirageView;
	@FXML
	private TableView<Photo> calendrierView;
	//CADRE
	@FXML
	private TableColumn<Photo, String> pageCadre;
	@FXML
	private TableColumn<Photo, String> cheminCadre;
	@FXML
	private TableColumn<Photo, String> resolutionCadre;
	@FXML
	private TableColumn<Photo, Integer> xCadre;
	@FXML
	private TableColumn<Photo, Integer> yCadre;
	@FXML
	private TableColumn<Photo, String> retoucheCadre;
	//ALBUM
	@FXML
	private TableColumn<Photo, String> pageAlbum;
	@FXML
	private TableColumn<Photo, String> cheminAlbum;
	@FXML
	private TableColumn<Photo, String> resolutionAlbum;
	@FXML
	private TableColumn<Photo, Integer> xAlbum;
	@FXML
	private TableColumn<Photo, String> yAlbum;
	@FXML
	private TableColumn<Photo, String> retoucheAlbum;
	//AGENDA
	@FXML
	private TableColumn<Photo, String> pageAgenda;
	@FXML
	private TableColumn<Photo, String> cheminAgenda;
	@FXML
	private TableColumn<Photo, String> resolutionAgenda;
	@FXML
	private TableColumn<Photo, Integer> xAgenda;
	@FXML
	private TableColumn<Photo, String> yAgenda;
	@FXML
	private TableColumn<Photo, String> retoucheAgenda;
	//TIRAGE
	@FXML
	private TableColumn<Photo, String> pageTirage;
	@FXML
	private TableColumn<Photo, String> cheminTirage;
	@FXML
	private TableColumn<Photo, String> resolutionTirage;
	@FXML
	private TableColumn<Photo, Integer> xTirage;
	@FXML
	private TableColumn<Photo, String> yTirage;
	@FXML
	private TableColumn<Photo, String> retoucheTirage;
	@FXML
	private TableColumn<Photo, String> nbExTirage;
	//CALENDRIER
	@FXML
	private TableColumn<Photo, String> pageCalendrier;
	@FXML
	private TableColumn<Photo, String> cheminCalendrier;
	@FXML
	private TableColumn<Photo, String> resolutionCalendrier;
	@FXML
	private TableColumn<Photo, Integer> xCalendrier;
	@FXML
	private TableColumn<Photo, String> yCalendrier;
	@FXML
	private TableColumn<Photo, String> retoucheCalendrier;
	
	
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
		
		switch(this.impression.getStock().getType_support()) {
			case CADRE : 
				pageCadre.setCellValueFactory(new PropertyValueFactory<Photo, String>(""));
				cheminCadre.setCellValueFactory(new PropertyValueFactory<Photo, String>("resolution"));
				resolutionCadre.setCellValueFactory(new PropertyValueFactory<Photo,String >("date_ajout"));
				xCadre.setCellValueFactory(new PropertyValueFactory<Photo, Integer>("client"));
				yCadre.setCellValueFactory(new PropertyValueFactory<Photo, Integer>("date_ajout"));
				retoucheCadre.setCellValueFactory(new PropertyValueFactory<Photo, String>("client"));
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
