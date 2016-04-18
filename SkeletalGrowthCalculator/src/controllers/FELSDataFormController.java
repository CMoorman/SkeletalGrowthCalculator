/*
 * 
 */
package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import main.FELSMethod;
import main.Indicator;
import main.SkeletalCalculator;
import main.SkeletalMaturityMethod;
import statistics.SkeletalEstimation;

public class FELSDataFormController extends SkeletalCalculator implements Initializable {

	private static Scene PatientDataFormScene = null;
	private static FELSDataFormController instance = null;
	private static SkeletalEstimation fels = new FELSMethod();
	private ObservableList<String> genderList = FXCollections.observableArrayList("Male", "Female");
	private static final String MALE_COLOR = "#ADD8E6;";
	private static final String FEMALE_COLOR = "#FAAFBA;";
	private static final String WHITE_COLOR = "#000000;";
	private static final String CSS_BACKGROUND = "-fx-background-color: ";
	private SkeletalMaturityMethod felsMethod;
	private static final String INDICATOR_FILE_PATH = "FELS_Indicators.csv";
	private static final String NOT_APPLICABLE = "N/A";
	private List<TextField> inputs = new ArrayList<TextField>();
	@FXML
	Pane paneMeasurementInputs;
	@FXML
	TabPane tabPane;
	public TabPane getTabPane(){
		return tabPane;
	}
	// -- Form FXML -- Patient Info ************
	@FXML
	TextField txtStudy;
	@FXML
	TextField txtID;
	@FXML
	TextField txtChronAge;
	@FXML
	ComboBox<String> cmbGender;
	@FXML
	TextField txtAssessorNum;
	@FXML
	TextField txtAssessmentNum;
	@FXML
	DatePicker birthDate;
	@FXML
	DatePicker xrayDate;
	@FXML
	DatePicker asmDate;
	@FXML
	TextField txtSA;
	@FXML
	TextField txtSEE;
	
	// -- Form FXML -- Measurements *************

	// -- Radius
	@FXML
	TextField R1; // 0
	@FXML
	TextField R2EW; // 1
	@FXML
	TextField R2MW; // 2
	@FXML
	TextField R3; // 3
	@FXML
	TextField R4; // 4
	@FXML
	TextField R5; // 5
	@FXML
	TextField R6; // 6
	@FXML
	TextField R7; // 7
	@FXML
	TextField R8; // 8

	// -- Triquetral
	@FXML
	TextField TRI1; // 9
	@FXML
	TextField TRI2; // 10
	@FXML
	TextField TRI3; // 11
	@FXML
	TextField TRI4; // 12

	// -- Trapezoid
	@FXML
	TextField TPD1; // 13
	@FXML
	TextField TPD2; // 14
	@FXML
	TextField TPD3; // 15
	@FXML
	TextField TPD4; // 16
	@FXML
	TextField TPD5; // 17
	@FXML
	TextField TPD6; // 18
	@FXML
	TextField TPD7; // 19

	// -- PISIForm
	@FXML
	TextField P1; // 20

	// -- Lunate
	@FXML
	TextField L1; // 21
	@FXML
	TextField L2; // 22

	// -- Adductor Sesamoid
	@FXML
	TextField AS1; // 23

	// -- Ulna
	@FXML
	TextField U1; // 24
	@FXML
	TextField U2EW; // 25
	@FXML
	TextField U2MW; // 26
	@FXML
	TextField U3; // 27

	// -- Scaphoid
	@FXML
	TextField S1; // 28
	@FXML
	TextField S2; // 29
	@FXML
	TextField S3; // 30

	// -- Metacarpal I
	@FXML
	TextField METI1; // 31
	@FXML
	TextField METI2EW; // 32
	@FXML
	TextField METI2MW; // 33
	@FXML
	TextField METI3; // 34
	@FXML
	TextField METI4; // 35
	@FXML
	TextField METI5; // 36
	@FXML
	TextField METI6; // 37
	@FXML
	TextField METI7; // 38

	// -- Capitate
	@FXML
	TextField C1; // 39
	@FXML
	TextField C2; // 40
	@FXML
	TextField C3; // 41
	@FXML
	TextField C4; // 42

	// -- Trapezium
	@FXML
	TextField TPM1; // 43
	@FXML
	TextField TPM2; // 44
	@FXML
	TextField TPM3; // 45
	@FXML
	TextField TPM4; // 46

	// -- Hamate
	@FXML
	TextField H1; // 47
	@FXML
	TextField H2; // 48
	@FXML
	TextField H3; // 49
	@FXML
	TextField H4; // 50

	// -- Proximal Phalanx III
	@FXML
	TextField PPIII1; // 51
	@FXML
	TextField PPIII2EW; // 52
	@FXML
	TextField PPIII2MW; // 53
	@FXML
	TextField PPIII3; // 54
	@FXML
	TextField PPIII4; // 55
	@FXML
	TextField PPIII5; // 56
	@FXML
	TextField PPIII6; // 57

	// -- Middle Phalanx V
	@FXML
	TextField MPV1; // 58
	@FXML
	TextField MPV2EW; // 59
	@FXML
	TextField MPV2MW; // 60
	@FXML
	TextField MPV3; // 61
	@FXML
	TextField MPV4; // 62
	@FXML
	TextField MPV5; // 63

	// -- Metacarpal III
	@FXML
	TextField METIII1; // 64
	@FXML
	TextField METIII2EW; // 65
	@FXML
	TextField METIII2MW; // 66
	@FXML
	TextField METIII3; // 67
	@FXML
	TextField METIII4; // 68
	@FXML
	TextField METIII5; // 69

	// -- Proximal Phalanx V
	@FXML
	TextField PPV1; // 70
	@FXML
	TextField PPV2EW; // 71
	@FXML
	TextField PPV2MW; // 72
	@FXML
	TextField PPV3; // 73
	@FXML
	TextField PPV4; // 74
	@FXML
	TextField PPV5; // 75

	// -- Distal Phalanx I
	@FXML
	TextField DPI2EW; // 76
	@FXML
	TextField DPI2MW; // 77
	@FXML
	TextField DPI4; // 78

	// -- Metacarpal V
	@FXML
	TextField METV1; // 79
	@FXML
	TextField METV2EW; // 80
	@FXML
	TextField METV2MW; // 81
	@FXML
	TextField METV3; // 82
	@FXML
	TextField METV4; // 83
	@FXML
	TextField METV5; // 84
	@FXML
	TextField METV6; // 85

	// -- Middle Phalanx III
	@FXML
	TextField MPIII1; // 86
	@FXML
	TextField MPIII2EW; // 87
	@FXML
	TextField MPIII2MW; // 88
	@FXML
	TextField MPIII3; // 89
	@FXML
	TextField MPIII4; // 90
	@FXML
	TextField MPIII5; // 91

	// -- Distal Phalanx III
	@FXML
	TextField DPIII1; // 92
	@FXML
	TextField DPIII2EW; // 93
	@FXML
	TextField DPIII2MW; // 94
	@FXML
	TextField DPIII3; // 95
	@FXML
	TextField DPIII4; // 96

	// -- Proximal Phalanx I
	@FXML
	TextField PPI1; // 97
	@FXML
	TextField PPI2EW; // 98
	@FXML
	TextField PPI2MW; // 99
	@FXML
	TextField PPI3; // 100
	@FXML
	TextField PPI4; // 101
	@FXML
	TextField PPI5; // 102
	@FXML
	TextField PPI6; // 103

	// -- Distal Phalanx V
	@FXML
	TextField DPV1; // 104
	@FXML
	TextField DPV2EW; // 105
	@FXML
	TextField DPV2MW; // 106
	@FXML
	TextField DPV3; // 107
	@FXML
	TextField DPV4; // 108

	@FXML
	Button btnSubmit;
	@FXML
	Button btnGoBack;
	@FXML
	Button btnClear;
	private String s_FormHeaderData = "";
	private String s_MeasurementData = "";
	private ArrayList<TextField> m_ErrorIDList = new ArrayList<>();

	// -- Used to limit the input within the range of numbers or an ".";
	@FXML
	private void validateInputCharacter(KeyEvent ev) {
		try {
			boolean bShouldConsume = false;
			TextField txtField = (TextField) ev.getSource();

			// -- If the key typed is not contained in this string, we want to
			// ignore it.
			if (!".1234567890".contains(ev.getCharacter()))
				bShouldConsume = true;

			// -- Make sure that the field is not null, safety check.
			if (txtField.getText() != null) {
				// -- Check if we already have a "." in the text field.
				if (ev.getCharacter().contains(".") && txtField.getText().contains("."))
					bShouldConsume = true;
			}

			if (bShouldConsume)
				ev.consume();

		} catch (Exception e) {
		} // -- Do nothing.
	}

	private void addHeaderInfoToString(Object inputObject) {
		// TODO: Do we need some sort of validation per header input text
		// field??
		String sCurrentInput = "";

		if (inputObject instanceof TextField) {
			sCurrentInput = ((TextField) inputObject).getText().trim();
		} else if (inputObject instanceof ComboBox) {
			// -- We only have one combobox.
			sCurrentInput = ((ComboBox<?>) inputObject).getSelectionModel().getSelectedItem().toString();
		}

		// TODO: What about the combo box?
		if (sCurrentInput.isEmpty() && inputObject.getClass() == TextField.class)
			m_ErrorIDList.add((TextField) inputObject);

		s_FormHeaderData += sCurrentInput;
	}

	private boolean loadHeaderInput() {
		boolean rval = true;

		addHeaderInfoToString(txtStudy);
		addHeaderInfoToString(txtID);
		addHeaderInfoToString(txtChronAge);
		addHeaderInfoToString(cmbGender);
		addHeaderInfoToString(txtAssessorNum);
		addHeaderInfoToString(txtAssessmentNum);
		// AddHeaderInfoToString( txtBirthDate );
		// AddHeaderInfoToString( txtXrayDate );
		// AddHeaderInfoToString( txtAsmDate );

		if (m_ErrorIDList.size() > 0) {
			// -- We found errors. Uh oh.
			showErrorAlert();
			rval = false;
		}

		return rval;
	}

	private void addMeasurementInputToString(TextField inputTextField) {
		String sCurrentInput = "";
		sCurrentInput = inputTextField.getText().trim();
		// -- Check if editable AND the value is a double.
		try{
		if (inputTextField.isEditable() && isDoubleValue(sCurrentInput)) {
			Indicator currentIndicator = felsMethod.getIndicatorMap().get(inputTextField.getId());
			if(currentIndicator == null){
				System.out.println("Null indicator in map " + inputTextField.getId());
				return;
			}
			int maxVal = currentIndicator.getMaximumValue();
			if(Double.parseDouble( sCurrentInput ) <= maxVal) {
				s_MeasurementData += sCurrentInput + ",";
				inputTextField.setStyle("-fx-background-color: white;");
			}
			else {
				if(maxVal > 0){
					inputTextField.setStyle("-fx-background-color: #D178F0;");
					m_ErrorIDList.add(inputTextField);
				}
			}
			
		} else {
			// -- Make sure that if we had a problem, we are looking at an
			// editable field.
			if (inputTextField.isEditable() == true) {
				inputTextField.setStyle("-fx-background-color: #FAFE8C;");
				m_ErrorIDList.add(inputTextField);
			}
			else {
				// -- Not editable, we want to set and empty value for this.
				s_MeasurementData += ",";
			}
		}}catch(Exception e){
			boolean temp = false;
		}
	}

	private boolean loadMeasurementInput() {

		boolean rval = true;

		// -- Make sure to have clean measurement data before we begin.
		s_MeasurementData = "";
		for (TextField input : inputs) {
			addMeasurementInputToString(input);
		}

		if (m_ErrorIDList.size() > 0) {

			showErrorAlert();
			rval = false;
			s_MeasurementData = "";
		}
	
		return rval;
	}

	private void showErrorAlert() {
		Alert eAlert = new Alert(AlertType.ERROR);
		eAlert.setTitle("Invalid Indicators");
		eAlert.setHeaderText("There are invalid indicators on the form.  Please review them below. Yellow indicators are\n"
				+ "invalid input and Purple indicators are values out of maximum range.");

		String sAlertString = "";

		for (int i = 0; i < m_ErrorIDList.size(); i++) {
			sAlertString += m_ErrorIDList.get(i).getId().toString() + "; ";
		}

		eAlert.setContentText(sAlertString);

		eAlert.showAndWait();
	}

	private boolean isDoubleValue(String dInput) {
		try {
			Double.parseDouble(dInput);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	private void buttonClicked(ActionEvent E) {

		if (E.getSource() == btnSubmit) {
			if (loadMeasurementInput() && loadHeaderInput()) {
				fels.loadData();
				fels.setAge(Double.parseDouble(txtChronAge.getText()));
				fels.setSex(cmbGender.getValue());
				fels.setInputList(getInputValueMap());
				double generatedAge = fels.performEstimation();
			} else {
				// -- Loading the input Failed, why? TODO: Handle validation
				// errors. Missing fields, etc...?

				// -- We displayed our errors. Clear it out for the next round.
				m_ErrorIDList.clear();
				s_MeasurementData = "";
			}
		}
		else if(E.getSource() == btnGoBack){
			// -- NEED TO RESET DATA BEFORE GOING BACK OR ELSE IT PERSISTS.
			setScene( TitleViewController.getInstance().getScene() );
		}
	}
	
	public String getIndicatorFilePath() {
		return INDICATOR_FILE_PATH;
	}

	public Scene getScene() {
		return PatientDataFormScene;
	}

	public static FELSDataFormController getInstance() {

		if (instance == null) {

			FXMLLoader loader = new FXMLLoader(SkeletalCalculator.class.getResource("PatientDataForm.fxml"));

			Parent titlePane = null;

			try {
				titlePane = loader.load();
			} catch (Exception e) {
				e.printStackTrace();
			}

			PatientDataFormScene = new Scene(titlePane);

			instance = loader.getController();
		}

		return instance;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		cmbGender.setItems(genderList);
		felsMethod = new SkeletalMaturityMethod("FELS", INDICATOR_FILE_PATH);
		felsMethod.load();
		birthDate.setEditable(false);
		xrayDate.setEditable(false);
		asmDate.setEditable(false);
		txtSA.setEditable(false);
		txtSEE.setEditable(false);
		txtSA.setDisable(true);
		txtSEE.setDisable(true);
		initializeInputList();
		addListeners();
		btnSubmit.setOnAction(e -> buttonClicked(e));
		btnGoBack.setOnAction(e -> buttonClicked(e));
	}

	private void addListeners() {
		cmbGender.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> ov, String old, String current) {
				if (current.equals("Male")) {
					paneMeasurementInputs.setStyle(CSS_BACKGROUND + MALE_COLOR);
				} else if (current.equals("Female")) {
					paneMeasurementInputs.setStyle(CSS_BACKGROUND + FEMALE_COLOR);
				} else {
					paneMeasurementInputs.setStyle(CSS_BACKGROUND + WHITE_COLOR);
				}
				enableDisableInputs();
			}
		});

		for (TextField text : inputs) {
			text.setOnKeyTyped(e -> validateInputCharacter(e));
			Indicator i = felsMethod.getIndicatorMap().get(text.getId());
			if (i != null) {
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
		btnClear.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				for(TextField input: inputs){
					if(input.isEditable() && !input.isDisable()){
						input.setText("");
					}
				}
				
			}
			
		});
	}

	protected void enableDisableInputs() {
		String current = txtChronAge.getText();
		if (current == null || current.isEmpty()) {
			return;
		}
		double age = -1.0;
		try {
			age = Double.parseDouble(current);
			if (age < 0) {
				return;
			}
		} catch (NumberFormatException e) {
			return;
		}
		enabledDisableInputs(age);

	}

	protected void ageChanged(String old, String current) {
		if (current == null || current.isEmpty()) {
			enableAllInputs();
			return;
		}
		double age = -1.0;
		try {
			age = Double.parseDouble(current);
			if (age < 0) {
				txtChronAge.setText(old);
				return;
			}
		} catch (NumberFormatException e) {
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

	private boolean isMale(String sex) {
		boolean isMale = false;
		if (sex.equals("Male")) {
			isMale = true;
		}
		return isMale;
	}

	private void enabledDisableInputs(double age) {
		String sex = cmbGender.getValue();
		if (sex == null || sex.isEmpty()) {
			return;
		}

		for (TextField text : inputs) {
			if (text.getUserData() != null && text.getUserData() instanceof Indicator) {
				Indicator i = (Indicator) text.getUserData();
				double startRange = 0.0;
				double endRange = 0.0;
				if (i != null) {
					if (isMale(sex)) {
						startRange = i.getMaleStartRange();
						endRange = i.getMaleEndRange();
					} else {
						startRange = i.getFemaleStartRange();
						endRange = i.getFemaleEndRange();
					}
					if (startRange <= age && age <= endRange) {
						text.setDisable(false);
						text.setEditable(true);
						text.setText("");
					} else {
						text.setDisable(true);
						text.setEditable(false);
						text.setText(NOT_APPLICABLE);
					}
				}
			}
		}
	}

	private void initializeInputList() {
		for (Node child : paneMeasurementInputs.getChildren()) {
			if (child instanceof TextField) {
				inputs.add((TextField) child);
			}
		}
	}
	class Ratio{
		private String name = "";
		double num = 1.0;
		double denom = 1.0;
		private String getRatio(){
			if(denom != 0){
				return Double.toString(Math.pow((num / denom), 3));
			}
			return "1.0";
		}
	}
	String num = "EW";
	String denom = "MW";
	Map<String, Ratio> ratios = new HashMap<String,Ratio>();
	List<String> ratioList = new ArrayList<String>(Arrays.asList("R2", "U2", "METI2", "METIII2", "METV2", "PPI2",
			"PPIII2", "PPV2", "MPIII2", "MPV2", "DPI2", "DPIII2", "DPV2"));
	public Map<String, String> getInputValueMap(){
		Map<String, String> inputMap = new HashMap<String, String>();
		for(TextField input: inputs){
			if(input != null){
				String val = input.getText();
				if(!val.trim().isEmpty() && !val.equals(NOT_APPLICABLE)){
					String Id = input.getId();
					if(Id.contains(num)){
						String key = subString(Id, num);
						Ratio ratio = null;
						if((ratio = ratios.get(key)) == null){
							ratio = new Ratio();
						}
						ratio.num = Double.parseDouble(val);
						ratios.put(key, ratio);
					}else if(Id.contains(denom)){
						String key = subString(Id, denom);
						Ratio ratio = null;
						if((ratio = ratios.get(key)) == null){
							ratio = new Ratio();
						}
						ratio.denom = Double.parseDouble(val);
						ratios.put(key, ratio);
					}else{
						inputMap.put(input.getId(), val);
					}
				}
			}
		}
		for(String ratioID : ratioList){
			Ratio ratio = ratios.get(ratioID);
			String ratioVal = "1.0";
			if(ratio != null){
				ratioVal = ratio.getRatio();
			}
			inputMap.put(ratioID, ratioVal);
		}
		return inputMap;
	}
	private String subString(String val, String sub){
		int index = val.indexOf(sub);
		if(index > 0){
			return val.substring(0, index);
		}
		return null;
	}
}
