/**
 * 
 */
package stockmarket.sim;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.lang.Math;


/**
 * this class generates the economic indicators and stock price
 *   each round. It will first create the test data for the player to learn by
 *   generating a number of rounds of economic indicators and stock prices.
 *   It will then go into live mode where it notifies the Market it is a new 
 *   round and give it the indicators whereafter the market will get the player
 *   trades. The Simulator will then update the price of each stock based on a 
 *   secret formula based on the economic indicators. This continues until the 
 *   the round limit is hit or Player goes bankrupt. At the end of the game the 
 *   Simulator prints out the score.
 *
 */
public class Simulator {

    private static Random           random;
	private double 					STARTING_CAPITAL				=100000;
	public int 						NUM_STOCKS						=10;
    private static final int        MAX_ROUNDS     					=500;
	private static final int		TRAINING_ROUNDS					=25;
	private static final double		PROB_NO_INFLUANCE	  		 	=.2;
	private static final double		RANDOM_STOCK_VARIATIONLIMIT 	=.2;
	public static final int			STOCK_MAX_PRICE					= 500;
	public static final int			STOCK_MIN_PRICE					= 10;

	public HashMap <Stock, ArrayList<Double>> calculateStocks; 
	public ArrayList<EconomicIndicator> indicators;
	public ArrayList<Stock> stocks;
	public ArrayList<Player> players;
	public ArrayList<String> stockNames;
	public Market market;
	
	public Simulator(){
		random = new Random();
		stockNames = new ArrayList<String>();
	}
	
	public void run(){
		int round = 0;
		indicators = new ArrayList<EconomicIndicator> ();
		stocks = new ArrayList<Stock>();
		players = new ArrayList<Player>();
		calculateStocks = new HashMap <Stock, ArrayList<Double>>();
		
		createIndicators();
		createStocks();
		initializePlayers();
		
		market = new Market(players, STARTING_CAPITAL);
		
		for (round = 0; round < TRAINING_ROUNDS; round++){
			updateIndicators(round);
			updateStockPrice(round);
		}
		
		for(Player player : players){
			player.learnStocks(indicators, stocks);
		}
		
		while (market.allNotBankrupt() && round <= MAX_ROUNDS){
			updateIndicators(round);
			market.newRound(round, indicators, stocks);
			updateStockPrice(round);
			round++;
		}
		
		printResults();
		
	}
	
	/**
	 * 
	 */
	private void printResults() {
		market.printPorfolios();
	}

	/**
	 * 
	 */
	private void initializePlayers() {
		players.add(new stockmarket.g0.RandomPlayer());
		
	}

	public void createIndicators(){
		//TODO Get from File
		indicators.add(new EconomicIndicator("Unemployment", 5));
		indicators.add(new EconomicIndicator("GDP", 13.263));
		indicators.add(new EconomicIndicator("Inflation", 2.01));
		indicators.add(new EconomicIndicator("Exports", 3.52));
		indicators.add(new EconomicIndicator("Imports", 6.93));
	}

	/**
	 * Creates the formula that determines each stocks's price
	 * 
	 */
	public void createStocks(){
		//does not check case where all coefficients are 0 
		for (int i = 0; i < NUM_STOCKS; i++){
			stocks.add(new Stock (createStockName(), getRandomBetween(STOCK_MIN_PRICE, STOCK_MAX_PRICE)));
			ArrayList<Double> formula = new ArrayList<Double>();
			for (int k = 0; k < indicators.size(); k++){
				formula.add(generateCoefficient());
			}
			calculateStocks.put(stocks.get(i), formula);
		}
	}
	
	/**
	 * Generates 3 letter stock names and checks for duplicate names 
	 */
	public String createStockName(){
		String name = "" + (char)('A' + Math.abs(random.nextInt() % 25)) +					
						   (char)('A' + Math.abs(random.nextInt() % 25)) + 
						   (char)('A' + Math.abs(random.nextInt() % 25));
		if(!stockNames.contains(name)){
			stockNames.add(name);
			return name;
		}
		else return createStockName();
	}
	
	/**
	 * Creates the coefficient for a stock equation
	 * All coefficients are between -1 and 1 and have a
	 * certain percentage change of being 0
	 * @return
	 */
	public double generateCoefficient(){
		if (random.nextDouble() < PROB_NO_INFLUANCE) return 0.0; 
		int coefficient = 1; 
		if(random.nextBoolean()) coefficient = -1;
		return (coefficient * random.nextDouble()); 
	}
	
	/**
	 * Randomly changes the values of the indicators
	 * @param round
	 */
	public void updateIndicators(int round){
		for(EconomicIndicator indicator: indicators){
			indicator.updateValue(round, indicator.getValue()*(1 + random.nextDouble()) );
		}
	}
	
	/**
	 * Updates each stock price
	 * @param round
	 */
	public void updateStockPrice(int round){
		for (Stock stock: stocks){
			stock.updatePrice(round, updateStockPrice(stock));
		}
	}
	
	/**
	 * Calculates the new stock price given the current indicators and 
	 * the stock's formula
	 * @param stock
	 * @return
	 */
	private double updateStockPrice(Stock stock){
		double newPrice = stock.getPrice();
		newPrice += RANDOM_STOCK_VARIATIONLIMIT * random.nextDouble();
		//change from indicators
		for (int i = 0; i < indicators.size(); i++){
			newPrice += indicators.get(i).getValue() * calculateStocks.get(stock).get(i);
		}
		return newPrice;
	}
	
	private double getRandomBetween(int lowerBound, int upperBound){
		return random.nextDouble()*(upperBound-lowerBound) + lowerBound; 
	}
		
}
