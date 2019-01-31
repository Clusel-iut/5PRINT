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
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
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
	private TableColumn<Impression, Integer> idImpression;
	@FXML
	private TableColumn<Impression, Date> dateImpression;
	@FXML
	private TableColumn<Impression, String> typeImpression;
	@FXML
	private TableColumn<Impression, String> formatImpression;
	@FXML
	private TableColumn<Impression, String> qualiteImpression;

	public void setObjects(int num) {
		System.out.println(num);
		commande = GestionDB.getCommandeById(num);
		idCommande.setText(Integer.toString(commande.getNumero()));
		listAddress.getItems().setAll(LocalDataClient.client.getAdresse());
		listAddress.getSelectionModel().select(commande.getAdresse());
		bonAchat.getItems().setAll(GestionDB.getBonAchatByEmail(LocalDataClient.client.getEmail()));
		bonAchat.getSelectionModel().select(commande.getBon_achat());
		modeLivraison.getItems().setAll("Point relais", "Colissimo", "UPS", "DHL");
		modeLivraison.getSelectionModel().select(commande.getMode_livraison());
		payer.setSelected(commande.getEtat_paiement());
		
		listImpressionView.getColumns().clear();
		
		//IMPRESSION
		idImpression.setCellValueFactory(new PropertyValueFactory<Impression, Integer>("id_impression"));
		dateImpression.setCellValueFactory((new PropertyValueFactory<Impression, Date>("date_impression")));
		typeImpression.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getStock().getType_support().toString()));
		formatImpression.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getStock().getFormat()));
		qualiteImpression.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getStock().getQualite()));
		
		//IMPRESSION
		ObservableList<Impression> listImpression = FXCollections.observableArrayList(commande.getImpressions());
		System.out.println(listImpression.toString());
		if (listImpression.size()>0) {
			listImpressionView.setItems(listImpression);
		}
					
		listImpressionView.getColumns().addAll(idImpression, dateImpression, typeImpression, formatImpression, qualiteImpression);
	}
	
	@FXML
	 void save(MouseEvent event) {
		commande.setBon_achat(bonAchat.getSelectionModel().getSelectedItem());
		commande.setBon_achat(bonAchat.getSelectionModel().getSelectedItem());
		commande.setMode_livraison(modeLivraison.getSelectionModel().getSelectedItem());
		commande.setAdresse(listAddress.getSelectionModel().getSelectedItem());
		commande.setEtat_paiement(payer.isSelected());
		
		if(GestionDB.updateCommande(commande)) {
			this.popup("Mise à jour de commande", "Mise à jour de commande réussie !", "Fermer");
		}
		else {
			this.popup("Mise à jour de commande", "Mise à jour échouée !", "Fermer");
		}
		
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
