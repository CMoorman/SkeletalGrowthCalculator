package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains information about the indicator of a method and a static method
 * to load indicators from a given filePath which corresponds to a .csv file
 * 
 * @author bdecker
 *
 */
public class Indicator {
	private String name = "";
	private String description = "";
	private int maleStartRange = 0;
	private int maleEndRange = 0;
	private int femaleStartRange = 0;
	private int femaleEndRange = 0;

	public Indicator(String name, String description, int maleStartRange, int maleEndRange, int femaleStartRange,
			int femaleEndRange) {
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

	public int getMaleStartRange() {
		return maleStartRange;
	}

	public void setMaleStartRange(int maleStartRange) {
		this.maleStartRange = maleStartRange;
	}

	public int getMaleEndRange() {
		return maleEndRange;
	}

	public void setMaleEndRange(int maleEndRange) {
		this.maleEndRange = maleEndRange;
	}

	public int getFemaleStartRange() {
		return femaleStartRange;
	}

	public void setFemaleStartRange(int femaleStartRange) {
		this.femaleStartRange = femaleStartRange;
	}

	public int getFemaleEndRange() {
		return femaleEndRange;
	}

	public void setFemaleEndRange(int femaleEndRange) {
		this.femaleEndRange = femaleEndRange;
	}

	public static List<Indicator> loadIndicators(String filePath) {
		List<Indicator> indicators = new ArrayList<Indicator>();
		if (filePath == null) {
			return indicators;
		}
		File inputFile = new File(filePath);
		if (!inputFile.exists()) {
			System.out.println("File does not exist");
			return indicators;
		}
		InputStream fileStream = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			fileStream = new FileInputStream(filePath);
			isr = new InputStreamReader(fileStream, Charset.forName("UTF-8"));
			br = new BufferedReader(isr);
			String line = "";
			while ((line = br.readLine()) != null) {
		       String[] indicatorFields = line.split(",");
		       if(indicatorFields.length == 6){
		    	   String name = indicatorFields[0];
		    	   String description = indicatorFields[1];
		    	   int maleStart = Integer.parseInt(indicatorFields[2]);
		    	   int maleEnd = Integer.parseInt(indicatorFields[3]);
		    	   int femaleStart = Integer.parseInt(indicatorFields[4]);
		    	   int femaleEnd = Integer.parseInt(indicatorFields[3]);
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
				if(fileStream != null){
					fileStream.close();
				}
				if(br != null){
					br.close();
				}
			} catch (IOException e) {
			}
		}
		return indicators;
	}
}