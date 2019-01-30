package interfaces.controllers;

import java.io.IOException;
import java.util.Date;

import DB.GestionDB;
import DB.LocalDataClient;
import DTO.Adresse;
import DTO.BonAchat;
import DTO.Client;
import DTO.Commande;
import DTO.FichierPhoto;
import DTO.Impression;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class PasserCommandeController {
	
	private Commande commande;
	
	@FXML
	private Label idCommande;
	@FXML
	private ComboBox<BonAchat> bonAchat;
	@FXML
	private ComboBox<String> modeLivraison;
	@FXML
	private ComboBox<Adresse> listAddress;
	@FXML
	private CheckBox payer;
	@FXML
	private TableView<Impression> listImpressionView;
	@FXML
	private TableColumn<Impression, String> idImpression;
	@FXML
	private TableColumn<Impression, String> dateImpression;
	@FXML
	private TableColumn<Impression, String> typeImpression;
	@FXML
	private TableColumn<Impression, String> formatImpression;
	@FXML
	private TableColumn<Impression, String> qualiteImpression;

	public void setObjects(int num) {
		commande = GestionDB.getCommandeById(num);
		idCommande.setText(Integer.toString(commande.getNumero()));
		listAddress.getItems().setAll(LocalDataClient.client.getAdresse());
		bonAchat.getItems().setAll(GestionDB.getBonAchatByEmail(LocalDataClient.client.getEmail()));
		modeLivraison.getItems().setAll("Point relais", "Colissimo", "UPS", "DHL");
		
		//listImpressionView.getColumns().clear();
		
		//IMPRESSION
		idImpression.setCellValueFactory(new PropertyValueFactory<Impression, String>("id_impression"));
		dateImpression.setCellValueFactory((new PropertyValueFactory<Impression, String>("date_impression")));
		typeImpression.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getStock().getType_support().toString()));
		formatImpression.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getStock().getFormat()));
		qualiteImpression.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getStock().getQualite()));
		
		//IMPRESSION
		ObservableList<Impression> listImpression = FXCollections.observableArrayList(commande.getImpressions());
		System.out.println(listImpression.toString());
		listImpressionView.setItems(listImpression);
					
		listImpressionView.getColumns().addAll(idImpression, dateImpression, typeImpression, formatImpression, qualiteImpression);
	}
	
	@FXML
	 void save(MouseEvent event) {
		
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

}
