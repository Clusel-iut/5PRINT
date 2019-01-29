package interfaces.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import DB.GestionDB;
import DB.LocalDataClient;
import DTO.Commande;
import DTO.FichierPhoto;
import DTO.Impression;
import DTO.Tirage;
import DTO.TypeSupport;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class PageRecapController implements Initializable {
	
	@FXML
	private Label prenomNomClient;
	@FXML
	private TableView<Impression> impressionsView;
	@FXML
	private TableView<FichierPhoto> photosView;
	@FXML
	private TableView<FichierPhoto> photosViewShare;
	@FXML
	private TableView<Commande> paniersView;
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
	@FXML
	private TableColumn<FichierPhoto, String> fichierPhoto;
	@FXML
	private TableColumn<FichierPhoto, String> resolutionPhoto;
	@FXML
	private TableColumn<FichierPhoto, Date> datePhoto;
	@FXML
	private TableColumn<FichierPhoto, String> fichierPhotoPartagee;
	@FXML
	private TableColumn<FichierPhoto, String> resolutionPhotoPartagee;
	@FXML
	private TableColumn<FichierPhoto, Date> datePhotoPartagee;
	@FXML
	private TableColumn<FichierPhoto, String> proprio;
	@FXML
	private TableColumn<Commande, Integer> idPanier;
	@FXML
	private TableColumn<Commande, Date> datePanier;
	@FXML
	private TableColumn<Commande, String> montantPanier;
	@FXML
	private TableColumn<Commande, String> etatPanier;


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		prenomNomClient.setText(LocalDataClient.client.getPrenom() + " " + LocalDataClient.client.getNom());
		
		initialiserTableau();
		
		
		//photosView.getColumns().addAll(cheminPhoto, resolutionPhoto, datePhoto);
		//photosViewShare.getColumns().addAll(cheminPhotoPartagee, resolutionPhotoPartagee, datePhotoPartagee);
		//paniersView.getColumns().addAll(idPanier, datePanier, typePanier, formatPanier, qualitePanier);
		//paniersViewShare.getColumns().addAll(idPanierEnCours, datePanierEnCours, montantPanierEnCours, etatPanierEnCours);
		
		/**
		if (LocalDataClient.client.getImpressions() != null) {
			for(int index = 0; index < LocalDataClient.client.getImpressions().size(); index++) {
				idImpression.setCellValueFactory(new PropertyValueFactory<Impression, String>(Integer.toString(LocalDataClient.client.getImpressions().get(index).getId_impression())));
				System.out.println(LocalDataClient.client.getImpressions().get(index).getId_impression());
				//dateImpression.setCellValueFactory(new PropertyValueFactory<Impression, String>(LocalDataClient.client.getImpressions().get(index).getDate_impression().toString()));
				//typeImpression.setCellValueFactory(new PropertyValueFactory<Impression, String>(LocalDataClient.client.getImpressions().get(index).getStock().getType_support().name()));
				//formatImpression.setCellValueFactory(new PropertyValueFactory<Impression, String>(LocalDataClient.client.getImpressions().get(index).getStock().getQualite()));
				//qualiteImpression.setCellValueFactory(new PropertyValueFactory<Impression, String>(LocalDataClient.client.getImpressions().get(index).getStock().getQualite()));				
			}
		}
		if (LocalDataClient.client.getPhotos() != null) {
			for(int index = 0; index < LocalDataClient.client.getPhotos().size(); index++) {
				cheminPhoto.setCellValueFactory(new PropertyValueFactory<FichierPhoto, String>(LocalDataClient.client.getPhotos().get(index).getChemin()));
				resolutionPhoto.setCellValueFactory(new PropertyValueFactory<FichierPhoto, String>(LocalDataClient.client.getImpressions().get(index).getStock().getType_support().name()));
				datePhoto.setCellValueFactory(new PropertyValueFactory<FichierPhoto, Date>(LocalDataClient.client.getImpressions().get(index).getStock().getQualite()));
			}
		}
		if (LocalDataClient.client.getCommandes() != null) {
			for(int index = 0; index < LocalDataClient.client.getImpressions().size(); index++) {
				idImpression.setCellValueFactory(new PropertyValueFactory<Impression, Integer>(Integer.toString(LocalDataClient.client.getImpressions().get(index).getId_impression())));
				dateImpression.setCellValueFactory(new PropertyValueFactory<Impression, Date>(Integer.toString(LocalDataClient.client.getImpressions().get(index).getId_impression())));
				typeImpression.setCellValueFactory(new PropertyValueFactory<Impression, String>(LocalDataClient.client.getImpressions().get(index).getStock().getType_support().name()));
				formatImpression.setCellValueFactory(new PropertyValueFactory<Impression, String>(LocalDataClient.client.getImpressions().get(index).getStock().getQualite()));
				qualiteImpression.setCellValueFactory(new PropertyValueFactory<Impression, String>(LocalDataClient.client.getImpressions().get(index).getStock().getQualite()));				
			}
		}*/
		System.out.println("erggr");
		
	}

	private void initialiserTableau() {
		impressionsView.getColumns().clear();
		photosView.getColumns().clear();
		paniersView.getColumns().clear();
		photosViewShare.getColumns().clear();
		
		//IMPRESSION
		idImpression.setCellValueFactory(new PropertyValueFactory<Impression, String>("id_impression"));
		dateImpression.setCellValueFactory((new PropertyValueFactory<Impression, String>("date_impression")));
		typeImpression.setCellValueFactory(new PropertyValueFactory<Impression, String>("stock.type_support"));
		formatImpression.setCellValueFactory(new PropertyValueFactory<Impression, String>("stock.format"));	
		qualiteImpression.setCellValueFactory(new PropertyValueFactory<Impression, String>("stock.qualite"));
		
		//PHOTO
		fichierPhoto.setCellValueFactory(new PropertyValueFactory<FichierPhoto, String>("chemin"));
		resolutionPhoto.setCellValueFactory(new PropertyValueFactory<FichierPhoto, String>("resolution"));
		datePhoto.setCellValueFactory(new PropertyValueFactory<FichierPhoto, Date>("date_ajout"));
		
		//PHOTOS PARTAGEES
		fichierPhotoPartagee.setCellValueFactory(new PropertyValueFactory<FichierPhoto, String>("chemin"));
		resolutionPhotoPartagee.setCellValueFactory(new PropertyValueFactory<FichierPhoto, String>("resolution"));
		datePhotoPartagee.setCellValueFactory(new PropertyValueFactory<FichierPhoto, Date>("date_ajout"));
		proprio.setCellValueFactory(new PropertyValueFactory<FichierPhoto, String>("client"));
		
		///COMMANDE
		idPanier.setCellValueFactory(new PropertyValueFactory<Commande, Integer>("numero"));
		datePanier.setCellValueFactory(new PropertyValueFactory<Commande, Date>("date_commande"));
		montantPanier.setCellValueFactory(new PropertyValueFactory<Commande, String>("montant_total_cmd"));
		etatPanier.setCellValueFactory(new PropertyValueFactory<Commande, String>("statut"));
		
		//IMPRESSION
		ObservableList<Impression> listImpression = FXCollections.observableArrayList(LocalDataClient.client.getImpressions());
		impressionsView.setItems(listImpression);
		
		//FICHIER PHOTO
		ObservableList<FichierPhoto> listFichierPhoto = FXCollections.observableArrayList(LocalDataClient.client.getPhotos());
		photosView.setItems(listFichierPhoto);
		
		//PHOTOS PARTAGEES
		ObservableList<FichierPhoto> listFichierPhotoShare = FXCollections.observableArrayList(LocalDataClient.client.getPhotos_partagees());
		photosViewShare.setItems(listFichierPhotoShare);
		
		//COMMANDE
		ObservableList<Commande> listCommande = FXCollections.observableArrayList(LocalDataClient.client.getCommandes());
		paniersView.setItems(listCommande);
		
		impressionsView.getColumns().addAll(idImpression, dateImpression, typeImpression, formatImpression, qualiteImpression);
		photosView.getColumns().addAll(fichierPhoto, resolutionPhoto, datePhoto);
		photosViewShare.getColumns().addAll(fichierPhotoPartagee, resolutionPhotoPartagee, datePhotoPartagee);
		paniersView.getColumns().addAll(idPanier, datePanier, montantPanier, etatPanier);
	}
	
	 @FXML
	void profil(MouseEvent event) {
		Parent home_page_parent;
		try {
			home_page_parent = new FXMLLoader(getClass().getResource("/interfaces/views/Profil.fxml")).load();
			Scene home_page_scene = new Scene(home_page_parent);
			Stage app_stage = (Stage) ((Node) event.getSource()).getScene()
				.getWindow();
			app_stage.setScene(home_page_scene);
			app_stage.show();
		} catch (IOException e) {
			System.err.println("Impossible d'afficher la page 'Profil'");
		}
	}
	 
	 @FXML
    void deconnect(MouseEvent event) {
		LocalDataClient.client = null;
    	Parent home_page_parent;
		try {
			home_page_parent = new FXMLLoader(getClass().getResource("/interfaces/views/Connexion.fxml")).load();
			Scene home_page_scene = new Scene(home_page_parent);
			Stage app_stage = (Stage) ((Node) event.getSource()).getScene()
				.getWindow();
			app_stage.setScene(home_page_scene);
			app_stage.show();
		} catch (IOException e) {
			System.err.println("Impossible d'afficher la page 'Connexion'");
		}
		
    }
	 
	 @FXML
	 void create(MouseEvent event) {
		 Parent home_page_parent;
			try {
				home_page_parent = new FXMLLoader(getClass().getResource("/interfaces/views/CreationImpression.fxml")).load();
				Scene home_page_scene = new Scene(home_page_parent);
				Stage app_stage = (Stage) ((Node) event.getSource()).getScene()
					.getWindow();
				app_stage.setScene(home_page_scene);
				app_stage.show();
			} catch (IOException e) {
				System.err.println("Impossible d'afficher la page 'Connexion'");
			}
	 }
}
