package main;

import java.util.List;
import java.util.ArrayList;

public class SkeletalMaturityMethod {
	private String methodName = "";
	private List<Indicator> indicators = new ArrayList<Indicator>();
	private String inputFilePath = "";
	public SkeletalMaturityMethod(String methodName, String inputFilePath){
		this.methodName = methodName;
		this.inputFilePath = inputFilePath;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public List<Indicator> getIndicators() {
		return indicators;
	}
	public void addIndicator(Indicator indicator){
		indicators.add(indicator);
	}
	public void addIndicators(List<Indicator> indicators){
		this.indicators.addAll(indicators);
	}
	public String getInputFilePath() {
		return inputFilePath;
	}
	public void setInputFilePath(String inputFilePath) {
		this.inputFilePath = inputFilePath;
	}
	
}
