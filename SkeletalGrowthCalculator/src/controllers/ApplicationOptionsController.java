package controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Comparator;
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
	
	ArrayList<ParameterEntry> b4EditEntryList = new ArrayList<>();	
	ObservableList<String> b4EditEntryObsViewList = FXCollections.observableArrayList();	

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
	}
	
	public void populateEntryList() {
		
		b4EditEntryList = entryList;
		b4EditEntryObsViewList = entryObsViewList;
		
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
		
		// -- Sort our entries.
		FXCollections.sort( entryObsViewList );
	}
	
	private void ButtonClicked( ActionEvent e ) {
		
		if( e.getSource() == btnSave ) {
			
			// -- Overwrite our temp lists so that they're updated.
			b4EditEntryList = entryList;
			b4EditEntryObsViewList = entryObsViewList;
			
			// -- Rewrite our input text file.
			File saveFile = new File( PARAMETER_DATA_FILE_PATH );
			try {
				FileOutputStream oStream = new FileOutputStream(saveFile, false);
				
				// -- I dunno why this isn't working.
				if( entryList.size() == 0 ) {
					// -- We have nothing to write, so write nothing.
					byte[] line = "".getBytes();
					oStream.write(line);
				}
				else
				{
					for( int i = 0; i < entryList.size(); i++ ) {
						String tmp = entryList.get(i).getEntryRawInput() + "\n";
						byte[] line = tmp.getBytes();
						oStream.write(line);
					}
				}				
				
				oStream.close();
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			// -- Save values and go back to options view.
			setScene( TitleViewController.getInstance().getScene() );			
		}
		else if( e.getSource() == btnGoBack ){
			// -- Go back to title view.			
			entryList = b4EditEntryList;
			entryObsViewList = b4EditEntryObsViewList;
						
			setScene( TitleViewController.getInstance().getScene() );
		}
		else if( e.getSource() == btnEdit ){
			
			ParameterEntry sDummyEntry = entryList.get( listEntryView.getSelectionModel().getSelectedIndex() );
			
			DummyEntryDataFormController.getInstance().setParameterEntry( sDummyEntry );
			
			DummyEntryDataFormController.getInstance().setCurrentEntryID( Integer.parseInt( sDummyEntry.getEntryName() ) );
			
			DummyEntryDataFormController.getInstance().setbEditFlag(true);
			
			DummyEntryDataFormController.getInstance().refresh();
			
			setScene( DummyEntryDataFormController.getInstance().getScene() );
		}
		else if( e.getSource() == btnRemove ){
			entryList.remove( listEntryView.getSelectionModel().getSelectedIndex() );
			entryObsViewList.remove( listEntryView.getSelectionModel().getSelectedIndex() );
		}
		else if( e.getSource() == btnNew ) {
			// -- Start a counter for our entry ID.
			int tmpId = 1; 
			
			// -- Make sure our list is sorted before we begin.
			FXCollections.sort( entryObsViewList );
			
			boolean inserted = false;
			while(!inserted) {
				if( entryObsViewList.contains( Integer.toString( tmpId ) ) ){
					tmpId++;
				}
				else {
					// -- We found our new ID.
					inserted = true;
				}
			}
			
			DummyEntryDataFormController.getInstance().setCurrentEntryID(tmpId);
			
			setScene( DummyEntryDataFormController.getInstance().getScene() );
		}
	}	
	
	// -- Getters and Setters
	
	public ArrayList<ParameterEntry> getEntryList() {
		return entryList;
	}

	public void setEntryList(ArrayList<ParameterEntry> entryList) {
		this.entryList = entryList;
	}
	
	public Scene getScene() {
		return ApplicationViewScene;
	}
	
	public ObservableList<String> getEntryObsViewList() {
		return entryObsViewList;
	}

	public void setEntryObsViewList(ObservableList<String> entryObsViewList) {
		this.entryObsViewList = entryObsViewList;
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
