package testing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class PatientValidationDataLoader {
	private static final String INPUT_FILE_PATH = "SampleData-Input.csv";
	private static final String OUTPUT_FILE_PATH = "SampleData-OutputSkelagePascal.csv";

	public static List<Patient> loadSampleData() {
		List<Patient> patients = new ArrayList<Patient>();
		URL inputUrl = PatientValidationDataLoader.class.getResource(INPUT_FILE_PATH);
		URL outputUrl = PatientValidationDataLoader.class.getResource(OUTPUT_FILE_PATH);
		if (inputUrl == null || outputUrl == null) {
			return patients;
		}
		File inputFile = new File(inputUrl.getPath());
		if (!inputFile.exists()) {
			System.out.println("Sample Input File cannot be found.");
			return patients;
		}
		File outputFile = new File(outputUrl.getPath());
		if (!outputFile.exists()) {
			System.out.println("Sample Output File cannot be found.");
			return patients;
		}
		// load data from input
		InputStream fileStream = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			fileStream = new FileInputStream(inputUrl.getPath());
			isr = new InputStreamReader(fileStream, Charset.forName("UTF-8"));
			br = new BufferedReader(isr);
			String line = "";
			List<String> parameterList = new ArrayList<String>();
			while ((line = br.readLine()) != null) {
				String[] sampleInput = line.split(",");
				List<String> inputLst = Arrays.asList(sampleInput);
				if (sampleInput != null) {
					int length = sampleInput.length;
					// check if this is the first line
					if (inputLst.get(0).equals("id")) {
						for (String input : inputLst) {
							parameterList.add(input);
						}
						continue;
					}
					Patient patient = new Patient();
					patient.setPatientID(inputLst.get(0));
					patient.setSex(inputLst.get(1));
					patient.setChronologicalAge(inputLst.get(2));
					Map<String, String> measurements = patient.getMeasurements();
					for (int i = 3; i < inputLst.size(); ++i) {
						String indicator = parameterList.get(i);
						String val = inputLst.get(i);
						String measurement = "0";
						if (!val.isEmpty() || !val.equals(".")) {
							measurement = val;
						}
						measurements.put(indicator, measurement);
					}
					patients.add(patient);
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("File could not be found at path: " + INPUT_FILE_PATH);
			return patients;
		} catch (IOException e) {
			System.out.println("IO Exception " + INPUT_FILE_PATH);
			return patients;
		} finally {
			try {
				if (fileStream != null) {
					fileStream.close();
				}
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
			}
		}
		fileStream = null;
		isr = null;
		br = null;
		try {
			// hack to add pascalAge and SEE to patient input
			fileStream = new FileInputStream(outputUrl.getPath());
			isr = new InputStreamReader(fileStream, Charset.forName("UTF-8"));
			br = new BufferedReader(isr);
			String line = "";
			boolean firstLine = true;
			int i = 0;
			while ((line = br.readLine()) != null) {
				if (firstLine) {
					firstLine = false;
					continue;
				}
				String[] sampleInput = line.split(",");
				patients.get(i).setPascalSkeletalAge(Double.parseDouble(sampleInput[3]));
				i++;
			}
		} catch (FileNotFoundException e) {
			System.out.println("File could not be found at path: " + INPUT_FILE_PATH);
			return patients;
		} catch (IOException e) {
			System.out.println("IO Exception " + INPUT_FILE_PATH);
			return patients;
		} finally

		{
			try {
				if (fileStream != null) {
					fileStream.close();
				}
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
			}
		}
		return patients;
	}
}
