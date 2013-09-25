/**
 * 
 */
package stockmarket;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class holds the stocks, economic indicators and the 
 * player's portfolios. The Market begins by giving the Players the training
 * data and asking them to learn the stocks. Every subsequent round it asks 
 * the player to trade and completes the buy or sell. 
 */
public class Market {
	private ArrayList<Stock> stocks;
	private ArrayList<EconomicIndicator> indicators;
	private ArrayList<Player> players;
	private HashMap <Player, Portfolio> portfolios;
	
	public Market(ArrayList<Stock> _stocks, ArrayList<EconomicIndicator> _indicators, 
				ArrayList<Player> _players, double startingCapital){
		stocks = _stocks;
		indicators = _indicators;
		players = _players;
		portfolios = new HashMap <Player, Portfolio>();
		for (Player player : players){
			portfolios.put(player, new Portfolio(startingCapital));
		}
	}

	/**
	 * @param indicators2
	 */
	public void newRound(int round) {
		for (Player player : players){
			Trade trade = player.placeTrade(round, indicators, stocks);
			if (trade == null){
				continue;
			}
			if(trade.type == -1){
				portfolios.get(player).sellStock(trade.stock, trade.quantity);
			}
			if(trade.type == 1){
				portfolios.get(player).buyStock(trade.stock, trade.quantity);
			}
		}
	}

	public Boolean allNotBankrupt(){
		for (Portfolio portfolio : portfolios.values()){
			if (portfolio.monetaryValue() > 0) return false;
		}
		return true;
	}

	/**
	 * 
	 */
	public void printPorfolios() {
		for (Player player : players){
			System.out.println(player.name + "final portfolio:");
			System.out.print(portfolios.get(player));
		}
	}
	
}