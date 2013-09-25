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

	private ArrayList<Player> players;
	private HashMap <Player, Portfolio> portfolios;
	
	public Market(ArrayList<Player> players, double startingCapital){
		this.players = players;
		portfolios = new HashMap <Player, Portfolio>();
		for (Player player : players){
			portfolios.put(player, new Portfolio(startingCapital));
			System.out.println("added a portfolio for " + player.name);
		}
	}

	/**
	 * @param indicators2
	 */
	public void newRound(int round, ArrayList<EconomicIndicator> indicators, ArrayList<Stock> stocks) {
		for (Player player : players){
			ArrayList<Trade> trades = player.placeTrade(round, indicators, stocks);
			for (Trade trade : trades){
				if(trade.type == SELL){
					portfolios.get(player).sellStock(trade.stock, trade.quantity);
				}
				if(trade.type == BUY){
					portfolios.get(player).buyStock(trade.stock, trade.quantity);
				}
			}
			
			System.out.println(player.name + " Portfolio: ");
			System.out.print(portfolios.get(player));
		}
	}

	public Boolean allNotBankrupt(){
		for (Portfolio portfolio : portfolios.values()){
			if (portfolio.monetaryValue() > 0) return false;
		}
		return true;
	}

	/**
	 * Prints all portfolios
	 */
	public void printPorfolios() {
		for (Player player : players){
			System.out.println(player.name + "final portfolio:");
			System.out.print(portfolios.get(player));
		}
	}
	
}