package controllers;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import main.Indicator;
import main.ParameterEntry;
import main.SkeletalCalculator;

public class ApplicationOptionsController extends SkeletalCalculator implements Initializable {

	private static Scene ApplicationViewScene = null;
	private static ApplicationOptionsController instance = null;
	
	private static final String PARAMETER_DATA_FILE_PATH = "ParameterData.txt";
	
	ArrayList<ParameterEntry> entryList = new ArrayList<>();
	ObservableList<String> entryObsViewList = FXCollections.observableArrayList();
	
	@FXML
	ListView<String> listEntryView;
	
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
		
		Platform.runLater( new Runnable() {
			@Override
			public void run() {
				populateEntryList();
			}
		});
	}
	
	private void populateEntryList() {
		// -- Going to read in each entry line by line.
		try{
			URL url = Indicator.class.getResource(PARAMETER_DATA_FILE_PATH);
			FileInputStream fileStream = new FileInputStream(url.getPath());
			InputStreamReader isr = new InputStreamReader(fileStream, Charset.forName("UTF-8"));
			BufferedReader br = new BufferedReader(isr);
			String line = "";
			while ((line = br.readLine()) != null) {
				ParameterEntry newEntry = new ParameterEntry(line);
				entryList.add(newEntry);
				entryObsViewList.add(newEntry.getEntryName());
		    }
			
			br.close();
			
			if( entryObsViewList.size() >= 0 ){
				listEntryView.setItems(entryObsViewList);
			}
			
		}catch(Exception e){
			// -- Unable to find the list.
			Alert eAlert = new Alert(AlertType.ERROR);
			eAlert.setTitle("ERROR");
			eAlert.setHeaderText("Missing parameter data text file.");

			eAlert.setContentText("The Parater Data text file could not be located or loaded.");

			eAlert.showAndWait();
			e.printStackTrace();
		}
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
