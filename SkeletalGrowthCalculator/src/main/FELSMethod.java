package main;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import statistics.SkeletalEstimation;

public class FELSMethod extends SkeletalEstimation {

	private static String[] pascalIndicatorOrder = { "R1", "R3", "R4", "R5", "R6", "R7", "R8", "U1", "U3", "C1", "C2",
			"C3", "C4", "H1", "H2", "H3", "H4", "TRI1", "TRI2", "TRI3", "TRI4", "P1", "L1", "L2", "S1", "S2", "S3",
			"TPM1", "TPM2", "TPM3", "TPM4", "TPM5", "TPD1", "TPD2", "TPD3", "TPD4", "TPD5", "TPD6", "TPD7", "AS1",
			"METI1", "METI3", "METI4", "METI5", "METI6", "METI7", "METIII1", "METIII3", "METIII4", "METIII5", "METV1",
			"METV3", "METV4", "METV5", "METV6", "PPI1", "PPI3", "PPI4", "PPI5", "PPI6", "PPI7", "PPIII1", "PPIII3",
			"PPIII4", "PPIII5", "PPIII6", "PPV1", "PPV3", "PPV4", "PPV5", "MPIII1", "MPIII3", "MPIII4", "MPIII5",
			"MPV1", "MPV3", "MPV4", "MPV5", "DPI4", "DPIII1", "DPIII3", "DPIII4", "DPV1", "DPV3", "DPV4", "R2", "U2",
			"METI2", "METIII2", "METV2", "PPI2", "PPIII2", "PPV2", "MPIII2", "MPV2", "DPI2", "DPIII2", "DPV2" };
	// -- Only 97 (98) indicators for hand/wrist. We are not including the knee
	// indicators.
	int TOTAL_INDICATORS = 98;
	int MAX_VALUES[] = { 3, 2, 2, 2, 4, 4, 3, 3, 2, 2, 2, 3, 2, 3, 2, 2, 2, 3, 3, 2, 2, 2, 5, 2, 4, 4, 2, 5, 3, 2, 2, 2,
			2, 2, 2, 2, 2, 2, 2, 2, 3, 2, 2, 2, 2, 3, 4, 2, 2, 3, 2, 2, 2, 3, 2, 2, 2, 2, 3, 2, 2, 2, 2, 2, 3, 2, 2, 2,
			2, 3, 2, 2, 2, 3, 2, 2, 2, 3, 3, 2, 2, 3, 2, 2, 3 };
	double[][] parameters = new double[198][5];
	int grade[];
	int cubed_ratio[];
	int sex; // 1 for male 2 for female
	int RUNMODE;
	int M1;
	double E;
	double U;
	double current_estimate;
	double deriv_holder;
	double age;

	int NGRADED = 0; // -- Not sure if this is right.
	int LGRADED = 84;

	int FIRST = 0;

	int MREGR = 85;
	int NREGR = 97;

	private static final String CALIBRATION_DATA_FILE = "FELS_calibration_data.csv";

	public void setInputList(Map<String, String> inputMap) {
		if (inputMap == null || inputMap.isEmpty()) {
			return;
		}
		for (int i = 0; i < pascalIndicatorOrder.length; ++i) {
			String indicator = pascalIndicatorOrder[i];
			String value = inputMap.get(indicator);
			double val = 1.0;
			if (value != null) {
				try {
					val = Double.parseDouble(value);
				} catch (NumberFormatException e) {
					val = 1.0;
				}
			}
			inputList.add(val);
		}
	}

	public void loadData() {

		int i, j;
		Pattern p = Pattern.compile("(?:\\d*\\.)?\\d+");
		Matcher matcher;
		// String pattern = "(?:\\d*\\.)?\\d+";
		i = 0;
		j = 0;
		try {
			// File calibration = new File(CALIBRATION_DATA_FILE);

			URL url = Indicator.class.getResource(CALIBRATION_DATA_FILE);
			FileInputStream fileStream = new FileInputStream(url.getPath());
			InputStreamReader isr = new InputStreamReader(fileStream, Charset.forName("UTF-8"));
			BufferedReader br = new BufferedReader(isr);
			String line = "";

			while ((line = br.readLine()) != null) {

				j = 0;

				matcher = p.matcher(line);

				while (matcher.find()) {
					double d = 0.0;
					String s = matcher.group();
					try{
						d = Double.parseDouble(s);
					}catch(NumberFormatException e){
						d = 0;
					}
					parameters[i][j++] = d;
				}
				
				i++;

			}

			br.close();

		} catch (Exception e) {
			// -- Unable to find the list.
			Alert eAlert = new Alert(AlertType.ERROR);
			eAlert.setTitle("ERROR");
			eAlert.setHeaderText("Missing calibration data file.");

			eAlert.setContentText("The Calibration Data file could not be located or loaded.");

			eAlert.showAndWait();
		}
	}

	public void setAge(double age) {
		this.age = age;
	}

	public void setSex(String sex) {
		if (sex != null && !sex.isEmpty()) {
			if(sex.equals("1") || sex.equals("2")){
				this.sex = Integer.parseInt(sex);
				return;
			}
			if (sex.equals("Male")) {
				this.sex = 1;
			} else {
				this.sex = 2;
			}
		}
	}
	double deriv1 = 0;
	double deriv2 = 0;
	public double performEstimation() {
		current_estimate = age;
		double T1 = 0;
		deriv1 = 0;
		deriv2 = 0;
		int iterator1 = 0;
		int iterator = 0;
		NGRADED = 0; // -- Not sure if this is right.
		LGRADED = 84;
		FIRST = 0;
		MREGR = 85;
		NREGR = 97;
		do {
			iterator1++;

			// Stands for non batch mode
			if (RUNMODE == 0) {
				System.out.printf("Iteration %d Estimate now: %f\n", iterator1, current_estimate);
			}

			double T0 = T1;
			T1 = current_estimate;
			deriv_holder = deriv1;
			deriv1 = 0;
			deriv2 = 0;

			for (iterator = FIRST; iterator <= LGRADED; iterator++) {
				double currIndicatorVal = inputList.get(iterator);
				if (currIndicatorVal != 0) {

					// SLOPE is d, the rate parameter
					double SLOPE = parameters[(TOTAL_INDICATORS * (sex - 1) + iterator)][1];

					// For each possible grade
					for (M1 = 1; M1 < MAX_VALUES[iterator]; M1++) {

						if (currIndicatorVal == M1) {

							double grade1 = 1.0;

							if (M1 != 1) {

								// U is (tau - current estimate)*d
								U = ((parameters[TOTAL_INDICATORS * (sex - 1) + iterator][M1]) - current_estimate)
										* SLOPE;

								// Correct values that are too large in
								// magnitude
								if (Math.abs(U) > 10) {

									if (U > 10) {

										U = 10.0;
									}

									if (U < -10) {

										U = -10.0;
									}
								}

								grade1 = 1.0 / (1.0 + Math.exp(U));
							}

							double grade2 = 0.0;

							if (M1 != MAX_VALUES[iterator]) {

								U = (parameters[TOTAL_INDICATORS * (sex - 1) + iterator][M1 + 1] - current_estimate)
										* SLOPE;

								// Correct values that are too large in
								// magnitude
								if (Math.abs(U) > 10) {

									if (U > 10) {

										U = 10.0;
									}

									if (U < -10) {

										U = -10.0;
									}
								}

								grade2 = 1.0 / (1.0 + Math.exp(U));
							}

							double grade3 = grade1 - grade2;

							// Correct values that are too small in magnitude
							// since later you divide by grade3
							if (grade3 < 0.005) {

								grade3 = 0.005;
							}

							double Q1 = 1.0 - grade1;
							double Q2 = 1.0 - grade2;
							double P1Q1 = grade1 * Q1;
							double P2Q2 = grade2 * Q2;

							// Add up the first derivatives with respect to
							// current_estimate across graded indicators
							deriv1 = deriv1 + ((SLOPE * (P1Q1 - P2Q2)) / grade3);
							double D = (Q1 - grade1) * P1Q1 + (grade2 - Q2) * P2Q2;
							D = D - Math.sqrt(P2Q2 - P1Q1) / grade3;

							// Add up the second derivatives with respect to
							// current_estimate across graded indicators
							deriv2 = deriv2 - D * Math.sqrt(SLOPE) / grade3;
						}
					}
				}
			}
			for (iterator = MREGR; iterator <= NREGR; iterator++) {
				double val = inputList.get(iterator);
				if (val != 0) {

					double BETA = parameters[TOTAL_INDICATORS * (sex - 1) + iterator][1];
					double MU = parameters[TOTAL_INDICATORS * (sex - 1) + iterator][2];
					double SIGMA = parameters[TOTAL_INDICATORS * (sex - 1) + iterator][3];
					double ZD = (val - BETA * current_estimate - MU) / SIGMA;

					// Add up derivatives with respect to theta across
					// continuous indicators
					deriv1 = deriv1 + BETA * ZD / SIGMA;
					deriv2 = deriv2 * Math.sqrt(BETA / SIGMA);
				}
			}
			// If second derivative is negative, make it positive
			if (deriv2 < 0) {
				deriv2 = -deriv2;
			}

			// If second derivative is 0, make it 0.01
			if (deriv2 == 0) {

				deriv2 = 0.01;
			}

			double deriv_end = deriv1 / deriv2;

			if (deriv_end > 1) {

				deriv_end = 1;
			}

			if (deriv_end < -1) {

				deriv_end = -1;
			}

			// Update the estimate
			if(Double.isNaN(deriv_end)){
				deriv_end = 0;
			}
			current_estimate = current_estimate + deriv_end;

			// Store second derivative to compute standard error
			E = deriv2;

			// If the derivative has changed sign since the last iteration, then
			// you have overshot the maximum
			if (iterator1 > 2 && (deriv1 * deriv_holder < 0)) {

				if (deriv_holder > 0 && current_estimate < T0) {

					current_estimate = (T0 + T1) / 2.0;
				}

				if (deriv_holder < 0 && current_estimate > T0) {

					current_estimate = (T0 + T1) / 2.0;
				}
			}

		} while ((iterator1 < 50) && (Math.abs(deriv1) > 0.0001));

		if (deriv1 > 0.0001) {
			System.out.printf("Algorithm did not converge. Beware of the results");
		}

		// Inverse of second derivative is the variance
		E = 1.0 / Math.sqrt(E);

		// Was added later in the program. If it did not converge, then E is not
		// the variance
		if (deriv1 > 0.0001) {

			E = 9999;
		}

		if (current_estimate < 0) {

			current_estimate = 0;

			if (RUNMODE == 0) {

				System.out.printf("The estimated skeletal age is less than zero.");
				System.out.printf("This probably means that all indicators entered are ");
				System.out.printf("in their immature state. No valid estimate exists.");
				System.out.printf("The immature skeletal age code is %f.2", current_estimate);
				System.out.printf("with an estimated standard error of %f.2 years.", E);
			}
		}

		if ((sex == 1 && (current_estimate > 18.00)) || (sex == 2 && (current_estimate > 18.00))) {

			if (sex == 1) {

				current_estimate = 18.00;

			} else {

				current_estimate = 18.00;

				if (RUNMODE == 0) {
					System.out.printf("The estimated skeletal age is greater than 18.");
					System.out.printf("This means that all or nearly all indicators entered are");
					System.out.printf("in their mature state. No valid estimate exists.");
					System.out.printf("The mature skeletal age code is %f.2 ", current_estimate);
					System.out.printf("with an estimated standard error of %f.2 years.", E);
				}
			}
		}

		if ((RUNMODE == 0) && (current_estimate > 0)
				&& (((sex == 1) && (current_estimate < 18.00)) || ((sex == 2) && (current_estimate < 18.00)))) {

			//System.out.printf("The estimated skeletal age is %f.2 years", current_estimate);
			//System.out.printf("with an estimated standard error of %f.2 years.", E);
			//System.out.printf("%d iterations were required.", iterator1);
		}

		return current_estimate;
	}
};