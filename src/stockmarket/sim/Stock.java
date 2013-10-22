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
	private HashMap<Integer, Double> priceHistory;

	public Stock (String _name, double initialPrice){
		name = _name;
		priceHistory = new HashMap<Integer, Double>(); 
		price = initialPrice;
		priceHistory.put(0, initialPrice);
	}
	
	public Stock (String _name, double currentPrice, HashMap<Integer, Double> history){
		name = _name;
		priceHistory = history; 
		price = currentPrice;
	}
	
	public void updatePrice (Integer round, double newPrice){
		price = newPrice;
		priceHistory.put(round, newPrice);
	}
	
	public double getPrice(){
		return price;
	}
	
	public double getPriceAtRound(int round){
		return priceHistory.get(round);
	}
	
	public HashMap<Integer, Double> getPriceHistory(){
		return priceHistory;
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
		for (Integer round : priceHistory.keySet()){
			copyHistory.put(round, priceHistory.get(round));
		}
		return new Stock(name, price, copyHistory);
	}
}
