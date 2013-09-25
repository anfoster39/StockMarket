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
	
	public EconomicIndicator(String _name, double initialValue){
		name = _name;
		value = initialValue;
	}
	
	public void updateValue (Integer round, double newValue){
		value = newValue;
		history.put(round, newValue);
	}
	
	public double getValue(){
		return value;
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

}
