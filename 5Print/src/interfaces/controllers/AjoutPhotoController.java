package interfaces.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import DB.GestionDB;
import DB.LocalDataClient;
import DTO.FichierPhoto;
import DTO.Impression;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AjoutPhotoController implements Initializable {
	
	@FXML
	private ComboBox<FichierPhoto> allFichierPhoto;
	@FXML
	private TextField description;
	@FXML
	private TextField retouche;
	@FXML
	private Spinner<Integer> numPage;
	@FXML
	private Spinner<Integer> positionX;
	@FXML
	private Spinner<Integer> positionY;
	@FXML
	private Spinner<Integer> nbExemplaire;
	
	private int idImpress;
	
	private Impression impression;
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	 @FXML
		void back(MouseEvent event) {
		 FXMLLoader Loader = new FXMLLoader();
		    Loader.setLocation(getClass().getResource("/interfaces/views/GestionImpression.fxml"));
		    try {
				Loader.load();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		    GestionImpressionController controller = Loader.getController();
		    controller.setObjects(idImpress);

		    Parent home_page_parent = Loader.getRoot();
		    Scene home_page_scene = new Scene(home_page_parent);
		    Stage app_stage = (Stage) ((Node) event.getSource()).getScene()
			    .getWindow();
		    app_stage.setScene(home_page_scene);
		    app_stage.show();
	 }
	 
	 @FXML
		void save(MouseEvent event) {
		 
		 GestionDB.createPhoto(allFichierPhoto.getSelectionModel().getSelectedItem().getChemin(), impression, description.getText(), positionX.getValue(), positionY.getValue(), numPage.getValue(), nbExemplaire.getValue(), retouche.getText());
		 
		 FXMLLoader Loader = new FXMLLoader();
		    Loader.setLocation(getClass().getResource("/interfaces/views/GestionImpression.fxml"));
		    try {
				Loader.load();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		    GestionImpressionController controller = Loader.getController();
		    controller.setObjects(idImpress);

		    Parent home_page_parent = Loader.getRoot();
		    Scene home_page_scene = new Scene(home_page_parent);
		    Stage app_stage = (Stage) ((Node) event.getSource()).getScene()
			    .getWindow();
		    app_stage.setScene(home_page_scene);
		    app_stage.show();
	 }

	public void setObjects(int idImpression) {
		this.idImpress = idImpression;
		ArrayList<FichierPhoto> listPhotos = LocalDataClient.client.getPhotos();
		listPhotos.addAll(LocalDataClient.client.getPhotos_partagees());
		allFichierPhoto.getItems().setAll(listPhotos);	
		impression = GestionDB.getImpressionById(idImpression);
		numPage.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10));
		positionX.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10));
		positionY.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10));
		nbExemplaire.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10));
	}

}
