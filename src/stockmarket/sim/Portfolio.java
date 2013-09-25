/**
 * 
 */
package stockmarket.sim;

import java.util.HashMap;
import java.util.Set;

/**
 * This class holds a player's current capital and their stocks
 *
 */
public class Portfolio {
	private double capital;
	private HashMap <Stock, Integer> stocks;
	private double startingMoney; 
	
	public Portfolio (double startingCapital){
		capital = startingCapital;
		startingMoney = startingCapital;
		stocks = new HashMap <Stock, Integer>(); 
		
	}
	
	/**
	 * Calculates the monetary value of the portfolio
	 * @return the monetary value of the portfolio
	 */
	public double monetaryValue(){
		double value = capital;
		for (Stock stock : stocks.keySet()){
			value += stock.getPrice() * stocks.get(stock);
		}
		return value;
	}

	/**
	 * Buys a certain amount of of the selected stock
	 * if there is sufficient capital to buy it
	 * @param stock The stock to buy
	 * @param amount The amount of the stock to buy
	 * @return If the buy went through
	 */
	public boolean buyStock(Stock stock, int amount){
		if(capital < stock.getPrice() * amount) {
			System.out.println("Trade to Buy " + stock.getName() + " failed: You do nothave enough money");
			return false;
		}
		capital -= stock.getPrice() * amount;
		if (stocks.containsKey(stock)){
			int oldAmount = stocks.get(stock);
			stocks.put(stock, amount+oldAmount);
		}
		else{
			stocks.put(stock, amount);
		}
		return true;
	}
	
	/**
	 * Sells a certain amount of the stock and updates the amount of capital
	 * if the portfolio contains the stock and has enough to sell. 
	 * @param stock Stock to sell
	 * @param amount Amount of stock to sell
	 * @return whether the stock has been sold
	 */
	public boolean sellStock(Stock stock, int amount){
		if (!stocks.containsKey(stock)){
			System.out.println("Trade to Sell " + stock.getName() + " failed: You do not own any stock in this company");
			return false;
		}
		int holding = stocks.get(stock);
		if (holding < amount){
			System.out.println("Trade to Sell " + stock.getName() + " failed: You do not own enough stock");
			return false;
		}
		stocks.put(stock, holding - amount);
		capital += (stock.getPrice() * amount);
		return true;
	}
	
	/**
	 * Returns the amount of stock owned by the portfolio
	 * or NUll if portfolio does not contain stock
	 * @param stock to check
	 * @return the number of stocks owned by the portfolio
	 */
	public int getSharesOwned(Stock stock){
		return stocks.get(stock);
	}
	
	public Set<Stock> getAllStocks (){
		return stocks.keySet();
	}
	
	
	@Override
	public String toString(){
		String portfolioString =  "Capital: " + capital + "\n" + 
				"Stocks:\n";
		for (Stock stock : stocks.keySet()){
			portfolioString += stock.getName() + ", " + stocks.get(stock) + " Shares\n";
		}
		portfolioString += "Monetary Value: " + monetaryValue();
		portfolioString += "\n Profit / Loss: " + startingMoney;
		return portfolioString;
	}
	
}
	

