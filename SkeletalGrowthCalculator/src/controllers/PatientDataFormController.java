package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import main.SkeletalCalculator;

public class PatientDataFormController extends SkeletalCalculator implements Initializable {

	private static Scene PatientDataFormScene = null;
	private static PatientDataFormController instance = null;
	private ObservableList<String> genderList = FXCollections.observableArrayList("Male", "Female");
	// -- Form FXML -- Patient Info ************
	@FXML TextField txtStudy;
	@FXML TextField txtID;
	@FXML TextField txtChronAge;
	@FXML ComboBox<String> cmbGender;
	@FXML TextField txtAssessorNum;
	@FXML TextField txtAssessmentNum;
	@FXML TextField txtBirthDate;
	@FXML TextField txtXrayDate;
	@FXML TextField txtAsmDate;
	@FXML TextField txtSA;
	@FXML TextField txtSEE;
	
	// -- Form FXML -- Measurements *************
	
	// -- Radius
	@FXML TextField txtR1;
	@FXML TextField txtR2;
	@FXML TextField txtR2EW;
	@FXML TextField txtR2MW;
	@FXML TextField txtR3;
	@FXML TextField txtR4;
	@FXML TextField txtR5;
	@FXML TextField txtR6;
	@FXML TextField txtR7;
	@FXML TextField txtR8;
	
	// -- Triquetral
	@FXML TextField txtTRI1;
	@FXML TextField txtTRI2;
	@FXML TextField txtTRI3;
	@FXML TextField txtTRI4;
	
	// -- Trapezoid
	@FXML TextField txtTPD1;
	@FXML TextField txtTPD2;
	@FXML TextField txtTPD3;
	@FXML TextField txtTPD4;
	@FXML TextField txtTPD5;
	@FXML TextField txtTPD6;
	@FXML TextField txtTPD7;
	
	// -- PISIForm
	@FXML TextField txtP1;
	
	// -- Lunate
	@FXML TextField txtL1;
	@FXML TextField txtL2;
	
	// -- Adductor Sesamoid
	@FXML TextField txtAS1;
	
	// -- Ulna
	@FXML TextField txtU1;
	//@FXML TextField txtU2; Not needed?
	@FXML TextField txtU2EW;
	@FXML TextField txtU2MW;
	@FXML TextField txtU3;
	
	// -- Scaphoid
	@FXML TextField txtS1;
	@FXML TextField txtS2;
	@FXML TextField txtS3;
	
	// -- Metacarpal I
	@FXML TextField txtMETI1;
	//@FXML TextField txtMETI2; Not needed?
	@FXML TextField txtMETI2EW;
	@FXML TextField txtMETI2MW;
	@FXML TextField txtMETI3;
	@FXML TextField txtMETI4;
	@FXML TextField txtMETI5;
	@FXML TextField txtMETI6;
	@FXML TextField txtMETI7;
	
	// -- Capitate
	@FXML TextField txtC1;
	@FXML TextField txtC2;
	@FXML TextField txtC3;
	@FXML TextField txtC4;
	
	// -- Trapezium
	@FXML TextField txtTPM1;
	@FXML TextField txtTPM2;
	@FXML TextField txtTPM3;
	@FXML TextField txtTPM4;
	
	// -- Hamate
	@FXML TextField txtH1;
	@FXML TextField txtH2;
	@FXML TextField txtH3;
	@FXML TextField txtH4;
	
	// -- Proximal Phalanx III
	@FXML TextField txtPPIII1;
	// @FXML TextField txtPPIII2; Not needed?
	@FXML TextField txtPPIII2EW;
	@FXML TextField txtPPIII2MW;
	@FXML TextField txtPPIII3;
	@FXML TextField txtPPIII4;
	@FXML TextField txtPPIII5;
	@FXML TextField txtPPIII6;
	
	// -- Middle Phalanx V
	@FXML TextField txtMPV1;
	// @FXML TextField txtMPV2; Not needed?
	@FXML TextField txtMPVEW;
	@FXML TextField txtMPVMW;
	@FXML TextField txtMPV3;
	@FXML TextField txtMPV4;
	@FXML TextField txtMPV5;
	
	// -- Metacarpal III
	@FXML TextField txtMETIII1;
	//@FXML TextField txtMETIII2; Not needed?
	@FXML TextField txtMETIII2EW;
	@FXML TextField txtMETIII2MW;
	@FXML TextField txtMETIII3;
	@FXML TextField txtMETIII4;
	@FXML TextField txtMETIII5;
	
	// -- Proximal Phalanx V
	@FXML TextField txtPPV1;
	@FXML TextField txtPPV2EW;
	@FXML TextField txtPPV2MW;
	@FXML TextField txtPPV3;
	@FXML TextField txtPPV4;
	@FXML TextField txtPPV5;
	
	// -- Distal Phalanx I
	@FXML TextField txtDPI2EW;
	@FXML TextField txtDPI2MW;
	@FXML TextField txtDPI4;
	
	// -- Metacarpal V
	@FXML TextField txtMCV1;
	@FXML TextField txtMCV2EW;
	@FXML TextField txtMCV2MW;
	@FXML TextField txtMCV3;
	@FXML TextField txtMCV4;
	@FXML TextField txtMCV5;
	@FXML TextField txtMCV6;
	
	// -- Middle Phalanx III
	@FXML TextField txtMPIII1;
	@FXML TextField txtMPIII2EW;
	@FXML TextField txtMPIII2MW;
	@FXML TextField txtMPIII3;
	@FXML TextField txtMPIII4;
	@FXML TextField txtMPIII5;
	
	// -- Distal Phalanx III
	@FXML TextField txtDPIII1;
	@FXML TextField txtDPIII2EW;
	@FXML TextField txtDPIII2MW;
	@FXML TextField txtDPIII3;
	@FXML TextField txtDPIII4;
	
	// -- Proximal Phalanx I
	@FXML TextField txtPPI1;
	@FXML TextField txtPPI2EW;
	@FXML TextField txtPPI2MW;
	@FXML TextField txtPPI3;
	@FXML TextField txtPPI4;
	@FXML TextField txtPPI5;
	@FXML TextField txtPPI6;
	
	// -- Distal Phalanx V
	@FXML TextField txtDPV1;
	@FXML TextField txtDPV2EW;
	@FXML TextField txtDPV2MW;
	@FXML TextField txtDPV3;
	@FXML TextField txtDPV4;
	
	
	// -- Used to limit the input within the range of numbers or an ".";
	@FXML
	private void validateInputCharacter( KeyEvent ev ) {
		try{
			boolean bShouldConsume = false;
			TextField txtField = (TextField) ev.getSource();
			
			// -- If the key typed is not contained in this string, we want to ignore it.
			if( !".1234567890".contains(ev.getCharacter()) )
				bShouldConsume = true;
			
			// -- Make sure that the field is not null, safety check.
			if( txtField.getText() != null ) {
				// -- Check if we already have a "." in the text field.
				if( ev.getCharacter().contains(".") && txtField.getText().contains(".") )
					bShouldConsume = true;
			}
			
			if( bShouldConsume )
				ev.consume();
			
		}catch( Exception e ){ } // -- Do nothing.
	}
	
	
	public Scene getScene() {
		return PatientDataFormScene;
	}
	
	public static PatientDataFormController getInstance() {
		
		if( instance == null ) {
			
			FXMLLoader loader = new FXMLLoader( SkeletalCalculator.class.getResource("PatientDataForm.fxml" ) );
			
			Parent titlePane = null;
			
			try {
				titlePane = loader.load();
			} catch ( Exception e ) {
				e.printStackTrace();
			}
			
			PatientDataFormScene = new Scene( titlePane );
			
			instance = loader.getController();
		}
		
		return instance;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		cmbGender.setItems(genderList);
	}
}
