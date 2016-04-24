package main;

import java.util.ArrayList;
import java.util.List;

import controllers.FELSDataFormController;

public class ParameterEntry {

	List<Indicator> indicators = new ArrayList<Indicator>();
	
	private String entryName = "";
	private String entryRawInput = "";
	
	public ParameterEntry( String entryInput ) {
		setEntryRawInput(entryInput);
		ParseInput( entryInput );
	}
	
	public ArrayList<Indicator> getEntryIndicators() {
		return (ArrayList<Indicator>) indicators;
	}
	
	public String getEntryName() {
		return entryName;
	}
	
	private void ParseInput( String input ) {
		// -- Create an array of our inputs.
		String[] inputList = input.split(",");
		
		// -- Entry name is always first position.
		entryName = inputList[0];
		
		ArrayList<Indicator> indicatorList = new ArrayList<>();
		
		indicatorList = (ArrayList<Indicator>) Indicator.loadIndicators( FELSDataFormController.getInstance().getIndicatorFilePath() );
		
		// -- Load up our indicators.  Starting at 1 to bypass name value.
		for(int i = 1; i < indicatorList.size(); i++ ) {
			try{
				indicatorList.get(i).setIndicatorValue( inputList[i] );
			} catch( Exception e ) {
				// -- Problem with parsing indicator.  Don't set it.
				indicatorList.get(i).setIndicatorValue("N/A");
			}
		}
		
		indicators = indicatorList;
	}

	public String getEntryRawInput() {
		return entryRawInput;
	}

	private void setEntryRawInput(String entryRawInput) {
		this.entryRawInput = entryRawInput;
	}
}
