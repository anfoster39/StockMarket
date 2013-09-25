/**
 * 
 */
package stockmarket.sim;

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
public abstract class Player {
	public static final int BUY    = 1;
	public static final int SELL   = -1;
	public String name;

	public abstract void learnStocks(ArrayList<EconomicIndicator> indicators,
			ArrayList<Stock> stocks);

	public abstract Trade placeTrade(int currentRound, ArrayList<EconomicIndicator> indicators,
			ArrayList<Stock> stocks);
	
}
