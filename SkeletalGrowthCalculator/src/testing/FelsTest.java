package testing;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import main.FELSMethod;
import statistics.SkeletalEstimation;

public class FelsTest {
	private static SkeletalEstimation fels = new FELSMethod();
	private static final String TEST_OUTPUT = "felsTestOutput.txt";
	public static void test(){
		List<Patient> patients = PatientValidationDataLoader.loadSampleData();
		if(patients == null || patients.isEmpty()){
			System.out.println("Patient Data could not be loaded");
			return;
		}
		fels.loadData();
		File outputFile = new File(TEST_OUTPUT);
		if(outputFile.exists()){
			outputFile.delete();
			outputFile = new File(TEST_OUTPUT);
		}
		StringBuilder fileContents = new StringBuilder();
		for(Patient patient : patients){
			double pascalAge = patient.getPascalSkeletalAge();
			fels.setAge(patient.getChronologicalAge());
			fels.setSex(patient.getSex());
			fels.setInputList(patient.getMeasurements());
			double generatedAge = fels.performEstimation();
			fileContents.append("The pascal Age for patient " + patient.getPatientID() + " at chronological age " + patient.getChronologicalAge() + " is " + pascalAge + ".\n");
			fileContents.append("The Age calculated from our program for patient " + patient.getPatientID() + " at chronological age " + patient.getChronologicalAge() + " is " + generatedAge + ".\n");
		}
		try {
			FileWriter fw = new FileWriter(outputFile);
			fw.write(fileContents.toString());
			fw.close();
		} catch (IOException e) {
		}
		
	}
}
