package application;

import DB.GestionDB;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Application client
 * 
 * @author cluselm
 *
 */
public class Gestion extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = new FXMLLoader(getClass().getResource("/interfaces/views/PageGestion.fxml")).load();
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		
		GestionDB.configure("ouzzineo", "Oussama123");
		launch(args);
		
		// Initialisation
		Gestion gestion = new Gestion();

		
		
		/**
		 * TODO Possible style : 
		 * 		DECORATED, UNDECORATED, TRANSPARENT, UTILITY, UNIFIED
		 * Default DECORATED
		 */
		Stage start = new Stage();

		// Application
		gestion.start(start);
	}
}