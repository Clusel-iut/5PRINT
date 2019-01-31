package interfaces.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import DB.GestionDB;
import DB.LocalDataClient;
import DTO.Client;
import DTO.Commande;
import DTO.FichierPhoto;
import DTO.Impression;
import DTO.StatutCommande;
import DTO.Stock;
import DTO.Tirage;
import DTO.TypeSupport;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PageGestionController implements Initializable {
	@FXML
	private TableView<Client> tableClients;
	@FXML
	private TableView<Impression> tableImpressions;
	@FXML
	private TableView<Stock> tableStocks;
	@FXML
	private TableView<FichierPhoto> tableFichiersPhotos;
	@FXML
	private TableView<Commande> tableCommandes;
	//CLIENT
	@FXML
	private TableColumn<Client, String> nom;
	@FXML
	private TableColumn<Client, String> prenom;
	@FXML
	private TableColumn<Client, String> email;
	//IMPRESSION
	@FXML
	private TableColumn<Impression, Integer> idImpression;
	@FXML
	private TableColumn<Impression, Date> dateImpression;
	@FXML
	private TableColumn<Impression, String> emailImpression;
	@FXML
	private TableColumn<Impression, String> typeImpression;
	@FXML
	private TableColumn<Impression, String> formatImpression;
	@FXML
	private TableColumn<Impression, String> qualiteImpression;
	@FXML
	private TableColumn<Impression, Integer> nbImpression;
	//STOCK
	@FXML
	private TableColumn<Stock, String> typeSupportStock;
	@FXML
	private TableColumn<Stock, String> qualiteStock;
	@FXML
	private TableColumn<Stock, String> formatStock;
	@FXML
	private TableColumn<Stock, Integer> quantiteStock;
	@FXML
	private TableColumn<Stock, Integer> prixStock;
	//FICHIERPHOTO
	@FXML
	private TableColumn<FichierPhoto, String> cheminPhoto;
	@FXML
	private TableColumn<FichierPhoto, String> emailPhoto;
	@FXML
	private TableColumn<FichierPhoto, String> resolutionFichierPhoto;
	@FXML
	private TableColumn<FichierPhoto, String> priseVuePhoto;
	@FXML
	private TableColumn<FichierPhoto, Date> datePhoto;
	@FXML
	private TableColumn<FichierPhoto, Boolean> partagePhoto;
	//COMMANDE
	@FXML
	private TableColumn<Commande, Integer> idCommande;
	@FXML
	private TableColumn<Commande, Date> dateCommande;
	@FXML
	private TableColumn<Commande, String> emailCommande;
	@FXML
	private TableColumn<Commande, String> adresseCommande;
	@FXML
	private TableColumn<Commande, String> bonAchatCommande;
	@FXML
	private TableColumn<Commande, String> bonAchatGenereCom;
	@FXML
	private TableColumn<Commande, String> statutCommande;
	@FXML
	private TableColumn<Commande, String> montantTotalCom;
	@FXML
	private TableColumn<Commande, Boolean> etatPayerCom;


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initialiserTableau();
	}

	private void initialiserTableau() {
		//CLIENT
		tableClients.getColumns().clear();
		
		nom.setCellValueFactory(new PropertyValueFactory<Client, String>("nom"));
		prenom.setCellValueFactory((new PropertyValueFactory<Client, String>("prenom")));
		email.setCellValueFactory(new PropertyValueFactory<Client, String>("email"));
		
		ObservableList<Client> listClient = FXCollections.observableArrayList(GestionDB.getAllClients());
		
		tableClients.setItems(listClient);
		tableClients.getColumns().addAll(nom, prenom, email);
		//IMPRESSION
		tableImpressions.getColumns().clear();
		
		idImpression.setCellValueFactory(new PropertyValueFactory<Impression, Integer>("id_impression"));
		dateImpression.setCellValueFactory(new PropertyValueFactory<Impression, Date>("date_impression"));
		emailImpression.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getClient().getEmail()));
		typeImpression.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getStock().getType_support().toString()));
		formatImpression.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getStock().getFormat()));
		qualiteImpression.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getStock().getQualite()));
		nbImpression.setCellValueFactory(new PropertyValueFactory<Impression, Integer>("nb_impression"));
		
		ObservableList<Impression> listImpressions = FXCollections.observableArrayList(GestionDB.getAllImpression());
		
		tableImpressions.setItems(listImpressions);
		tableImpressions.getColumns().addAll(idImpression, dateImpression, emailImpression, typeImpression, formatImpression, qualiteImpression, nbImpression);
		//STOCK
		tableStocks.getColumns().clear();
		
		quantiteStock.setEditable(true);
		tableStocks.getSelectionModel().setCellSelectionEnabled(true);
		
		typeSupportStock.setCellValueFactory(new PropertyValueFactory<Stock, String>("type_support"));
		qualiteStock.setCellValueFactory(new PropertyValueFactory<Stock, String>("qualite"));
		formatStock.setCellValueFactory(new PropertyValueFactory<Stock, String>("format"));
		quantiteStock.setCellValueFactory(new PropertyValueFactory<Stock, Integer>("quantite"));
		//quantiteStock.setCellFactory(TextFieldTableCell.<Integer>forTableColumn());
		prixStock.setCellValueFactory(new PropertyValueFactory<Stock, Integer>("prix"));
		
		ObservableList<Stock> listStocks = FXCollections.observableArrayList(GestionDB.getAllStock());
		
		tableStocks.setItems(listStocks);
		tableStocks.getColumns().addAll(typeSupportStock, qualiteStock, formatStock, quantiteStock, prixStock);
		
		quantiteStock.setOnEditCommit(event -> {
		    Stock stock = event.getRowValue();
		    stock.setQuantite(event.getNewValue());
		    GestionDB.updateStock(stock);
		});
		//FichierPhoto
		tableFichiersPhotos.getColumns().clear();
		
		cheminPhoto.setCellValueFactory(new PropertyValueFactory<FichierPhoto, String>("chemin"));
		emailPhoto.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getClient().getEmail()));
		resolutionFichierPhoto.setCellValueFactory(new PropertyValueFactory<FichierPhoto, String>("resolution"));
		priseVuePhoto.setCellValueFactory(new PropertyValueFactory<FichierPhoto, String>("info_prise_de_vue"));
		datePhoto.setCellValueFactory(new PropertyValueFactory<FichierPhoto, Date>("date_ajout"));
		partagePhoto.setCellValueFactory(new PropertyValueFactory<FichierPhoto, Boolean>("est_partage"));
		
		ObservableList<FichierPhoto> listFichierPhoto = FXCollections.observableArrayList(GestionDB.getAllFichierPhotos());
		System.out.println(listFichierPhoto);
		tableFichiersPhotos.setItems(listFichierPhoto);
		tableFichiersPhotos.getColumns().addAll(cheminPhoto, emailPhoto, resolutionFichierPhoto, priseVuePhoto, datePhoto, partagePhoto);
		//COMMANDE
		tableCommandes.getColumns().clear();
		
		idCommande.setCellValueFactory(new PropertyValueFactory<Commande, Integer>("numero"));
		dateCommande.setCellValueFactory(new PropertyValueFactory<Commande, Date>("date_commande"));
		emailCommande.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getClient().getEmail()));
		adresseCommande.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getAdresse().toString()));
		//bonAchatCommande.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getBon_achat().getCode_bon()));
		//bonAchatGenereCom.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getBon_achat_genere().toString()));
		statutCommande.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getStatut().toString()));
		montantTotalCom.setCellValueFactory(data -> new ReadOnlyStringWrapper(Float.toString(data.getValue().getMontant_total_cmd())));
		etatPayerCom.setCellValueFactory(new PropertyValueFactory<Commande, Boolean>("est_partage"));
		
		ObservableList<Commande> listCommandes = FXCollections.observableArrayList(GestionDB.getAllCommandes());
		
		tableCommandes.setItems(listCommandes);
		tableCommandes.getColumns().addAll(idCommande, dateCommande, emailCommande, adresseCommande, bonAchatCommande, bonAchatGenereCom, statutCommande, montantTotalCom, etatPayerCom);
	}
	
	@FXML
	 void deleteClient(MouseEvent event) {
		if(GestionDB.deleteClientByEmail(tableClients.getSelectionModel().getSelectedItem().getEmail())) {
			this.popup("Suppression de client", "Suppression validée !", "Fermer");
			
			tableClients.getColumns().clear();
			
			nom.setCellValueFactory(new PropertyValueFactory<Client, String>("nom"));
			prenom.setCellValueFactory((new PropertyValueFactory<Client, String>("prenom")));
			email.setCellValueFactory(new PropertyValueFactory<Client, String>("email"));
			
			ObservableList<Client> listClient = FXCollections.observableArrayList(GestionDB.getAllClients());
			
			tableClients.setItems(listClient);
			tableClients.getColumns().addAll(nom, prenom, email);
		}
		else {
			this.popup("Suppression de client", "Suppression echouée !", "Fermer");
		}
	}
	
	@FXML
	 void deleteImpression(MouseEvent event) {
		if(GestionDB.deleteImpression(tableImpressions.getSelectionModel().getSelectedItem().getId_impression(),tableImpressions.getSelectionModel().getSelectedItem().getStock().getType_support().toString().toUpperCase())) {
			this.popup("Suppression d'impression", "Suppression validée !", "Fermer");
			
			tableImpressions.getColumns().clear();
			
			idImpression.setCellValueFactory(new PropertyValueFactory<Impression, Integer>("id_impression"));
			dateImpression.setCellValueFactory(new PropertyValueFactory<Impression, Date>("date_impression"));
			emailImpression.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getClient().getEmail()));
			typeImpression.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getStock().getType_support().toString()));
			formatImpression.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getStock().getFormat()));
			qualiteImpression.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getStock().getQualite()));
			nbImpression.setCellValueFactory(new PropertyValueFactory<Impression, Integer>("nb_impression"));
			
			ObservableList<Impression> listImpressions = FXCollections.observableArrayList(GestionDB.getAllImpression());
			
			tableImpressions.setItems(listImpressions);
			tableImpressions.getColumns().addAll(idImpression, dateImpression, emailImpression, typeImpression, formatImpression, qualiteImpression, nbImpression);
		}
		else {
			this.popup("Suppression d'impression", "Suppression echouée !", "Fermer");
		}
	}
	
	@FXML
	 void addStock(MouseEvent event) {
		Parent home_page_parent;
		try {
			home_page_parent = new FXMLLoader(getClass().getResource("/interfaces/views/CreationStock.fxml")).load();
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
	 void deleteStock(MouseEvent event) {
		if(GestionDB.deleteStock(tableStocks.getSelectionModel().getSelectedItem().getType_support(), 
				tableStocks.getSelectionModel().getSelectedItem().getQualite(), 
				tableStocks.getSelectionModel().getSelectedItem().getFormat())) {
			this.popup("Suppression de stock", "Suppression validée !", "Fermer");
			
			tableStocks.getColumns().clear();
			
			typeSupportStock.setCellValueFactory(new PropertyValueFactory<Stock, String>("type_support"));
			qualiteStock.setCellValueFactory(new PropertyValueFactory<Stock, String>("qualite"));
			formatStock.setCellValueFactory(new PropertyValueFactory<Stock, String>("format"));
			quantiteStock.setCellValueFactory(new PropertyValueFactory<Stock, Integer>("quantite"));
			prixStock.setCellValueFactory(new PropertyValueFactory<Stock, Integer>("prix"));
			
			ObservableList<Stock> listStocks = FXCollections.observableArrayList(GestionDB.getAllStock());
			
			tableStocks.setItems(listStocks);
			tableStocks.getColumns().addAll(typeSupportStock, qualiteStock, formatStock, quantiteStock, prixStock);
		}
		else {
			this.popup("Suppression de stock", "Suppression echouée !", "Fermer");
		}
	}
	
	@FXML
	 void deleteFichierPhoto(MouseEvent event) {
		if(GestionDB.deleteFichierPhoto(tableFichiersPhotos.getSelectionModel().getSelectedItem().getChemin())) {
			this.popup("Suppression de fichier photo", "Suppression validée !", "Fermer");
			
			tableFichiersPhotos.getColumns().clear();
			
			cheminPhoto.setCellValueFactory(new PropertyValueFactory<FichierPhoto, String>("chemin"));
			emailPhoto.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getClient().getEmail()));
			resolutionFichierPhoto.setCellValueFactory(new PropertyValueFactory<FichierPhoto, String>("resolution"));
			priseVuePhoto.setCellValueFactory(new PropertyValueFactory<FichierPhoto, String>("info_prise_de_vue"));
			datePhoto.setCellValueFactory(new PropertyValueFactory<FichierPhoto, Date>("date_ajout"));
			partagePhoto.setCellValueFactory(new PropertyValueFactory<FichierPhoto, Boolean>("est_partage"));
			
			ObservableList<FichierPhoto> listFichierPhoto = FXCollections.observableArrayList(GestionDB.getAllFichierPhotos());
			System.out.println(listFichierPhoto);
			tableFichiersPhotos.setItems(listFichierPhoto);
			tableFichiersPhotos.getColumns().addAll(cheminPhoto, emailPhoto, resolutionFichierPhoto, priseVuePhoto, datePhoto, partagePhoto);
		}
		else {
			this.popup("Suppression de fichier photo", "Suppression echouée !", "Fermer");
		}
	}
	
	@FXML
	 void deleteCommande(MouseEvent event) {
		if(GestionDB.deleteCommande(tableCommandes.getSelectionModel().getSelectedItem().getNumero())) {
			this.popup("Suppression de commande", "Suppression validée !", "Fermer");
			
			tableCommandes.getColumns().clear();
			
			idCommande.setCellValueFactory(new PropertyValueFactory<Commande, Integer>("numero"));
			dateCommande.setCellValueFactory(new PropertyValueFactory<Commande, Date>("date_commande"));
			emailCommande.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getClient().getEmail()));
			adresseCommande.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getAdresse().toString()));
			//bonAchatCommande.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getBon_achat().getCode_bon()));
			//bonAchatGenereCom.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getBon_achat_genere().toString()));
			statutCommande.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getStatut().toString()));
			montantTotalCom.setCellValueFactory(data -> new ReadOnlyStringWrapper(Float.toString(data.getValue().getMontant_total_cmd())));
			etatPayerCom.setCellValueFactory(new PropertyValueFactory<Commande, Boolean>("est_partage"));
			
			ObservableList<Commande> listCommandes = FXCollections.observableArrayList(GestionDB.getAllCommandes());
			
			tableCommandes.setItems(listCommandes);
			tableCommandes.getColumns().addAll(idCommande, dateCommande, emailCommande, adresseCommande, bonAchatCommande, bonAchatGenereCom, statutCommande, montantTotalCom, etatPayerCom);
		}
		else {
			this.popup("Suppression de commande", "Suppression echouée !", "Fermer");
		}
	}
	
	@FXML
	 void validerCommande(MouseEvent event) {
		Commande commande = tableCommandes.getSelectionModel().getSelectedItem();
		commande.setStatut(StatutCommande.Pret_a_l_envoi);
		
		if(GestionDB.updateCommande(commande)) {
			this.popup("Mise à jour de commande", "Mise à jour validée !", "Fermer");
			
			tableCommandes.getColumns().clear();
			
			idCommande.setCellValueFactory(new PropertyValueFactory<Commande, Integer>("numero"));
			dateCommande.setCellValueFactory(new PropertyValueFactory<Commande, Date>("date_commande"));
			emailCommande.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getClient().getEmail()));
			adresseCommande.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getAdresse().toString()));
			//bonAchatCommande.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getBon_achat().getCode_bon()));
			//bonAchatGenereCom.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getBon_achat_genere().toString()));
			statutCommande.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getStatut().toString()));
			montantTotalCom.setCellValueFactory(data -> new ReadOnlyStringWrapper(Float.toString(data.getValue().getMontant_total_cmd())));
			etatPayerCom.setCellValueFactory(new PropertyValueFactory<Commande, Boolean>("est_partage"));
			
			ObservableList<Commande> listCommandes = FXCollections.observableArrayList(GestionDB.getAllCommandes());
			
			tableCommandes.setItems(listCommandes);
			tableCommandes.getColumns().addAll(idCommande, dateCommande, emailCommande, adresseCommande, bonAchatCommande, bonAchatGenereCom, statutCommande, montantTotalCom, etatPayerCom);
		}
		else {
			this.popup("Mise à jour de commande", "Mise à jour echouée !", "Fermer");
		}
	}
	
	@FXML
	 void cancelCommande(MouseEvent event) {
		Commande commande = tableCommandes.getSelectionModel().getSelectedItem();
		commande.setStatut(StatutCommande.En_cours);
		
		if(GestionDB.updateCommande(commande)) {
			this.popup("Mise à jour de commande", "Mise à jour validée !", "Fermer");
			
			tableCommandes.getColumns().clear();
			
			idCommande.setCellValueFactory(new PropertyValueFactory<Commande, Integer>("numero"));
			dateCommande.setCellValueFactory(new PropertyValueFactory<Commande, Date>("date_commande"));
			emailCommande.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getClient().getEmail()));
			adresseCommande.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getAdresse().toString()));
			//bonAchatCommande.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getBon_achat().getCode_bon()));
			//bonAchatGenereCom.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getBon_achat_genere().toString()));
			statutCommande.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getStatut().toString()));
			montantTotalCom.setCellValueFactory(data -> new ReadOnlyStringWrapper(Float.toString(data.getValue().getMontant_total_cmd())));
			etatPayerCom.setCellValueFactory(new PropertyValueFactory<Commande, Boolean>("est_partage"));
			
			ObservableList<Commande> listCommandes = FXCollections.observableArrayList(GestionDB.getAllCommandes());
			
			tableCommandes.setItems(listCommandes);
			tableCommandes.getColumns().addAll(idCommande, dateCommande, emailCommande, adresseCommande, bonAchatCommande, bonAchatGenereCom, statutCommande, montantTotalCom, etatPayerCom);
		}
		else {
			this.popup("Mise à jour de commande", "Mise à jour echouée !", "Fermer");
		}
	}
	
	@FXML
	 void livrerCommande(MouseEvent event) {
		Commande commande = tableCommandes.getSelectionModel().getSelectedItem();
		commande.setStatut(StatutCommande.Envoyee);
		
		if(GestionDB.updateCommande(commande)) {
			this.popup("Mise à jour de commande", "Mise à jour validée !", "Fermer");
			
			tableCommandes.getColumns().clear();
			
			idCommande.setCellValueFactory(new PropertyValueFactory<Commande, Integer>("numero"));
			dateCommande.setCellValueFactory(new PropertyValueFactory<Commande, Date>("date_commande"));
			emailCommande.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getClient().getEmail()));
			adresseCommande.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getAdresse().toString()));
			//bonAchatCommande.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getBon_achat().getCode_bon()));
			//bonAchatGenereCom.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getBon_achat_genere().toString()));
			statutCommande.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getStatut().toString()));
			montantTotalCom.setCellValueFactory(data -> new ReadOnlyStringWrapper(Float.toString(data.getValue().getMontant_total_cmd())));
			etatPayerCom.setCellValueFactory(new PropertyValueFactory<Commande, Boolean>("est_partage"));
			
			ObservableList<Commande> listCommandes = FXCollections.observableArrayList(GestionDB.getAllCommandes());
			
			tableCommandes.setItems(listCommandes);
			tableCommandes.getColumns().addAll(idCommande, dateCommande, emailCommande, adresseCommande, bonAchatCommande, bonAchatGenereCom, statutCommande, montantTotalCom, etatPayerCom);
		}
		else {
			this.popup("Mise à jour de commande", "Mise à jour echouée !", "Fermer");
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
