package application;

import DB.GestionDB;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Application client
 * 
 * @author cluselm
 *
 */
public class Client extends Application {
    @Override
    public void start(Stage primaryStage) {
	try {
		
	    Parent root = FXMLLoader.load(this.getClass().getResource(
		    "/interfaces/views/Connexion.fxml"));
	    Scene scene = new Scene(root);
	    primaryStage.setScene(scene);
	    primaryStage.initStyle(StageStyle.TRANSPARENT);
	    primaryStage.initStyle(StageStyle.UNDECORATED);
	    primaryStage.show();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    public static void main(String[] args) {
    	launch(args);
    }
}