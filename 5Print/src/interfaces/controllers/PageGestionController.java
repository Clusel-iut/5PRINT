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

public class PageGestionController implements Initializable {
	@FXML
	private TableView<Client> tableClient;
	@FXML
	private TableColumn<Client, String> nom;
	@FXML
	private TableColumn<Client, String> prenom;
	@FXML
	private TableColumn<Client, String> email;



	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initialiserTableau();
	}

	private void initialiserTableau() {
		//CLIENT
		nom.setCellValueFactory(new PropertyValueFactory<Client, String>("nom"));
		prenom.setCellValueFactory((new PropertyValueFactory<Client, String>("prenom")));
		email.setCellValueFactory(new PropertyValueFactory<Client, String>("email"));
		ObservableList<Client> listImpression = FXCollections.observableArrayList(GestionDB.getAllClients());
		tableClient.setItems(listImpression);
		tableClient.getColumns().addAll(nom, prenom, email);
		
	}
}
