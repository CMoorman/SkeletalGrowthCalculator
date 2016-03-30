package main;

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
import java.util.List;

/**
 * Contains information about the indicator of a method and a static method to
 * load indicators from a given filePath which corresponds to a .csv file
 * 
 * @author bdecker
 *
 */
public class Indicator {
	private String name = "";
	private String description = "";
	private double maleStartRange = 0;
	private double maleEndRange = 0;
	private double femaleStartRange = 0;
	private double femaleEndRange = 0;
	private int maximumValue = 0;

	private String indicatorValue = "";

	public Indicator(String name, String description, double maleStartRange, double maleEndRange,
			double femaleStartRange, double femaleEndRange, int max) {
		this.name = name;
		this.description = description;
		this.maleStartRange = maleStartRange;
		this.maleEndRange = maleEndRange;
		this.femaleStartRange = femaleStartRange;
		this.femaleEndRange = femaleEndRange;
		this.maximumValue = max;
	}

	public Indicator(String name, String description, double maleStartRange, double maleEndRange,
			double femaleStartRange, double femaleEndRange) {
		this.name = name;
		this.description = description;
		this.maleStartRange = maleStartRange;
		this.maleEndRange = maleEndRange;
		this.femaleStartRange = femaleStartRange;
		this.femaleEndRange = femaleEndRange;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getMaleStartRange() {
		return maleStartRange;
	}

	public void setMaleStartRange(double maleStartRange) {
		this.maleStartRange = maleStartRange;
	}

	public double getMaleEndRange() {
		return maleEndRange;
	}

	public void setMaleEndRange(double maleEndRange) {
		this.maleEndRange = maleEndRange;
	}

	public double getFemaleStartRange() {
		return femaleStartRange;
	}

	public void setFemaleStartRange(double femaleStartRange) {
		this.femaleStartRange = femaleStartRange;
	}

	public double getFemaleEndRange() {
		return femaleEndRange;
	}

	public void setFemaleEndRange(double femaleEndRange) {
		this.femaleEndRange = femaleEndRange;
	}

	public String getIndicatorValue() {
		return indicatorValue;
	}

	public void setIndicatorValue(String indicatorValue) {
		this.indicatorValue = indicatorValue;
	}

	public int getMaximumValue() {
		return maximumValue;
	}

	public void setMaximumValue(int maximumValue) {
		this.maximumValue = maximumValue;
	}

	public static List<Indicator> loadIndicators(String filePath) {
		List<Indicator> indicators = new ArrayList<Indicator>();
		if (filePath == null) {
			return indicators;
		}
		URL url = Indicator.class.getResource(filePath);
		if (url == null) {
			return indicators;
		}
		File inputFile = new File(url.getPath());
		if (!inputFile.exists()) {
			System.out.println("File does not exist");
			return indicators;
		}
		InputStream fileStream = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			fileStream = new FileInputStream(url.getPath());
			isr = new InputStreamReader(fileStream, Charset.forName("UTF-8"));
			br = new BufferedReader(isr);
			String line = "";
			while ((line = br.readLine()) != null) {
				String[] indicatorFields = line.split(",");
				if (indicatorFields != null && indicatorFields.length == 7) {
					String name = indicatorFields[0];
					String description = indicatorFields[1];
					double maleStart = Double.parseDouble(indicatorFields[2]);
					double maleEnd = Double.parseDouble(indicatorFields[3]);
					double femaleStart = Double.parseDouble(indicatorFields[4]);
					double femaleEnd = Double.parseDouble(indicatorFields[5]);
					int maximum = Integer.parseInt(indicatorFields[6]);
					indicators
							.add(new Indicator(name, description, maleStart, maleEnd, femaleStart, femaleEnd, maximum));
				} else if (indicatorFields.length == 6) {
					String name = indicatorFields[0];
					String description = indicatorFields[1];
					double maleStart = Double.parseDouble(indicatorFields[2]);
					double maleEnd = Double.parseDouble(indicatorFields[3]);
					double femaleStart = Double.parseDouble(indicatorFields[4]);
					double femaleEnd = Double.parseDouble(indicatorFields[5]);
					indicators.add(new Indicator(name, description, maleStart, maleEnd, femaleStart, femaleEnd));
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("File could not be found at path: " + filePath);
			return indicators;
		} catch (IOException e) {
			System.out.println("IO Exception " + filePath);
			return indicators;
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
		return indicators;
	}
}