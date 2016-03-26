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
import javafx.scene.control.ListView;
import main.SkeletalCalculator;

public class ApplicationOptionsController extends SkeletalCalculator implements Initializable {

	private static Scene ApplicationViewScene = null;
	private static ApplicationOptionsController instance = null;
	
	@FXML
	ListView<String> btnEntryList;
	
	@FXML
	Button btnSave;
	
	@FXML
	Button btnGoBack;
	
	@FXML
	Button btnEdit;
	
	@FXML
	Button btnRemove;
	
	@FXML
	Button btnNew;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		btnSave.setOnAction( e -> ButtonClicked(e) );
		btnGoBack.setOnAction( e -> ButtonClicked(e) );
		btnEdit.setOnAction( e -> ButtonClicked(e) );
		btnRemove.setOnAction( e -> ButtonClicked(e) );
		btnNew.setOnAction( e -> ButtonClicked(e) );
	}
	
	private void ButtonClicked( ActionEvent e ) {
		
		if( e.getSource() == btnSave ) {
			// -- Save values and go back to title view.
		}
		else if( e.getSource() == btnGoBack ){
			// -- Go back to title view.
			setScene( TitleViewController.getInstance().getScene() );
		}
		else if( e.getSource() == btnEdit ){
			// -- Go to a view with indicators.
		}
		else if( e.getSource() == btnRemove ){
			// -- Delete the selected option in the data list and reload the list.
			// -- Prompt for warning if wanting to delte?
		}
		else if( e.getSource() == btnGoBack ){
			// -- Go to a new view with empty indicators list.
		}
	}
	
	public Scene getScene() {
		return ApplicationViewScene;
	}
	
	public static ApplicationOptionsController getInstance() {
		
		if( instance == null ) {
			
			FXMLLoader loader = new FXMLLoader( SkeletalCalculator.class.getResource("ApplicationOptions.fxml") );
			
			Parent titlePane = null;
			
			try {
				titlePane = loader.load();
			} catch ( Exception e ) {
				e.printStackTrace();
			}
			
			ApplicationViewScene = new Scene( titlePane );
			
			instance = loader.getController();
		}
		
		return instance;
	}
}
