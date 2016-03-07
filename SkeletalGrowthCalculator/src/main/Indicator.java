package main;
/**
 * Contains information about the indicator of a method
 * @author bdecker
 *
 */
public class Indicator {
	private String name = "";
	private String description ="";
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
	
}