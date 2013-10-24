/**
 * 
 */
package stockmarket.sim;

import java.util.HashMap;

/**
 * This class holds the stock price and historical prices based on round
 *
 */
public class Stock {
	private String name;
	private double price;
	private HashMap<Integer, Double> history;

	public Stock (String _name, double initialPrice){
		name = _name;
		history = new HashMap<Integer, Double>(); 
		price = initialPrice;
		history.put(0, initialPrice);
	}
	
	public Stock (String _name, double currentPrice, HashMap<Integer, Double> history){
		name = _name;
		this.history = history; 
		price = currentPrice;
	}
	
	public void updatePrice (Integer round, double newPrice){
		price = newPrice;
		history.put(round, newPrice);
	}
	
	public double getPrice(){
		return price;
	}
	
	public double getPriceAtRound(int round){
		return history.get(round);
	}
	
	public HashMap<Integer, Double> getPriceHistory(){
		return history;
	}
	
	public String getName (){
		return name;
	}
	
	@Override
	public String toString(){
		return "Stock " + name + ", Current Price: " + price; 
	}
	
	public Stock copy(){
		HashMap<Integer, Double> copyHistory = new HashMap<Integer, Double>();
		for (Integer round : history.keySet()){
			copyHistory.put(round, history.get(round));
		}
		return new Stock(name, price, copyHistory);
	}
}
