/**
 * 
 */
package stockmarket.sim;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class holds the stocks, economic indicators and the 
 * player's portfolios. The Market begins by giving the Players the training
 * data and asking them to learn the stocks. Every subsequent round it asks 
 * the player to trade and completes the buy or sell. 
 */
public class Market {
	public static final int BUY    = 1;
	public static final int SELL   = -1;

	public ArrayList<Player> players;
	public HashMap <Player, Portfolio> portfolios;
	
	public Market(ArrayList<Player> _players, double startingCapital){
		players = _players;
		portfolios = new HashMap <Player, Portfolio>();
		for (Player player : players){
			portfolios.put(player, new Portfolio(startingCapital));
			System.out.println("added a portfolio for " + player.getName());
		}
	}

	/**
	 * @param indicators2
	 */
	public void newRound(int round, ArrayList<EconomicIndicator> indicators, ArrayList<Stock> stocks) {
		for (Player player : players){
			ArrayList<Trade> trades = player.placeTrade(round, indicators, stocks);
			for (Trade trade : trades){
				if(trade.getType() == SELL){
					portfolios.get(player).sellStock(trade.getStock(), trade.getQuantity());
				}
				if(trade.getType() == BUY){
					portfolios.get(player).buyStock(trade.getStock(), trade.getQuantity());
				}
			}
		}
	}
	

	public Boolean allBankrupt(){
		for (Portfolio portfolio : portfolios.values()){
			if (portfolio.getMonetaryValue() > 0) return false;
		}
		return true;
	}

	/**
	 * Prints all portfolios
	 */
	public void printPorfolios() {
		for (Player player : players){
			System.out.println("\n" + player.name );
			System.out.print(portfolios.get(player));
		}
	}
	
}