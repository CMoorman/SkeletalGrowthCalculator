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
		txtCurrentTextField = txtR1; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtR2EW; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtR2MW; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtR3; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtR4; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtR5; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtR6; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtR7; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtR8; AddMeasurementInputToString( txtCurrentTextField );
		
		// -- Triquetral
		txtCurrentTextField = txtTRI1; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtTRI2; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtTRI3; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtTRI4; AddMeasurementInputToString( txtCurrentTextField );
		
		// -- Trapezoid
		txtCurrentTextField = txtTPD1; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtTPD2; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtTPD3; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtTPD4; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtTPD5; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtTPD6; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtTPD7; AddMeasurementInputToString( txtCurrentTextField );
		
		// -- PISIForm
		txtCurrentTextField = txtP1; AddMeasurementInputToString( txtCurrentTextField );
		
		// -- Lunate
		txtCurrentTextField = txtL1; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtL2; AddMeasurementInputToString( txtCurrentTextField );
		
		// -- Adductor Sesamoid
		txtCurrentTextField = txtAS1; AddMeasurementInputToString( txtCurrentTextField );
		
		// -- Ulna
		txtCurrentTextField = txtU1; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtU2EW; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtU2MW; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtU3; AddMeasurementInputToString( txtCurrentTextField );
		
		// -- Scaphoid
		txtCurrentTextField = txtS1; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtS2; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtS3; AddMeasurementInputToString( txtCurrentTextField );
		
		// -- Metacarpal I
		txtCurrentTextField = txtMETI1; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtMETI2EW; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtMETI2MW; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtMETI3; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtMETI4; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtMETI5; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtMETI6; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtMETI7; AddMeasurementInputToString( txtCurrentTextField );
		
		// -- Capitate
		txtCurrentTextField = txtC1; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtC2; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtC3; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtC4; AddMeasurementInputToString( txtCurrentTextField );
		
		// -- Trapezium
		txtCurrentTextField = txtTPM1; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtTPM2; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtTPM3; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtTPM4; AddMeasurementInputToString( txtCurrentTextField );
		
		// -- Hamate
		txtCurrentTextField = txtH1; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtH2; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtH3; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtH4; AddMeasurementInputToString( txtCurrentTextField );
		
		// -- Proximal Phalanx III
		txtCurrentTextField = txtPPIII1; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtPPIII2EW; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtPPIII2MW; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtPPIII3; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtPPIII4; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtPPIII5; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtPPIII6; AddMeasurementInputToString( txtCurrentTextField );
		
		// -- Middle Phalanx V
		txtCurrentTextField = txtMPV1; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtMPVEW; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtMPVMW; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtMPV3; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtMPV4; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtMPV5; AddMeasurementInputToString( txtCurrentTextField );
		
		// -- Metacarpal III
		txtCurrentTextField = txtMETIII1; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtMETIII2EW; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtMETIII2MW; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtMETIII3; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtMETIII4; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtMETIII5; AddMeasurementInputToString( txtCurrentTextField );
		
		// -- Proximal Phalanx V
		txtCurrentTextField = txtPPV1; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtPPV2EW; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtPPV2MW; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtPPV3; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtPPV4; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtPPV5; AddMeasurementInputToString( txtCurrentTextField );
		
		// -- Distal Phalanx I
		txtCurrentTextField = txtDPI2EW; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtDPI2MW; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtDPI4; AddMeasurementInputToString( txtCurrentTextField );
		
		// -- Metacarpal V
		txtCurrentTextField = txtMCV1; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtMCV2EW; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtMCV2MW; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtMCV3; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtMCV4; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtMCV5; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtMCV6; AddMeasurementInputToString( txtCurrentTextField );
		
		// -- Middle Phalanx III
		txtCurrentTextField = txtMPIII1; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtMPIII2EW; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtMPIII2MW; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtMPIII3; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtMPIII4; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtMPIII5; AddMeasurementInputToString( txtCurrentTextField );
		
		// -- Distal Phalanx III
		txtCurrentTextField = txtDPIII1; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtDPIII2EW; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtDPIII2MW; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtDPIII3; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtDPIII4; AddMeasurementInputToString( txtCurrentTextField );
		
		// -- Proximal Phalanx I
		txtCurrentTextField = txtPPI1; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtPPI2EW; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtPPI2MW; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtPPI3; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtPPI4; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtPPI5; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtPPI6; AddMeasurementInputToString( txtCurrentTextField );
		
		// -- Distal Phalanx V
		txtCurrentTextField = txtDPV1; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtDPV2EW; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtDPV2MW; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtDPV3; AddMeasurementInputToString( txtCurrentTextField );
		txtCurrentTextField = txtDPV4; AddMeasurementInputToString( txtCurrentTextField );
		
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
