/**
 * 
 */
package stockmarket;

import java.util.ArrayList;

/**
 * has two methods, Learn and Trade. The Learn method takes in the
 *   starting historical data of the economic indicators and the stocks for the 
 *   Player to build a model. The Trade method applies the stock model to the current
 *   Economic Indicators and decides to buy or sell stocks and how much. It then tells
 *   the market to place the trade by returning a list of trades for the market to 
 *   process. 
 *
 */
public class Player {
	String name;

	/**
	 * @param indicators
	 * @param stocks
	 */
	public void learnStocks(ArrayList<EconomicIndicator> indicators,
			ArrayList<Stock> stocks) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @param indicators
	 * @param stocks
	 * @return
	 */
	public Trade placeTrade(int currentRound, ArrayList<EconomicIndicator> indicators,
			ArrayList<Stock> stocks) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	//train method
	//play method
	
}
