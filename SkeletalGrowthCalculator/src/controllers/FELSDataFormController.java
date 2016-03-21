package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import main.Indicator;
import main.SkeletalCalculator;
import main.SkeletalMaturityMethod;

public class FELSDataFormController extends SkeletalCalculator implements Initializable {

	private static Scene PatientDataFormScene = null;
	private static FELSDataFormController instance = null;
	private ObservableList<String> genderList = FXCollections.observableArrayList("Male", "Female");
	private final String maleColor = "#ADD8E6;";
	private final String femaleColor = "#FAAFBA;";
	private final String whiteColor = "#000000;";
	private final String fxBckgrndStyleConst = "-fx-background-color: ";
	private SkeletalMaturityMethod felsMethod;
	private static final String INDICATOR_FILE_PATH = "FELS_Indicators.csv";
	private static final String NOT_APPLICABLE = "N/A";
	private List<TextField> inputs = new ArrayList<TextField>();
	@FXML Pane paneMeasurementInputs;
	// -- Form FXML -- Patient Info ************
	@FXML TextField txtStudy;
	@FXML TextField txtID;
	@FXML TextField txtChronAge;
	@FXML ComboBox<String> cmbGender;
	@FXML TextField txtAssessorNum;
	@FXML TextField txtAssessmentNum;
	@FXML DatePicker birthDate;
	@FXML DatePicker xrayDate;
	@FXML DatePicker asmDate;
	@FXML TextField txtSA;
	@FXML TextField txtSEE;
	
	// -- Form FXML -- Measurements *************
	
	// -- Radius
	@FXML TextField R1;						// 0
	@FXML TextField R2EW;					// 1
	@FXML TextField R2MW;					// 2
	@FXML TextField R3;						// 3
	@FXML TextField R4;						// 4
	@FXML TextField R5;						// 5
	@FXML TextField R6;						// 6
	@FXML TextField R7;						// 7
	@FXML TextField R8;						// 8
	
	// -- Triquetral
	@FXML TextField TRI1;					// 9
	@FXML TextField TRI2;					// 10
	@FXML TextField TRI3;					// 11
	@FXML TextField TRI4;					// 12
	
	// -- Trapezoid
	@FXML TextField TPD1;					// 13
	@FXML TextField TPD2;					// 14
	@FXML TextField TPD3;					// 15
	@FXML TextField TPD4;					// 16
	@FXML TextField TPD5;					// 17
	@FXML TextField TPD6;					// 18
	@FXML TextField TPD7;					// 19
	
	// -- PISIForm
	@FXML TextField P1;						// 20
	
	// -- Lunate
	@FXML TextField L1;						// 21
	@FXML TextField L2;						// 22
	
	// -- Adductor Sesamoid
	@FXML TextField AS1;						// 23
	
	// -- Ulna
	@FXML TextField U1;						// 24		
	@FXML TextField U2EW;					// 25
	@FXML TextField U2MW;					// 26
	@FXML TextField U3;						// 27
	
	// -- Scaphoid
	@FXML TextField S1;						// 28
	@FXML TextField S2;						// 29
	@FXML TextField S3;						// 30
	
	// -- Metacarpal I
	@FXML TextField METI1;					// 31
	@FXML TextField METI2EW;					// 32
	@FXML TextField METI2MW;					// 33
	@FXML TextField METI3;					// 34
	@FXML TextField METI4;					// 35
	@FXML TextField METI5;					// 36
	@FXML TextField METI6;					// 37
	@FXML TextField METI7;					// 38
	
	// -- Capitate
	@FXML TextField C1;						// 39
	@FXML TextField C2;						// 40
	@FXML TextField C3;						// 41
	@FXML TextField C4;						// 42
	
	// -- Trapezium
	@FXML TextField TPM1;					// 43
	@FXML TextField TPM2;					// 44
	@FXML TextField TPM3;					// 45
	@FXML TextField TPM4;					// 46
	
	// -- Hamate
	@FXML TextField H1;						// 47
	@FXML TextField H2;						// 48
	@FXML TextField H3;						// 49
	@FXML TextField H4;						// 50
	
	// -- Proximal Phalanx III
	@FXML TextField PPIII1;					// 51
	@FXML TextField PPIII2EW;				// 52
	@FXML TextField PPIII2MW;				// 53
	@FXML TextField PPIII3;					// 54
	@FXML TextField PPIII4;					// 55
	@FXML TextField PPIII5;					// 56
	@FXML TextField PPIII6;					// 57
	
	// -- Middle Phalanx V
	@FXML TextField MPV1;					// 58
	@FXML TextField MPVEW;					// 59
	@FXML TextField MPVMW;					// 60
	@FXML TextField MPV3;					// 61
	@FXML TextField MPV4;					// 62
	@FXML TextField MPV5;					// 63
	
	// -- Metacarpal III
	@FXML TextField METIII1;					// 64
	@FXML TextField METIII2EW;				// 65
	@FXML TextField METIII2MW;				// 66
	@FXML TextField METIII3;					// 67
	@FXML TextField METIII4;					// 68
	@FXML TextField METIII5;					// 69
	
	// -- Proximal Phalanx V
	@FXML TextField PPV1;					// 70
	@FXML TextField PPV2EW;					// 71
	@FXML TextField PPV2MW;					// 72
	@FXML TextField PPV3;					// 73
	@FXML TextField PPV4;					// 74
	@FXML TextField PPV5;					// 75
	
	// -- Distal Phalanx I
	@FXML TextField DPI2EW;					// 76
	@FXML TextField DPI2MW;					// 77
	@FXML TextField DPI4;					// 78
	
	// -- Metacarpal V
	@FXML TextField MCV1;					// 79
	@FXML TextField MCV2EW;					// 80
	@FXML TextField MCV2MW;					// 81
	@FXML TextField MCV3;					// 82
	@FXML TextField MCV4;					// 83
	@FXML TextField MCV5;					// 84
	@FXML TextField MCV6;					// 85
	
	// -- Middle Phalanx III
	@FXML TextField MPIII1;					// 86
	@FXML TextField MPIII2EW;				// 87
	@FXML TextField MPIII2MW;				// 88
	@FXML TextField MPIII3;					// 89
	@FXML TextField MPIII4;					// 90
	@FXML TextField MPIII5;					// 91
	
	// -- Distal Phalanx III
	@FXML TextField DPIII1;					// 92
	@FXML TextField DPIII2EW;				// 93
	@FXML TextField DPIII2MW;				// 94
	@FXML TextField DPIII3;					// 95
	@FXML TextField DPIII4;					// 96
	
	// -- Proximal Phalanx I
	@FXML TextField PPI1;					// 97
	@FXML TextField PPI2EW;					// 98
	@FXML TextField PPI2MW;					// 99
	@FXML TextField PPI3;					// 100
	@FXML TextField PPI4;					// 101
	@FXML TextField PPI5;					// 102
	@FXML TextField PPI6;					// 103
	
	// -- Distal Phalanx V
	@FXML TextField DPV1;					// 104
	@FXML TextField DPV2EW;					// 105
	@FXML TextField DPV2MW;					// 106
	@FXML TextField DPV3;					// 107
	@FXML TextField DPV4;					// 108
	
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
		
		if( inputObject instanceof TextField ){
			sCurrentInput = ((TextField)inputObject).getText().trim();
		}
		else if( inputObject instanceof ComboBox ) {
			// -- We only have one combobox.
			sCurrentInput = ((ComboBox<?>)inputObject).getSelectionModel().getSelectedItem().toString();
		}
		
		// TODO: What about the combo box?
		if( sCurrentInput.isEmpty() && inputObject.getClass() == TextField.class )
			m_ErrorIDList.add( (TextField)inputObject );
		
		s_FormHeaderData += sCurrentInput;
	}
	
	private boolean LoadHeaderInput() {
		boolean rval = true;
		
		AddHeaderInfoToString( txtStudy );
		AddHeaderInfoToString( txtID );
		AddHeaderInfoToString( txtChronAge );
		AddHeaderInfoToString( cmbGender );
		AddHeaderInfoToString( txtAssessorNum );
		AddHeaderInfoToString( txtAssessmentNum );
		//AddHeaderInfoToString( txtBirthDate );
		//AddHeaderInfoToString( txtXrayDate );
		//AddHeaderInfoToString( txtAsmDate );
		AddHeaderInfoToString( txtSA );
		AddHeaderInfoToString( txtSEE );
		
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
		if( inputTextField.isEditable() && IsDoubleValue( sCurrentInput ) ){
			s_MeasurementData += sCurrentInput + ",";
			inputTextField.setStyle("-fx-background-color: white;");
		}else { 
			// -- Make sure that if we had a problem, we are looking at an editable field.
			if( inputTextField.isEditable() ) 
				inputTextField.setStyle("-fx-background-color: #FAFE8C;");
				m_ErrorIDList.add(inputTextField);
		}
	}
	
	private boolean LoadMeasurementInput() {	
		
		boolean rval = true;
		
		// -- Make sure to have clean measurement data before we begin.
		s_MeasurementData = "";
		for(TextField input : inputs){
			AddMeasurementInputToString(input);
		}
		
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
		}catch (NumberFormatException e ){
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
				m_ErrorIDList.clear();
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
		initializeInputList();
		addListeners();
		btnSubmit.setOnAction( e -> ButtonClicked(e) );
		
	}

	private void addListeners() {
		cmbGender.valueProperty().addListener(new ChangeListener<String>() {
	        @Override public void changed(ObservableValue<? extends String> ov, String old, String current) {
	        	if(current.equals("Male")){
	        		paneMeasurementInputs.setStyle(fxBckgrndStyleConst + maleColor);
	        	} else if(current.equals("Female")){
	        		paneMeasurementInputs.setStyle(fxBckgrndStyleConst + femaleColor);
	        	} else {
	        		paneMeasurementInputs.setStyle(fxBckgrndStyleConst + whiteColor);
	        	}
	        	enableDisableInputs();
	        } 
	    });
		
		for(TextField text : inputs ){
			text.setOnKeyTyped( e -> validateInputCharacter(e) );
			Indicator i = felsMethod.getIndicatorMap().get(text.getId());
			if(i != null){
				text.setUserData(i);
				final Tooltip tooltip = new Tooltip();
				tooltip.setText(i.getDescription());
				text.setTooltip(tooltip);
			}
		}
		txtChronAge.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> ov, String old, String current) {
				ageChanged(old, current);
			}

	    });
		
	}

	protected void enableDisableInputs() {
		String current = txtChronAge.getText();
		if(current == null || current.isEmpty()){
			return;
		}
		double age = -1.0;
		try{
			age = Double.parseDouble(current);
			if(age < 0){
				return;
			}
		} catch(NumberFormatException e){
			return;
		}
		enabledDisableInputs(age);
		
	}

	protected void ageChanged(String old, String current) {
		if(current == null || current.isEmpty()){
			enableAllInputs();
			return;
		}
		double age = -1.0;
		try{
			age = Double.parseDouble(current);
			if(age < 0){
				txtChronAge.setText(old);
				return;
			}
		} catch(NumberFormatException e){
			txtChronAge.setText(old);
			return;
		}
		enabledDisableInputs(age);
	}
	private void enableAllInputs() {
		for (TextField text : inputs) {
			text.setDisable(false);
			text.setEditable(true);
			text.setText("");
		}
		
	}

	private boolean isMale(String sex){
		boolean isMale = false;
		if(sex.equals("Male")){
			isMale = true;
		}
		return isMale;
	}
	
	private void enabledDisableInputs(double age){
		String sex = cmbGender.getValue();
		if(sex == null || sex.isEmpty()){
			return;
		}
		
		for(TextField text : inputs){
			if(text.getUserData() != null && text.getUserData() instanceof Indicator){
				Indicator i = (Indicator) text.getUserData();
				double startRange = 0.0;
				double endRange = 0.0;
				if(i != null){
					if(isMale(sex)){
						startRange = i.getMaleStartRange();
						endRange = i.getMaleEndRange();
					}else{
						startRange = i.getFemaleStartRange();
						endRange = i.getFemaleEndRange();
					}
					if (startRange <= age && age <= endRange){
						text.setDisable(false);
						text.setEditable(true);
						text.setText("");
					}else{
						text.setDisable(true);
						text.setEditable(false);
						text.setText(NOT_APPLICABLE);
					}
				}
			}
		}
	}
	private void initializeInputList() {
		for(Node child : paneMeasurementInputs.getChildren()){
			if(child instanceof TextField){
				inputs.add((TextField)child);
			}
		}
	}
}
