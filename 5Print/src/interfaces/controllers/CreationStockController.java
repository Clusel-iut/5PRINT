package interfaces.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import DB.GestionDB;
import DTO.Stock;
import DTO.TypeSupport;
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
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CreationStockController implements Initializable{

	@FXML
	private ComboBox<TypeSupport> lesTypesSupports;
	@FXML
	private TextField format;
	@FXML
	private TextField qualite;
	@FXML
	private Spinner<Integer> quantite;
	@FXML
	private Spinner<Integer> prix;
	
	boolean isUpdate = false;
	Stock stock = null;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		isUpdate = false;
		quantite.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50));
		prix.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50));
		lesTypesSupports.getItems().setAll(TypeSupport.values());
	}
	
	public void setObjects(Stock stock) {
		isUpdate = true;
		this.stock = stock;
		quantite.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50));
		prix.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50));
		
		quantite.getValueFactory().setValue(stock.getQuantite());
		prix.getValueFactory().setValue(stock.getPrix());
		
		lesTypesSupports.setValue(stock.getType_support());;
		format.setText(stock.getFormat());
		qualite.setText(stock.getQualite());
		
		lesTypesSupports.setDisable(true);
		format.setDisable(true);
		qualite.setDisable(true);
	}
	
	@FXML
	 void back(MouseEvent event) {
		Parent home_page_parent;
		try {
			home_page_parent = new FXMLLoader(getClass().getResource("/interfaces/views/PageGestion.fxml")).load();
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
	 void save(MouseEvent event) {
		if(isUpdate == false) {
			if(GestionDB.createStock(lesTypesSupports.getSelectionModel().getSelectedItem(), qualite.getText(), format.getText(), quantite.getValue(), prix.getValue())) {
				this.popup("Ajout de stock", "Le stock a été ajouté !", "Fermer");
				Parent home_page_parent;
				try {
					home_page_parent = new FXMLLoader(getClass().getResource("/interfaces/views/PageGestion.fxml")).load();
					Scene home_page_scene = new Scene(home_page_parent);
					Stage app_stage = (Stage) ((Node) event.getSource()).getScene()
						.getWindow();
					app_stage.setScene(home_page_scene);
					app_stage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			else {
				this.popup("Ajout de stock", "Le stock n'a pas pu être ajouté !", "Fermer");
			}
		} else {
			stock.setQuantite(quantite.getValue());
			stock.setPrix(prix.getValue());
			if(GestionDB.updateStock(stock)) {
				this.popup("Modification de stock", "Le stock a été mis à jour !", "Fermer");
				Parent home_page_parent;
				try {
					home_page_parent = new FXMLLoader(getClass().getResource("/interfaces/views/PageGestion.fxml")).load();
					Scene home_page_scene = new Scene(home_page_parent);
					Stage app_stage = (Stage) ((Node) event.getSource()).getScene()
						.getWindow();
					app_stage.setScene(home_page_scene);
					app_stage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			else {
				this.popup("Modification de stock", "Le stock n'a pas pu être mis à jour !", "Fermer");
			}
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
