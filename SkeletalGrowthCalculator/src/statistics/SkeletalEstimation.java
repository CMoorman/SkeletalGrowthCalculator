/*
 * 
 */
package statistics;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class SkeletalEstimation {
	protected List<Double> inputList = new ArrayList<Double>();
	public abstract void setInputList(Map<String, String> inputMap);
	public abstract double performEstimation();
	public abstract void loadData();
	public abstract void setAge(double age);
}
