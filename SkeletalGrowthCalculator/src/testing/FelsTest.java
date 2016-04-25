package testing;

import java.util.List;

import main.FELSMethod;
import statistics.SkeletalEstimation;

public class FelsTest {
	private static SkeletalEstimation fels = new FELSMethod();
	public static void test(){
		List<Patient> patients = PatientValidationDataLoader.loadSampleData();
		if(patients == null || patients.isEmpty()){
			System.out.println("Patient Data could not be loaded");
			return;
		}
		fels.loadData();
		for(Patient patient : patients){
			double pascalAge = patient.getPascalSkeletalAge();
			fels.setAge(patient.getChronologicalAge());
			fels.setSex(patient.getSex());
			fels.setInputList(patient.getMeasurements());
			double generatedAge = fels.performEstimation();
			System.out.println("The pascal Age for patient " + patient.getPatientID() + " at chronological age " + patient.getChronologicalAge() + " is " + pascalAge + ".");
			System.out.println("The Age calculated from our program for patient " + patient.getPatientID() + " at chronological age " + patient.getChronologicalAge() + " is " + generatedAge + ".");
		}
	}
}
