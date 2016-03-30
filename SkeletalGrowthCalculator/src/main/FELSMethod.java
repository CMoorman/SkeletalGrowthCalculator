package main;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;

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
	double[][] parameters = new double[197/* Place holder */][5];
	int grade[];
	int cubed_ratio[];
	int sex; // 1 for male 2 for female
	int iterator1;
	int RUNMODE;
	int M1;
	int iterator;
	double E;
	double U;
	double grade1;
	double grade2;
	double grade3;
	double Q1;
	double Q2;
	double SLOPE;
	double P1Q1;
	double P2Q2;
	double T0;
	double T1;
	double current_estimate;
	double deriv1;
	double deriv2;
	double deriv_holder;
	double age;

	int NGRADED = 76; // -- Not sure if this is right.
	int LGRADED = TOTAL_INDICATORS;

	int FIRST = 0;

	int MREGR = 76; // -- Not sure if this is right.
	int NREGR = TOTAL_INDICATORS;

	private static final String CALIBRATION_DATA_FILE = "FEL_calibration_data.csv";

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
			System.out.println(indicator + " : " + val);
		}
	}

	public void loadData() {

		int i, j;
		String[] matcher = new String[5];
		// String pattern = "(?:\\d*\\.)?\\d+";
		i = j = 0;

		try {
			URL url = Indicator.class.getResource(CALIBRATION_DATA_FILE);
			FileInputStream fileStream = new FileInputStream(url.getPath());
			InputStreamReader isr = new InputStreamReader(fileStream, Charset.forName("UTF-8"));
			BufferedReader br = new BufferedReader(isr);
			String line = "";
			while ((line = br.readLine()) != null) {

				i++;
				j = 0;

				matcher = line.split("(?:\\d*\\.)?\\d+");

				for (String pull : matcher) {

					parameters[i][j++] = Double.parseDouble(pull);
				}

			}

			br.close();

		} catch (Exception e) {
			// -- Unable to find the list.
			Alert eAlert = new Alert(AlertType.ERROR);
			eAlert.setTitle("ERROR");
			eAlert.setHeaderText("Missing calibration data file.");

			eAlert.setContentText("The Calibration Data file could not be located or loaded.");

			eAlert.showAndWait();
			e.printStackTrace();
		}
	}

	public void setAge(double age) {
		this.age = age;
	}

	public void setSex(String sex) {
		if (sex != null && !sex.isEmpty()) {
			if (sex.equals("Male")) {
				this.sex = 1;
			} else {
				this.sex = 2;
			}
		}
	}

	public double performEstimation() {

		current_estimate = age;
		T1 = 0;
		deriv1 = 0;
		iterator1 = 0;

		do {
			iterator1++;

			// Stands for non batch mode
			if (RUNMODE == 0) {
				System.out.printf("Iteration %d Estimate now: %f", iterator1, current_estimate);
			}

			T0 = T1;
			T1 = current_estimate;
			deriv_holder = deriv1;
			deriv1 = 0;
			deriv2 = 0;

			for (iterator = FIRST; iterator <= LGRADED; iterator++) {
				double currentIdicatorValue = inputList.get(iterator);
				if (currentIdicatorValue != 0) {

					// SLOPE is d, the rate parameter
					SLOPE = parameters[(TOTAL_INDICATORS * (sex - 1) + iterator)][1];

					// For each possible grade
					for (M1 = 1; M1 < MAX_VALUES[iterator]; M1++) {

						if (currentIdicatorValue == M1) {

							grade1 = 1.0;

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

							grade2 = 0.0;

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

							grade3 = grade1 - grade2;

							// Correct values that are too small in magnitude
							// since later you divide by grade3
							if (grade3 < 0.005) {

								grade3 = 0.005;
							}

							Q1 = 1.0 - grade1;
							Q2 = 1.0 - grade2;
							P1Q1 = grade1 * Q1;
							P2Q2 = grade2 * Q2;

							// Add up the first derivatives with respect to
							// current_estimate across graded indicators
							deriv1 = deriv1 + SLOPE * (P1Q1 - P2Q2) / grade3;
							double D = (Q1 - grade1) * P1Q1 + (grade2 - Q2) * P2Q2;
							D = D - Math.sqrt(P2Q2 - P1Q1) / grade3;

							// Add up the second derivatives with respect to
							// current_estimate across graded indicators
							deriv2 = deriv2 - D * Math.sqrt(SLOPE) / grade3;
						}
					}
				}
			}

			// -- Don't think we need RSW.
			// if( RSW ) {

			for (iterator = MREGR; iterator <= NREGR; iterator++) {

				if (cubed_ratio[iterator] != 0.0) {

					double BETA = parameters[TOTAL_INDICATORS * (sex - 1) + iterator][1];
					double MU = parameters[TOTAL_INDICATORS * (sex - 1) + iterator][2];
					double SIGMA = parameters[TOTAL_INDICATORS * (sex - 1) + iterator][3];
					double ZD = (cubed_ratio[iterator] - BETA * current_estimate - MU) / SIGMA;

					// Add up derivatives with respect to theta across
					// continuous indicators
					deriv1 = deriv1 + BETA * ZD / SIGMA;
					deriv2 = deriv2 * Math.sqrt(BETA / SIGMA);
				}
			}
			// }

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

		} while ((iterator1 > 50) || (Math.abs(deriv1) < 0.001));

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

			System.out.printf("The estimated skeletal age is %f.2 years", current_estimate);
			System.out.printf("with an estimated standard error of %f.2 years.", E);
			System.out.printf("%d iterations were required.", iterator1);
		}

		return current_estimate;
	}
};
/*
 * UPPER BOUNDS {REM***parameters, male first, then female,hand******} (2.50,
 * 1.24, 1.67, 0,0), {R-1} (1.12, 2.73, 0,0,0), {R-3} (1.11, 3.27, 0,0,0), {R-4}
 * (0.73, 6.46, 0,0,0), {R-5} (1.43, 13.32, 16.93, 18.28, 0), {R-6} (1.69,
 * 14.48, 16.77, 17.87, 0), {R-7} (1.48, 16.33, 17.05, 0,0), {R-8} (1.15, 6.99,
 * 9.28, 0,0), {U-1} (1.57, 18.09, 0,0,0), {U-3} (10.39, 0.25, 0,0,0), {C-1}
 * (1.83, 0.08, 0,0,0), {C-2} (0.61, 2.50, 4.86, 0,0), {C-3} (0.71, 12.84,
 * 0,0,0), {C-4} (2.68, 0.13, 0.73, 0,0), {H-1} (1.24, 5.68, 0,0,0), {H-2}
 * (1.23, 9.45, 0,0,0), {H-3} (1.53, 12.65, 0,0,0), {H-4} (1.01, 2.52, 3.27,
 * 0,0), {TRI-1} (0.88, 8.00, 9.49, 0,0), {TRI-2} (0.97, 7.70, 0,0,0), {TRI-3}
 * (0.96, 10.22, 0,0,0), {TRI-4} (1.26, 10.66, 0,0,0), {P-1} (1.12, 4.16, 4.68,
 * 6.45, 10.12), {L-1} (0.76, 11.07, 0,0,0), {L-2} (1.28, 6.02, 6.56, 7.18, 0),
 * {S-1} (1.19, 7.99, 8.48, 9.37, 0), {S-2} (1.09, 11.71, 0,0,0), {S-3} (1.14,
 * 6.11, 6.50, 8.20, 9.49), {TPM-1} (1.11, 8.58, 11.09, 0,0), {TPM-2} (1.09,
 * 11.20, 0,0,0), {TPM-3} (0.94, 12.35, 0,0,0), {TPM-4} (1.16, 12.63, 0,0,0),
 * {TPM-5} (1.47, 6.13, 0,0,0), {TPD-1} (1.15, 8.87, 0,0,0), {TPD-2} (0.68,
 * 9.44, 0,0,0), {TPD-3} (1.04, 9.34, 0,0,0), {TPD-4} (1.35, 8.95, 0,0,0),
 * {TPD-5} (0.84, 10.83, 0,0,0), {TPD-6} (0.92, 11.13, 0,0,0), {TPD-7} (1.68,
 * 12.63, 0,0,0), {AS-1} (2.08, 2.74, 2.99, 0,0), {MET I-1} (0.77, 4.87, 0,0,0),
 * {MET I-3} (1.04, 9.09, 0,0,0), {MET I-4} (0.99, 13.05, 0,0,0), {MET I-5}
 * (1.21, 14.13, 0,0,0), {MET I-6} (1.69, 15.23, 15.91, 0,0), {MET I-7} (2.36,
 * 1.75, 2.04, 3.48, 0), {MET III-1} (0.63, 13.43, 0,0,0), {MET III-3} (0.77,
 * 10.16, 0,0,0), {MET III-4} (1.54, 15.67, 16.63, 0,0), {MET III-5} (2.89,
 * 2.10, 0,0,0), {MET V-1} (1.18, 11.93, 0,0,0), {MET V-3} (1.22, 12.26, 0,0,0),
 * {MET V-4} (1.07, 15.27, 16.78, 0,0), {MET V-5} (0.88, 10.12, 0,0,0), {MET
 * V-6} (2.43, 2.69, 0,0,0), {PP I-1} (1.14, 3.93, 0,0,0), {PP I-3} (0.71,
 * 11.38, 0,0,0), {PP I-4} (1.60, 15.59, 16.23, 0,0), {PP I-5} (0.85, 10.34,
 * 0,0,0), {PP I-6} (1.34, 13.87, 0,0,0), {PP I-7} (4.67, 1.24, 0,0,0), {PP
 * III-1} (1.21, 4.14, 0,0,0), {PP III-3} (0.87, 10.42, 0,0,0), {PP III-4}
 * (1.42, 15.53, 16.37, 0,0), {PP III-5} (0.75, 7.75, 0,0,0), {PP III-6} (3.81,
 * 1.77, 0,0,0), {PP V-1} (1.06, 4.30, 0,0,0), {PP V-3} (0.87, 12.12, 0,0,0),
 * {PP V-4} (1.56, 15.61, 16.05, 0,0), {PP V-5} (3.01, 1.95, 0,0,0), {MP III-1}
 * (0.98, 5.15, 0,0,0), {MP III-3} (0.86, 12.77, 0,0,0), {MP III-4} (1.55,
 * 15.43, 16.37, 0,0), {MP III-5} (1.58, 3.21, 0,0,0), {MP V-1} (1.07, 6.07,
 * 0,0,0), {MP V-3} (0.82, 13.79, 0,0,0), {MP V-4} (1.51, 15.75, 16.25, 0,0),
 * {MP V-5} (1.67, 14.95, 15.48, 0,0), {DP I-4} (2.78, 2.26, 0,0,0), {DP III-1}
 * (0.95, 6.38, 0,0,0), {DP III-3} (1.71, 15.13, 15.80, 0,0), {DP III-4} (2.40,
 * 3.09, 0,0,0), {DP V-1} (0.91, 8.05, 0,0,0), {DP V-3} (1.59, 15.37, 15.95,
 * 0,0), {DP V-4} (0.103, -0.086, 0.129, 0,0), {R-2} (0.159, -1.117, 0.202,
 * 0,0), {U-2} (0.116, -0.401, 0.138, 0,0), {MET I-2} (0.071, 0.008, 0.105,
 * 0,0), {MET III-2} (0.083, -0.092, 0.148, 0,0), {MET V-2} (0.118, -0.207,
 * 0.181, 0,0), {PP I-2} (0.076, 0.074, 0.102, 0,0), {PP III-2} (0.090, -0.128,
 * 0.142, 0,0), {PP V-2} (0.082, -0.080, 0.115, 0,0), {MP III-2} (0.093, -0.305,
 * 0.154, 0,0), {MP V-2} (0.134, -0.083, 0.223, 0,0), {DP I-2} (0.179, -0.402,
 * 0.213, 0,0), {DP III-2} (0.198, -0.628, 0.260, 0,0), {DP V-2}
 * {REM***parameters, female start here**********************}
 * (5.30,0.43,1.00,1.73,2.28), {FEM-A} (5.15,1.05,1.39,1.88,2.32), {FEM-B}
 * (4.09,1.09,1.39,1.69,2.11), {FEM-C} (8.85,0.18,0.71,0,0), {FEM-D}
 * (6.19,0.40,0,0,0), {FEM-E} (0.90,7.79,0,0,0), {FEM-F} (0.84,9.14,0,0,0),
 * {FEM-G} (1.09,2.35,5.28,0,0), {FEM-H} (1.30,6.85,0,0,0), {FEM-J}
 * (1.45,10.19,13.60,0,0), {FEM-K} (1.45,14.10,16.12,0,0), {FEM-L}
 * (1.19,12.72,15.39,0,0), {FEM-M} (2.31,0.55,1.75,2.94,4.01), {TIB-A}
 * (1.16,3.84,4.82,6.35,8.30), {TIB-B} (7.98,0.26,0,0,0), {TIB-C}
 * (5.92,0.20,0,0,0), {TIB-D} (0.43,10.65,0,0,0), {TIB-E} (0.58,6.05,0,0,0),
 * {TIB-F} (0.73,6.44,0,0,0), {TIB-G} (0.60,11.54,0,0,0), {TIB-H}
 * (0.62,11.66,0,0,0), {TIB-J} (0.53,12.11,0,0,0), {TIB-K} (0.73,4.51,0,0,0),
 * {TIB-L} (1.33,5.34,0,0,0), {TIB-M} (1.38,11.34,0,0,0), {TIB-N}
 * (1.23,12.72,0,0,0), {TIB-P} (1.50,14.47,15.55,0,0), {TIB-Q}
 * (1.37,14.44,15.93,0,0), {TIB-R} (1.61,3.25,4.16,5.99,8.74), {FIB-A}
 * (1.50,3.41,0,0,0), {FIB-B} (1.80,2.94,3.91,0,0), {FIB-C} (0.69,7.44,0,0,0),
 * {FIB-D} (0.98,9.97,0,0,0), {FIB-E} (1.37,14.39,15.47,16.11,0), {FIB-F} (3.24,
 * 0.92, 1.42, 0,0), {R-1} (1.79, 2.26, 0,0,0), {R-3} (1.58, 2.37, 0,0,0), {R-4}
 * (0.80, 6.30, 0,0,0), {R-5} (1.48, 11.49, 15.20, 17.42, 0), {R-6} (1.63,
 * 12.32, 15.06, 17.05, 0), {R-7} (1.49, 14.67, 15.67, 0,0), {R-8} (1.37, 5.78,
 * 7.42, 0,0), {U-1} (1.19, 17.14, 0,0,0), {U-3} (11.13, 0.18, 0,0,0), {C-1}
 * (2.41, 0.22, 0,0,0), {C-2} (0.74, 2.10, 4.63, 0,0), {C-3} (0.80, 10.68,
 * 0,0,0), {C-4} (4.21, 0.09, 0.49, 0,0), {H-1} (1.35, 4.63, 0,0,0), {H-2}
 * (1.21, 7.70, 0,0,0), {H-3} (1.41, 10.50, 0,0,0), {H-4} (1.42, 1.82, 2.40,
 * 0,0), {TRI-1} (0.82, 6.26, 7.83, 0,0), {TRI-2} (1.01, 6.57, 0,0,0), {TRI-3}
 * (0.99, 8.20, 0,0,0), {TRI-4} (1.46, 8.32, 0,0,0), {P-1} (1.32, 3.00, 3.57,
 * 5.12, 8.62), {L-1} (0.65, 9.31, 0,0,0), {L-2} (1.52, 4.38, 4.83, 5.31, 0),
 * {S-1} (1.36, 6.42, 6.85, 7.37, 0), {S-2} (0.85, 9.91, 0,0,0), {S-3} (1.27,
 * 4.34, 4.62, 6.60, 7.90), {TPM-1} (1.43, 7.12, 9.39, 0,0), {TPM-2} (1.08,
 * 9.88, 0,0,0), {TPM-3} (0.85, 10.78, 0,0,0), {TPM-4} (1.57, 10.16, 0,0,0),
 * {TPM-5} (1.71, 4.30, 0,0,0), {TPD-1} (1.23, 7.10, 0,0,0), {TPD-2} (0.52,
 * 8.17, 0,0,0), {TPD-3} (1.19, 7.74, 0,0,0), {TPD-4} (1.46, 7.36, 0,0,0),
 * {TPD-5} (0.98, 9.28, 0,0,0), {TPD-6} (0.77, 9.94, 0,0,0), {TPD-7} (1.76,
 * 10.57, 0,0,0), {AS-1} (3.70, 1.60, 1.75, 0,0), {MET I-1} (0.81, 4.53, 0,0,0),
 * {MET I-3} (1.19, 7.05, 0,0,0), {MET I-4} (0.93, 11.15, 0,0,0), {MET I-5}
 * (1.10, 12.29, 0,0,0), {MET I-6} (1.32, 13.15, 14.51, 0,0), {MET I-7} (3.11,
 * 1.21, 1.43, 2.64, 0), {MET III-1} (0.81, 10.95, 0,0,0), {MET III-3} (0.74,
 * 8.15, 0,0,0), {MET III-4} (1.34, 13.04, 15.23, 0,0), {MET III-5} (4.72, 1.38,
 * 0,0,0), {MET V-1} (1.45, 9.68, 0,0,0), {MET V-3} (1.45, 10.00, 0,0,0), {MET
 * V-4} (1.20, 13.55, 15.28, 0,0), {MET V-5} (1.15, 8.13, 0,0,0), {MET V-6}
 * (3.17, 1.62, 0,0,0), {PP I-1} (0.70, 2.23, 0,0,0), {PP I-3} (0.93, 8.82,
 * 0,0,0), {PP I-4} (1.79, 13.41, 13.97, 0,0), {PP I-5} (1.03, 8.08, 0,0,0), {PP
 * I-6} (1.31, 11.50, 0,0,0), {PP I-7} (7.99, 0.81, 0,0,0), {PP III-1} (1.42,
 * 2.68, 0,0,0), {PP III-3} (0.92, 9.64, 0,0,0), {PP III-4} (1.67, 13.37, 13.95,
 * 0,0), {PP III-5} (0.83, 6.18, 0,0,0), {PP III-6} (5.08, 1.20, 0,0,0), {PP
 * V-1} (1.30, 3.21, 0,0,0), {PP V-3} (1.02, 10.21, 0,0,0), {PP V-4} (1.56,
 * 13.05, 13.98, 0,0), {PP V-5} (4.13, 1.20, 0,0,0), {MP III-1} (0.66, 3.47,
 * 0,0,0), {MP III-3} (0.72, 11.21, 0,0,0), {MP III-4} (1.79, 13.23, 14.26,
 * 0,0), {MP III-5} (2.74, 1.95, 0,0,0), {MP V-1} (1.34, 4.56, 0,0,0), {MP V-3}
 * (0.64, 12.57, 0,0,0), {MP V-4} (1.40, 13.37, 14.37, 0,0), {MP V-5} (1.84,
 * 12.73, 13.19, 0,0), {DP I-4} (3.98, 1.45, 0,0,0), {DP III-1} (0.85, 4.86,
 * 0,0,0), {DP III-3} (1.74, 12.90, 13.38, 0,0), {DP III-4} (3.23, 1.95, 0,0,0),
 * {DP V-1} (0.88, 7.16, 0,0,0), {DP V-3} (1.50, 13.01, 13.68, 0,0), {DP V-4}
 * (0.114, -0.042, 0.130, 0,0), {R-2} (0.181, -1.070, 0.188, 0,0), {U-2} (0.111,
 * -0.184, 0.128, 0,0), {MET I-2} (0.079, 0.065, 0.123, 0,0), {MET III-2}
 * (0.091, -0.030, 0.151, 0,0), {MET V-2} (0.128, -0.036, 0.199, 0,0), {PP I-2}
 * (0.078, 0.169, 0.111, 0,0), {PP III-2} (0.094, -0.028, 0.191, 0,0), {PP V-2}
 * (0.094, -0.040, 0.124, 0,0), {MP III-2} (0.088, -0.137, 0.149, 0,0), {MP V-2}
 * (0.140, 0.136, 0.257, 0,0), {DP I-2} (0.175, -0.149, 0.205, 0,0), {DP III-2}
 * (0.203, -0.345, 0.294, 0,0)); {DP V-2}
 * 
 * 
 * /* {REM***lower age boundaries, male, then female,knee*} Y : array[1..N,1..2]
 * of real = ((0,0),(1,0.75),(1,0.75),(0,0),(0,0),(5.5,3),(5.5,4),(1,0.75),
 * (5,4.5),(8,7.5),(11,11),(11,9),
 * (0,0),(4.5,3.5),(0,0),(0,0),(5,3),(1,1),(2.5,1.5),(7,7),
 * (7,7),(9,6),(1,0.75),(2.5,2.5),(10,8.5),(12,9.5),(12,11),
 * (11,11),(3.5,3),(1,0.25),(1,1),(3.5,2),(7,6),(11,10), {REM***lower age
 * boundaries, male, then female,hand*}
 * (0.25,0),(0.5,0.75),(0.5,0.75),(2,2),(10,8.5),
 * (11,9),(13.5,12),(2.5,1.5),(15.5,13.5),(0,0),
 * (0,0),(0,0),(8,6),(0,0),(2.5,1.5), (4,3),(9.5,7.5),(0,0),(2.5,2),(3,2),
 * (6.5,2.5),(6.5,5),(0,0.25),(4.5,3.5),(2.5,2),
 * (5,2.5),(7,5.5),(1,1.5),(4.5,3.5),(7,5.5),
 * (7,6.5),(8.5,7),(2,1),(4.5,3),(4.5,2.5),
 * (5,3),(5.5,4),(6.5,4.5),(7.5,5.5),(8.5,7.5),(0.75,0.5),
 * (1.5,1.5),(3.5,2),(8.5,7),(10.5,10),(13,10),
 * (0.5,0.25),(5.5,6),(4.5,3.5),(13.5,8.5),(0.5,0.5),
 * (7.5,5),(8,6),(10.5,10),(5.5,3), (1,0),(1.5,1),(6.5,4),(13.5,10.5),(5.5,3.5),
 * (11,8),(0.25,0.25),(0.5,0.5),(6,4),(13.5,10.5),
 * (2.5,0.75),(0,0.5),(0.75,0.5),(8,7),(13,10.5),
 * (0.5,0),(1.5,1.5),(7.5,5),(13,10.5),(0.75,0.25),
 * (2.5,1.5),(8.5,8),(13,10.5),(12,10.5),(0.5,0.25),
 * (2.5,2),(13,10.5),(1,0.75),(3.5,3),(13,10),
 * (0.5,0.5),(7,5.5),(3.5,1.5),(3,2.5),(1.5,1),
 * (2.5,2.5),(2.5,2),(1.5,0.75),(1.5,0.75),(3.5,1),
 * (1,1.5),(2.5,1.5),(2.5,1.5));
 * 
 * {REM***upper age boundaries, male, then female,knee*} Z : array[1..N,1..2] of
 * real = ((6.5,5.5),(6.0,5.5),(5.5,3.5),(2,2),(2.5,2),(18,13),
 * (14,15),(11,11),(17,11),(17,16),(18,18),(18,18),
 * (12,12),(12,12),(1.5,1.5),(2,1),(18,18),(14,16),(14,12),
 * (18,18),(18,18),(18,18),(10,9),(12,9),(17,16), (17,17),(18,18),(18,18),
 * (16,14),(9,7),(9,7),(15,14),(16,14),(18,18), {REM***upper age boundaries,
 * male, then female,hand*} (4.5,3.5),(6.5,4.5),(8,6),(11.5,10.5),(22,22),
 * (22,22),(20,20),(14.5,12),(22,20),(2,1),
 * (3.5,3),(13,10),(22,22),(3.5,3),(10,7.5),
 * (13.5,10.5),(15.5,17.5),(8,7),(13.5,14.5),(13.5,12),
 * (16,12.5),(14.5,10.5),(14,11.5),(15.5,17.5),(12,8.5),
 * (12.5,10.5),(15.5,15),(14,11),(15.5,12.5),(15.5,14),
 * (20,22),(17.5,14),(10,8.5),(12.5,10.5),(16,15.5),
 * (13.5,11.5),(12.5,10),(16.5,15),(15.5,14),(16,13),(7,4),
 * (8,9.5),(13.5,10.5),(22,22),(16.5,20),(20,20),
 * (6.5,5.5),(22,15),(14.5,12.5),(22,18),(4.5,3),
 * (15.5,13),(15.5,15),(20,20),(14.5,12),
 * (5.5,3.5),(10,10),(16.5,14),(20,16.5),(15,12.5),
 * (16.5,14.5),(3,2),(8,7),(14,14.5),(22,16.5),
 * (14.5,11.5),(3.5,3),(4.5,3.5),(16.5,14.5),(20,17.5),
 * (4,3),(10.5,10.5),(22,15.5),(20,16.5),(7,4.5),
 * (10,8.5),(22,22),(22,20),(18,15.5),(5,4),
 * (13,11.5),(20,17),(6,4.5),(13.5,12),(22,16.5),
 * (12.5,12.5),(13.5,12),(13,13),(13.5,13),(16,15),
 * (13,11),(11.5,12),(14.5,14),(13.5,12),(14,11), (12,11),(10.5,10),(11,10.5));
 * 
 */