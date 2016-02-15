package main;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class TitleViewController implements Initializable {

	
	private static Scene TitleViewScene = null;
	
	private static TitleViewController instance = null;
	
	public Scene getScene() {
		return TitleViewScene;
	}
	
	public static TitleViewController getInstance() {
		
		if( instance == null ) {
			
			FXMLLoader loader = new FXMLLoader( TitleViewController.class.getResource("TitleView.fxml" ) );
			
			Parent titlePane = null;
			
			try {
				titlePane = loader.load();
			} catch ( Exception e ) {
				e.printStackTrace();
			}
			
			TitleViewScene = new Scene( titlePane );
			
			instance = loader.getController();
		}
		
		return instance;
	}
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
			
	}
}
