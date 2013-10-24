/**
 * 
 */
package stockmarket.sim;

import java.util.HashMap;

/**
 * @author Anne
 *
 */
public class EconomicIndicator {
	private String name;
	private double value;
	private HashMap<Integer, Double> history;
	
	public EconomicIndicator(String name, double initialValue){
		this.name = name;
		value = initialValue;
		history = new HashMap<Integer, Double>();
		history.put(0, initialValue);
	}
	
	public EconomicIndicator(String _name, double currentValue, HashMap<Integer, Double> history){
		name = _name;
		this.value = currentValue;
		this.history = history;
	}
	
	public void updateValue (Integer round, double newValue){
		value = newValue;
		history.put(round, newValue);
	}
	
	public double getValue(){
		return value;
	}
	
	public double getValueAtRound(int round){
		return history.get(round);
	}
	
	public HashMap<Integer, Double> getHistory(){
		return history;
	}
	
	public String getName (){
		return name;
	}
	
	@Override
	public String toString(){
		return "Economic Indicator " + name + ", Current Value: " + value; 
	}

	public EconomicIndicator copy(){
		HashMap<Integer, Double> copyHistory = new HashMap<Integer, Double>();
		for (Integer round : history.keySet()){
			copyHistory.put(round, history.get(round));
		}
		return new EconomicIndicator(name, value, copyHistory);
	}
	
	
}

