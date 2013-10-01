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
		history = new HashMap<Integer, Double>();
		history.put(0, initialValue);
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

}
