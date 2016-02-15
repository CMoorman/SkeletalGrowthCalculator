package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import main.SkeletalCalculator;

public class PatientDataFormController extends SkeletalCalculator implements Initializable {

	private static Scene PatientDataFormScene = null;
	private static PatientDataFormController instance = null;
	
	// -- Form FXML -- Patient Info ************
	@FXML TextField txtStudy;
	@FXML TextField txtID;
	@FXML TextField txtChronAge;
	@FXML SplitMenuButton mnuSex;
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
		
	}
}
