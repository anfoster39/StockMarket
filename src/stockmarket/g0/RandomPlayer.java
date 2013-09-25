/**
 * 
 */
package stockmarket.g0;

import java.util.ArrayList;
import java.util.Random;

import stockmarket.sim.EconomicIndicator;
import stockmarket.sim.Stock;
import stockmarket.sim.Trade;

/**
 * @author Anne
 *
 */
public class RandomPlayer extends stockmarket.sim.Player{
	static final String name = "Random Player";
	private Random random = new Random();
	
	/* (non-Javadoc)
	 * @see stockmarket.sim.Player#learnStocks(java.util.ArrayList, java.util.ArrayList)
	 */
	@Override
	public void learnStocks(ArrayList<EconomicIndicator> indicators,
			ArrayList<Stock> stocks) {
		
	}

	/* (non-Javadoc)
	 * @see stockmarket.sim.Player#placeTrade(int, java.util.ArrayList, java.util.ArrayList)
	 */
	@Override
	public Trade placeTrade(int currentRound,
			ArrayList<EconomicIndicator> indicators, ArrayList<Stock> stocks) {
		Stock stockToTrade = stocks.get(Math.abs(random.nextInt()%10));
		int tradeAmount = Math.abs(random.nextInt()%100);
		int type = BUY;
		if(Math.abs(random.nextInt() %1) > 0){
			type = SELL;
		}
		return new Trade(type, stockToTrade, tradeAmount);
		
	}

	
	
	
}
