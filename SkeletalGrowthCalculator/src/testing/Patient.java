package testing;

import java.util.HashMap;
import java.util.Map;

public class Patient {
	private Map<String, String> measurements = new HashMap<String, String>();
	private double chronologicalAge = 0.0;
	private double pascalSkeletalAge = 0.0;
	private double calculatedSkeletalAge = 0.0;
	private long patientID;
	private String sex;
	public double getChronologicalAge() {
		return chronologicalAge;
	}
	public void setChronologicalAge(double chronologicalAge) {
		this.chronologicalAge = chronologicalAge;
	}
	public double getPascalSkeletalAge() {
		return pascalSkeletalAge;
	}
	public void setPascalSkeletalAge(double skeletalAge) {
		this.pascalSkeletalAge = skeletalAge;
	}
	public Map<String, String> getMeasurements() {
		return measurements;
	}
	public long getPatientID() {
		return patientID;
	}
	public void setPatientID(long patientID) {
		this.patientID = patientID;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public void setPatientID(String string) {
		patientID = Long.parseLong(string);
	}
	public void setChronologicalAge(String string) {
		chronologicalAge = Double.parseDouble(string);
	}
	public double getCalculatedSkeletalAge() {
		return calculatedSkeletalAge;
	}
	public void setCalculatedSkeletalAge(double calculatedSkeletalAge) {
		this.calculatedSkeletalAge = calculatedSkeletalAge;
	}	
}
