package interfaces.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import DB.GestionDB;
import DB.LocalDataClient;
import DTO.Agenda;
import DTO.Album;
import DTO.Cadre;
import DTO.Calendrier;
import DTO.Client;
import DTO.Commande;
import DTO.FichierPhoto;
import DTO.Impression;
import DTO.Photo;
import DTO.Tirage;
import DTO.TypeSupport;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

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
	@FXML
	private TextField modeleCadre;
	@FXML
	private TextField miseEnPageCadre;
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
	private TableColumn<Photo, Integer> yAlbum;
	@FXML
	private TableColumn<Photo, String> retoucheAlbum;
	@FXML
	private TextField titreAlbum;
	@FXML
	private TextField miseEnPageAlbum;
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
	private TableColumn<Photo, Integer> yAgenda;
	@FXML
	private TableColumn<Photo, String> retoucheAgenda;
	@FXML
	private ComboBox<String> modeleAgenda;
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
	private TableColumn<Photo, Integer> yTirage;
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
	private TableColumn<Photo, Integer> yCalendrier;
	@FXML
	private TableColumn<Photo, String> retoucheCalendrier;
	@FXML
	private TextField modeleCalendrier;
	
	
	private int idImpression;
	
	private Impression impression;

	public void setObjects(int idImpress) {
		this.idImpression = idImpress;
		this.impressionID.setText(Integer.toString(idImpression));
		
		impression = GestionDB.getImpressionById(idImpression);
				
		for(int index=0; index < tabType.getTabs().size(); index++) {
			if(!tabType.getTabs().get(index).getText().toLowerCase().equals(impression.getStock().getType_support().toString().toLowerCase()))
			{
				tabType.getTabs().get(index).setDisable(true);
			}
		}
		
		switch(this.impression.getStock().getType_support()) {
		
			case CADRE : 
				
				cadreView.getColumns().clear();
				
				Cadre cadre = GestionDB.getImpressionById(idImpression);
				
				if(cadre.getDate_impression() != null) {
					impressionDate.setText(cadre.getDate_impression().toString());
				}
				montantTotal.setText(Float.toString(cadre.getMontant_total()));
				etatImpression.setText(Boolean.toString(cadre.getEtat_impression()));
				qualite.setText(cadre.getStock().getQualite());
				format.setText(cadre.getStock().getFormat());
				nbImpression.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10));
				nbImpression.getValueFactory().setValue(cadre.getNb_impression());
				System.out.println(cadre.getStock().getType_support().toString().toLowerCase());
				
				pageCadre.setCellValueFactory(new PropertyValueFactory<Photo, String>("numero_page"));
				
				//PROBLEME DE CAST
				cheminCadre.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getFichier().getChemin()));
				resolutionCadre.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getFichier().getResolution()));
				
				xCadre.setCellValueFactory(new PropertyValueFactory<Photo, Integer>("position_X"));
				yCadre.setCellValueFactory(new PropertyValueFactory<Photo, Integer>("position_Y"));
				retoucheCadre.setCellValueFactory(new PropertyValueFactory<Photo, String>("retouche"));
				modeleCadre.setText(cadre.getModele());
				miseEnPageCadre.setText(cadre.getModele());
				
				//IMPRESSION
				ObservableList<Photo> photoCadre = FXCollections.observableArrayList(cadre.getPhotos());
				cadreView.setItems(photoCadre);
				
				cadreView.getColumns().addAll(pageCadre, cheminCadre, resolutionCadre, xCadre, yCadre, retoucheCadre);	
				break;
				
			case ALBUM : 
				
				albumView.getColumns().clear();
				
				Album album = GestionDB.getImpressionById(idImpression);
				
				if(album.getDate_impression() != null) {
					impressionDate.setText(album.getDate_impression().toString());
				}
				montantTotal.setText(Float.toString(album.getMontant_total()));
				etatImpression.setText(Boolean.toString(album.getEtat_impression()));
				qualite.setText(album.getStock().getQualite());
				format.setText(album.getStock().getFormat());
				nbImpression.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10));
				nbImpression.getValueFactory().setValue(album.getNb_impression());
				System.out.println(album.getStock().getType_support().toString().toLowerCase());
				
				pageAlbum.setCellValueFactory(new PropertyValueFactory<Photo, String>("numero_page"));
				
				//PROBLEME DE CAST
				cheminAlbum.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getFichier().getChemin()));
				resolutionAlbum.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getFichier().getResolution()));
				
				xAlbum.setCellValueFactory(new PropertyValueFactory<Photo, Integer>("position_X"));
				yAlbum.setCellValueFactory(new PropertyValueFactory<Photo, Integer>("position_Y"));
				retoucheAlbum.setCellValueFactory(new PropertyValueFactory<Photo, String>("retouche"));
				titreAlbum.setText(album.getTitre());
				miseEnPageAlbum.setText(album.getMise_en_page());
				
				//IMPRESSION
				ObservableList<Photo> photoAlbum = FXCollections.observableArrayList(album.getPhotos());
				albumView.setItems(photoAlbum);
				
				albumView.getColumns().addAll(pageAlbum, cheminAlbum, resolutionAlbum, xAlbum, yAlbum, retoucheAlbum);
				break;
				
			case AGENDA:
				
				agendaView.getColumns().clear();
				
				Agenda agenda = GestionDB.getImpressionById(idImpression);
				
				if(agenda.getDate_impression() != null) {
					impressionDate.setText(agenda.getDate_impression().toString());
				}
				montantTotal.setText(Float.toString(agenda.getMontant_total()));
				etatImpression.setText(Boolean.toString(agenda.getEtat_impression()));
				qualite.setText(agenda.getStock().getQualite());
				format.setText(agenda.getStock().getFormat());
				nbImpression.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10));
				nbImpression.getValueFactory().setValue(agenda.getNb_impression());
				System.out.println(agenda.getStock().getType_support().toString().toLowerCase());
				
				pageAgenda.setCellValueFactory(new PropertyValueFactory<Photo, String>("numero_page"));
				
				//PROBLEME DE CAST
				cheminAgenda.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getFichier().getChemin()));
				resolutionAgenda.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getFichier().getResolution()));
				
				xAgenda.setCellValueFactory(new PropertyValueFactory<Photo, Integer>("position_X"));
				yAgenda.setCellValueFactory(new PropertyValueFactory<Photo, Integer>("position_Y"));
				retoucheAgenda.setCellValueFactory(new PropertyValueFactory<Photo, String>("retouche"));
				modeleAgenda.getItems().setAll(agenda.getModele());
				
				//IMPRESSION
				ObservableList<Photo> photoAgenda = FXCollections.observableArrayList(agenda.getPhotos());
				agendaView.setItems(photoAgenda);
				
				agendaView.getColumns().addAll(pageAgenda, cheminAgenda, resolutionAgenda, xAgenda, yAgenda, retoucheAgenda);
				break;
				
			case TIRAGE: 
				
				tirageView.getColumns().clear();
				
				Tirage tirage = GestionDB.getImpressionById(idImpression);
				
				if(tirage.getDate_impression() != null) {
					impressionDate.setText(tirage.getDate_impression().toString());
				}
				montantTotal.setText(Float.toString(tirage.getMontant_total()));
				etatImpression.setText(Boolean.toString(tirage.getEtat_impression()));
				qualite.setText(tirage.getStock().getQualite());
				format.setText(tirage.getStock().getFormat());
				nbImpression.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10));
				nbImpression.getValueFactory().setValue(tirage.getNb_impression());
				System.out.println(tirage.getStock().getType_support().toString().toLowerCase());
				
				pageTirage.setCellValueFactory(new PropertyValueFactory<Photo, String>("numero_page"));
				
				//PROBLEME DE CAST
				cheminTirage.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getFichier().getChemin()));
				resolutionTirage.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getFichier().getResolution()));
				
				xTirage.setCellValueFactory(new PropertyValueFactory<Photo, Integer>("position_X"));
				yTirage.setCellValueFactory(new PropertyValueFactory<Photo, Integer>("position_Y"));
				retoucheTirage.setCellValueFactory(new PropertyValueFactory<Photo, String>("retouche"));
				nbExTirage.setCellValueFactory(new PropertyValueFactory<Photo, String>("nb_exemplaire"));
				
				//IMPRESSION
				ObservableList<Photo> photoTirage = FXCollections.observableArrayList(tirage.getPhotos());
				tirageView.setItems(photoTirage);
				
				tirageView.getColumns().addAll(pageTirage, cheminTirage, resolutionTirage, xTirage, yTirage, retoucheTirage, nbExTirage);
				break;
				
			case CALENDRIER :
				
				calendrierView.getColumns().clear();
				
				Calendrier calendrier = GestionDB.getImpressionById(idImpression);
				
				if(calendrier.getDate_impression() != null) {
					impressionDate.setText(calendrier.getDate_impression().toString());
				}
				montantTotal.setText(Float.toString(calendrier.getMontant_total()));
				etatImpression.setText(Boolean.toString(calendrier.getEtat_impression()));
				qualite.setText(calendrier.getStock().getQualite());
				format.setText(calendrier.getStock().getFormat());
				nbImpression.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10));
				nbImpression.getValueFactory().setValue(calendrier.getNb_impression());
				System.out.println(calendrier.getStock().getType_support().toString().toLowerCase());
				
				pageCalendrier.setCellValueFactory(new PropertyValueFactory<Photo, String>("numero_page"));
				
				//PROBLEME DE CAST
				cheminCalendrier.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getFichier().getChemin()));
				resolutionCalendrier.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getFichier().getResolution()));
				
				xCalendrier.setCellValueFactory(new PropertyValueFactory<Photo, Integer>("position_X"));
				yCalendrier.setCellValueFactory(new PropertyValueFactory<Photo, Integer>("position_Y"));
				retoucheCalendrier.setCellValueFactory(new PropertyValueFactory<Photo, String>("retouche"));
				modeleCalendrier.setText(calendrier.getModele());
				
				//IMPRESSION
				ObservableList<Photo> photoCalendrier = FXCollections.observableArrayList(calendrier.getPhotos());
				calendrierView.setItems(photoCalendrier);
				
				calendrierView.getColumns().addAll(pageCalendrier, cheminCalendrier, resolutionCalendrier, xCalendrier, yCalendrier, retoucheCalendrier);
				break;
	
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
	
	@FXML
    void deleteCadre(MouseEvent event) { 
		if(GestionDB.deletePhoto(cadreView.getSelectionModel().getSelectedItem())) {
			this.popup("Gerer photos", "Suppression de photo" , "Fermer");
		}
		else {
			this.popup("Gerer photos", "Suppression échouée !" , "Fermer");
		}
	}
	
	@FXML
    void deleteAlbum(MouseEvent event) { 
		if(GestionDB.deletePhoto(albumView.getSelectionModel().getSelectedItem())) {
			this.popup("Gerer photos", "Suppression de photo" , "Fermer");
		}
		else {
			this.popup("Gerer photos", "Suppression échouée !" , "Fermer");
		}
	}
	
	@FXML
    void deleteAgenda(MouseEvent event) { 
		if(GestionDB.deletePhoto(agendaView.getSelectionModel().getSelectedItem())) {
			this.popup("Gerer photos", "Suppression de photo" , "Fermer");
		}
		else {
			this.popup("Gerer photos", "Suppression échouée !" , "Fermer");
		}
	}
	
	@FXML
    void deleteTirage(MouseEvent event) { 
		if(GestionDB.deletePhoto(tirageView.getSelectionModel().getSelectedItem())) {
			this.popup("Gerer photos", "Suppression de photo" , "Fermer");
		}
		else {
			this.popup("Gerer photos", "Suppression échouée !" , "Fermer");
		}
	}
	
	@FXML
    void deleteCalendrier(MouseEvent event) { 
		if(GestionDB.deletePhoto(calendrierView.getSelectionModel().getSelectedItem())) {
			this.popup("Gerer photos", "Suppression de photo" , "Fermer");
		}
		else {
			this.popup("Gerer photos", "Suppression échouée !" , "Fermer");
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	
	// TODO PROBLEME DE MISE A JOUR
	@FXML
    void save(MouseEvent event) { 
		
		switch(this.impression.getStock().getType_support()) {
		
		case CADRE :
			Cadre cadre = GestionDB.getImpressionById(idImpression);
			cadre.setModele(modeleCadre.getText());
			cadre.setModele(miseEnPageCadre.getText());
			cadre.setNb_impression(nbImpression.getValue());
			
			    if (GestionDB.updateImpression(TypeSupport.CADRE, cadre)) {
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
			    else {
			    	this.popup("Sauvegarde", "Mise à jour échouée !" , "Fermer");
			    }
			    break;
		case ALBUM:
			Album album = GestionDB.getImpressionById(idImpression);
			album.setTitre(titreAlbum.getText());
			album.setMise_en_page(miseEnPageAlbum.getText());
			album.setNb_impression(nbImpression.getValue());
			
			    if (GestionDB.updateImpression(TypeSupport.ALBUM, album)) {
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
			    else {
			    	this.popup("Sauvegarde", "Mise à jour échouée !" , "Fermer");
			    }
			break;
		case AGENDA:
			Agenda agenda = GestionDB.getImpressionById(idImpression);
			agenda.setModele(modeleAgenda.getValue());
			agenda.setNb_impression(nbImpression.getValue());
			
			    if (GestionDB.updateImpression(TypeSupport.AGENDA, agenda)) {
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
			    else {
			    	this.popup("Sauvegarde", "Mise à jour échouée !" , "Fermer");
			    }
			break;
		case TIRAGE:
			Tirage tirage = GestionDB.getImpressionById(idImpression);
			tirage.setNb_impression(nbImpression.getValue());
			
			    if (GestionDB.updateImpression(TypeSupport.TIRAGE, tirage)) {
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
			    else {
			    	this.popup("Sauvegarde", "Mise à jour échouée !" , "Fermer");
			    }
			break;
		case CALENDRIER:
			Calendrier calendrier = GestionDB.getImpressionById(idImpression);
			calendrier.setModele(modeleCalendrier.getText());
			calendrier.setNb_impression(nbImpression.getValue());
			
			    if (GestionDB.updateImpression(TypeSupport.CALENDRIER, calendrier)) {
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
			    else {
			    	this.popup("Sauvegarde", "Mise à jour échouée !" , "Fermer");
			    }
			break;
		}
	}
	
	@FXML
    void commander(MouseEvent event) { 
	  	int i = idImpression;
	  	
	 	FXMLLoader Loader = new FXMLLoader();
	    Loader.setLocation(getClass().getResource("/interfaces/views/ChoisirCommande.fxml"));
	    try {
			Loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    ChoisirCommandeController controller = Loader.getController();
	    controller.setObjects(i);

	    Parent home_page_parent = Loader.getRoot();
	    Scene home_page_scene = new Scene(home_page_parent);
	    Stage app_stage = (Stage) ((Node) event.getSource()).getScene()
		    .getWindow();
	    app_stage.setScene(home_page_scene);
	    app_stage.show();	
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
