package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.layout.Pane;
import main.Indicator;
import main.ParameterEntry;
import main.SkeletalCalculator;

public class DummyEntryDataFormController extends SkeletalCalculator implements Initializable{

	private static Scene DummyEntryDataFormScene = null;
	private static DummyEntryDataFormController instance = null;
	private static ParameterEntry currentEntry = null;	
	private List<TextField> inputs = new ArrayList<TextField>();
	
	@FXML
	Pane paneMeasurementInputs;
	
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
	Button btnSave;
	@FXML
	Button btnGoBack;
	
	private void ButtonClicked( ActionEvent e ) {
		
		if( e.getSource() == btnSave ) {
			// -- Save values and go back to title view.
			
			setScene( ApplicationOptionsController.getInstance().getScene() );
		}
		else if( e.getSource() == btnGoBack ){
			
			for(TextField input: inputs){
				if(input.isEditable() && !input.isDisable()){
					input.setText("");
				}
			}
			
			// -- Go back to title view.
			setScene( ApplicationOptionsController.getInstance().getScene() );
		}
		
		// -- Reset the entry that we are looking at.
		currentEntry = null;
	}
	
	public void refresh() {
		if( inputs != null ){
			for(TextField input: inputs){
				try {
					input.setText( currentEntry.getEntryIndicators().get( inputs.indexOf(input) + 1 ).getIndicatorValue() );
				}catch (Exception e ) {
					// -- Catch what we can.
				}
			}
			
			// -- Hard coding for now. First slot will always be the entry ID.
			txtID.setText( currentEntry.getEntryName() );
		}
	}
	
	private void initializeInputList() {
		for (Node child : paneMeasurementInputs.getChildren()) {
			if (child instanceof TextField) {
				inputs.add((TextField) child);
			}
		}
	}
	
	public Scene getScene() {
		return DummyEntryDataFormScene;
	}
	
	public void setParameterEntry( ParameterEntry pEntry ) {
		currentEntry = pEntry;		
	}
	
	public static DummyEntryDataFormController getInstance() {
		
		if( instance == null ) {
			
			FXMLLoader loader = new FXMLLoader( SkeletalCalculator.class.getResource("DummyEntryDataForm.fxml") );
			
			Parent titlePane = null;
			
			try {
				titlePane = loader.load();
			} catch ( Exception e ) {
				e.printStackTrace();
			}
			
			DummyEntryDataFormScene = new Scene( titlePane );
			
			instance = loader.getController();
		}
		
		return instance;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		btnGoBack.setOnAction( e -> ButtonClicked(e) );
		
		initializeInputList();
	}
}
