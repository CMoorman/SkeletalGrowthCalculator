package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import main.SkeletalCalculator;

public class TitleViewController extends SkeletalCalculator implements Initializable {

	private static Scene TitleViewScene = null;
	private static TitleViewController instance = null;
	
	@FXML Button btnStart;
	@FXML Button btnOptions;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		btnStart.setOnAction( e -> ButtonClicked( e ) );
		btnOptions.setOnAction( e -> ButtonClicked( e ) );
	}
	
	private void ButtonClicked( ActionEvent e ) {
		Object buttonSource = e.getSource();
		
		if( buttonSource == btnStart ) {
			setScene( FELSDataFormController.getInstance().getScene() );
		}
		else if( buttonSource == btnOptions ) {
			ApplicationOptionsController.getInstance().populateEntryList();
			setScene( ApplicationOptionsController.getInstance().getScene() );
		}
	}
	
	public Scene getScene() {
		return TitleViewScene;
	}
	
	public static TitleViewController getInstance() {
		
		if( instance == null ) {
			
			FXMLLoader loader = new FXMLLoader( SkeletalCalculator.class.getResource("TitleView.fxml" ) );
			
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
}
