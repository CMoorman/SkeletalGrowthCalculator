package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import main.SkeletalCalculator;
import main.SkeletalMaturityMethod;

public class FELSDataFormController extends SkeletalCalculator implements Initializable {

	private static Scene PatientDataFormScene = null;
	private static FELSDataFormController instance = null;
	private ObservableList<String> genderList = FXCollections.observableArrayList("Male", "Female");
	private SkeletalMaturityMethod felsMethod;
	private final String INDICATOR_FILE_PATH = "FELS_Indicators.csv";
	
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
	@FXML TextField txtR1;						// 0
	@FXML TextField txtR2EW;					// 1
	@FXML TextField txtR2MW;					// 2
	@FXML TextField txtR3;						// 3
	@FXML TextField txtR4;						// 4
	@FXML TextField txtR5;						// 5
	@FXML TextField txtR6;						// 6
	@FXML TextField txtR7;						// 7
	@FXML TextField txtR8;						// 8
	
	// -- Triquetral
	@FXML TextField txtTRI1;					// 9
	@FXML TextField txtTRI2;					// 10
	@FXML TextField txtTRI3;					// 11
	@FXML TextField txtTRI4;					// 12
	
	// -- Trapezoid
	@FXML TextField txtTPD1;					// 13
	@FXML TextField txtTPD2;					// 14
	@FXML TextField txtTPD3;					// 15
	@FXML TextField txtTPD4;					// 16
	@FXML TextField txtTPD5;					// 17
	@FXML TextField txtTPD6;					// 18
	@FXML TextField txtTPD7;					// 19
	
	// -- PISIForm
	@FXML TextField txtP1;						// 20
	
	// -- Lunate
	@FXML TextField txtL1;						// 21
	@FXML TextField txtL2;						// 22
	
	// -- Adductor Sesamoid
	@FXML TextField txtAS1;						// 23
	
	// -- Ulna
	@FXML TextField txtU1;						// 24		
	@FXML TextField txtU2EW;					// 25
	@FXML TextField txtU2MW;					// 26
	@FXML TextField txtU3;						// 27
	
	// -- Scaphoid
	@FXML TextField txtS1;						// 28
	@FXML TextField txtS2;						// 29
	@FXML TextField txtS3;						// 30
	
	// -- Metacarpal I
	@FXML TextField txtMETI1;					// 31
	@FXML TextField txtMETI2EW;					// 32
	@FXML TextField txtMETI2MW;					// 33
	@FXML TextField txtMETI3;					// 34
	@FXML TextField txtMETI4;					// 35
	@FXML TextField txtMETI5;					// 36
	@FXML TextField txtMETI6;					// 37
	@FXML TextField txtMETI7;					// 38
	
	// -- Capitate
	@FXML TextField txtC1;						// 39
	@FXML TextField txtC2;						// 40
	@FXML TextField txtC3;						// 41
	@FXML TextField txtC4;						// 42
	
	// -- Trapezium
	@FXML TextField txtTPM1;					// 43
	@FXML TextField txtTPM2;					// 44
	@FXML TextField txtTPM3;					// 45
	@FXML TextField txtTPM4;					// 46
	
	// -- Hamate
	@FXML TextField txtH1;						// 47
	@FXML TextField txtH2;						// 48
	@FXML TextField txtH3;						// 49
	@FXML TextField txtH4;						// 50
	
	// -- Proximal Phalanx III
	@FXML TextField txtPPIII1;					// 51
	@FXML TextField txtPPIII2EW;				// 52
	@FXML TextField txtPPIII2MW;				// 53
	@FXML TextField txtPPIII3;					// 54
	@FXML TextField txtPPIII4;					// 55
	@FXML TextField txtPPIII5;					// 56
	@FXML TextField txtPPIII6;					// 57
	
	// -- Middle Phalanx V
	@FXML TextField txtMPV1;					// 58
	@FXML TextField txtMPVEW;					// 59
	@FXML TextField txtMPVMW;					// 60
	@FXML TextField txtMPV3;					// 61
	@FXML TextField txtMPV4;					// 62
	@FXML TextField txtMPV5;					// 63
	
	// -- Metacarpal III
	@FXML TextField txtMETIII1;					// 64
	@FXML TextField txtMETIII2EW;				// 65
	@FXML TextField txtMETIII2MW;				// 66
	@FXML TextField txtMETIII3;					// 67
	@FXML TextField txtMETIII4;					// 68
	@FXML TextField txtMETIII5;					// 69
	
	// -- Proximal Phalanx V
	@FXML TextField txtPPV1;					// 70
	@FXML TextField txtPPV2EW;					// 71
	@FXML TextField txtPPV2MW;					// 72
	@FXML TextField txtPPV3;					// 73
	@FXML TextField txtPPV4;					// 74
	@FXML TextField txtPPV5;					// 75
	
	// -- Distal Phalanx I
	@FXML TextField txtDPI2EW;					// 76
	@FXML TextField txtDPI2MW;					// 77
	@FXML TextField txtDPI4;					// 78
	
	// -- Metacarpal V
	@FXML TextField txtMCV1;					// 79
	@FXML TextField txtMCV2EW;					// 80
	@FXML TextField txtMCV2MW;					// 81
	@FXML TextField txtMCV3;					// 82
	@FXML TextField txtMCV4;					// 83
	@FXML TextField txtMCV5;					// 84
	@FXML TextField txtMCV6;					// 85
	
	// -- Middle Phalanx III
	@FXML TextField txtMPIII1;					// 86
	@FXML TextField txtMPIII2EW;				// 87
	@FXML TextField txtMPIII2MW;				// 88
	@FXML TextField txtMPIII3;					// 89
	@FXML TextField txtMPIII4;					// 90
	@FXML TextField txtMPIII5;					// 91
	
	// -- Distal Phalanx III
	@FXML TextField txtDPIII1;					// 92
	@FXML TextField txtDPIII2EW;				// 93
	@FXML TextField txtDPIII2MW;				// 94
	@FXML TextField txtDPIII3;					// 95
	@FXML TextField txtDPIII4;					// 96
	
	// -- Proximal Phalanx I
	@FXML TextField txtPPI1;					// 97
	@FXML TextField txtPPI2EW;					// 98
	@FXML TextField txtPPI2MW;					// 99
	@FXML TextField txtPPI3;					// 100
	@FXML TextField txtPPI4;					// 101
	@FXML TextField txtPPI5;					// 102
	@FXML TextField txtPPI6;					// 103
	
	// -- Distal Phalanx V
	@FXML TextField txtDPV1;					// 104
	@FXML TextField txtDPV2EW;					// 105
	@FXML TextField txtDPV2MW;					// 106
	@FXML TextField txtDPV3;					// 107
	@FXML TextField txtDPV4;					// 108
	
	@FXML Button btnSubmit;
	
	private String s_FormHeaderData = "";
	private String s_MeasurementData = "";
	private ArrayList<TextField> m_ErrorIDList = new ArrayList<>();
	
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
	
	private void AddHeaderInfoToString( Object inputObject ) {
		// TODO: Do we need some sort of validation per header input text field??
		String sCurrentInput = "";
		
		if( inputObject.getClass() == TextField.class ){
			sCurrentInput = ((TextField)inputObject).getText().trim();
		}
		else if( inputObject.getClass() == ComboBox.class ) {
			// -- We only have one combobox.
			sCurrentInput = ((ComboBox)inputObject).getSelectionModel().getSelectedItem().toString();
		}
		
		// TODO: What about the combo box?
		if( sCurrentInput.isEmpty() && inputObject.getClass() == TextField.class )
			m_ErrorIDList.add( (TextField)inputObject );
		
		s_FormHeaderData += sCurrentInput;
	}
	
	private boolean LoadHeaderInput() {
		boolean rval = true;
		
		Object txtCurrentTextField = null;
		
		txtCurrentTextField = txtStudy; AddHeaderInfoToString( txtCurrentTextField );
		txtCurrentTextField = txtID; AddHeaderInfoToString( txtCurrentTextField );
		txtCurrentTextField = txtChronAge; AddHeaderInfoToString( txtCurrentTextField );
		txtCurrentTextField = cmbGender; AddHeaderInfoToString( txtCurrentTextField );
		txtCurrentTextField = txtAssessorNum; AddHeaderInfoToString( txtCurrentTextField );
		txtCurrentTextField = txtAssessmentNum; AddHeaderInfoToString( txtCurrentTextField );
		txtCurrentTextField = txtBirthDate; AddHeaderInfoToString( txtCurrentTextField );
		txtCurrentTextField = txtXrayDate; AddHeaderInfoToString( txtCurrentTextField );
		txtCurrentTextField = txtAsmDate; AddHeaderInfoToString( txtCurrentTextField );
		txtCurrentTextField = txtSA; AddHeaderInfoToString( txtCurrentTextField );
		txtCurrentTextField = txtSEE; AddHeaderInfoToString( txtCurrentTextField );
		
		if( m_ErrorIDList.size() > 0 ){
			// -- We found errors.  Uh oh.
			rval = false;
		}
		
		return rval;
	}
	
	private void AddMeasurementInputToString( TextField  inputTextField ) {
		String sCurrentInput = "";
		sCurrentInput = inputTextField.getText().trim();
		// -- Check if editable AND the value is a double.
		if( inputTextField.isEditable() && IsDoubleValue( sCurrentInput ) ) s_MeasurementData += sCurrentInput + ",";
		else { 
			// -- Make sure that if we had a problem, we are looking at an editable field.
			if( inputTextField.isEditable() ) 
				m_ErrorIDList.add(inputTextField);
		}
	}
	
	private boolean LoadMeasurementInput() {	
		
		boolean rval = true;
		TextField txtCurrentTextField = null;
		
		// -- Make sure to have clean measurement data before we begin.
		s_MeasurementData = "";
		
		// -- Radius
		AddMeasurementInputToString( txtR1 );  
		AddMeasurementInputToString( txtR2EW );  
		AddMeasurementInputToString( txtR2MW );  
		AddMeasurementInputToString( txtR3 );  
		AddMeasurementInputToString( txtR4 );  
		AddMeasurementInputToString( txtR5 );  
		AddMeasurementInputToString( txtR6 );  
		AddMeasurementInputToString( txtR7 );  
		AddMeasurementInputToString( txtR8 );  
		
		// -- Triquetral
		AddMeasurementInputToString( txtTRI1 );  
		AddMeasurementInputToString( txtTRI2 );  
		AddMeasurementInputToString( txtTRI3 );  
		AddMeasurementInputToString( txtTRI4 );  
		
		// -- Trapezoid
		AddMeasurementInputToString( txtTPD1 );  
		AddMeasurementInputToString( txtTPD2 );  
		AddMeasurementInputToString( txtTPD3 );  
		AddMeasurementInputToString( txtTPD4 );  
		AddMeasurementInputToString( txtTPD5 );  
		AddMeasurementInputToString( txtTPD6 );  
		AddMeasurementInputToString( txtTPD7 );  
		
		// -- PISIForm
		AddMeasurementInputToString( txtP1 );  
		
		// -- Lunate
		AddMeasurementInputToString( txtL1 );  
		AddMeasurementInputToString( txtL2 );  
		
		// -- Adductor Sesamoid
		AddMeasurementInputToString( txtAS1 );  
		
		// -- Ulna
		AddMeasurementInputToString( txtU1 );  
		AddMeasurementInputToString( txtU2EW );  
		AddMeasurementInputToString( txtU2MW );  
		AddMeasurementInputToString( txtU3 );  
		
		// -- Scaphoid
		AddMeasurementInputToString( txtS1 );  
		AddMeasurementInputToString( txtS2 );  
		AddMeasurementInputToString( txtS3 );  
		
		// -- Metacarpal I
		AddMeasurementInputToString( txtMETI1 );  
		AddMeasurementInputToString( txtMETI2EW );  
		AddMeasurementInputToString( txtMETI2MW );  
		AddMeasurementInputToString( txtMETI3 );  
		AddMeasurementInputToString( txtMETI4 );  
		AddMeasurementInputToString( txtMETI5 );  
		AddMeasurementInputToString( txtMETI6 );  
		AddMeasurementInputToString( txtMETI7 );  
		
		// -- Capitate
		AddMeasurementInputToString( txtC1 );  
		AddMeasurementInputToString( txtC2 );  
		AddMeasurementInputToString( txtC3 );  
		AddMeasurementInputToString( txtC4 );  
		
		// -- Trapezium
		AddMeasurementInputToString( txtTPM1 );  
		AddMeasurementInputToString( txtTPM2 );  
		AddMeasurementInputToString( txtTPM3 );  
		AddMeasurementInputToString( txtTPM4 );  
		
		// -- Hamate
		AddMeasurementInputToString( txtH1 );  
		AddMeasurementInputToString( txtH2 );  
		AddMeasurementInputToString( txtH3 );  
		AddMeasurementInputToString( txtH4 );  
		
		// -- Proximal Phalanx III
		AddMeasurementInputToString( txtPPIII1 );  
		AddMeasurementInputToString( txtPPIII2EW );  
		AddMeasurementInputToString( txtPPIII2MW );  
		AddMeasurementInputToString( txtPPIII3 );  
		AddMeasurementInputToString( txtPPIII4 );  
		AddMeasurementInputToString( txtPPIII5 );  
		AddMeasurementInputToString( txtPPIII6 );  
		
		// -- Middle Phalanx V
		AddMeasurementInputToString( txtMPV1 );  
		AddMeasurementInputToString( txtMPVEW );  
		AddMeasurementInputToString( txtMPVMW );  
		AddMeasurementInputToString( txtMPV3 );  
		AddMeasurementInputToString( txtMPV4 );  
		AddMeasurementInputToString( txtMPV5 );  
		
		// -- Metacarpal III
		AddMeasurementInputToString( txtMETIII1 );  
		AddMeasurementInputToString( txtMETIII2EW );  
		AddMeasurementInputToString( txtMETIII2MW );  
		AddMeasurementInputToString( txtMETIII3 );  
		AddMeasurementInputToString( txtMETIII4 );  
		AddMeasurementInputToString( txtMETIII5 );  
		
		// -- Proximal Phalanx V
		AddMeasurementInputToString( txtPPV1 );  
		AddMeasurementInputToString( txtPPV2EW );  
		AddMeasurementInputToString( txtPPV2MW );  
		AddMeasurementInputToString( txtPPV3 );  
		AddMeasurementInputToString( txtPPV4 );  
		AddMeasurementInputToString( txtPPV5 );  
		
		// -- Distal Phalanx I
		AddMeasurementInputToString( txtDPI2EW );  
		AddMeasurementInputToString( txtDPI2MW );  
		AddMeasurementInputToString( txtDPI4 );  
		
		// -- Metacarpal V
		AddMeasurementInputToString( txtMCV1 );  
		AddMeasurementInputToString( txtMCV2EW );  
		AddMeasurementInputToString( txtMCV2MW );  
		AddMeasurementInputToString( txtMCV3 );  
		AddMeasurementInputToString( txtMCV4 );  
		AddMeasurementInputToString( txtMCV5 );  
		AddMeasurementInputToString( txtMCV6 );  
		
		// -- Middle Phalanx III
		AddMeasurementInputToString( txtMPIII1 );  
		AddMeasurementInputToString( txtMPIII2EW );  
		AddMeasurementInputToString( txtMPIII2MW );  
		AddMeasurementInputToString( txtMPIII3 );  
		AddMeasurementInputToString( txtMPIII4 );  
		AddMeasurementInputToString( txtMPIII5 );  
		
		// -- Distal Phalanx III
		AddMeasurementInputToString( txtDPIII1 );  
		AddMeasurementInputToString( txtDPIII2EW );  
		AddMeasurementInputToString( txtDPIII2MW );  
		AddMeasurementInputToString( txtDPIII3 );  
		AddMeasurementInputToString( txtDPIII4 );  
		
		// -- Proximal Phalanx I
		AddMeasurementInputToString( txtPPI1 );  
		AddMeasurementInputToString( txtPPI2EW );  
		AddMeasurementInputToString( txtPPI2MW );  
		AddMeasurementInputToString( txtPPI3 );  
		AddMeasurementInputToString( txtPPI4 );  
		AddMeasurementInputToString( txtPPI5 );  
		AddMeasurementInputToString( txtPPI6 );  
		
		// -- Distal Phalanx V
		AddMeasurementInputToString( txtDPV1 );  
		AddMeasurementInputToString( txtDPV2EW );  
		AddMeasurementInputToString( txtDPV2MW );  
		AddMeasurementInputToString( txtDPV3 );  
		AddMeasurementInputToString( txtDPV4 );
		
		if( m_ErrorIDList.size() > 0 ){
			// -- We found errors.  Uh oh.
			rval = false;
			s_MeasurementData = "";
		}
		return rval;
	}
	
	private boolean IsDoubleValue( String dInput ) {
		try {
			Double.parseDouble(dInput);
		}catch (Exception e ){
			return false;
		}
		return true;
	}
	
	private void SaveAllData() {
		
	}
	
	private void ButtonClicked( ActionEvent E ) {
		
		if( E.getSource() == btnSubmit ) {
			if( LoadMeasurementInput() && LoadHeaderInput() ) {
				SaveAllData();
			}
			else{
				// -- Loading the input Failed, why?  TODO: Handle validation errors.  Missing fields, etc...?
				
				
				// -- We displayed our errors.  Clear it out for the next round.
				m_ErrorIDList = new ArrayList<>();
				s_MeasurementData = "";
			}
		}
	}
	
	public Scene getScene() {
		return PatientDataFormScene;
	}
	
	public static FELSDataFormController getInstance() {
		
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
		felsMethod = new SkeletalMaturityMethod("FELS", INDICATOR_FILE_PATH);
		felsMethod.load();
		
		btnSubmit.setOnAction( e -> ButtonClicked(e) );
		
	}
}
